package kr.ac.kopo.lego_guestbook.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.kopo.lego_guestbook.entity.Board;
import kr.ac.kopo.lego_guestbook.entity.QBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

public class SearchBoardRepositoryImpl implements SearchBoardRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final JPAQueryFactory queryFactory;

    public SearchBoardRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (types != null && types.length > 0) {
            for (String type : types) {
                switch (type) {
                    case "t":
                        booleanBuilder.or(board.title.containsIgnoreCase(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.containsIgnoreCase(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.containsIgnoreCase(keyword));
                        break;
                }
            }
        }

        List<Board> results = queryFactory.selectFrom(board)
                .where(booleanBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(board)
                .where(booleanBuilder)
                .fetchCount();

        return new PageImpl<>(results, pageable, total);
    }
}

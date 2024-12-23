package kr.ac.kopo.lego_guestbook.repository;

import kr.ac.kopo.lego_guestbook.entity.Board;
import kr.ac.kopo.lego_guestbook.repository.search.SearchBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {

    @Query("select m, (select mi from LEGOImage mi where mi.board = m order by mi.inum asc limit 1), avg(coalesce(r.grade, 0)), count(r) " +
            "from Board m " +
            "left join Review r on r.board = m " +
            "group by m")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(r) " +
            "from Board m " +
            "left join LEGOImage mi on mi.board = m " +
            "left join Review r on r.board = m " +
            "where m.bno = :bno " +
            "group by m, mi")
    List<Object[]> getBoardWithAll(Long bno);

//    @Query("select b, w from Board b left join b.writer w where b.bno =:bno")
//    Object getBoardWithWriter(@Param("bno") Long bno);
//
//    @Query("select b, r from Board b left join Reply r ON r.board = b where b.bno =:bno")
//    List<Object[]> getBoardWithReply(@Param("bno") Long bno);
//
//    @Query(value = "SELECT b, w, count(r) from Board b "
//            + "left join b.writer w "
//            + "left join Reply r on r.board = b "
//            + "group by b, w",
//            countQuery = "select count(b) from Board b")
//    Page<Object[]> getBoardWithReplyCount(Pageable pageable);
//
//    @Query("select b, w, count(r) "
//            + "from Board b left join b.writer w "
//            + "left outer join Reply r on r.board=b "
//            + "where b.bno=:bno group by b, w")
//    Object getBoardByBno(@Param("bno")Long bno);
}

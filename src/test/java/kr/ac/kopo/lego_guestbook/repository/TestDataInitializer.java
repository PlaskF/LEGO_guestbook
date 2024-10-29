package kr.ac.kopo.lego_guestbook.repository;

import kr.ac.kopo.lego_guestbook.entity.Board;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestDataInitializer implements CommandLineRunner {

    private final BoardRepository boardRepository;

    public TestDataInitializer(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 기존 데이터 삭제
        boardRepository.deleteAll();

        // 테스트 데이터 생성
        for (int i = 1; i <= 150; i++) {
            Board board = Board.builder()
                    .title("테스트 제목 " + i)
                    .content("이것은 테스트 내용입니다. 게시물 번호: " + i)
                    .writer("작성자 " + i)
                    .build();

            boardRepository.save(board);
        }

        System.out.println("Test data inserted into the database.");
    }
}
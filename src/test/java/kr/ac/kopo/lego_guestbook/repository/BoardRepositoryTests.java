package kr.ac.kopo.lego_guestbook.repository;


import kr.ac.kopo.lego_guestbook.entity.Board;
import kr.ac.kopo.lego_guestbook.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Board board = Board.builder()
                    .title("Title " + i)
                    .content("Content " + i)
                    .writer("user" + i + "@kopo.kr")
                    .build();

            boardRepository.save(board);
        });
    }
}
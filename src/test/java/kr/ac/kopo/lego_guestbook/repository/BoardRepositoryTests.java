package kr.ac.kopo.lego_guestbook.repository;


import kr.ac.kopo.lego_guestbook.entity.Board;
import kr.ac.kopo.lego_guestbook.entity.LEGOImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private LEGOImageRepository legoImageRepository;

    @Test
    public void insertBoard() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            // 임의의 게시번호
            long mid = (long)(Math.random()*100) + 1;


            Board board = Board.builder()
                    .title("Title " + i)
                    .content("Content " + i)
                    .writer("reviewer" + mid)
                    .build();

            boardRepository.save(board);

            int count = (int)(Math.random() * 5) + 1;

            for(int j = 0; j < count; j++) {
                LEGOImage legoImage = LEGOImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .imgName("test"+j+".jpg")
                        .board(board)
                        .build();

                legoImageRepository.save(legoImage);
            }
        });
    }
}
package kr.ac.kopo.lego_guestbook.repository;

import static org.junit.jupiter.api.Assertions.*;

import kr.ac.kopo.lego_guestbook.entity.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
                    .writer("Writer " + i)
                    .build();

            boardRepository.save(board);
        });
    }

//    @Transactional
//    @Test
//    public void testRead() {
//        Optional<Board> result = boardRepository.findById(5L);
//        Board board = result.get();
//
//        System.out.println(board);
//        System.out.println(board.getWriter());
//    }

//    @Test
//    public void testReadWithWriter() {
//        Object result = boardRepository.getBoardWithWriter(10L);
//        Object[] arr = (Object[]) result;
//        System.out.println(Arrays.toString(arr));
//    }
//
//    @Test
//    public void testReadWithReply() {
//        List<Object[]> result = boardRepository.getBoardWithReply(77L);
//        for (Object[] arr: result) {
//            System.out.println(Arrays.toString(arr));
//        }
//    }
//
//    @Test
//    public void testGetBoardWithReplyCount(){
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
//        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
//
//        result.get().forEach(row ->{
//            Object[] arr = (Object[]) row;
//            System.out.println(Arrays.toString(arr));
//        });
//    }
//
//    @Test
//    public void testRead3(){
//        Object result = boardRepository.getBoardByBno(99L);
//        Object[] arr = (Object[]) result;
//        System.out.println(Arrays.toString(arr));
//    }
//
//    @Test
//    public void testSearch1(){
//        boardRepository.search1();
//    }
//
//    @Test
//    public void testSearchPage(){
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending().and(Sort.by("title").ascending()));
//        boardRepository.searchPage("t", "1", pageable);
//    }
}
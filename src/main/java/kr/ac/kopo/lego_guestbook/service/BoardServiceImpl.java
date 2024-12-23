package kr.ac.kopo.lego_guestbook.service;

import kr.ac.kopo.lego_guestbook.dto.BoardDTO;
import kr.ac.kopo.lego_guestbook.dto.LEGOImageDTO;
import kr.ac.kopo.lego_guestbook.dto.PageRequestDTO;
import kr.ac.kopo.lego_guestbook.dto.PageResultDTO;
import kr.ac.kopo.lego_guestbook.entity.Board;
import kr.ac.kopo.lego_guestbook.entity.LEGOImage;
import kr.ac.kopo.lego_guestbook.repository.BoardRepository;
import kr.ac.kopo.lego_guestbook.repository.LEGOImageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final LEGOImageRepository imageRepository;

    @Transactional
    @Override
    public Long register(BoardDTO boardDTO) {

        Map<String, Object> entityMap = dtoToEntity(boardDTO);
        Board board = (Board) entityMap.get("board");
        List<LEGOImage> legoImageList = (List<LEGOImage>) entityMap.get("imgList");
        if (legoImageList == null) {
            legoImageList = new ArrayList<>();
        }

        boardRepository.save(board);

        legoImageList.forEach(legoImage -> {
            imageRepository.save(legoImage);
        });

        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getListPage(pageable);

        log.info("==============================================");
        result.getContent().forEach(arr -> {
            log.info(Arrays.toString(arr));
        });

        // 수정된 데이터 매핑 함수
        Function<Object[], BoardDTO> fn = (arr -> {
            Board board = (Board) arr[0];
            List<LEGOImage> legoImageList = new ArrayList<>();

            // arr[1] 값이 null인지 확인하고 안전하게 처리
            if (arr[1] != null && arr[1] instanceof LEGOImage) {
                legoImageList.add((LEGOImage) arr[1]);
            } else {
                log.warn("LEGOImage이 비어있거나 null입니다. arr[1]: {}", arr[1]);
            }

            return entitiesToDTO(board, legoImageList);
        });

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardDTO get(Long bno) {

        List<Object[]> result = boardRepository.getBoardWithAll(bno);

        Board movie = (Board) result.get(0)[0];

        List<LEGOImage> movieImageList = new ArrayList<>();

        result.forEach(arr -> {
            LEGOImage  movieImage = (LEGOImage)arr[1];
            movieImageList.add(movieImage);
        });

        return entitiesToDTO(movie, movieImageList);
    }

    @Override
    public void modify(BoardDTO dto) {
        Optional<Board> result = boardRepository.findById(dto.getBno());
        if (result.isPresent()) {
            Board board = result.get();
            board.changeTitle(dto.getTitle());
            board.changeContent(dto.getContent());
            boardRepository.save(board);
        }
    }

    @Override
    @Transactional
    public void remove(Long bno) {
        // 1. 외래 키로 연결된 자식 데이터를 먼저 삭제
        imageRepository.deleteAllByBoard_Bno(bno);

        // 2. 부모 데이터 삭제
        boardRepository.deleteById(bno);
    }

}

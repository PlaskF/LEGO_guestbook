package kr.ac.kopo.lego_guestbook.service;

import kr.ac.kopo.lego_guestbook.dto.BoardDTO;
import kr.ac.kopo.lego_guestbook.dto.PageRequestDTO;
import kr.ac.kopo.lego_guestbook.dto.PageResultDTO;
import kr.ac.kopo.lego_guestbook.entity.Board;
import kr.ac.kopo.lego_guestbook.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public Long register(BoardDTO dto) {
//        if (dto.getWriter() == null || dto.getWriter().trim().isEmpty()) {
//            throw new IllegalArgumentException("작성자는 필수 입력 사항입니다.");
//        }
//
//        Board board = dtoToEntity(dto);
//        boardRepository.save(board);
//        return board.getBno();
        Board board = dtoToEntity(dto);
        boardRepository.save(board);

        return board.getBno();
    }


    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        Function<Object[], BoardDTO> fn = (en -> entityToDto((Board) en[0]));
        Page<Object[]> result = boardRepository.searchPage(pageRequestDTO.getType(), pageRequestDTO.getKeyword(), pageRequestDTO.getPageable(Sort.by("bno").descending()));
        return new PageResultDTO<>(result, fn);
    }

//    @Override
//    public PageResultDTO<BoardDTO, Board> search(PageRequestDTO requestDTO) {
//        Pageable pageable = requestDTO.getPageable(Sort.by("bno").descending());
//
//        String[] types = requestDTO.getType() != null ? requestDTO.getType().split("") : new String[0];
//        String keyword = requestDTO.getKeyword() != null ? requestDTO.getKeyword() : "";
//
//        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);
//
//        Function<Board, BoardDTO> fn = (entity -> entityToDto(entity));
//
//        return new PageResultDTO<>(result, fn);
//    }

    @Override
    public BoardDTO get(Long bno) {
        Optional<Board> result = boardRepository.findById(bno);
        return result.isPresent() ? entityToDto(result.get()) : null;
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
    public void remove(Long bno) {
        boardRepository.deleteById(bno);
    }

    private Board dtoToEntity(BoardDTO dto) {
        return Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
    }

    private BoardDTO entityToDto(Board board) {
        return BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .build();
    }
}

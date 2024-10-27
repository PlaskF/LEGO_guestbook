package kr.ac.kopo.lego_guestbook.service;

import kr.ac.kopo.lego_guestbook.dto.BoardDTO;
import kr.ac.kopo.lego_guestbook.dto.PageRequestDTO;
import kr.ac.kopo.lego_guestbook.dto.PageResultDTO;
import kr.ac.kopo.lego_guestbook.entity.Board;
import kr.ac.kopo.lego_guestbook.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        if (dto.getWriter() == null || dto.getWriter().trim().isEmpty()) {
            throw new IllegalArgumentException("작성자는 필수 입력 사항입니다.");
        }

        Board board = dtoToEntity(dto);
        boardRepository.save(board);
        return board.getBno();
    }


    @Override
    public List<BoardDTO> getList() {
        return List.of();
    }

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

    @Override
    public PageResultDTO<BoardDTO, Board> search(PageRequestDTO requestDTO) {
        // 요청된 페이지가 1보다 작을 경우 기본값 설정
        int page = requestDTO.getPage() > 0 ? requestDTO.getPage() - 1 : 0;
        int size = requestDTO.getSize() > 0 ? requestDTO.getSize() : 10; // 기본 사이즈 설정

        Pageable pageable = PageRequest.of(page, size);
        String[] types = requestDTO.getType() != null ? requestDTO.getType().split("") : new String[0];
        String keyword = requestDTO.getKeyword() != null ? requestDTO.getKeyword() : "";

        // 레포지토리의 검색 메서드 호출
        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        // 검색 결과를 DTO로 변환하는 함수
        Function<Board, BoardDTO> fn = (entity -> entityToDto(entity));

        // 검색 결과와 변환 함수로 PageResultDTO 생성
        return new PageResultDTO<>(result, fn);
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

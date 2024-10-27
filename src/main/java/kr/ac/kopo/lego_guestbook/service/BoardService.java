package kr.ac.kopo.lego_guestbook.service;

import kr.ac.kopo.lego_guestbook.dto.BoardDTO;
import kr.ac.kopo.lego_guestbook.dto.PageRequestDTO;
import kr.ac.kopo.lego_guestbook.dto.PageResultDTO;
import kr.ac.kopo.lego_guestbook.entity.Board;

import java.util.List;

public interface BoardService {
    Long register(BoardDTO dto);
    List<BoardDTO> getList();
    BoardDTO get(Long id);
    void modify(BoardDTO dto);
    void remove(Long id);
    PageResultDTO<BoardDTO, Board> search(PageRequestDTO requestDTO);
}
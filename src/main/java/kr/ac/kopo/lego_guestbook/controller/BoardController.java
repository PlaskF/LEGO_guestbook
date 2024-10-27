package kr.ac.kopo.lego_guestbook.controller;

import kr.ac.kopo.lego_guestbook.dto.BoardDTO;
import kr.ac.kopo.lego_guestbook.dto.PageRequestDTO;
import kr.ac.kopo.lego_guestbook.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notice")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/list")
    public String list(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", boardService.search(pageRequestDTO));
        return "notice/list";
    }

    @GetMapping("/register")
    public String register() {
        return "notice/register";
    }

    @PostMapping("/register")
    public String registerPost(BoardDTO dto) {
        // dto에 writer가 null이 아닌지 확인
        if (dto.getWriter() == null || dto.getWriter().isEmpty()) {
            throw new IllegalArgumentException("작성자 정보가 누락되었습니다.");
        }

        boardService.register(dto);
        return "redirect:/notice/list";
    }

    @GetMapping("/read")
    public String read(@RequestParam Long bno, Model model) {
        model.addAttribute("dto", boardService.get(bno));
        return "notice/read";
    }

    @GetMapping("/modify")
    public String modifyForm(@RequestParam Long bno, Model model) {
        // 수정할 게시물을 가져와서 모델에 추가
        BoardDTO boardDTO = boardService.get(bno);
        model.addAttribute("dto", boardDTO);
        return "notice/modify"; // 수정 폼 페이지로 이동
    }

    @PostMapping("/modify")
    public String modify(BoardDTO dto) {
        boardService.modify(dto);
        return "redirect:/notice/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam Long bno) {
        boardService.remove(bno);
        return "redirect:/notice/list";
    }
}

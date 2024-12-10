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
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", boardService.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() {
    }

    @PostMapping("/register")
    public String registerPost(BoardDTO dto, RedirectAttributes redirectAttributes) {
        Long bno = boardService.register(dto);

        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/notice/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long bno, Model model) {
        BoardDTO boardDTO = boardService.get(bno);
        model.addAttribute("dto", boardDTO);
    }

    @PostMapping("/modify")
    public String modify(BoardDTO dto, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes) {

        boardService.modify(dto);

        redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
        redirectAttributes.addAttribute("type", pageRequestDTO.getType());
        redirectAttributes.addAttribute("keyword", pageRequestDTO.getKeyword());
        redirectAttributes.addAttribute("bno", dto.getBno());

        return "redirect:/notice/read";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam Long bno) {
        boardService.remove(bno);
        return "redirect:/notice/list";
    }
}

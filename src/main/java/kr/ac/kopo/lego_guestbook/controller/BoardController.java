package kr.ac.kopo.lego_guestbook.controller;

import kr.ac.kopo.lego_guestbook.dto.BoardDTO;
import kr.ac.kopo.lego_guestbook.dto.LEGODTO;
import kr.ac.kopo.lego_guestbook.dto.PageRequestDTO;
import kr.ac.kopo.lego_guestbook.service.BoardService;
import kr.ac.kopo.lego_guestbook.service.LEGOService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notice")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final LEGOService legoService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", boardService.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() {
    }

    @PostMapping("/register")
    public String registerPost(BoardDTO dtoB, LEGODTO dtoL,RedirectAttributes redirectAttributes) {
        Long bno = boardService.register(dtoB);
        Long mno = legoService.register(dtoL);

        redirectAttributes.addFlashAttribute("msg", bno);
        redirectAttributes.addFlashAttribute("mno", mno);

        return "redirect:/notice/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, long bno,
                     @RequestParam(required = false) Long mno, Model model) {
        BoardDTO boardDTO = boardService.get(bno);
        LEGODTO legodto = (mno != null) ? legoService.getLEGO(mno) : null;

        model.addAttribute("dtoB", boardDTO);
        model.addAttribute("dtoL", legodto);
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

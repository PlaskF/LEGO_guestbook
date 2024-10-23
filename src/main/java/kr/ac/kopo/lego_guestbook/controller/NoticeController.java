package kr.ac.kopo.lego_guestbook.controller;

import kr.ac.kopo.lego_guestbook.dto.NoticeDTO;
import kr.ac.kopo.lego_guestbook.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<NoticeDTO> notices = noticeService.getAllNotices();
        model.addAttribute("notices", notices);
        return "notice/list";  // 목록 페이지
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("noticeDTO", new NoticeDTO());
        return "notice/create";  // 공지사항 작성 페이지
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("noticeDTO") NoticeDTO noticeDTO) {
        noticeDTO.setCreatedAt(LocalDateTime.now());
        noticeService.createNotice(noticeDTO);
        return "redirect:/notice/list";  // 등록 후 목록으로 리다이렉트
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        NoticeDTO noticeDTO = noticeService.getNoticeById(id);
        noticeService.incrementViews(id);  // 조회수 증가
        model.addAttribute("notice", noticeDTO);
        return "notice/view";  // 상세 페이지
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        NoticeDTO noticeDTO = noticeService.getNoticeById(id);
        model.addAttribute("noticeDTO", noticeDTO);
        return "notice/edit";  // 수정 페이지
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute("noticeDTO") NoticeDTO noticeDTO) {
        noticeService.updateNotice(id, noticeDTO);
        return "redirect:/notice/view/" + id;  // 수정 후 해당 공지사항 페이지로 리다이렉트
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return "redirect:/notice/list";  // 삭제 후 목록으로 리다이렉트
    }
}

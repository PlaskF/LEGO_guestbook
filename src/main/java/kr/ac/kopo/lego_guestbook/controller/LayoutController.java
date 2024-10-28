package kr.ac.kopo.lego_guestbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LayoutController {

    @GetMapping("/layout/front")
    public String frontPage(Model model) {
        // 필요한 경우 모델에 데이터를 추가
        model.addAttribute("title", "Front Page");
        return "layout/front";
    }

    @GetMapping("/layout/history")
    public String historyPage(Model model) {
        model.addAttribute("title", "History Page");
        return "layout/history";
    }

    @GetMapping("/layout/play")
    public String playPage(Model model) {
        model.addAttribute("title", "Play Page");
        return "layout/play";
    }

    @GetMapping("/layout/item")
    public String itemPage(Model model) {
        model.addAttribute("title", "Item Page");
        return "layout/item";
    }
}
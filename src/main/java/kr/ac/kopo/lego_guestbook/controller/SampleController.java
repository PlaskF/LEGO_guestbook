package kr.ac.kopo.lego_guestbook.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/sample/")
public class SampleController {

    @GetMapping("/all")
    public void exAll() {
        log.info("★ exAll() 메소드 호출");
    }

    @PreAuthorize("isAuthenticated()") // 로그인된 사용자만 허용
    @GetMapping("/member")
    public void exMember() {
        log.info("★★ exMember() 메소드 호출");
    }

    @PreAuthorize("hasRole('ADMIN')") // 관리자 권한을 가진 사용자만 허용
    @GetMapping("/admin")
    public void exAdmin() {
        log.info("★★★ exAdmin() 메소드 호출");
    }
}
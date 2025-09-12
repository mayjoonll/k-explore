package com.my.touristAttraction.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/home.html")
    public String home() {
        return "home";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/isAuthenticated")
    @ResponseBody
    public String isAuthenticated() {
        return "인증한 사용자만 접근 가능";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/adminOnly")
    @ResponseBody
    public String adminOnly() {
        return "관리자 전용 페이지";
    }
}

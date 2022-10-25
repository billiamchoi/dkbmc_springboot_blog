package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    // 사용자가 /index페이지나 / 페이지에 도달했을 경우 질문 목록 페이지로 이동 시켜주는 컨트롤러
    @RequestMapping(value={ "/", "/index"})
    public String index() {
        return "redirect:/question/list";
    }
}

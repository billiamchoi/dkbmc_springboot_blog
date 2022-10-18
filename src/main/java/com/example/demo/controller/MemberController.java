package com.example.demo.controller;


import com.example.demo.domain.member.MemberDTO;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @RequestMapping(value="/login")
    public String loginView(ModelMap model) {
        model.addAttribute("pageTitle", "로그인 페이지");
        
        return "/account/login";
    }

    @RequestMapping(value = "/signup")
    public String signupView(ModelMap model) {
        model.addAttribute("pageTitle", "회원가입 페이지");
        return "/account/signup";
    }

    @GetMapping("/login/result")
    public String LoginResultView(ModelMap model) {
        model.addAttribute("pageTitle", "로그인 성공");
        return "/account/loginSuccess";
    }

    @GetMapping("/logout/result")
    public String LogoutResultView(ModelMap model) {
        model.addAttribute("pageTitle", "로그아웃 성공");
        return "/account/logoutSuccess";
    }

    @PostMapping("/signup")
    public String execSignup(MemberDTO memberDTO) {
        memberService.joinUser(memberDTO);

        return "redirect:/account/login";
    }
}


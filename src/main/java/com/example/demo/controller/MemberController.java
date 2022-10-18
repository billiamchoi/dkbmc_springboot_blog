package com.example.demo.controller;


import com.example.demo.domain.member.MemberDTO;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

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
    public String signupView(MemberDTO memberDTO, ModelMap model) {
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
    public String signup( @Valid MemberDTO memberDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/account/signup";
        }

        if (!memberDTO.getPassword1().equals(memberDTO.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordIncorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "/account/signup";
        }

        try{
            memberService.joinUser(memberDTO);
        }catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "/account/signup";
        }catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "/account/signup";
        }

        return "redirect:/account/login";
    }
}


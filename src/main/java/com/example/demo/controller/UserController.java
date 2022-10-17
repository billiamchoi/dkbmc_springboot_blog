package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value="/login")
    public String loginView() {
        return "/user/login";
    }

    @RequestMapping(value = "/signup")
    public String signupView() {
        return "/user/signup";
    }

    @GetMapping("/login/result")
    public String LoginResultView() {
        return "user/loginSuccess";
    }

    @GetMapping("/logout/result")
    public String LogoutResultView() {
        return "user/logoutSuccess";
    }



}


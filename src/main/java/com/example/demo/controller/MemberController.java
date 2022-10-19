package com.example.demo.controller;


import com.example.demo.domain.member.MemberDTO;
import com.example.demo.domain.member.MemberModifyDTO;
import com.example.demo.service.AccountService;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/account")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private AccountService accountService;

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

    @GetMapping("/info")
    public String myInfoView(Principal principal, ModelMap model) {

        MemberDTO member = accountService.get(principal.getName());
        Long id = member.getId();
        String username = member.getUsername();
        String email = member.getEmail();

        model.addAttribute("pageTitle", "내 정보");
        model.addAttribute("id", id);
        model.addAttribute("username", username);
        model.addAttribute("email", email);

        return "/account/myinfo";
    }

    @GetMapping("/modify")
    public String myInfoModifyView(Principal principal, MemberModifyDTO memberModifyDTO, ModelMap model) {

        MemberDTO member = accountService.get(principal.getName());

        String username = member.getUsername();
        String email = member.getEmail();
        Long id = member.getId();
        System.out.println("here"+id);
        // account/modify에서 명시한 th:object인 memberDTO는 여기의 memberDTO를 의미하며 여기서
        // set해서 데려가서 이 페이지 도달시 항상 변경전 사용자id와 이메일을 input의 value로 가져온다.
        memberModifyDTO.setId(id);
        memberModifyDTO.setUsername(username);
        memberModifyDTO.setEmail(email);

        model.addAttribute("pageTitle", "내 정보 수정");

        return "/account/modify";
    }

    @PostMapping("/modify")
    public String myInfoModify(Principal principal, @Valid MemberModifyDTO memberModifyDTO, BindingResult bindingResult) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 중복 id 체크를 위해 회원가입 시도하려면 MemberDTO로 넣어야함
        MemberDTO memberDTO = memberModifyDTO.toMemberDTO();

        if (bindingResult.hasErrors()) {
            return "/account/modify";
        }

        // 현재 접속중인 회원 정보를 가져오기 위한 currentUser 회원 정보 수정에서 가져오는 객체 memberDTO와 비교
        // 하기 때문에 특별히 currentUser라는 이름을 씀
        MemberDTO currentUser = accountService.get(principal.getName());

        // BCryptPasswordEncoder에 matches를 사용하여 내 정보 수정 페이지에서 입력 받은 현재 비밀번호 String과
        // db에서 직접 가져온 hash된 값을 비교함
        if (!passwordEncoder.matches(memberModifyDTO.getPassword1(), currentUser.getPassword1())) {
            bindingResult.rejectValue("password1", "passwordIncorrect",
                    "현재 패스워드가 일치하지 않습니다.");
            return "/account/modify";
        }

        if(!principal.getName().equals(memberDTO.getUsername())) {
            try{
                memberService.joinUser(memberDTO);
            }catch (DataIntegrityViolationException e) {
                e.printStackTrace();
                bindingResult.reject("modifyFailed", "이미 등록된 사용자입니다.");
                return "/account/modify";
            }catch (Exception e) {
                e.printStackTrace();
                bindingResult.reject("modifyFailed", e.getMessage());
                return "/account/modify";
            }
        }

        accountService.modify(memberModifyDTO);

        return "redirect:/account/info";
    }

    @PostMapping("/delete")
    public String memberRemove(@RequestParam ("id") Long id) {

        accountService.remove(id);
        return "redirect:/account/logout";
    }
}


package com.example.demo.controller;


import com.example.demo.domain.member.MemberDTO;
import com.example.demo.domain.member.MemberModifyDTO;
import com.example.demo.service.member.AccountService;
import com.example.demo.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/account")
public class MemberController {
    // 회원가입, 로그인, 로그아웃은 memberService에서 처리
    // 회원 정보 보기, 회원 정보 수정, 회원 탈퇴는 accountService에서 처리
    @Autowired
    private MemberService memberService;

    @Autowired
    private AccountService accountService;

    // 로그인 페이지를 렌더링할 경우, 사용하는 controller
    // url: /account/login
    // method: GET
    // 사용하는 위치: 사용자가 네비게이션 바의 Login 버튼
    // pageTitle : 로그인
    @GetMapping(value="/login")
    public String loginView(ModelMap model) {
        model.addAttribute("pageTitle", "로그인");
        
        return "/account/login";
    }

    // 회원가입 페이지를 렌더링할 경우, 사용하는 controller
    // url: /account/signup
    // method: GET
    // 사용하는 위치: 사용자가 네비게이션 바의 Sign Up 버튼
    // pageTitle : 회원가입
    @GetMapping(value = "/signup")
    public String signupView(MemberDTO memberDTO, ModelMap model) {
        model.addAttribute("pageTitle", "회원가입");
        return "/account/signup";
    }

    // 로그인 성공 페이지를 렌더링할 경우, 사용하는 controller
    // url: /account/login/result
    // method: GET
    // 사용하는 위치: 로그인 버튼
    // pageTitle : 로그인 성공
    @GetMapping("/login/result")
    public String LoginResultView(ModelMap model) {
        model.addAttribute("pageTitle", "로그인 성공");
        return "/account/loginSuccess";
    }

    // 로그아웃 성공 페이지를 렌더링할 경우, 사용하는 controller
    // url: /account/logout/result
    // method: GET
    // 사용하는 위치: 네비게이션 바의 Logout 버튼
    // pageTitle : 로그아웃 성공
    @GetMapping("/logout/result")
    public String LogoutResultView(ModelMap model) {
        model.addAttribute("pageTitle", "로그아웃 성공");
        return "/account/logoutSuccess";
    }

    // 회원가입 로직을 담당하는 controller
    // url: /account/signup
    // method: POST
    // 사용하는 위치: 회원가입 페이지의 회원가입 버튼
    // bindingResult를 사용하여 화면에서 form의 th:object로 memberDTO 객체를 받아
    // 회원가입 validation을 구현
    // 1. 패스워드와 패스워드 확인 일치 여부
    // 2. 사용자ID 중복 확인
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

    // 내 정보 페이지를 렌더링할 경우, 사용하는 controller
    // url: /account/info
    // method: GET
    // 사용하는 위치: 네비게이션 바의 MyInfo 버튼
    // pageTitle : 내 정보
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

    // 내 정보 수정페이지를 렌더링할 경우, 사용하는 controller
    // url: /account/modify
    // method: GET
    // 사용하는 위치: 내 정보 페이지 하단에 수정하기 버튼
    // pageTitle : 내 정보 수정
    // MemberModifyDTO 클래스를 별도로 만듬 (이유: 현재 비밀번호, 새로운 비밀번호, 새로운 비밀번호 확인을 구현하기 위해 객체에 하나의 변수를 추가해야 하기 때문)
    // 내 정보 수정 페이지에서 th:object로 설정할 memberModifyDTO를 여기서 가져와서 로그인한 유저의 id, 사용자ID, 이메일을 set함
    @GetMapping("/modify")
    public String myInfoModifyView(Principal principal, MemberModifyDTO memberModifyDTO, ModelMap model) {

        MemberDTO member = accountService.get(principal.getName());

        String username = member.getUsername();
        String email = member.getEmail();
        Long id = member.getId();

        // account/modify에서 명시한 th:object인 memberDTO는 여기의 memberDTO를 의미하며 여기서
        // set해서 데려가서 이 페이지 도달시 항상 변경전 사용자id와 이메일을 input의 value로 가져온다.
        memberModifyDTO.setId(id);
        memberModifyDTO.setUsername(username);
        memberModifyDTO.setEmail(email);

        model.addAttribute("pageTitle", "내 정보 수정");

        return "/account/modify";
    }

    // 내 정보 수정 로직을 담당하는 controller
    // url: /account/modify
    // method: POST
    // 사용하는 위치: 내 정보 수정 하단 수정하기 버튼
    // bindingResult를 사용하여 화면에서 form의 th:object로 memberModifyDTO 객체를 받아
    // 내 정보 수정 validation을 구현
    // 1. 현재 비밀번호 일치 여부
    // 2. 새로운 패스워드와 새로운 패스워드 확인 일치 여부
    @PostMapping("/modify")
    public String myInfoModify(Principal principal, @Valid MemberModifyDTO memberModifyDTO, BindingResult bindingResult) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

        if (!memberModifyDTO.getPassword2().equals(memberModifyDTO.getPassword3())) {
            bindingResult.rejectValue("password2", "passwordIncorrect",
                    "2개의 새로운 패스워드가 일치하지 않습니다.");
            return "/account/modify";
        }

        accountService.modify(memberModifyDTO);

        return "redirect:/account/info";
    }

    // 회원 삭제 로직을 담당하는 controller
    // url: /account/delete
    // method: POST
    // 사용하는 위치: 현재 없음
    @PostMapping("/delete")
    public String memberRemove(@RequestParam ("id") Long id) {

        accountService.remove(id);
        return "redirect:/account/logout";
    }

    // 회원 탈퇴 로직을 담당하는 controller
    // url: /account/withdrawal
    // method: POST
    // 사용하는 위치: 회원 정보 하단 회원 탈퇴 버튼
    // HttpSession session으로 삭제시 세션 종료 구현
    @PostMapping("/withdrawal")
    public String memberWithdrawl(HttpSession session, @RequestParam("id") Long id) {

        accountService.withdrawal(id);
        session.invalidate();
        return "redirect:/";
    }
}


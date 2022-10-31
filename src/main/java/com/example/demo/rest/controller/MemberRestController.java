package com.example.demo.rest.controller;

import com.example.demo.domain.member.MemberDTO;
import com.example.demo.rest.request.SignUpDTO;
import com.example.demo.rest.response.common.Message;
import com.example.demo.rest.response.common.StatusEnum;
import com.example.demo.service.member.AccountService;
import com.example.demo.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;

@RestController
@RequestMapping("/api/v1/account")
public class MemberRestController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private AccountService accountService;

    // 회원가입 로직을 담당하는 rest api controller
    // url: /api/v1/account/signup
    // method: POST
    // request json format :
    // {    "username":         <사용자아이디>,
    //      "email":            <이메일>,
    //      "password":         <비밀번호>,
    //      "password_confirm": <비밀번호 확인>
    // }
    // response json format :
    // {
    //    "status":  <상태메세지>,
    //    "message": <메세지>,
    //    "data": {
    //        "id":        <데이터베이스의 member id>,
    //        "username":  <사용자아이디>,
    //        "email":     <이메일>,
    //        "password1": <해쉬된 비밀번호>,
    //        "password2": <해쉬된 비밀번호 확인>
    //    }
    // }
    @PostMapping("/signup")
    public ResponseEntity<Message> signup(@RequestBody SignUpDTO signUpDto){

        MemberDTO memberDto = signUpDto.toMemberDto();
        memberService.joinUser(memberDto);
        MemberDTO retrievedMemberDto = accountService.get(memberDto.getUsername());

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(StatusEnum.CREATED);
        message.setMessage("success");
        message.setData(retrievedMemberDto);

        return new ResponseEntity<>(message, headers, HttpStatus.CREATED);
    }
}

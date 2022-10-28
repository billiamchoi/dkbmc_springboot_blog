package com.example.demo.rest.controller;

import com.example.demo.domain.member.Member;
import com.example.demo.domain.member.MemberDTO;
import com.example.demo.repository.MemberRepository;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/account")
public class MemberRestController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/{id}")
    public ResponseEntity<Message> memberGetOne(@PathVariable Long id) {

        Optional<Member> member = memberRepository.findById(id);
        Member response = member.get();

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        message.setData(response);

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

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

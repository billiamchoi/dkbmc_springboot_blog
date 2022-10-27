package com.example.demo.rest.controller;

import com.example.demo.domain.member.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.rest.response.common.Message;
import com.example.demo.rest.response.common.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/account")
public class MemberRestController {

    @Autowired
    private MemberRepository memberRepository;

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
}

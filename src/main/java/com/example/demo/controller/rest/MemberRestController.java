package com.example.demo.controller.rest;

import com.example.demo.domain.member.Member;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/account")
public class MemberRestController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/{id}")
    public Member memberGet(@PathVariable Long id) {
        Optional<Member> member = memberRepository.findById(id);

        return member.get();
    }
}

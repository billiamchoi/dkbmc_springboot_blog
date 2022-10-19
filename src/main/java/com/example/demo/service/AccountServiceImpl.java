package com.example.demo.service;

import com.example.demo.domain.member.Member;
import com.example.demo.domain.member.MemberDTO;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private MemberRepository repository;

    @Override
    public MemberDTO get(String username) {

        MemberDTO memberDto = new MemberDTO();
        Optional<Member> memberOptional = repository.findByUsername(username);
        Member member = memberOptional.get();
        memberDto = member.toDtO();
        return memberDto;
    }

    @Override
    public void modify(MemberDTO member) {

    }

    @Override
    public void remove(Long id) {

    }
}

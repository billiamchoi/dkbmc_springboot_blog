package com.example.demo.service;

import com.example.demo.domain.member.MemberDTO;

public interface AccountService {

    MemberDTO get(String username);

    void modify(MemberDTO member);

    void remove(Long id);
}

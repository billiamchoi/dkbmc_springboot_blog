package com.example.demo.service.member;

import com.example.demo.domain.member.MemberDTO;
import com.example.demo.domain.member.MemberModifyDTO;

public interface AccountService {

    MemberDTO get(String username);

    void modify(MemberModifyDTO memberModifyDTO);

    void remove(Long id);

    void withdrawal(Long id);
}

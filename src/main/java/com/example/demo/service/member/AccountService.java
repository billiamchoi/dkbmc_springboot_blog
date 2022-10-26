package com.example.demo.service.member;

import com.example.demo.domain.member.MemberDTO;
import com.example.demo.domain.member.MemberModifyDTO;

public interface AccountService {

    // 특정 멤버 조회
    MemberDTO get(String username);

    // 멤버 수정
    void modify(MemberModifyDTO memberModifyDTO);

    // 멤버 삭제
    void remove(Long id);

    // 멤버 탈퇴
    void withdrawal(Long id);
}

package com.example.demo.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

// MemberService.loadUserByUsername()에서 새로운 User 객체를 만들때
// 권한이 필수 argument이기 때문에 사용
// 코드의 통일성을 위해 enum 객체로 사용하고 있음

@AllArgsConstructor
@Getter
public enum Role {
    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER");

    private String value;
}

package com.example.demo.rest.request;

import com.example.demo.domain.member.MemberDTO;
import lombok.Builder;
import lombok.Data;

// rest api로 회원가입 요청시 사용하는 DTO 객체
@Data
@Builder
public class SignUpDTO {

    private String username;
    private String email;

    private String password;

    private String password_confirm;

    // controller에서 SignUpDTO를 memberDTO로 변활할때 사용하는 함수
    public MemberDTO toMemberDto() {

        return MemberDTO.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
    }
}

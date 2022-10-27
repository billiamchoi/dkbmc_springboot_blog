package com.example.demo.rest.request;

import com.example.demo.domain.member.MemberDTO;
import lombok.Data;


@Data
public class SignUpDTO {

    private String username;
    private String email;

    private String password;

    private String password_confirm;

    public MemberDTO toMemberDto(){
        return MemberDTO.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
    }
}

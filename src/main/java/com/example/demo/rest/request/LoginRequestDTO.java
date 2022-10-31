package com.example.demo.rest.request;

import lombok.Data;

// rest api로 로그인 요청시 사용하는 DTO 객체
@Data
public class LoginRequestDTO {
    private String username;
    private String password;
}

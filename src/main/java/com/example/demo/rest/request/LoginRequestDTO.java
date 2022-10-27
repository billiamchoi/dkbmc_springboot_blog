package com.example.demo.rest.request;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String username;
    private String password;
}

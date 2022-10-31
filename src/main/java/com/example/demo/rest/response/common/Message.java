package com.example.demo.rest.response.common;

import lombok.Data;

// ResponseEntity body에 사용할 객체
@Data
public class Message {

    // HttpStatus
    private StatusEnum status;

    // response body의 메세지
    private String message;

    // response body에서 data 객체
    private Object data;

    // Message 객체에 아무것도 없이 생성이 되면 Bad Request error를 반환하도록 생성자를 만듬
    public Message() {
        this.status = StatusEnum.BAD_REQUEST;
        this.data = null;
        this.message = null;
    }
}

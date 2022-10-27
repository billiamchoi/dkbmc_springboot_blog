package com.example.demo.rest.response;

import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;

@Getter
@Setter
public class QuestionResponse {

    private Long id;

    private String content;

    private String subject;

    private Date create_date;

    private Date modify_date;

    private Long author_id;
}

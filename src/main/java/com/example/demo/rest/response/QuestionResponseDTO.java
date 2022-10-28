package com.example.demo.rest.response;

import lombok.*;


import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionResponseDTO {

    private Long id;

    private String content;

    private String subject;

    private Date create_date;

    private Date modify_date;

    private Long author_id;
}

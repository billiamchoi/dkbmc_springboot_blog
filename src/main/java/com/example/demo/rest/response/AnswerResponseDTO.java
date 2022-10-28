package com.example.demo.rest.response;

import lombok.*;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnswerResponseDTO {

    private Long id;

    private String content;

    private Long question_id;

    private Long author_id;

    private Integer vote_count;

    private Date create_date;

    private Date modify_date;


}

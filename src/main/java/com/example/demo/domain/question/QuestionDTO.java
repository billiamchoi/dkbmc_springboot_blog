package com.example.demo.domain.question;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

import com.example.demo.domain.answer.Answer;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDTO {

    private Long id;

    private String content;

    private String subject;

    private Date create_date;

    private Date modify_date;

    private Set<Answer> answer = Collections.emptySet();

    public Question toEntity() {
        return Question.builder()
                .id(id)
                .content(content)
                .subject(subject)
                .create_date(create_date)
                .modify_date(modify_date)
                .answer(answer)
                .build();
    }
}

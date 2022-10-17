package com.example.demo.domain.answer;

import com.example.demo.domain.question.Question;
import com.example.demo.domain.question.QuestionDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerDTO {

    private Long id;

    private String content;
    private Question question;
    private Date create_date;
    private Date modify_date;

    public Answer toEntity() {
        return Answer.builder()
                .id(id)
                .content(content)
                .create_date(create_date)
                .modify_date(modify_date)
                .question(question)
                .build();
    }

    public List<AnswerDTO> toDtoList(List<Answer> answerList) {
        List<AnswerDTO> answerDTOList = answerList.stream().map(m -> AnswerDTO.builder()
                .id(m.getId())
                .content(m.getContent())
                .create_date(m.getCreate_date())
                .modify_date(m.getModify_date())
                .question(m.getQuestion())
                .build())
                .toList();

        return answerDTOList;
    }

}

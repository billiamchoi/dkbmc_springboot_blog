package com.example.demo.domain.answer;

import com.example.demo.domain.question.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="answer")
public class Answer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String content;
    @ManyToOne(targetEntity = Question.class, fetch = FetchType.LAZY)
    @JoinColumn(name="question_id")
    private Question question;
    private Date create_date;
    private Date modify_date;

    public AnswerDTO toDto() {
        return AnswerDTO.builder()
                .id(id)
                .content(content)
                .create_date(create_date)
                .modify_date(modify_date)
                .question(question)
                .build();
    }
}

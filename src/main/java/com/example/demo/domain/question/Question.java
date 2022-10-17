package com.example.demo.domain.question;

import com.example.demo.domain.answer.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="question")
public class Question {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String content;
    private String subject;
    private Date create_date;
    private Date modify_date;
    @OneToMany(mappedBy = "question", orphanRemoval = true)
    private Set<Answer> answer;

    public QuestionDTO toDto() {
        return QuestionDTO.builder()
                .id(id)
                .content(content)
                .subject(subject)
                .create_date(create_date)
                .modify_date(modify_date)
                .answer(answer)
                .build();
    }
}

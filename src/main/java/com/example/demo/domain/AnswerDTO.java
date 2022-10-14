package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@Entity
@ToString
@Table(name="answer")
public class AnswerDTO {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String content;

    @ManyToOne(targetEntity = QuestionDTO.class, fetch = FetchType.LAZY)
    @JoinColumn(name="question_id")
    private QuestionDTO question;

    private Date create_date;

    private Date modify_date;

}

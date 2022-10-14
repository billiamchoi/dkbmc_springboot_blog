package com.example.demo.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@Entity
@ToString
@Table(name="question")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    //What is GeneratedValue
    private Long id;

    private String content;

    private String subject;

    private Date create_date;

    private Date modify_date;

    @OneToMany(mappedBy = "question", orphanRemoval = true)
    private Set<AnswerDTO> answer;
}

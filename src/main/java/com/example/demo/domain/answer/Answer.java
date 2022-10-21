package com.example.demo.domain.answer;

import com.example.demo.domain.member.Member;
import com.example.demo.domain.question.Question;
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
@Table(name="answer")
public class Answer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String content;
    @ManyToOne(targetEntity = Question.class, fetch = FetchType.LAZY)
    @JoinColumn(name="question_id")
    private Question question;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name="author_id")
    private Member member;

    @ManyToMany
    Set<Member> voter;

    private Date create_date;

    private Date modify_date;

    public AnswerDTO toDto() {
        return AnswerDTO.builder()
                .id(id)
                .content(content)
                .create_date(create_date)
                .modify_date(modify_date)
                .question(question)
                .member(member)
                .voter(voter)
                .build();
    }
}

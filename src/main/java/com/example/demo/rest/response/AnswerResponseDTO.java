package com.example.demo.rest.response;

import com.example.demo.domain.answer.Answer;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.question.Question;
import lombok.*;

import java.util.Date;
import java.util.Set;

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

    public Answer toEntity(Question question, Member member, Set<Member> voter) {
        return Answer.builder()
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

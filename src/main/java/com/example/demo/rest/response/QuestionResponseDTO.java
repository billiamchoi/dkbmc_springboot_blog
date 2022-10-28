package com.example.demo.rest.response;

import com.example.demo.domain.answer.Answer;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.question.Question;
import lombok.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.Set;

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

    private Integer vote_count;

    public Question toEntity(Set<Answer> answer, Member member, Set<Member> voter) {
        return Question.builder()
                .id(id)
                .subject(subject)
                .content(content)
                .create_date(create_date)
                .modify_date(modify_date)
                .answer(answer)
                .member(member)
                .voter(voter)
                .build();
    }
}

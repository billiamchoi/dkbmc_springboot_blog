package com.example.demo.domain.question;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.example.demo.domain.answer.Answer;
import com.example.demo.domain.member.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDTO {

    private Long id;

    private String content;

    private String subject;

    // repository save()에서 다음과 같은 에러가 발생
    // A collection with cascade="all-delete-orphan" was no longer referenced by the owning entity instance
    // answer = emptySet으로 초기화하는 것으로 문제 해결함
    private Set<Answer> answer = Collections.emptySet();

    private Member member;

    private Set<Member> voter;

    private Date create_date;

    private Date modify_date;

    // DTO 객체 -> Entity 객체 전환시 사용
    // Controller에서 DTO 객체로 받아 serviceImpl까지 전달되고 Repository에서 argument로
    // Entity 객체를 전달해줘야 하기 위함
    public Question toEntity() {
        return Question.builder()
                .id(id)
                .content(content)
                .subject(subject)
                .create_date(create_date)
                .modify_date(modify_date)
                .answer(answer)
                .member(member)
                .voter(voter)
                .build();
    }
}

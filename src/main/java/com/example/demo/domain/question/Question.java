package com.example.demo.domain.question;

import com.example.demo.domain.answer.Answer;
import com.example.demo.domain.member.Member;
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

    // Answer Entity와 OneToMany 매핑 (Question(1) : Answer(다)
    // 양방향 Question(1) : Answer(다) 매핑을 구현하고
    // 1인 Entity(Question)에 orphanRemoval = true를 명시하여
    // Question 지워지면 해당 Question에 속한 Answer도 지워지도록 구현
    @OneToMany(mappedBy = "question", orphanRemoval = true)
    private Set<Answer> answer;

    // Member Entity와 ManyToOne 매핑 (Question(다) : Member(1))
    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name="author_id")
    private Member member;

    // 추천 기능을 위한 ManyToMany 매핑  (Question(다) : voter(다))
    @ManyToMany
    Set<Member> voter;

    private Date create_date;
    private Date modify_date;

    // Etity 객체 -> DTO 객체 전환시 사용
    // get()과 같이 특정한 레코드를 데이터베이스에서 조회하여 front까지 렌더링할때 사용
    // serviceImpl에서 사용
    public QuestionDTO toDto() {
        return QuestionDTO.builder()
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

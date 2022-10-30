package com.example.demo.domain.answer;

import com.example.demo.domain.member.Member;
import com.example.demo.domain.question.Question;
import com.example.demo.rest.response.AnswerResponseDTO;
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

    // Question Entity와 ManyToOne 매핑 (Answer(다) : Question(1))
    @ManyToOne(targetEntity = Question.class, fetch = FetchType.LAZY)
    @JoinColumn(name="question_id")
    private Question question;

    // Member Entity와 ManyToOne 매핑 (Answer(다) : Member(1))
    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name="author_id")
    private Member member;

    // 추천 기능을 위한 ManyToMany 매핑  (Answer(다) : voter(다))
    @ManyToMany
    Set<Member> voter;

    private Date create_date;

    private Date modify_date;

    // Etity 객체 -> DTO 객체 전환시 사용
    // get()과 같이 특정한 레코드를 데이터베이스에서 조회하여 front까지 렌더링할때 사용
    // serviceImpl에서 사용
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

    public AnswerResponseDTO toResponseDto() {
        return AnswerResponseDTO.builder()
                .id(id)
                .content(content)
                .question_id(question.getId())
                .author_id(member.getId())
                .vote_count(voter.size())
                .create_date(create_date)
                .modify_date(modify_date)
                .build();
    }
}

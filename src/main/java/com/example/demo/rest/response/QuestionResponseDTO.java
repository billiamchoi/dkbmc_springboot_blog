package com.example.demo.rest.response;

import com.example.demo.domain.answer.Answer;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.question.Question;
import lombok.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.Set;

// rest api에서 response로 질문 객체가 반환될때 사용됨
// database에서와 동일하게 아래와 같이 객체 속성을 구성함
// author_id는 답변을 작성한 글쓴이의 id를 의미함
// vote_count는 해당 질문의 추천수를 의미함
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionResponseDTO {

    private Long id;

    private String subject;

    private String content;

    private Long author_id;

    private Integer vote_count;

    private Date create_date;

    private Date modify_date;

    // QuestionResponseDTO 객체를 Question 객체로 변환시 사용
    // 질문 수정 api에서 사용함
    // 여기서 @Autowired로 서비스를 가져와서 set하고 싶었으나
    // toEntity parameter로
    // 질문에 속한 답변을 Set<Answer> 객체를 answer
    // 질문을 작성한 글쓴이 Member 객체를 member
    // 질문을 추천한 회원의 집합 Set<Member> 객체를 voter를 설정
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

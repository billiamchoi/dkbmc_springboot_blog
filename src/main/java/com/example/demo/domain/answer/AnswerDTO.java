package com.example.demo.domain.answer;

import com.example.demo.domain.member.Member;
import com.example.demo.domain.question.Question;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerDTO {

    private Long id;

    private String content;
    private Question question;

    private Member member;

    private Set<Member> voter;
    private Date create_date;
    private Date modify_date;

    // DTO 객체 -> Entity 객체 전환시 사용
    // Controller에서 DTO 객체로 받아 serviceImpl까지 전달되고 Repository에서 argument로
    // Entity 객체를 전달해줘야 하기 위함
    public Answer toEntity() {
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
    // listByQuestion()과 같이 여러개의 레코드를 데이터베이스에서 조회하여 front까지 렌더링할때 사용
    // serviceImpl에서 사용
    public List<AnswerDTO> toDtoList(List<Answer> answerList) {
        List<AnswerDTO> answerDTOList = answerList.stream().map(m -> AnswerDTO.builder()
                .id(m.getId())
                .content(m.getContent())
                .create_date(m.getCreate_date())
                .modify_date(m.getModify_date())
                .question(m.getQuestion())
                .member(m.getMember())
                .voter(m.getVoter())
                .build())
                .toList();

        return answerDTOList;
    }

}

package com.example.demo.domain;
import com.example.demo.domain.question.Question;
import com.example.demo.domain.question.QuestionDTO;
import org.springframework.data.domain.Page;

public class PageDTO {

    // Page<Entity> -> Page<Dto> 변환처리
    // ServiceImpl에서 Entity 객체 -> DTO 객체로 변환하므로서
    // repository(Persistence Layer)에서는 Entity로 Database와 소통하고
    // 그외 layer(Presentation Layer, Business Layer)에서는 DTO 객체를 사용하는 코드의 전체적인 룰을 지키기 위함
    public Page<QuestionDTO> toDtoList(Page<Question> questionList) {
        Page<QuestionDTO> questionDtoList = questionList.map(m -> QuestionDTO.builder()
                .id(m.getId())
                .content(m.getContent())
                .subject(m.getSubject())
                .create_date(m.getCreate_date())
                .modify_date(m.getModify_date())
                .answer(m.getAnswer())
                .member(m.getMember())
                .build());

        return questionDtoList;
    }
}


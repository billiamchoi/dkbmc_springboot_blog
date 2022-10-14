package com.example.demo.domain;
import org.springframework.data.domain.Page;

public class PageDTO {

    /* Page<Entity> -> Page<Dto> 변환처리 */
    public Page<QuestionDTO> toDtoList(Page<QuestionDTO> boardList){
        Page<QuestionDTO> questionDtoList = boardList.map(m -> QuestionDTO.builder()
                .id(m.getId())
                .content(m.getContent())
                .subject(m.getSubject())
                .create_date(m.getCreate_date())
                .modify_date(m.getModify_date())
                .answer(m.getAnswer())
                .build());
        return questionDtoList;
    }
}

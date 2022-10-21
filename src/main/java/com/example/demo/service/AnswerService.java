package com.example.demo.service;

import com.example.demo.domain.answer.AnswerDTO;
import com.example.demo.domain.member.MemberDTO;


import java.util.List;

public interface AnswerService {

    void create(AnswerDTO answer, Long questionId, Long authorId);

    AnswerDTO get(Long id);

    List<AnswerDTO> listByQuestion(Long id);

    void modify(AnswerDTO answer, Long id, Long authorId);

    void remove(Long id);

    void vote(AnswerDTO answerDto, MemberDTO memberDto);

}

package com.example.demo.service;

import com.example.demo.domain.answer.AnswerDTO;

import java.util.List;

public interface AnswerService {

    void create(AnswerDTO answer, Long id);

    List<AnswerDTO> listByQuestion(Long id);

    void modify(AnswerDTO answer, Long id);

    void remove(Long id);

}

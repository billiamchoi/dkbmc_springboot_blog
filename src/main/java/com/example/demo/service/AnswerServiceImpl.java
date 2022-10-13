package com.example.demo.service;

import com.example.demo.domain.AnswerDTO;
import com.example.demo.domain.QuestionDTO;
import com.example.demo.repository.AnswerRepository;
import com.example.demo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerRepository repository;

    @Autowired
    private QuestionRepository questionRepository;


    @Override
    public void create(AnswerDTO answer, Long id) {

        QuestionDTO question = questionRepository.findById(id).get();
        answer.setQuestion(question);
        answer.setCreate_date(new Date());
        answer.setModify_date(new Date());
        this.repository.save(answer);
    }

    @Override
    public List<AnswerDTO> listByQuestion(Long id) {
        List<AnswerDTO> AnswerList = repository.findAnswerDTOByQuestionId(id);
        return AnswerList;
    }

    @Override
    public void modify(AnswerDTO answer) {

    }

    @Override
    public void remove(Long id) {

    }
}

package com.example.demo.controller.rest;

import com.example.demo.domain.question.Question;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.restResponse.QuestionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/question")
public class QuestionRestController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public List<QuestionResponse> questionGetAll() {

        List<Question> questionOpt = questionRepository.findAll();
        List<QuestionResponse> qr = new ArrayList<QuestionResponse>();

        for (Question q : questionOpt) {
           QuestionResponse questionResponse = new QuestionResponse();
           questionResponse.setId(q.getId());
           questionResponse.setContent(q.getContent());
           questionResponse.setSubject(q.getSubject());
           questionResponse.setCreate_date(q.getCreate_date());
           questionResponse.setModify_date(q.getModify_date());
           questionResponse.setAuthor_id(q.getMember().getId());
           qr.add(questionResponse);
        }

        return qr;
    }


    @GetMapping("/{id}")
    public QuestionResponse questionGetOne(@PathVariable Long id) {

        Optional<Question> questionOpt = questionRepository.findById(id);
        Question question =  questionOpt.get();
        QuestionResponse response = new QuestionResponse();
        response.setId(question.getId());
        response.setContent(question.getContent());
        response.setSubject(question.getSubject());
        response.setCreate_date(question.getCreate_date());
        response.setModify_date(question.getModify_date());
        response.setAuthor_id(question.getMember().getId());

        return response;
    }
}

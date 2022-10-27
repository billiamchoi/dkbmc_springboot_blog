package com.example.demo.rest.controller;

import com.example.demo.domain.question.Question;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.rest.response.QuestionResponse;
import com.example.demo.rest.response.common.Message;
import com.example.demo.rest.response.common.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/question")
public class QuestionRestController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping({"/", ""})
    public ResponseEntity<Message> questionGetAll() {

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

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        message.setData(qr);

        return new ResponseEntity<>(message, headers,HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> questionGetOne(@PathVariable Long id) {

        Optional<Question> questionOpt = questionRepository.findById(id);
        Question question =  questionOpt.get();
        QuestionResponse response = new QuestionResponse();
        response.setId(question.getId());
        response.setContent(question.getContent());
        response.setSubject(question.getSubject());
        response.setCreate_date(question.getCreate_date());
        response.setModify_date(question.getModify_date());
        response.setAuthor_id(question.getMember().getId());

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        message.setData(response);

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
}

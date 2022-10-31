package com.example.demo.rest.controller;

import com.example.demo.domain.answer.AnswerDTO;
import com.example.demo.rest.response.AnswerResponseDTO;
import com.example.demo.rest.response.common.Message;
import com.example.demo.rest.response.common.StatusEnum;
import com.example.demo.service.answer.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;

@RestController
@RequestMapping("/api/v1/answer")
public class AnswerRestController {

    @Autowired
    AnswerService answerService;

    @GetMapping("/vote/{id}")
    public ResponseEntity<Message> answerVote(@RequestHeader("Authorization") String jwtToken, @PathVariable Long id) {

        AnswerDTO answerDto = answerService.get(id);
        AnswerResponseDTO answerResponseDto = answerService.restVote(jwtToken, answerDto);

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        message.setData(answerResponseDto);

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
}

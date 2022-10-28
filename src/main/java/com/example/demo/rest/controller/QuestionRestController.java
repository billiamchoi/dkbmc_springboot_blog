package com.example.demo.rest.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.config.jwt.JwtProperties;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.question.Question;
import com.example.demo.domain.question.QuestionDTO;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.rest.response.QuestionResponseDTO;
import com.example.demo.rest.response.common.Message;
import com.example.demo.rest.response.common.StatusEnum;
import com.example.demo.service.member.AccountService;
import com.example.demo.service.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/question")
public class QuestionRestController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AccountService accountService;

    @GetMapping({"/", ""})
    public ResponseEntity<Message> questionGetAll() {

        List<QuestionResponseDTO> questionResponseDtoList = questionService.restGetAll();

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        message.setData(questionResponseDtoList);

        return new ResponseEntity<>(message, headers,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> questionGetOne(@PathVariable Long id) {

        QuestionResponseDTO questionResponseDto = questionService.restGetOne(id);

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        message.setData(questionResponseDto);

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    // 질문 생성에는 글쓴이의 정보가 필요함
    // RequestHeader의 JWT token이 담긴 "Authorization" 만 String type의 jwtToken으로 가져옴
    // Token prefix인 "Bearer "를 제거하여 String 변수 token에 초기화
    // String username에 token을 디코딩하여 토큰 발행시 저장했던 username을 가져옴 (글쓴이 이름)
    // accountservice.get(username).toEntity()으로 가져와서 questionDto에 setmember 실행
    @PostMapping({"/", ""})
    public ResponseEntity<Message> questionCreate( @RequestHeader("Authorization") String jwtToken, @RequestBody QuestionDTO questionDto) {

        QuestionResponseDTO questionResponseDto = questionService.restCreate(jwtToken, questionDto);

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(StatusEnum.CREATED);
        message.setMessage("success");
        message.setData(questionResponseDto);

        return new ResponseEntity<>(message, headers, HttpStatus.CREATED);
    }
}

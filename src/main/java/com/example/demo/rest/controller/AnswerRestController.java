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

    // 답변(answer) 추천 로직을 담당하는 rest api controller
    // url: /api/v1/answer/vote/{id}
    // method: POST
    // request path : 답변 id
    // response json format :
    // {
    //    "status":  <상태메세지>,
    //    "message": <메세지>,
    //    "data": {
    //        "id":          <답변 id>,
    //        "content":     <답변 내용>,
    //        "question_id": <답변이 속한 질문 id>,
    //        "author_id":   <답변 글쓴이의 id>,
    //        "vote_count":  <답변 추천 수>,
    //        "create_date": <답변 생성일시>,
    //        "modify_date": <답변 수정일시>
    //    }
    // }
    // RequestHeader에서 키가 "Authorization"인 값, jwtToken을 받아 어떤 사용자가 추천을 했는지 확인
    // POST method이기 때문에 비회원은 아에 접근 안됨 (SecurityConfig 파일에 정의함)
    // answerService.restVote()함수에 jwtToken과 answerDTO 자체를 넘겨줌
    // ResponseEntity 객체에 담을 message와 headers, http status code를 set
    // response의 vote_count에서 하나가 늘었는지 확인이 가능함
    @PostMapping("/vote/{id}")
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

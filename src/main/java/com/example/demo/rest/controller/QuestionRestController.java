package com.example.demo.rest.controller;

import com.example.demo.domain.answer.AnswerDTO;
import com.example.demo.domain.question.QuestionDTO;
import com.example.demo.rest.response.AnswerResponseDTO;
import com.example.demo.rest.response.QuestionResponseDTO;
import com.example.demo.rest.response.common.Message;
import com.example.demo.rest.response.common.StatusEnum;
import com.example.demo.service.answer.AnswerService;
import com.example.demo.service.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;


@RestController
@RequestMapping("/api/v1/question")
public class QuestionRestController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

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
    public ResponseEntity<Message> questionCreate(@RequestHeader("Authorization") String jwtToken, @RequestBody QuestionDTO questionDto) {

        QuestionResponseDTO questionResponseDto = questionService.restCreate(jwtToken, questionDto);

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(StatusEnum.CREATED);
        message.setMessage("success");
        message.setData(questionResponseDto);

        return new ResponseEntity<>(message, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> questionModify(@RequestHeader("Authorization") String jwtToken, @PathVariable Long id, @RequestBody QuestionDTO questionDto) {

        HttpStatus httpStatus = null;

        QuestionResponseDTO questionResponseDto = questionService.restModify(jwtToken, id, questionDto);

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        if (questionResponseDto == null) {
            message.setStatus(StatusEnum.UNAUTHORIZED);
            message.setMessage("fail : unauthorized");
            message.setData(null);
            httpStatus = HttpStatus.UNAUTHORIZED;

        } else {
            message.setStatus(StatusEnum.OK);
            message.setMessage("success");
            message.setData(questionResponseDto);
            httpStatus = HttpStatus.OK;
        }

        return new ResponseEntity<>(message, headers, httpStatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> questionDelete(@RequestHeader("Authorization") String jwtToken, @PathVariable Long id) {

        HttpStatus httpStatus = null;

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        String messageText = questionService.restRemove(jwtToken, id);

        if (messageText.equals("success")) {

            message.setStatus(StatusEnum.OK);
            message.setMessage(messageText);
            message.setData(null);
            httpStatus = HttpStatus.OK;
        } else {

            message.setStatus(StatusEnum.UNAUTHORIZED);
            message.setMessage(messageText);
            message.setData(null);
            httpStatus = HttpStatus.UNAUTHORIZED;
        }

        return new ResponseEntity<>(message, headers, httpStatus);
    }

    // 질문 id로 답변 조회
    @GetMapping("/{id}/answer")
    public ResponseEntity<Message> answerGetAllByQuestionId(@PathVariable Long id) {
        //401 error exception 만들어야됨
        List<AnswerResponseDTO> answerResponseDtoList = answerService.restGetAllByQuestionId(id);

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        message.setData(answerResponseDtoList);

        return new ResponseEntity<>(message, headers,HttpStatus.OK);
    }

    // 질문 id로 답변 생성
    // 답변은 항상 질문에 속해 있기 때문
    @PostMapping({"/{id}/answer", "/{id}/answer/"})
    public ResponseEntity<Message> answerCreateByQuestionId(@RequestHeader("Authorization") String jwtToken, @PathVariable Long id, @RequestBody AnswerDTO answerDto) {

        AnswerResponseDTO answerResponseDto = answerService.restCreateByQuestionId(jwtToken, id, answerDto);

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(StatusEnum.CREATED);
        message.setMessage("success");
        message.setData(answerResponseDto);

        return new ResponseEntity<>(message, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{questionId}/answer/{answerId}")
    public ResponseEntity<Message> answerModify(@RequestHeader("Authorization") String jwtToken, @PathVariable Long questionId, @PathVariable Long answerId, @RequestBody AnswerDTO answerDto) {

        HttpStatus httpStatus = null;

        AnswerResponseDTO answerResponseDto = answerService.restModify(jwtToken, questionId, answerId, answerDto);

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        if (answerResponseDto == null) {
            message.setStatus(StatusEnum.UNAUTHORIZED);
            message.setMessage("fail : unauthorized");
            message.setData(null);
            httpStatus = HttpStatus.UNAUTHORIZED;

        } else {
            message.setStatus(StatusEnum.OK);
            message.setMessage("success");
            message.setData(answerResponseDto);
            httpStatus = HttpStatus.OK;
        }

        return new ResponseEntity<>(message, headers, httpStatus);
    }

}

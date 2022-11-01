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

    // 질문 목록 조회 로직을 담당하는 rest api controller
    // url: /api/v1/question
    // method: GET
    // request json format : 없음
    // response json format :
    // {
    //    "status":  <상태메세지>,
    //    "message": <메세지>,
    //    "data": [
    //        {
    //            "id":          <질문 id>,
    //            "subject":     <질문 제목>,
    //            "content":     <질문 내용>,
    //            "author_id":   <글쓴이 id>,
    //            "vote_count":  <질문 추천 수>,
    //            "create_date": <질문 생성일시>,
    //            "modify_date": <질문 수정일시>
    //        },
    //        ...
    //      ]
    // }
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

    // 특정 질문 조회 로직을 담당하는 rest api controller
    // url: /api/v1/question/{id}
    // method: GET
    // request path : 질문 id
    // response json format :
    // {
    //    "status":                  <상태메세지>,
    //    "message":                 <메세지>,
    //    "data": {
    //                "id":          <질문 id>,
    //                "subject":     <질문 제목>",
    //                "content":     <질문 내용>",
    //                "author_id":   <글쓴이 id>",
    //                "vote_count":  <질문 추천 수>",
    //                "create_date": <질문 생성일시>",
    //                "modify_date": <질문 수정일시>"
    //    }
    // }
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

    // 질문 생성 로직을 담당하는 rest api controller
    // url: /api/v1/question
    // method: POST
    // request json format :
    // {
    //      "subject": <질문 제목>
    //      "content"  <질문 내용>
    // }
    // response json format :
    //{
    //    "status": <상태메세지>,
    //    "message": <메세지>,
    //    "data": {
    //                "id":          <질문 id>,
    //                "subject":     <질문 제목>,
    //                "content":     <질문 내용>,
    //                "author_id":   <글쓴이 id>,
    //                "vote_count":  <질문 추천 수>,
    //                "create_date": <질문 생성일시>,
    //                "modify_date": <질문 수정일시>
    //    }
    // }
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

    // 질문 수정 로직을 담당하는 rest api controller
    // url: /api/v1/question/{id}
    // method: PUT
    // request path : 질문 id
    // request json format :
    // {
    //      "subject": <질문 제목>
    //      "content"  <질문 내용>
    // }
    // response json format :
    // {
    //    "status": <상태메세지>,
    //    "message": <메세지>,
    //    "data": {
    //                "id":          <질문 id>,
    //                "subject":     <질문 제목>,
    //                "content":     <질문 내용>,
    //                "author_id":   <글쓴이 id>,
    //                "vote_count":  <질문 추천 수>,
    //                "create_date": <질문 생성일시>,
    //                "modify_date": <질문 수정일시>
    //    }
    // }
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

    // 질문 삭제 로직을 담당하는 rest api controller
    // url: /api/v1/question/{id}
    // method: DELETE
    // request path : 질문 id
    // request json format : 없음
    // response json format :
    // {
    //    "status":  <상태메세지>,
    //    "message": <삭제성공 여부>,
    //    "data": null
    // }
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

    // 질문 id로 답변 조회를 담당하는 rest api controller
    // url: /api/v1/question/{id}/answer
    // method: GET
    // request path : 질문 id
    // request json format : 없음
    // response json format :
    // {
    //    "status":  <상태메세지>,
    //    "message": <메세지>,
    //    "data": [
    //        {
    //            "id":          <질문 id>,
    //            "content":     <답변 내용>,
    //            "author_id":   <글쓴이 id>,
    //            "vote_count":  <질문 추천 수>,
    //            "create_date": <질문 생성일시>,
    //            "modify_date": <질문 수정일시>
    //        },
    //        ...
    //      ]
    // }
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

    // 답변 생성 로직을 담당하는 rest api controller
    // url: /api/v1/question/{id}/answer
    // method: POST
    // request path : 질문 id
    // request json format :
    // {
    //      "content"  <질문 내용>
    // }
    // response json format :
    // {
    //    "status":  <상태메세지>,
    //    "message": <메세지>,
    //    "data": {
    //                "id":          <질문 id>,
    //                "content":     <답변 내용>,
    //                "author_id":   <글쓴이 id>,
    //                "vote_count":  <질문 추천 수>,
    //                "create_date": <질문 생성일시>,
    //                "modify_date": <질문 수정일시>
    //    }
    // }
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

    // 답변 수정 로직을 담당하는 rest api controller
    // url: /api/v1/question/{questionId}/answer/{answerId}
    // method: PUT
    // request path : 질문 id, 답변 id
    // request json format :
    // {
    //      "content"  <질문 내용>
    // }
    // response json format :
    // {
    //    "status":  <상태메세지>,
    //    "message": <메세지>,
    //    "data": {
    //                "id":          <질문 id>,
    //                "content":     <답변 내용>,
    //                "author_id":   <글쓴이 id>,
    //                "vote_count":  <질문 추천 수>,
    //                "create_date": <질문 생성일시>,
    //                "modify_date": <질문 수정일시>
    //    }
    // }
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

    // 답변 삭제 로직을 담당하는 rest api controller
    // url: /api/v1/question/{questionId}/answer/{answerId}
    // method: DELETE
    // request path : 질문 id, 답변 id
    // request json format : 없음
    // response json format :
    // {
    //    "status":  <상태메세지>,
    //    "message": <삭제성공 여부>,
    //    "data": null
    // }
    @DeleteMapping("/{questionId}/answer/{answerId}")
    public ResponseEntity<Message> answerRemove(@RequestHeader("Authorization") String jwtToken, @PathVariable Long questionId, @PathVariable Long answerId) {

        HttpStatus httpStatus = null;

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        String messageText = answerService.restRemove(jwtToken, answerId);


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

    // 질문(question) 추천 로직을 담당하는 rest api controller
    // url: /api/v1/question/vote/{id}
    // method: POST
    // request path : 질문 id
    // response json format :
    // {
    //    "status":  <상태메세지>,
    //    "message": <메세지>,
    //    "data": {
    //        "id":          <질문 id>,
    //        "subject":     <질문 제목>
    //        "content":     <질문 내용>,
    //        "question_id": <질문이 속한 질문 id>,
    //        "author_id":   <질문 글쓴이의 id>,
    //        "vote_count":  <질문 추천 수>,
    //        "create_date": <질문 생성일시>,
    //        "modify_date": <질문 수정일시>
    //    }
    // }
    // RequestHeader에서 키가 "Authorization"인 값, jwtToken을 받아 어떤 사용자가 추천을 했는지 확인
    // POST method이기 때문에 비회원은 아에 접근 안됨 (SecurityConfig 파일에 정의함)
    // questionService.restVote()함수에 jwtToken과 answerDTO 자체를 넘겨줌
    // ResponseEntity 객체에 담을 message와 headers, http status code를 set
    // response의 vote_count에서 하나가 늘었는지 확인이 가능함
    @PostMapping("/vote/{id}")
    public ResponseEntity<Message> questionVote(@RequestHeader("Authorization") String jwtToken, @PathVariable Long id) {

        QuestionDTO questionDto = questionService.get(id);
        QuestionResponseDTO questionResponseDto = questionService.restVote(jwtToken, questionDto);

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        message.setData(questionResponseDto);

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
}

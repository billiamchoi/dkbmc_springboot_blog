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
    private AccountService accountService;

    @GetMapping({"/", ""})
    public ResponseEntity<Message> questionGetAll() {

        List<Question> questionOpt = questionRepository.findAll();
        List<QuestionResponseDTO> qr = new ArrayList<QuestionResponseDTO>();

        for (Question q : questionOpt) {
           QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO();
           questionResponseDTO.setId(q.getId());
           questionResponseDTO.setContent(q.getContent());
           questionResponseDTO.setSubject(q.getSubject());
           questionResponseDTO.setCreate_date(q.getCreate_date());
           questionResponseDTO.setModify_date(q.getModify_date());
           questionResponseDTO.setAuthor_id(q.getMember().getId());
           qr.add(questionResponseDTO);
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
        QuestionResponseDTO response = new QuestionResponseDTO();
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

    // 질문 생성에는 글쓴이의 정보가 필요함
    // RequestHeader의 JWT token이 담긴 "Authorization" 만 String type의 jwtToken으로 가져옴
    // Token prefix인 "Bearer "를 제거하여 String 변수 token에 초기화
    // String username에 token을 디코딩하여 토큰 발행시 저장했던 username을 가져옴 (글쓴이 이름)
    // accountservice.get(username).toEntity()으로 가져와서 questionDto에 setmember 실행
    @PostMapping({"/", ""})
    public ResponseEntity<Message> questionCreate( @RequestHeader("Authorization") String jwtToken, @RequestBody QuestionDTO questionDto) {

        String token = jwtToken.replace(JwtProperties.TOKEN_PREFIX, "");
        String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
                .getClaim("username").asString();
        Member member = accountService.get(username).toEntity();

        questionDto.setMember(member);
        questionDto.setCreate_date(new Date());
        questionDto.setModify_date(new Date());
        Question savedQuestion = questionRepository.save(questionDto.toEntity());

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(StatusEnum.CREATED);
        message.setMessage("success");
        message.setData(savedQuestion);

        return new ResponseEntity<>(message, headers, HttpStatus.CREATED);
    }
}

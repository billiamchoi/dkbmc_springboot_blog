package com.example.demo.controller;

import com.example.demo.domain.answer.AnswerDTO;
import com.example.demo.domain.member.MemberDTO;
import com.example.demo.service.member.AccountService;
import com.example.demo.service.answer.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AccountService accountService;

    // 답변(answer) 생성 로직을 담당하는 컨트롤러
    // url: /answer/create
    // method: POST
    // 사용하는 위치: /question/detail/{id} 답변 등록 버튼
    // RequestParam으로 질문 id를 받아와서 답변 등록 시 원래 위치로 리다이렉트하도록 함
    @PostMapping("/create")
    public String answerCreate(Principal principal, AnswerDTO answer, @RequestParam ("question_id") Long questionId, @RequestParam ("author_id") Long authorId) {

        answerService.create(answer, questionId, authorId);
        return "redirect:/question/detail/"+questionId;
    }

    // 답변(answer) 수정 로직을 담당하는 컨트롤러
    // url: /answer/modify
    // method: POST
    // 사용하는 위치: /question/detail/{id} 답변 수정 버튼
    // RequestParam으로 질문 id를 받아와서 답변 등록 시 원래 위치로 리다이렉트하도록 함
    @PostMapping("/modify")
    public String answerModify(AnswerDTO answer, @RequestParam ("question_id") Long questionId, @RequestParam ("author_id") Long authorId) {

        answerService.modify(answer, questionId, authorId);
        return "redirect:/question/detail/"+questionId;
    }

    // 답변(answer) 삭제 로직을 담당하는 controller
    // url: /answer/remove/{id}
    // method: POST
    // 사용하는 위치: /question/detail/{id} 답변 삭제 버튼
    // @RequestParam으로 바꿔줘야할 것 같음
    @PostMapping("/remove/{id}")
    public String answerRemove(@PathVariable Long id, @RequestParam ("question_id") Long questionId) {

        answerService.remove(id);
        return "redirect:/question/detail/"+questionId;
    }

    // 답변(question) 추천 로직을 담당하는 controller
    // url: /answer/vote/{id}
    // method: GET
    // 사용하는 위치: /question/detail/{id} 답변 추천 버튼
    // principal을 사용하여 어떤 사용자가 추천을 했는지 설정하고
    // 화면에서 a tag에서 /answer/vote/{id}로 이동하여 id를 받아
    // vote함수에 사용자 객체, 답변 객체를 argument로 넘겨줌으로서 해당 사용자가 답변을 추천하는 로직을 구현
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String answerVote(Principal principal, @PathVariable("id") Long id) {
        AnswerDTO answerDto = answerService.get(id);
        MemberDTO memberDto = accountService.get(principal.getName());
        answerService.vote(answerDto, memberDto);
        return String.format("redirect:/question/detail/%s", answerDto.getQuestion().getId());
    }
}

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

    @PostMapping("/create")
    public String answerCreate(Principal principal, AnswerDTO answer, @RequestParam ("question_id") Long questionId, @RequestParam ("author_id") Long authorId) {

        answerService.create(answer, questionId, authorId);
        return "redirect:/question/detail/"+questionId;
    }

    @PostMapping("/modify")
    public String answerModify(AnswerDTO answer, @RequestParam ("question_id") Long questionId, @RequestParam ("author_id") Long authorId) {

        answerService.modify(answer, questionId, authorId);
        return "redirect:/question/detail/"+questionId;
    }

    @PostMapping("/remove/{id}")
    public String answerRemove(@PathVariable Long id, @RequestParam ("question_id") Long questionId) {

        answerService.remove(id);
        return "redirect:/question/detail/"+questionId;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String answerVote(Principal principal, @PathVariable("id") Long id) {
        AnswerDTO answerDto = answerService.get(id);
        MemberDTO memberDto = accountService.get(principal.getName());
        answerService.vote(answerDto, memberDto);
        return String.format("redirect:/question/detail/%s", answerDto.getQuestion().getId());
    }
}

package com.example.demo.controller;

import com.example.demo.domain.answer.AnswerDTO;
import com.example.demo.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

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
}

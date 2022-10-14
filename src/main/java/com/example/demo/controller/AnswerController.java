package com.example.demo.controller;

import com.example.demo.domain.AnswerDTO;
import com.example.demo.domain.QuestionDTO;
import com.example.demo.service.AnswerService;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping("/create")
    public String answerCreate(AnswerDTO answer, @RequestParam ("question_id") Long id) {

        answerService.create(answer, id);
        return "redirect:/question/detail/"+id;
    }

    @PostMapping("/modify")
    public String answerModify(AnswerDTO answer, @RequestParam ("question_id") Long id) {

        answerService.modify(answer, id);
        return "redirect:/question/detail/"+id;
    }

    @PostMapping("/remove/{id}")
    public String answerRemove(@PathVariable Long id, @RequestParam ("question_id") Long questionId) {

        answerService.remove(id);
        return "redirect:/question/detail/"+questionId;
    }
}

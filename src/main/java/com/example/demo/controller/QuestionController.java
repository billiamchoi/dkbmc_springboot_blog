package com.example.demo.controller;


import java.util.List;
import java.util.Map;

import com.example.demo.service.AnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.example.demo.domain.QuestionDTO;
import com.example.demo.service.QuestionService;

@Controller
@RequestMapping("/question")
@Slf4j
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @PostMapping("/create")
    public String createQuestion(QuestionDTO question) {

        questionService.create(question);
        return "redirect:/question/list";
    }

    @GetMapping("/create")
    public String questionCreate() {
        return "/question/create";
    }

    @GetMapping("/list")
    public String questionList(@RequestParam Map<String, Object> param, ModelMap model) {
        List<QuestionDTO> questionList = (List<QuestionDTO>) questionService.list();
        model.put("questionList", questionList);

        return "/question/list";
    }

    @GetMapping("/detail/{id}")
    public String get(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.get(id));
        model.addAttribute("answerList", answerService.listByQuestion(id));
        return "/question/get";
    }

    @GetMapping("/modify/{id}")
    public String modify(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.get(id));
        return "/question/modify";
    }

    @PostMapping("/modify")
    public String modifyQuestion(QuestionDTO question) {

        questionService.modify(question);
        return "redirect:/question/list";
    }

    @PostMapping("/remove/{id}")
    public String removeQuestion(@PathVariable Long id) {

        questionService.remove(id);
        return "redirect:/question/list";

    }




}

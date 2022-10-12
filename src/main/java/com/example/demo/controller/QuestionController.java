package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.example.demo.domain.QuestionDTO;
import com.example.demo.service.QuestionService;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/create")
    public String createQuestion(QuestionDTO question) {

        questionService.create(question);
        return "redirect:/question/list";
    }


    @GetMapping("/create")
    public String questionCreate() {
        return "/question/create";
    }

    @GetMapping("list")
    public String questionList(@RequestParam Map<String, Object> param, ModelMap model) {
        List<QuestionDTO> questionList = (List<QuestionDTO>) questionService.list();
        model.put("questionList", questionList);

        return "/question/list";
    }


}

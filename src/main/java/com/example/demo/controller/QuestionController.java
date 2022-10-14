package com.example.demo.controller;


import java.util.List;
import java.util.Map;

import com.example.demo.domain.PageDTO;
import com.example.demo.service.AnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public String questionCreate(QuestionDTO question) {
        questionService.create(question);
        return "redirect:/question/list";
    }

    @GetMapping("/create")
    public String questionCreateView(ModelMap model) {
        model.addAttribute("pageTitle", "질문 등록");
        return "/question/create";
    }

    @GetMapping("/list")
    public String questionListView(@RequestParam Map<String, Object> param, ModelMap model, Pageable pageable, String searchKeyword) {

        model.addAttribute("pageTitle", "질문 목록");

        Page<QuestionDTO> inputPageableQ = null;

        if(searchKeyword == null) {
            inputPageableQ = questionService.list(pageable);
        } else {
            inputPageableQ = questionService.searchList(searchKeyword, pageable);
        }

        // SearchTerm needed for later
        //input of PageDTO.toDtoList

        //output of PageDTO.toDtoList
        Page<QuestionDTO> questionDtoList = new PageDTO().toDtoList(inputPageableQ);
        int startPage = Math.max(1, questionDtoList.getPageable().getPageNumber() - 10);
        int endPage = Math.min(questionDtoList.getTotalPages(), questionDtoList.getPageable().getPageNumber() + 10);
        List<QuestionDTO> questionContentList =  questionDtoList.getContent();



        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("questionDtoList", questionDtoList);
        model.addAttribute("questionList", questionContentList);
        return "/question/list";
    }

    @GetMapping("/detail/{id}")
    public String questionDetailView(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "질문과 답변");
        model.addAttribute("question", questionService.get(id));
        model.addAttribute("answerList", answerService.listByQuestion(id));
        return "/question/get";
    }

    @GetMapping("/modify/{id}")
    public String questionModifyView(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "질문 수정");
        model.addAttribute("question", questionService.get(id));
        return "/question/modify";
    }

    @PostMapping("/modify")
    public String questionModify(QuestionDTO question) {

        questionService.modify(question);
        return "redirect:/question/list";
    }

    @PostMapping("/remove/{id}")
    public String questionRemove(@PathVariable Long id) {

        questionService.remove(id);
        return "redirect:/question/list";

    }
}

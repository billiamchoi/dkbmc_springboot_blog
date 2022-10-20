package com.example.demo.controller;


import java.security.Principal;
import java.util.List;
import java.util.Map;

import com.example.demo.domain.member.MemberDTO;
import com.example.demo.service.AccountService;
import com.example.demo.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.example.demo.domain.question.QuestionDTO;
import com.example.demo.service.QuestionService;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AnswerService answerService;

    @GetMapping("/create")
    public String questionCreateView(Principal principal, ModelMap model) {

        if (principal == null) return "redirect:/account/login";

        MemberDTO member = accountService.get(principal.getName());
        Long author_id = member.getId();
        model.addAttribute("author_id", author_id);
        model.addAttribute("pageTitle", "질문 등록");
        return "/question/create";
    }

    @PostMapping("/create")
    public String questionCreate(QuestionDTO question, @RequestParam("author_id") Long authorId) {

        questionService.create(question, authorId);
        return "redirect:/question/list";
    }

    @GetMapping("/list")
    public String questionListView(@RequestParam Map<String, Object> param, ModelMap model, Pageable pageable, String searchKeyword) {

        model.addAttribute("pageTitle", "질문 목록");

        Page<QuestionDTO> questionDtoList = null;

        if(searchKeyword == null) {
            questionDtoList = questionService.list(pageable);
        } else {
            questionDtoList = questionService.searchList(searchKeyword, pageable);
        }

        int startPage = Math.max(1, questionDtoList.getPageable().getPageNumber() - 10);

        // 검색결과가 없을때 getTotalPages가 0이 나옴 -> 이를 항상 endPage 변수는 1로 고정
        int endPage = (questionDtoList.getTotalPages() == 0)? Math.min(questionDtoList.getTotalPages()+1, questionDtoList.getPageable().getPageNumber() + 10)
                                                            : Math.min(questionDtoList.getTotalPages(), questionDtoList.getPageable().getPageNumber() + 10);
        List<QuestionDTO> questionContentList =  questionDtoList.getContent();

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("questionDtoList", questionDtoList);
        model.addAttribute("questionList", questionContentList);
        return "/question/list";
    }

    @GetMapping("/detail/{id}")
    public String questionDetailView(Principal principal, @PathVariable Long id, Model model) {

        // 로그인 안됬을때는 author_id를 null로 지정
        Long author_id = null;

        if (principal != null) {
            MemberDTO member = accountService.get(principal.getName());
            author_id = member.getId();
        }

        model.addAttribute("author_id", author_id);
        model.addAttribute("pageTitle", "질문과 답변");
        model.addAttribute("question", questionService.get(id));
        model.addAttribute("answerList", answerService.listByQuestion(id));
        return "/question/get";
    }

    @GetMapping("/modify/{id}")
    public String questionModifyView(Principal principal, @PathVariable Long id, Model model) {

        if (principal == null) return "redirect:/account/login";

        model.addAttribute("pageTitle", "질문 수정");
        MemberDTO member = accountService.get(principal.getName());
        Long author_id = member.getId();
        model.addAttribute("author_id", author_id);
        model.addAttribute("question", questionService.get(id));
        return "/question/modify";
    }

    @PostMapping("/modify")
    public String questionModify(QuestionDTO question, @RequestParam("author_id") Long authorId) {

        questionService.modify(question, authorId);
        return "redirect:/question/list";
    }

    @PostMapping("/remove/{id}")
    public String questionRemove(@PathVariable Long id) {

        questionService.remove(id);
        return "redirect:/question/list";

    }
}

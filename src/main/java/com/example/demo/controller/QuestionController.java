package com.example.demo.controller;


import java.security.Principal;
import java.util.List;
import java.util.Map;

import com.example.demo.domain.member.MemberDTO;
import com.example.demo.service.member.AccountService;
import com.example.demo.service.answer.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.example.demo.domain.question.QuestionDTO;
import com.example.demo.service.question.QuestionService;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AnswerService answerService;

    // 질문(question) 생성 페이지를 렌더링할 경우, 사용하는 controller
    // url: /question/create
    // method: GET
    // 사용하는 위치: /question/list 페이지에 좌측 상단 질문 등록하기 버튼
    // pageTitle : 질문 등록
    // principal을 parameter로 설정하여
    // 1. 로그인 안된 사용자에게 로그인 페이지로 이동시키고
    // 2. 질문 작성 완료시 호출되는 questionCreate()에서 사용할 @RequestParam author_id를 여기서 설정함
    @GetMapping("/create")
    public String questionCreateView(Principal principal, ModelMap model) {

        if (principal == null) return "redirect:/account/login";

        MemberDTO member = accountService.get(principal.getName());
        Long author_id = member.getId();
        model.addAttribute("author_id", author_id);
        model.addAttribute("pageTitle", "질문 등록");
        return "/question/create";
    }

    // 질문(question) 목록 로직을 담당하는 controller
    // url: /question/create
    // method: POST
    // 사용하는 위치: /question/create 페이지에 좌측 하단 저장하기 버튼
    @PostMapping("/create")
    public String questionCreate(QuestionDTO question, @RequestParam("author_id") Long authorId) {

        questionService.create(question, authorId);
        return "redirect:/question/list";
    }

    // 질문(question) 목록 페이지를 렌더링할 경우, 사용하는 controller
    // url: /question/list
    // method: GET
    // 사용하는 위치: 사용자가 /question/list에 도달했을 때
    // pageTitle : 질문 목록
    // jpa pageable로 페이징 처리
    // 검색어가 있을경우 list()를 타고 검색어가 없을경우 searchList()를 탐
    // 각 페이지의 시작페이지를 startPage로 끝 페이지를 endPage로 화면에 넘겨줌
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

    // 질문과 답변 페이지를 렌더링할 경우, 사용하는 controller
    // url: /question/detail/{id}
    // method: GET
    // 사용하는 위치: 사용자가 /question/detail/{id}에 도달했을 때
    // pageTitle : 질문과 답변
    // username이 null이면 로그인하지 않은 사용장
    // username이 화면에서 질문 작성자의 이름과 같으면 수정/삭제/readonly 권한 부여
    // author_id는 로그인한 사용자가 질문을 생성할때 사용함
    // username은 자기 글만 수정 삭제 할 수 있도록 화면에서 사용할 수 있도록 설정
    @GetMapping("/detail/{id}")
    public String questionDetailView(Principal principal, @PathVariable Long id, Model model) {

        // 로그인 안됬을때는 author_id를 null로 지정
        Long author_id = null;
        String username = null;

        if (principal != null) {
            MemberDTO member = accountService.get(principal.getName());
            username = principal.getName();
            author_id = member.getId();
        }

        model.addAttribute("username", username);
        model.addAttribute("author_id", author_id);
        model.addAttribute("pageTitle", "질문과 답변");
        model.addAttribute("question", questionService.get(id));
        model.addAttribute("answerList", answerService.listByQuestion(id));
        return "/question/get";
    }

    // 질문 수정 페이지를 렌더링할 경우, 사용하는 controller
    // url: /question/modify/{id}
    // method: GET
    // 사용하는 위치: 사용자가 /question/detail/{id}에 수정하기 버튼
    // pageTitle : 질문 수정
    // 질문 수정 완료시 호출되는 questionModify()에서 사용할 @RequestParam author_id를 여기서 설정함
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

    // 질문(question) 수정 로직을 담당하는 controller
    // url: /question/modify
    // method: POST
    // 사용하는 위치: /question/modify/{id} 페이지에 좌측 하단 수정하기 버튼
    @PostMapping("/modify")
    public String questionModify(QuestionDTO question, @RequestParam("author_id") Long authorId) {

        questionService.modify(question, authorId);
        return "redirect:/question/list";
    }

    // 질문(question) 삭제 로직을 담당하는 controller
    // url: /question/remove/{id}
    // method: POST
    // 사용하는 위치: /question/detail/{id} 페이지에 좌측 하단 삭제하기 버튼
    // @RequestParam으로 바꿔줘야할 것 같음
    @PostMapping("/remove/{id}")
    public String questionRemove(@PathVariable Long id) {

        questionService.remove(id);
        return "redirect:/question/list";
    }

    // 질문(question) 추천 로직을 담당하는 controller
    // url: /question/vote/{id}
    // method: GET
    // 사용하는 위치: /question/detail/{id} 질문 추천 버튼
    // principal을 사용하여 어떤 사용자가 추천을 했는지 설정하고
    // 화면에서 a tag에서 /question/vote/{id}로 이동하여 id를 받아
    // vote함수에 사용자 객체, 질문 객체를 argument로 넘겨줌으로서 해당 사용자가 질문을 추천하는 로직을 구현
    @PreAuthorize("isAuthenticated()") // 이 컨트롤러를 사용할 수 있는 권한이 있는지 먼저 확인
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Long id) {

        QuestionDTO questionDto = questionService.get(id);
        MemberDTO memberDto = accountService.get(principal.getName());
        questionService.vote(questionDto, memberDto);

        return String.format("redirect:/question/detail/%s", id);
    }
}

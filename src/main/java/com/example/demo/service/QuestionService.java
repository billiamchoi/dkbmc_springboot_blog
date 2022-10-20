package com.example.demo.service;

import com.example.demo.domain.question.Question;
import com.example.demo.domain.question.QuestionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface QuestionService {

	Page<QuestionDTO> searchList(String searchKeyword, Pageable pageable);

	void create( QuestionDTO question, Long authorId);

	QuestionDTO get(Long id);

	Page<QuestionDTO> list(Pageable page);

	void modify(QuestionDTO question, Long authorId);

	void remove(Long id);

}

package com.example.demo.service;

import com.example.demo.domain.question.Question;
import com.example.demo.domain.question.QuestionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {

	Page<QuestionDTO> searchList(String searchKeyword, Pageable pageable);

	void create(QuestionDTO question);

	QuestionDTO get(Long id);

	Page<QuestionDTO> list(Pageable page);

	void modify(QuestionDTO question);

	void remove(Long id);

}

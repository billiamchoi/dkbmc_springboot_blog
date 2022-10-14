package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.QuestionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {

	void create(QuestionDTO question);

	Object get(Long id);

	Page<QuestionDTO> list(Pageable Page);

	void modify(QuestionDTO question);

	void remove(Long id);

}

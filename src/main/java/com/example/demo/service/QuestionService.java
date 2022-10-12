package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.QuestionDTO;

public interface QuestionService {

	void create(QuestionDTO question);

	Object get(Long id);

	List<QuestionDTO> list();

	void modify(QuestionDTO question);

	void remove(Long id);

}

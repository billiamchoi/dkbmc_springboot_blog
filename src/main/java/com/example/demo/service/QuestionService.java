package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.QuestionDTO;

public interface QuestionService {

	List<QuestionDTO> list();

	void create(QuestionDTO question);

	Object get(Long id);

}

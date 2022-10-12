package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.repository.QuestionRepository;
import org.springframework.ui.ModelMap;

import com.example.demo.domain.QuestionDTO;

public interface QuestionService {

	List<QuestionDTO> list();

	void create(QuestionDTO question);

}

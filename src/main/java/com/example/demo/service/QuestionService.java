package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.QuestionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {

	Page<QuestionDTO> searchList(String searchKeyword, Pageable pageable);

	void create(QuestionDTO question);

	Object get(Long id);

	Page<QuestionDTO> list(Pageable page);

	void modify(QuestionDTO question);

	void remove(Long id);

}

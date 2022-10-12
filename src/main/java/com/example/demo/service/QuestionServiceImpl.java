package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.example.demo.domain.QuestionDTO;
import com.example.demo.repository.QuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService{
	
	@Autowired
	private QuestionRepository repository;

	@Override
	public List<QuestionDTO> list() {
		
		List<QuestionDTO> QuestionList = (List<QuestionDTO>) repository.findAll(); 
		return QuestionList;
	}

	@Override
	public void create(QuestionDTO question)
	{
		question.setCreate_date(new Date());
		question.setModify_date(new Date());
		this.repository.save(question);
	}

}

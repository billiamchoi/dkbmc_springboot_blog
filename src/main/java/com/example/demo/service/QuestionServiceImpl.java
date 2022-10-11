package com.example.demo.service;

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
	public void create(Map<String, Object> param, ModelMap model) {
		QuestionDTO qd = new QuestionDTO();
		qd.setId( (int) param.get("id") );
		qd.setSubject(param.get("subject")+"");
		qd.setContent(param.get("content")+"");
		repository.save(qd);
	}
	
}

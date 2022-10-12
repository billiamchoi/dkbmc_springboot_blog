package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Override
	public void modify(QuestionDTO question) {
		QuestionDTO q = new QuestionDTO();
		Optional<QuestionDTO> qq = repository.findById(question.getId());
		Date creted_date = qq.get().getCreate_date();
		question.setCreate_date(creted_date);
		question.setModify_date(new Date());
		this.repository.save(question);
	}

	@Override
	public void remove(Long id) {
		repository.deleteById(id);
	}

	@Override
	public QuestionDTO get(Long id) {
		QuestionDTO q = new QuestionDTO();
		Optional<QuestionDTO> qq = repository.findById(id);
		q = qq.get();
		return q;
	}

}

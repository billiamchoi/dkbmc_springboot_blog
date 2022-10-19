package com.example.demo.service;

import java.util.Date;
import java.util.Optional;


import com.example.demo.domain.PageDTO;
import com.example.demo.domain.question.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.domain.question.QuestionDTO;
import com.example.demo.repository.QuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService{
	
	@Autowired
	private QuestionRepository repository;

	@Override
	public Page<QuestionDTO> list(Pageable pageable) {
		Page<Question> questionList = repository.findAllByOrderByIdAsc(pageable);
		Page<QuestionDTO> questionDtoList = new  PageDTO().toDtoList(questionList);
		return questionDtoList;
	}

	@Override
	public Page<QuestionDTO> searchList(String searchKeyword, Pageable pageable) {

		Page<Question> questionSearchList = repository.findBySubjectContainingOrContentContainingOrderById(searchKeyword, searchKeyword, pageable);

		Page<QuestionDTO> questionDtoSearchList = new  PageDTO().toDtoList(questionSearchList);
		return questionDtoSearchList;
	}

	@Override
	public void create(QuestionDTO questionDto) {

		questionDto.setCreate_date(new Date());
		questionDto.setModify_date(new Date());
		Question question = questionDto.toEntity();
		this.repository.save(question);
	}

	@Override
	public void modify(QuestionDTO questionDto) {

		Question question = new Question();
		Optional<Question> qq = repository.findById(questionDto.getId());
		Date created_date = qq.get().getCreate_date();
		questionDto.setCreate_date(created_date);
		questionDto.setModify_date(new Date());
		question = questionDto.toEntity();
		this.repository.save(question);
	}

	@Override
	public void remove(Long id) {
		repository.deleteById(id);
	}

	@Override
	public QuestionDTO get(Long id) {

		QuestionDTO questionDto = new QuestionDTO();
		Optional <Question> qq = repository.findById(id);
		Question question = qq.get();
		questionDto = question.toDto();
		return questionDto;
	}

}

package com.example.demo.service;

import java.security.Principal;
import java.util.Date;
import java.util.Optional;


import com.example.demo.domain.PageDTO;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.question.Question;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.domain.question.QuestionDTO;
import com.example.demo.repository.QuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService{
	
	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Override
	public Page<QuestionDTO> list(Pageable pageable) {
		Page<Question> questionList = questionRepository.findAllByOrderByIdAsc(pageable);
		Page<QuestionDTO> questionDtoList = new  PageDTO().toDtoList(questionList);
		return questionDtoList;
	}

	@Override
	public Page<QuestionDTO> searchList(String searchKeyword, Pageable pageable) {

		Page<Question> questionSearchList = questionRepository.findBySubjectContainingOrContentContainingOrderById(searchKeyword, searchKeyword, pageable);

		Page<QuestionDTO> questionDtoSearchList = new  PageDTO().toDtoList(questionSearchList);
		return questionDtoSearchList;
	}

	@Override
	public void create(QuestionDTO questionDto, Long authorId) {

		Member member = memberRepository.findById(authorId).get();

		questionDto.setMember(member);
		questionDto.setCreate_date(new Date());
		questionDto.setModify_date(new Date());
		Question question = questionDto.toEntity();
		this.questionRepository.save(question);
	}

	@Override
	public void modify(QuestionDTO questionDto, Long authorId) {

		Question question = new Question();
		Member member = memberRepository.findById(authorId).get();

		Optional<Question> qq = questionRepository.findById(questionDto.getId());
		Date created_date = qq.get().getCreate_date();
		questionDto.setMember(member);
		questionDto.setCreate_date(created_date);
		questionDto.setModify_date(new Date());
		question = questionDto.toEntity();
		this.questionRepository.save(question);
	}

	@Override
	public void remove(Long id) {
		questionRepository.deleteById(id);
	}

	@Override
	public QuestionDTO get(Long id) {

		QuestionDTO questionDto = new QuestionDTO();
		Optional <Question> qq = questionRepository.findById(id);
		Question question = qq.get();
		questionDto = question.toDto();
		return questionDto;
	}

}

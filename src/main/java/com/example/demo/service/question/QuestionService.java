package com.example.demo.service.question;

import com.example.demo.domain.member.Member;
import com.example.demo.domain.member.MemberDTO;
import com.example.demo.domain.question.Question;
import com.example.demo.domain.question.QuestionDTO;
import com.example.demo.rest.response.QuestionResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

public interface QuestionService {

	//For Web

	// 검색 질문 목록 조회
	Page<QuestionDTO> searchList(String searchKeyword, Pageable pageable);

	// 질문 생성
	void create( QuestionDTO question, Long authorId);

	// 특정 질문 조회
	QuestionDTO get(Long id);

	// 검색을 안할 경우 질문 목록 조회
	Page<QuestionDTO> list(Pageable page);

	// 질문 수정
	void modify(QuestionDTO question, Long authorId);

	// 질문 삭제
	void remove(Long id);

	// 질문 추천
	void vote(QuestionDTO questionDto, MemberDTO memberDto);

	//For rest api

	// 질문 목록 조회 api
	List<QuestionResponseDTO> restGetAll();

	// 특정 질문 조회 api
	QuestionResponseDTO restGetOne(Long id);

	// 질문 생성 api
	QuestionResponseDTO restCreate(String jwtToken, QuestionDTO questionDto);

	// 질문 수정 api
	QuestionResponseDTO restModify(String jwtToken, Long id, QuestionDTO questionDto);

	// 질문 삭제 api
	String restRemove(String jwtToken, Long id);
}

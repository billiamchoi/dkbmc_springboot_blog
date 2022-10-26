package com.example.demo.service.question;

import com.example.demo.domain.member.Member;
import com.example.demo.domain.member.MemberDTO;
import com.example.demo.domain.question.Question;
import com.example.demo.domain.question.QuestionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface QuestionService {

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

}

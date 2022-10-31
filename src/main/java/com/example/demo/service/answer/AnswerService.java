package com.example.demo.service.answer;

import com.example.demo.domain.answer.AnswerDTO;
import com.example.demo.domain.member.MemberDTO;
import com.example.demo.rest.response.AnswerResponseDTO;

import java.util.List;

public interface AnswerService {

    //For Web

    // 답변 생성
    void create(AnswerDTO answer, Long questionId, Long authorId);

    // 특정 답변 조회
    AnswerDTO get(Long id);

    // 질문에 속해있는 답변 조회
    List<AnswerDTO> listByQuestion(Long id);

    // 답변 수정
    void modify(AnswerDTO answer, Long id, Long authorId);

    // 답변 삭제
    void remove(Long id);

    // 답변 추천
    void vote(AnswerDTO answerDto, MemberDTO memberDto);

    //For rest api

    // 질문 id로 답변 목록 조회 api
    List<AnswerResponseDTO> restGetAllByQuestionId(Long QuestionId);

    // 질문 id로 해당 질문에 속한 답변 생성 api
    AnswerResponseDTO restCreateByQuestionId(String jwtToken, Long questionId, AnswerDTO answerDto);

    // 답변 수정 api
    AnswerResponseDTO restModify(String jwtToken, Long questionId, Long answerId, AnswerDTO answerDto);

    // 답변 삭제 api
    String restRemove(String jwtToken, Long answerId);

    // 답변 추천 api
    AnswerResponseDTO restVote(String jwtToken, AnswerDTO answerDto);
}

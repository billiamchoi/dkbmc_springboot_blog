package com.example.demo.repository;

import com.example.demo.domain.answer.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

   // parameter id로 Question에 속해 있는 Answer를 모두 조회하고 그것을 AnswerId로 정렬하는 함수
   List<Answer> findAnswerByQuestionIdOrderById(Long id);

}

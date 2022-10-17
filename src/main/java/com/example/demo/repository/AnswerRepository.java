package com.example.demo.repository;

import com.example.demo.domain.answer.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
   List<Answer> findAnswerByQuestionIdOrderById(Long id);

}

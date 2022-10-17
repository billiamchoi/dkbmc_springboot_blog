package com.example.demo.repository;

import com.example.demo.domain.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.question.QuestionDTO;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findAllByOrderByIdAsc(Pageable pageable);

    Page<Question> findBySubjectContainingOrContentContainingOrderById(String subject, String content, Pageable pageable);
}
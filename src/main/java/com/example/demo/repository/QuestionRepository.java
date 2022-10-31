package com.example.demo.repository;

import com.example.demo.domain.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    // 모든 Question 레코드를 id의 내림차순으로 조회함
    Page<Question> findAllByOrderByIdDesc(Pageable pageable);

    // 질문의 제목 혹은 내용 검색기능을 위해 Containing함수를 활용하여
    // or 조건으로 조합하고 OrderByIdDesc까지하여 id의 내림차순으로 조회함
    Page<Question> findBySubjectContainingOrContentContainingOrderByIdDesc(String subject, String content, Pageable pageable);
}
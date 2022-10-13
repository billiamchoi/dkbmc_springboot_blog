package com.example.demo.repository;

import com.example.demo.domain.AnswerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerDTO, Long> {
   //@Query( "SELECT ans.id FROM answer ans WHERE ans.question_id = :question_id")
   List<AnswerDTO> findAnswerDTOByQuestionId(Long id);
}

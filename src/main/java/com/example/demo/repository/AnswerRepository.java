package com.example.demo.repository;

import com.example.demo.domain.AnswerDTO;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerDTO, Long> {
   List<AnswerDTO> findAllByOrderByIdAsc();
   List<AnswerDTO> findAnswerDTOByQuestionId(Long id);

}

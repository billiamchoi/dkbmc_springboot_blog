package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.QuestionDTO;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionDTO, Long> {
    Page<QuestionDTO> findAllByOrderByIdAsc(Pageable pageable);
}
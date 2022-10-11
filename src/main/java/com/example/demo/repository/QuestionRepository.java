package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.QuestionDTO;

@Repository
public interface QuestionRepository extends CrudRepository<QuestionDTO, Long> {

}

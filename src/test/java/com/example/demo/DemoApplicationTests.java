package com.example.demo;


import com.example.demo.domain.AnswerDTO;
import com.example.demo.domain.QuestionDTO;
import com.example.demo.repository.AnswerRepository;
import com.example.demo.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
@SpringBootTest
class DemoApplicationTests {

	@Autowired
	AnswerRepository repository;

	@Test
	public void contextLoads() {

		Object a = repository.findAnswerDTOByQuestionId(2L);

	}

}

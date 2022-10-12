package com.example.demo;

import com.example.demo.domain.QuestionDTO;
import com.example.demo.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	QuestionRepository questionRepository;

	@Test
	void contextLoads() {
		QuestionDTO qt = new QuestionDTO();

		qt.setSubject("dd");
		qt.setContent("ff");
		qt.setCreate_date(new Date());
				qt.setModify_date(new Date());
				this.questionRepository.save(qt);
	}

}

package com.example.demo.service.question;

import java.util.Date;
import java.util.Optional;


import com.example.demo.domain.PageDTO;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.member.MemberDTO;
import com.example.demo.domain.question.Question;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.domain.question.QuestionDTO;
import com.example.demo.repository.QuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService{
	
	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private MemberRepository memberRepository;

	// 검색을 안할 경우 질문 조회 서비스
	// controller로 부터 받은 Pageable 객체를 Repository에 넘겨주고
	// toDtoList()를 사용하여 Page<QuestionDTO> 형태로 반환함
	@Override
	public Page<QuestionDTO> list(Pageable pageable) {
		Page<Question> questionList = questionRepository.findAllByOrderByIdAsc(pageable);
		Page<QuestionDTO> questionDtoList = new  PageDTO().toDtoList(questionList);
		return questionDtoList;
	}

	// 검색을 할 경우 질문 조회 서비스
	// controller로 부터 받은 searchKeyword와  Pageable 객체를 Repository에 넘겨주고
	// toDtoList()를 사용하여 Page<QuestionDTO> 형태로 반환함
	@Override
	public Page<QuestionDTO> searchList(String searchKeyword, Pageable pageable) {

		Page<Question> questionSearchList = questionRepository.findBySubjectContainingOrContentContainingOrderById(searchKeyword, searchKeyword, pageable);

		Page<QuestionDTO> questionDtoSearchList = new  PageDTO().toDtoList(questionSearchList);
		return questionDtoSearchList;
	}

	@Override
	public void create(QuestionDTO questionDto, Long authorId) {

		Member member = memberRepository.findById(authorId).get();
		questionDto.setMember(member);
		questionDto.setCreate_date(new Date());
		questionDto.setModify_date(new Date());
		Question question = questionDto.toEntity();
		this.questionRepository.save(question);
	}

	// 답변 생성 서비스
	// controller로부터 질문 객체, 글쓴이id를 받아 Repository까지 넘겨줌
	// repositoy에서 나온 결과값은 Optional이기때문에 .get()을 통해 Entity로 변환하여
	// 전체적인 Set을 하고 save()로 저장함
	@Override
	public void modify(QuestionDTO questionDto, Long authorId) {

		Question question = new Question();
		Member member = memberRepository.findById(authorId).get();

		Optional<Question> qq = questionRepository.findById(questionDto.getId());
		Date created_date = qq.get().getCreate_date();
		questionDto.setVoter(qq.get().getVoter());
		questionDto.setMember(member);
		questionDto.setCreate_date(created_date);
		questionDto.setModify_date(new Date());
		question = questionDto.toEntity();
		this.questionRepository.save(question);
	}

	// 질문 삭제 서비스
	// controller로부터 id를 받아 Repository까지 넘겨줌
	@Override
	public void remove(Long id) {
		questionRepository.deleteById(id);
	}

	// 특정 질문 조회
	// controller로부터 id를 받아 특정 답변 조회 후 Repository까지 넘겨줌
	// 해당 QuestionDTO 반환
	@Override
	public QuestionDTO get(Long id) {

		QuestionDTO questionDto = new QuestionDTO();
		Optional <Question> qq = questionRepository.findById(id);
		Question question = qq.get();
		questionDto = question.toDto();
		return questionDto;
	}

	// 질문 추천 서비스
	// controller로부터 QuestionDTO 객체, MemberDTO 객체를 받아
	// memberDto를 Entity로 변환하여 questionDto voter에 추가하고
	// questionDto를 Entity로 변환하여 Repository까지 넘겨줌
	@Override
	public void vote(QuestionDTO questionDto, MemberDTO memberDto) {

		questionDto.getVoter().add(memberDto.toEntity());
		this.questionRepository.save(questionDto.toEntity());
	}

}

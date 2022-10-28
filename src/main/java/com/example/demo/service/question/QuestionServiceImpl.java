package com.example.demo.service.question;

import java.util.*;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.config.jwt.JwtProperties;
import com.example.demo.domain.PageDTO;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.member.MemberDTO;
import com.example.demo.domain.question.Question;
import com.example.demo.repository.MemberRepository;
import com.example.demo.rest.response.QuestionResponseDTO;
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

	// 질문 생성 서비스
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

	@Override
	public List<QuestionResponseDTO> restGetAll() {

		List<Question> questionList = questionRepository.findAll();
		List<QuestionResponseDTO> questionResponseDtoList = new ArrayList<QuestionResponseDTO>();

		for (Question q : questionList) {
			QuestionResponseDTO questionResponseDto = new QuestionResponseDTO();
			questionResponseDto.setId(q.getId());
			questionResponseDto.setContent(q.getContent());
			questionResponseDto.setSubject(q.getSubject());
			questionResponseDto.setCreate_date(q.getCreate_date());
			questionResponseDto.setModify_date(q.getModify_date());
			questionResponseDto.setAuthor_id(q.getMember().getId());
			questionResponseDto.setVote_count(q.getVoter().size());
			questionResponseDtoList.add(questionResponseDto);
		}

		return questionResponseDtoList;
	}

	@Override
	public QuestionResponseDTO restGetOne(Long id) {

		Question question = questionRepository.findById(id).get();
		QuestionResponseDTO questionResponseDto = new QuestionResponseDTO();

		// builder를 이용해서 set 한번에하는 방법으로 수정할 필요가 있음
		questionResponseDto.setId(question.getId());
		questionResponseDto.setContent(question.getContent());
		questionResponseDto.setSubject(question.getSubject());
		questionResponseDto.setCreate_date(question.getCreate_date());
		questionResponseDto.setModify_date(question.getModify_date());
		questionResponseDto.setAuthor_id(question.getMember().getId());
		questionResponseDto.setVote_count(question.getVoter().size());

		return questionResponseDto;
	}

	@Override
	public QuestionResponseDTO restCreate(String jwtToken, QuestionDTO questionDto) {

		String token = jwtToken.replace(JwtProperties.TOKEN_PREFIX, "");
		String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
				.getClaim("username").asString();

		Member member = memberRepository.findByUsername(username).get();

		questionDto.setMember(member);
		questionDto.setCreate_date(new Date());
		questionDto.setModify_date(new Date());
		questionDto.setVoter(Collections.emptySet());
		Question savedQuestion = questionRepository.save(questionDto.toEntity());
		QuestionResponseDTO questionResponseDto = savedQuestion.toResponseDto();

		return questionResponseDto;
	}

	@Override
	public QuestionResponseDTO restModify(String jwtToken, Long id, QuestionDTO questionDto) {

		Question question = questionRepository.findById(id).get();
		QuestionResponseDTO questionResponseDto = new QuestionResponseDTO();

		String token = jwtToken.replace(JwtProperties.TOKEN_PREFIX, "");
		String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
				.getClaim("username").asString();

		Long memberId = memberRepository.findByUsername(username).get().getId();

		Long authorId = question.getMember().getId();

		if (memberId.equals(authorId)) {
			// api 요청자의 id와 글쓴이의 id가 같다면
			questionResponseDto.setId(question.getId());
			questionResponseDto.setSubject(questionDto.getSubject());
			questionResponseDto.setContent(questionDto.getContent());
			questionResponseDto.setCreate_date(question.getCreate_date());
			questionResponseDto.setModify_date(new Date());
			questionResponseDto.setAuthor_id(question.getMember().getId());
			questionResponseDto.setVote_count(question.getVoter().size());

			questionRepository.save(questionResponseDto.toEntity(question.getAnswer(), question.getMember(), question.getVoter()));
		} else {
			//다르면 401에러 및 에러메세지 : 글쓴이가 다릅니다 띄어줘야됨
			// 일단 null로 띄어보자
			questionResponseDto = null;
		}

		return questionResponseDto;
	}

	@Override
	public String restRemove(String jwtToken, Long id) {

		String message = null;

		Question question = questionRepository.findById(id).get();
		QuestionResponseDTO questionResponseDto = new QuestionResponseDTO();

		String token = jwtToken.replace(JwtProperties.TOKEN_PREFIX, "");
		String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
				.getClaim("username").asString();

		Long memberId = memberRepository.findByUsername(username).get().getId();

		Long authorId = question.getMember().getId();

		if (memberId.equals(authorId)) {
			// api 요청자의 id와 글쓴이의 id가 같다면
			questionRepository.deleteById(id);

			message = "success";
		} else {
			//다르면 401에러
			message = "fail : unauthorized";
		}


		return message;
	}


}

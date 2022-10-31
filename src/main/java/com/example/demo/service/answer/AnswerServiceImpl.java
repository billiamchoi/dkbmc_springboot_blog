package com.example.demo.service.answer;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.config.jwt.JwtProperties;
import com.example.demo.domain.answer.Answer;
import com.example.demo.domain.answer.AnswerDTO;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.member.MemberDTO;
import com.example.demo.domain.question.Question;
import com.example.demo.repository.AnswerRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.rest.response.AnswerResponseDTO;
import com.example.demo.rest.response.QuestionResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private MemberRepository memberRepository;


    // 답변 생성 서비스
    // controller로부터 답변 객체, 답변이 속해있는 질문id, 글쓴이id를 받아 Repository까지 넘겨줌
    // 변수 question에 질문id로 질문을 찾아 set
    // 변수 memeber에 글쓴이id로 멤버을 찾아  set
    // 여기서 작성 일시, 수정 일시 모두 현재 set
    @Override
    public void create(AnswerDTO answerDto, Long questionId, Long authorId) {

        Question question = questionRepository.findById(questionId).get();
        Member member = memberRepository.findById(authorId).get();

        answerDto.setQuestion(question);
        answerDto.setMember(member);
        answerDto.setCreate_date(new Date());
        answerDto.setModify_date(new Date());
        Answer answer = answerDto.toEntity();
        this.answerRepository.save(answer);
    }

    // 질문에 해당한 답변 조회 서비스
    // controller로 부터 받은 id로 Repository에 전달해 특정 질문 id에 속한 답변 조회하여
    // List<AnswerDTO> 형태로 반환함
    @Override
    public List<AnswerDTO> listByQuestion(Long id) {

        List<Answer> answerList = answerRepository.findAnswerByQuestionIdOrderById(id);
        List<AnswerDTO> answerDtoList = new AnswerDTO().toDtoList(answerList);
        return answerDtoList;
    }

    // 답변 수정 서비스
    // controller로부터 답변 객체, 답변이 속해있는 질문id, 글쓴이id를 받아 Repository까지 넘겨줌
    // 들어온 답변 객체 getter를 통해 해당 Entity를 찾고 수정
    @Override
    public void modify(AnswerDTO answerDto, Long questionId, Long authorId) {

        Answer answer = new Answer();
        Question question = questionRepository.findById(questionId).get();
        Optional<Answer> aa = answerRepository.findById(answerDto.getId());
        Member member = memberRepository.findById(authorId).get();

        Date create_date = aa.get().getCreate_date();
        answerDto.setQuestion(question);
        answerDto.setVoter(aa.get().getVoter());
        answerDto.setMember(member);
        answerDto.setCreate_date(create_date);
        answerDto.setModify_date(new Date());
        answer = answerDto.toEntity();
        this.answerRepository.save(answer);
    }

    // 답변 삭제 서비스
    // controller로부터 id를 받아 Repository까지 넘겨줌
    @Override
    public void remove(Long id) {
        answerRepository.deleteById(id);
    }

    // 답변 추천 서비스
    // controller로부터 AnswerDTO 객체, MemberDTO 객체를 받아
    // memberDto를 Entity로 변환하여 answerDto voter에 추가하고
    // answerDto를 Entity로 변환하여 Repository까지 넘겨줌
    @Override
    public void vote(AnswerDTO answerDto, MemberDTO memberDto) {

        answerDto.getVoter().add(memberDto.toEntity());;
        this.answerRepository.save(answerDto.toEntity());
    }


    // 특정 답변 조회
    // controller로부터 id를 받아 특정 답변 조회 후 Repository까지 넘겨줌
    // 해당 AnswerDTO를 반환
    @Override
    public AnswerDTO get(Long id){

        AnswerDTO answerDto = new AnswerDTO();
        Optional <Answer> ansOpt =  this.answerRepository.findById(id);
        Answer answer = ansOpt.get();
        answerDto = answer.toDto();
        return answerDto;
    }

    @Override
    public List<AnswerResponseDTO> restGetAllByQuestionId(Long QuestionId) {
        List<Answer> answerList = answerRepository.findAnswerByQuestionIdOrderById(QuestionId);
        List<AnswerResponseDTO> answerResponseDtoList = new ArrayList<AnswerResponseDTO>();

        for (Answer a : answerList ) {
            AnswerResponseDTO answerResponseDto = new AnswerResponseDTO();
            answerResponseDto.setId(a.getId());
            answerResponseDto.setContent(a.getContent());
            answerResponseDto.setQuestion_id(a.getQuestion().getId());
            answerResponseDto.setAuthor_id(a.getMember().getId());
            answerResponseDto.setVote_count(a.getVoter().size());
            answerResponseDto.setCreate_date(a.getCreate_date());
            answerResponseDto.setModify_date(a.getModify_date());
            answerResponseDtoList.add(answerResponseDto);
        }
        return answerResponseDtoList;
    }

    @Override
    public AnswerResponseDTO restCreateByQuestionId(String jwtToken, Long questionId, AnswerDTO answerDto) {

        String token = jwtToken.replace(JwtProperties.TOKEN_PREFIX, "");
        String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
                .getClaim("username").asString();

        Member member = memberRepository.findByUsername(username).get();
        Question question = questionRepository.findById(questionId).get();

        answerDto.setMember(member);
        answerDto.setQuestion(question);
        answerDto.setVoter(Collections.emptySet());
        answerDto.setCreate_date(new Date());
        answerDto.setModify_date(new Date());
        Answer savedAnswer = answerRepository.save(answerDto.toEntity());

        return savedAnswer.toResponseDto();
    }

    @Override
    public AnswerResponseDTO restModify(String jwtToken, Long questionId, Long answerId, AnswerDTO answerDto) {

        Answer answer = answerRepository.findById(answerId).get();
        AnswerResponseDTO answerResponseDto = new AnswerResponseDTO();

        String token = jwtToken.replace(JwtProperties.TOKEN_PREFIX, "");
        String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
                .getClaim("username").asString();

        Long memberId = memberRepository.findByUsername(username).get().getId();
        Long authorId = answer.getMember().getId();

        if (memberId.equals(authorId)) {
            // api 요청자의 id와 글쓴이 id가 같다면
            answerResponseDto.setId(answerId);
            answerResponseDto.setContent(answerDto.getContent());
            answerResponseDto.setAuthor_id(answer.getMember().getId());
            answerResponseDto.setVote_count(answer.getVoter().size());
            answerResponseDto.setQuestion_id(questionId);
            answerResponseDto.setCreate_date(answer.getCreate_date());
            answerResponseDto.setModify_date(new Date());

            answerRepository.save(answerResponseDto.toEntity(answer.getQuestion(), answer.getMember(), answer.getVoter()));
        } else {
            answerResponseDto = null;
        }

        return answerResponseDto;
    }

    @Override
    public String restRemove(String jwtToken, Long answerId) {

        String message = null;

        Answer answer = answerRepository.findById(answerId).get();

        String token = jwtToken.replace(JwtProperties.TOKEN_PREFIX, "");
        String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
                .getClaim("username").asString();

        Long memberId = memberRepository.findByUsername(username).get().getId();

        Long authorId = answer.getMember().getId();

        if (memberId.equals(authorId)) {
            // api 요청자의 id와 글쓴이의 id가 같다면
            answerRepository.deleteById(answerId);

            message = "success";
        } else {
            //다르면 401에러
            message = "fail : unauthorized";
        }


        return message;
    }

    @Override
    public AnswerResponseDTO restVote(String jwtToken, AnswerDTO answerDto) {

        // 먼가 여기서 jwtToken 없는 비 회원 분기 해야 할 것 같은...
        String token = jwtToken.replace(JwtProperties.TOKEN_PREFIX, "");
        String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
                .getClaim("username").asString();

        Member member = memberRepository.findByUsername(username).get();

        answerDto.getVoter().add(member);
        Answer savedAnswer = answerRepository.save(answerDto.toEntity());

        return savedAnswer.toResponseDto();
    }
}

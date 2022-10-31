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


    // 특정 답변 조회 서비스
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

    // 질문 id에 속한 답변 목록 조회 api 서비스
    // controller로부터 질문 id를 받아 answerRepository까지 넘겨줌
    // answerList를 answerRepository에서 받아 answerResponseDtoList에 for loop으로 set후
    // answerResponseDtoList를 반환
    @Override
    public List<AnswerResponseDTO> restGetAllByQuestionId(Long id) {
        List<Answer> answerList = answerRepository.findAnswerByQuestionIdOrderById(id);
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

    // 질문 id에 속한 답변 생성 api 서비스
    // controller로부터 jwt token,  질문id, answerDto를 받아와
    // jwt token에 암호화된 username를 String 타입의 username으로 초기화
    // answerDto에 필요한 member, question, voter, create_date, modify_date를 set함
    // answerRepository.save()로 답변을 저장하고
    // 저장된 값을 AnswerResponseDTO 객체 형태로 반환함
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

    // 질문 id에 속한 답변 수정 api 서비스
    // jwt token에 암호화된 username를 String 타입의 username으로 초기화
    // username으로 조회한 사용자 id를 memberId라 하고
    // 수정하는 답변의 글쓴이 id를 authorId라 할때
    // equals 함수를 사용하여 memberId라 authorId가 같다면
    //      answerResponseDto에 필요한 정보를 set하고
    //      answerRepository.save()를 통해 수정된 사항을 저장
    //      이때 답변이 속한 Question 객체, 답변 글쓴이의 Member 객체, 답변을 추천한 Memeber의 집합을
    //      함수 toEntity argument 넘겨줌
    // memberId와 authorId가 다르다면
    //      answerResponseDto에 null을 초기화하여 controller에서 분기하여
    //      http status code 401 UNAUTHORIZED를 반환하도록 함
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

    // 질문 id에 속한 답변 삭제 api 서비스
    // jwt token에 암호화된 username를 String 타입의 username으로 초기화
    // username으로 조회한 사용자 id를 memberId라 하고
    // 삭제하는 답변의 글쓴이 id를 authorId라 할때
    // equals 함수를 사용하여 memberId와 authorId가 같다면
    //     answerRepository.deleteById(answerId)로
    //     controller에서 받은 answerId로 답변 삭제
    //     message에 "success"를 초기화하여 반환
    // memberId와 authorId가 다르다면
    //      message에 "fail : unauthorized"를 초기화하여 반환
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

    // 답변 추천 api 서비스
    // jwt token에 암호화된 username를 String 타입의 username으로 초기화
    // username으로 findByUsername함수로 member entity를 조회하여
    // member 객체로 초기화
    // answerDto.getVoter().add(member)로 멤버 객체를 answerDto에 추가
    // save로 answerDto를 저장
    // Answer 객체를 toResponseDto()로 AnswerResponseDTO로 변환해서 반환
    @Override
    public AnswerResponseDTO restVote(String jwtToken, AnswerDTO answerDto) {

        String token = jwtToken.replace(JwtProperties.TOKEN_PREFIX, "");
        String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
                .getClaim("username").asString();

        Member member = memberRepository.findByUsername(username).get();
        answerDto.getVoter().add(member);
        Answer savedAnswer = answerRepository.save(answerDto.toEntity());

        return savedAnswer.toResponseDto();
    }
}

package com.example.demo.service.answer;

import com.example.demo.domain.answer.Answer;
import com.example.demo.domain.answer.AnswerDTO;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.member.MemberDTO;
import com.example.demo.domain.question.Question;
import com.example.demo.repository.AnswerRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


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
}

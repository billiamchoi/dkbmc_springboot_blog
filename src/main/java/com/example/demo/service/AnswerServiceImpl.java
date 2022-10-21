package com.example.demo.service;

import com.example.demo.domain.answer.Answer;
import com.example.demo.domain.answer.AnswerDTO;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.member.MemberDTO;
import com.example.demo.domain.question.Question;
import com.example.demo.domain.question.QuestionDTO;
import com.example.demo.repository.AnswerRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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

    @Override
    public List<AnswerDTO> listByQuestion(Long id) {

        List<Answer> answerList = answerRepository.findAnswerByQuestionIdOrderById(id);
        List<AnswerDTO> answerDtoList = new AnswerDTO().toDtoList(answerList);
        return answerDtoList;
    }

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

    @Override
    public void remove(Long id) {
        answerRepository.deleteById(id);
    }

    @Override
    public void vote(AnswerDTO answerDto, MemberDTO memberDto) {

        answerDto.getVoter().add(memberDto.toEntity());;
        this.answerRepository.save(answerDto.toEntity());
    }

    @Override
    public AnswerDTO get(Long id){

        AnswerDTO answerDto = new AnswerDTO();
        Optional <Answer> ansOpt =  this.answerRepository.findById(id);
        Answer answer = ansOpt.get();
        answerDto = answer.toDto();
        return answerDto;
    }
}

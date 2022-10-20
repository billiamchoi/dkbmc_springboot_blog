package com.example.demo.service;

import com.example.demo.domain.answer.Answer;
import com.example.demo.domain.answer.AnswerDTO;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.question.Question;
import com.example.demo.domain.question.QuestionDTO;
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
    public void modify(AnswerDTO answerDto, Long id) {

        Answer answer = new Answer();
        Question question = questionRepository.findById(id).get();
        Optional<Answer> aa = answerRepository.findById(answerDto.getId());

        Date create_date = aa.get().getCreate_date();
        answerDto.setQuestion(question);
        answerDto.setCreate_date(create_date);
        answerDto.setModify_date(new Date());
        answer = answerDto.toEntity();
        this.answerRepository.save(answer);
    }

    @Override
    public void remove(Long id) {
        answerRepository.deleteById(id);
    }
}

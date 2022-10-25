package com.example.demo.service.member;

import com.example.demo.domain.member.Member;
import com.example.demo.domain.member.MemberDTO;
import com.example.demo.domain.member.MemberModifyDTO;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private MemberRepository repository;

    @Override
    public MemberDTO get(String username) {

        MemberDTO memberDto = new MemberDTO();
        Optional<Member> memberOptional = repository.findByUsername(username);
        Member member = memberOptional.get();
        memberDto = member.toDtO();
        return memberDto;
    }

    @Override
    public void modify(MemberModifyDTO memberModifyDTO) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberModifyDTO.setPassword2(passwordEncoder.encode(memberModifyDTO.getPassword2()));

        this.repository.updateMember( memberModifyDTO.getPassword2(), memberModifyDTO.getEmail(), memberModifyDTO.getId());
    }

    @Override
    public void remove(Long id) {

        repository.deleteById(id);
    }

    @Override
    public void withdrawal(Long id) {

        repository.withdrawalMember(id);
    }
}

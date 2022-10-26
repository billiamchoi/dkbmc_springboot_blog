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

    // 특정 멤버 조회
    // controller로부터 username을 받아 특정 멤버 조회 후 Repository까지 넘겨줌
    // 해당 MemberDTO를 반환
    @Override
    public MemberDTO get(String username) {

        MemberDTO memberDto = new MemberDTO();
        Optional<Member> memberOptional = repository.findByUsername(username);
        Member member = memberOptional.get();
        memberDto = member.toDto();
        return memberDto;
    }

    // 멤버 수정 서비스
    // controller로부터 멤버 객체 받아
    // Controller에서 받은 String 비밀번호를 -> hash로 변환후 Repository까지 넘겨줌
    @Override
    public void modify(MemberModifyDTO memberModifyDTO) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberModifyDTO.setPassword2(passwordEncoder.encode(memberModifyDTO.getPassword2()));

        this.repository.updateMember( memberModifyDTO.getPassword2(), memberModifyDTO.getEmail(), memberModifyDTO.getId());
    }

    // 멤버 삭제 서비스
    // controller로부터 id를 받아 Repository까지 넘겨줌
    @Override
    public void remove(Long id) {

        repository.deleteById(id);
    }

    // 멤버 탈퇴 서비스
    // controller로부터 id를 받아 Repository까지 넘겨줌
    @Override
    public void withdrawal(Long id) {

        repository.withdrawalMember(id);
    }
}

package com.example.demo.service;

import com.example.demo.domain.member.Role;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.member.MemberDTO;
import com.example.demo.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {
    private MemberRepository memberRepository;

    @Transactional
    public Long joinUser(MemberDTO userDto) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDto.setPassword1(passwordEncoder.encode(userDto.getPassword1()));
        userDto.setPassword2(passwordEncoder.encode(userDto.getPassword2()));

        return memberRepository.save(userDto.toEntity()).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> userWrapper = memberRepository.findByUsername(username);

        Member userEntity = userWrapper.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (("admin").equals(username)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        return new User(userEntity.getUsername(), userEntity.getPassword(),authorities);
    }
}

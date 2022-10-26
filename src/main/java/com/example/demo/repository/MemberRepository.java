package com.example.demo.repository;

import com.example.demo.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // parameter username으로 Member를 조회하는 함수
    Optional<Member> findByUsername(String username);

    // @Query로 들어온 parameter들로 raw query를 작성
    // @Transactional로 transaction처럼 작동하도록 함
    // @Modifying으로 레코드를 수정하는 쿼리임을 명시함
    // 들어온 id인 Member의 password와 email를 수정
    @Transactional
    @Modifying
    @Query("UPDATE Member m " +
            "SET  m.password = :password, m.email = :email " +
            "WHERE m.id = :id")
    void updateMember( @Param("password") String password, @Param("email") String email, @Param("id") Long id);

    // 들어온 id인 Member의 password를 ''로 isActive를 false로 설정함
    // 회원 탈퇴시 회원 정보는 지우지 않고 isActive로 탈퇴회원 여부를 판단함
    @Transactional
    @Modifying
    @Query("UPDATE Member m " +
            "SET  m.password = '', m.isActive = false " +
            "WHERE m.id = :id")
    void withdrawalMember( @Param("id") Long id);
}

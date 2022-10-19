package com.example.demo.repository;

import com.example.demo.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    @Transactional
    @Modifying
    @Query("UPDATE Member m " +
            "SET  m.password = :password, m.email = :email " +
            "WHERE m.id = :id")
    void updateMember( @Param("password") String password, @Param("email") String email, @Param("id") Long id);
}

package com.example.demo.domain.member;

import com.example.demo.domain.answer.Answer;
import com.example.demo.domain.question.Question;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name= "member")
public class Member {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(length = 20, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(name= "is_active")
    @ColumnDefault("true")
    private boolean isActive;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private Set<Question> question;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private Set<Answer> answer;

    @Builder
    public Member(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isActive = true;
    }

    public MemberDTO toDtO() {
        return MemberDTO.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .build();
    }
}

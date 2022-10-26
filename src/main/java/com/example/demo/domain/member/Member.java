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

    @Column( nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    // 회원 탈퇴시 is_active false로 만들고 password를 ""으로 만들어 회원 탈퇴 구현
    // default 값 : true
    @Column(name= "is_active")
    @ColumnDefault("true")
    private boolean isActive;

    // Question Entity와 OneToMany 매핑 (Member(1) : Question(다)
    // 양방향 Member(1) : Question(다) 매핑을 구현하고
    // 1인 Entity(Member)에 orphanRemoval = true를 명시하여
    // Member가 지워지면 해당 Member에 속한 Question도 지워지도록 구현
    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private Set<Question> question;

    // Answer Entity와 OneToMany 매핑 (Member(1) : Question(다)
    // 양방향 Member(1) : Answer(다) 매핑을 구현하고
    // 1인 Entity(Member)에 orphanRemoval = true를 명시하여
    // Member가 지워지면 해당 Member에 속한 Answer도 지워지도록 구현
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

    // Etity 객체 -> DTO 객체 전환시 사용
    // 내 정보 페이지, 내 정보 수정에서 사용함
    public MemberDTO toDto() {
        return MemberDTO.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .build();
    }
}

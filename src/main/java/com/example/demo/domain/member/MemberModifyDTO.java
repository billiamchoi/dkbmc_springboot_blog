package com.example.demo.domain.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MemberModifyDTO {

    private Long id;

    @Size(min =3, max = 25)
    @NotEmpty(message = "사용자 ID는 필수항목입니다.")
    private String username;

    @NotEmpty(message = "이메일은 필수 항목입니다.")
    private String email;

    @NotEmpty(message = "현재 비밀번호는 필수 항목입니다.")
    private String password1;

    @NotEmpty(message = "새로운 비밀번호는 필수 항목입니다.")
    private String password2;

    @NotEmpty(message = "새로운 비밀번호 확인은 필수 항목입니다.")
    private String password3;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public Member toEntity(){
        return Member.builder()
                .id(id)
                .username(username)
                .email(email)
                // 이거 엔티티로 가져갈때는 새로운 비밀번호를 가져감
                .password(password2)
                .build();
    }

    public MemberDTO toMemberDTO(){
        return MemberDTO.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password2)
                .build();
    }
}

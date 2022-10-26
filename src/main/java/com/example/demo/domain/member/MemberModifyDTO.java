package com.example.demo.domain.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

// 회원 정보 수정시 기존 MemberDTO에서 핸들링할수 없는 새로운 비밀번호와
// 새로운 비밀번호 확인 validation을 위하여 존재함
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
}

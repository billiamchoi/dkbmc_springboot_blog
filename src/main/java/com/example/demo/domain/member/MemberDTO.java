package com.example.demo.domain.member;
import com.example.demo.rest.request.SignUpDTO;
import lombok.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class MemberDTO {
    private Long id;
    @Size(min = 3, max = 25)
    @NotEmpty(message = "사용자 ID는 필수항목입니다.")
    private String username;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;

    // DTO 객체 -> Entity 객체 전환시 사용
    // Controller에서 DTO 객체로 받아 serviceImpl까지 전달되고 Repository에서 argument로
    // Entity 객체를 전달해줘야 하기 위함
    public Member toEntity(){
        return Member.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password1)
                .build();
    }

    public SignUpDTO toSignUpDto() {
        return SignUpDTO.builder()
                .username(username)
                .email(email)
                .password(password1)
                .password_confirm(password2)
                .build();
    }

    @Builder
    public MemberDTO(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password1 = password;
        this.password2 = password;
    }
}

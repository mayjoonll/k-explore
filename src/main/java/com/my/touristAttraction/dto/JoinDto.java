package com.my.touristAttraction.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDto {
    @NotBlank(message = "아이디 입력은 필수입니다.")
    private String username;

    @NotBlank
    @Size(min = 6, max = 12, message = "비밀번호는 6자 ~ 12자 이내로 입력하세요.")
    private String password;

    @NotBlank
    @Email(message = "유효한 이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "닉네임을 입력해주세요")
    private String nickname;

    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    private UserRole role = UserRole.ROLE_USER;
}
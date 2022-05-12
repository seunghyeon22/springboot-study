package com.springboot.study.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SigninReqDto {
	@NotBlank
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-z]).{5,20}",
	message = "아이디는 5~20자의 영문 소,대문자와 숫자만 사용하여 합니다.  ")
	private String username;
	@NotBlank
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W).{8,16}",
	message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
	private String password;
}

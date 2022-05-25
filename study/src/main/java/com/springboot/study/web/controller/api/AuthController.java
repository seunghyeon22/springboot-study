package com.springboot.study.web.controller.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.study.config.auth.PrincipalDetails;
import com.springboot.study.domain.user.User;
import com.springboot.study.domain.user.UserRepository;
import com.springboot.study.web.dto.CMRespDto;
import com.springboot.study.web.dto.SigninReqDto;
import com.springboot.study.web.dto.SignupReqDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		//password 암호화
		
		List<String> roles = List.of("ROLE_USER");
		//List<String> roles = List.of("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN");
		//리스트에 바로 값 넣는 방법
		user.setRoles(String.join(",", roles));
		//join - 리스트에 들어있는 문자열들을 쉼표(,)로 구분해서 합쳐주는 방법.
		
		userRepository.insertUser(user);
		
		return new ResponseEntity<>(new CMRespDto<User>(1, "회원가입", user),HttpStatus.OK);
	}
	
	//세션 정보 확인
	@GetMapping("/authentication")
	public ResponseEntity<?> getAuthentication(@AuthenticationPrincipal PrincipalDetails principalDetails){
		//@AuthenticationPrincipal 세션
		System.out.println(principalDetails.getUser().getUsercode());
		String password = principalDetails.getUser().getPassword();
		System.out.println(bCryptPasswordEncoder.matches("1111", password)); //password 복호화해서 비교하여 같으면 true 아니면 false
		return new ResponseEntity<>(new CMRespDto<PrincipalDetails>(1, "세션정보", principalDetails),HttpStatus.OK);
	}

	
//	@GetMapping("/user")
//	public ResponseEntity<?> testUser(){	
//		return new ResponseEntity<>(new CMRespDto<String>(1, "유저권한","role_user"),HttpStatus.OK);
//	}
	@GetMapping("/manager")
	public ResponseEntity<?> testManager(){
		return new ResponseEntity<>(new CMRespDto<String>(1, "매니저권한","role_manager"),HttpStatus.OK);
	}
	@GetMapping("/admin")
	public ResponseEntity<?> testAdmin(){
		return new ResponseEntity<>(new CMRespDto<String>(1, "관리자권한","role_admin"),HttpStatus.OK);
	}
	@GetMapping("/auth/signup/check/{username}")
	public ResponseEntity<?> checkUsername(@PathVariable String username) {
		User user = new User();
		CMRespDto<String> cmRespDto = null;
		HttpStatus status = null;
		if(user.getUsername().equals(username)) {
			cmRespDto = new CMRespDto<String>(-1, "사용할 수 없는 사용자이름", username);
			status = HttpStatus.BAD_REQUEST;
		}else {
			cmRespDto = new CMRespDto<String>(1, "사용할 수 있는 사용자이름", username);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(cmRespDto,status);
	}
	//mvn에서 Spring Boot Starter Validation 라이브러리 받아와야함 
	//@Valid, @NotBlank, BindingResult 사용 변수의 null값 확인
	@PostMapping("/auth/signup")
	public ResponseEntity<?> signup(@Valid SignupReqDto signupReqDto, BindingResult bindingResult){

		return new ResponseEntity<>(new CMRespDto<SignupReqDto>(1, "회원가입 완료", signupReqDto),HttpStatus.OK);
	}
	
	@PostMapping("/auth/signin")
	public ResponseEntity<?> signin(@Valid SigninReqDto signinReqDto, BindingResult bindingResult){
		
		User user = new User();
		if(signinReqDto.getUsername().equals(user.getUsername())
				&&signinReqDto.getPassword().equals(user.getPassword())) {
			return new ResponseEntity<>(new CMRespDto<User>(1,"로그인 성공", user),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new CMRespDto<SigninReqDto>(-1,"로그인 실패", signinReqDto),HttpStatus.BAD_REQUEST);
			
		}		
	}
}

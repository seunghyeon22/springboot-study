package com.springboot.study.web.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.study.web.controller.api.data.User;
import com.springboot.study.web.dto.CMRespDto;
import com.springboot.study.web.dto.SigninReqDto;
import com.springboot.study.web.dto.SignupReqDto;

@RestController
public class UserController {
//
//	@Autowired
//	private User user;

	@GetMapping("/user/{usercode}")
	public ResponseEntity<?> getUser(@PathVariable int usercode) {

		System.out.println(usercode);
		return new ResponseEntity<>(10, HttpStatus.BAD_REQUEST);// ResponseEntity httpEntity를 상속받아서 사용
	}

	/*
	 * 1. 사용자 이름 중복 확인(/auth/signup/check/???) -> User 객체에 존재하는 사용자 이름과 같으면 사용할 수 없는
	 * 사용자 이름입니다. -> 사용할 수 있는 사용자 이름입니다.
	 * 
	 * 2. 회원가입(/auth/signup) -> 회원가입 정보 출력(console), 응답은 회원가입 완료
	 * 
	 * 3. 로그인(/auth/signin) -> User 객체의 정보와 일치하면(username, password) 로그인 성공, 로그인 실패
	 * 
	 * 4. 회원수정(/account/aaa) -> name, email 수정 -> 회원수정 완료, 회원수정 실패
	 * 
	 * 5. 회원탈퇴(/account/aaa) -> 회원탈퇴 완료, 회원탈퇴 실패
	 */
	
	//잘못된 코딩
//	@GetMapping("/auth/signup/check/{username}") //PathVariable 경로로 가져온다. 파라미터와 다르다
//	public ResponseEntity<?> usernameCheck(@PathVariable String username) {
//		if (user.getUsername() != username) {
//			return new ResponseEntity<>(200, HttpStatus.OK);
//		} else {
//			System.out.println("사용할 수 없는 사용자 이름입니다.");
//			return new ResponseEntity<>(400, HttpStatus.BAD_REQUEST);
//		}
//	}

//	@PostMapping("/auth/signup")
//	public ResponseEntity<?> signup(User user) {
//		return null;
//	}
//
//	@PostMapping("/auth/signin")
//	public ResponseEntity<?> signin(String username, String password) {
//		if (user.getUsername() == username) {
//			if (user.getPassword() == password) {
//				return new ResponseEntity<>(200, HttpStatus.OK);
//			} else {
//				return new ResponseEntity<>(400, HttpStatus.BAD_REQUEST);
//			}
//		} else {
//			return new ResponseEntity<>(400, HttpStatus.BAD_REQUEST);
//		}
//	}
//
//	@PutMapping("/account/aaa")
//	public ResponseEntity<?> account(String email, String name) {
//		if (email != user.getEmail()|| name != user.getName()) {
//			System.out.println("회원수정 완료");
//			return new ResponseEntity<>(200, HttpStatus.OK);
//		}else {
//			System.out.println("회원수정 실패");
//			return new ResponseEntity<>(400, HttpStatus.BAD_REQUEST);
//		}
//	}
//
//	@DeleteMapping("/account/user/aaa")
//	public ResponseEntity<?> deleteUser(User user) {
//		return null;
//	}
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
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<String, String>();
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			return new ResponseEntity<>(new CMRespDto<Map<String, String>>(-1,"필드오류",errorMap),HttpStatus.BAD_REQUEST);
		}	
		return new ResponseEntity<>(new CMRespDto<SignupReqDto>(1, "회원가입 완료", signupReqDto),HttpStatus.OK);
	}
	
	@PostMapping("/auth/signin")
	public ResponseEntity<?>signin(@Valid SigninReqDto signinReqDto, BindingResult bindingResult){
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<String, String>();
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			return new ResponseEntity<>(new CMRespDto<Map<String, String>>(-1,"오류", errorMap),HttpStatus.BAD_REQUEST);
		}
		User user = new User();
		if(signinReqDto.getUsername().equals(user.getUsername())
				&&signinReqDto.getPassword().equals(user.getPassword())) {
			return new ResponseEntity<>(new CMRespDto<User>(1,"로그인 성공", user),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new CMRespDto<SigninReqDto>(-1,"로그인 실패", signinReqDto),HttpStatus.BAD_REQUEST);
			
		}
		
	}
}

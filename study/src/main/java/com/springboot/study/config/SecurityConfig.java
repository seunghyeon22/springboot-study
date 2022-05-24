package com.springboot.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.springboot.study.config.oauth2.PrincipalOauth2UserService;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity //기존의 WebSecurityConfigurerAdapter의 설정을 비활성화 시키고 현재 클래스(SecurityConfig)의 설정을 따르겠다.
@Configuration //컴포넌트 개념, @Bean을 사용할 수 있게 해준다. 
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {//WebSecurityConfigurerAdapter 모든 시큐리티의 메소드들이 들어있음
	//UserDetailsService 로그인 관련 메소드
	//configure 많이 사용되는 메소드
	//BCrypt 
	/*
	 * @Bean(name ="BCrypt")
	 * public BCryptPasswordEncoder bCryptPasswordEncoder() { return new
	 * BCryptPasswordEncoder() }
	 */
	private final PrincipalOauth2UserService principalOauth2UserService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	http.csrf().disable();// csrf()는 사용하지 않겠다. 
	http.authorizeRequests()// 인증요청
		.antMatchers("/api/board/**","/","/board/list")//이러한 요청이 들어오면
		.authenticated() // 인증이 필요하다.
		.antMatchers("/api/v1/user/**", "/user/account/**")
		.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") 
		.antMatchers("/api/v1/manager/**")
		.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") 
		.antMatchers("/api/v1/admin/**")
		.access("hasRole('ROLE_ADMIN')") 
		.anyRequest() //나머지 다른 모든 요청들은
		.permitAll() //권한이 필요없다.
		.and()
		.formLogin()
		.loginPage("/auth/signin") //로그인 페이지 get요청(view)
		.loginProcessingUrl("/auth/signin") // 로그인 post요청(PrincipalDetailsService -> loadUserByUsername() 호출)
		.defaultSuccessUrl("/") //로그인이 되어졌으면 이쪽으로 가라
		.and()
		.oauth2Login()
		.loginPage("/auth/signin")
		.userInfoEndpoint() 
		/*
		1. 코드를 받는다(google, naver,kakao 등 로그인 요청을 했을 때 부여되는 코드 번호
		2. 에세스토큰을 발급받는다(JWT)
		3. 스코프 정보에 접근할 수 있는 권한이 생긴다.
		4. 해당 정보를 시큐리티에서 활용하면 됨
		.userInfoEndpoint()이 호출되면 위의 프로그램이 전부 실행됨
		*/
		.userService(principalOauth2UserService)
		.and()
		.defaultSuccessUrl("/");
	}
	
}

package com.springboot.study.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration //컴포넌트 개념 
public class SecurityConfig extends WebSecurityConfigurerAdapter {//WebSecurityConfigurerAdapter 모든 시큐리티의 메소드들이 들어있음
	//UserDetailsService 로그인 관련 메소드
	//configure 많이 사용되는 메소드
	//BCrypt 
	/*
	 * @Bean(name ="BCrypt")
	 * public BCryptPasswordEncoder bCryptPasswordEncoder() { return new
	 * BCryptPasswordEncoder() }
	 */
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	http.csrf().disable();
	http.authorizeHttpRequests()
		.anyRequest()
		.permitAll();
	}
	
}

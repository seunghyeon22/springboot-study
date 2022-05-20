package com.springboot.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	http.csrf().disable();
	http.authorizeRequests()
		.antMatchers("/api/board/**","/","/board/list")//이러한 요청이 들어오면
		.authenticated() // 인증이 필요하다.
		.antMatchers("/api/v1/user/**")
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
		.defaultSuccessUrl("/"); //로그인이 되어졌으면 이쪽으로 가라
		
	}
	
}

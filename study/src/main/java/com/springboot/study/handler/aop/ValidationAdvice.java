package com.springboot.study.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.springboot.study.handler.ex.CustomValidationApiException;

/*
 * Aspect : 여러 핵심 기능에 적용될 관심사 모듈을 의미한다. Aspect에는 구체적인 기능을 구현한 Advice와
 *  Advice가 어디(Target)에 적용될지 결정하는 PointCut을 포함하고 있다.
 * 
 * Target : 공통 기능을 부여할 대상. 즉, 핵심 기능을 담당하는 비즈니스 로직이고, 어떤 관심사들과도 관계를 맺지 않는다.
 * 
 * Advice : 공통 기능을 담은 구현체, Advice는 Aspect가 무엇을 언제 적용할 지를 정의하는 것
 * 
 * PointCut : 공통 기능이 적용될 대상을 결정하는 것
 * 
 * JoinPoint : Advice가 적용될 지점을 의미한다. Spring에서는 메서드에만 제공이 된다.
 * 
 * Mvn에서 Spring Boot Starter AOP 라이브러리를 등록해야 한다.
 * Mvn에서 SLF4J등록 해야 API Module Logger 사용가능
 */
@Aspect
@Component
public class ValidationAdvice {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ValidationAdvice.class);
	
	//execution(=특정메소드들을 지정하고싶을때 사용)((=접근제어자, 생략가능)*(=리턴타입) com.springboot.study.web.controller.api(=경로).*Controller.*(=메소드)(매개변수))")
	@Around("execution(* com.springboot.study.web.controller.api.*Controller.*(..))") 
	public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable { 
		// Throwable : 최상위 throws, proceedingJoinPoint : around어노테이션의 클래스의 모든 매개변수를 가져온다
		Object[] args = proceedingJoinPoint.getArgs(); //오브젝트 형태로 업케스팅되어 매개변수들을 모두 args에 담는다.
		for(Object arg : args) {
			if(arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult) arg; //업케스팅 된 상태여서 사용을 못하기 때문에 BindingResult형태로 다운캐스팅
				if(bindingResult.hasErrors()) {
					Map<String, String> errorMap =new HashMap<String, String>();
					for(FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
					}
					LOGGER.error("Validation AOP실행됨");
					throw new CustomValidationApiException("유효성 검사 실패", errorMap); //
				}
			}	
		}

		return proceedingJoinPoint.proceed(); //필터에서 chain과 같은 용도
	}
}
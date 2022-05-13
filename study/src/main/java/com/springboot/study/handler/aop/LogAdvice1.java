package com.springboot.study.handler.aop;

import java.nio.channels.Pipe;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAdvice1 {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogAdvice1.class);
	
	@Around("within(com.springboot.study..*)")
	public Object loggin(ProceedingJoinPoint pjp) throws Throwable{
		long startAt = System.currentTimeMillis();
		
		Map<String, Object> params = getParams(pjp);
		
		LOGGER.info("-------- advice call : {}({}) = {}", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName(), params);
		
		Object result = pjp.proceed();
		
		long endAt = System.currentTimeMillis();
		
		LOGGER.info("-------- advice end : {}({}) = {} ({}ms)", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName(), result, endAt - startAt);
		return result;
	}
	private Map<String, Object> getParams(ProceedingJoinPoint pjp){
		Map<String, Object> params = new HashMap<String, Object>();
		Object[] args = pjp.getArgs();
		String[] argName = ((CodeSignature)pjp.getSignature()).getParameterNames();
		for(int i =0; i<args.length; i++) {
			params.put(argName[i],args[i]);
		}
		return params;
	}
	
}

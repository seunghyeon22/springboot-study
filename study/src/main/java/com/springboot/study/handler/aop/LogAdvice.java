package com.springboot.study.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


@Aspect
@Component
public class LogAdvice {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogAdvice.class);
	
	@Pointcut("within(com.springboot.study..*)")
	private void pointcut() {}
	
	@Around("pointcut()")
	//within(=해당패키지의안의 모든 클래스를 사용하고 싶을때 사용)(com.springboot.study..*)
	public Object loggin(ProceedingJoinPoint pjp) throws Throwable{
		//long startAt = System.currentTimeMillis();
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		Map<String,Object> params = getParams(pjp);
		
		LOGGER.info("--Advice Call :{}({})={}",pjp.getSignature().getDeclaringTypeName(),pjp.getSignature().getName(), params);
		
		Object result =pjp.proceed();
		
		//long endAt = System.currentTimeMillis();
		stopWatch.stop();
		
		LOGGER.info("--Advice End :{}({})={}({}ms)",pjp.getSignature().getDeclaringTypeName(),pjp.getSignature().getName(), result, stopWatch.getTotalTimeMillis());
		
		return result;
	}
	private Map<String,Object> getParams(ProceedingJoinPoint pjp){
		Map<String, Object> params = new HashMap<String, Object>();
		Object[] args = pjp.getArgs();
		String[] argNames = ((CodeSignature)pjp.getSignature()).getParameterNames(); //CodeSignature로 다운 캐스팅을 시킬 수 있음. 다운캐스팅을 시켜야 getParameterNames을 사용할 수 있다.
		for(int i =0; i<args.length;i++) {
			params.put(argNames[i],args[i]);
		}
		return params;
	}
}

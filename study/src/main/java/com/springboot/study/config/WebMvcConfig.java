package com.springboot.study.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	@Value("${file.path}")
	private String filePath;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/image/**") //이 이미지 요청이 들어오면
		.addResourceLocations("file:///"+filePath) //이 경로로 바꾼다.		
		.setCachePeriod(60*60) //캐시 지속시간(초 단위)
		.resourceChain(true) // 해당 경로로 쓰겠다.
		.addResolver(new PathResourceResolver()); // 연결시켜주는 객체
		
		
	}
	
}

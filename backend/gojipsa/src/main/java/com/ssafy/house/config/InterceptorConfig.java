package com.ssafy.house.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.house.interceptor.JWTInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
	private final JWTInterceptor jwtInterceptor;

	public InterceptorConfig(JWTInterceptor jwtInterceptor) {
		this.jwtInterceptor = jwtInterceptor;
	} 
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor);
	}
}

package com.ssafy.house.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.house.filter.HTMLCharacterExcapes;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	@Bean
	public MappingJackson2HttpMessageConverter jsonEscapeConverter() {
		// MappingJackson2HttpMessageConverter Default OjbectMapper 설정 및 ObjectMapper Config 설정
		ObjectMapper objectMapper=Jackson2ObjectMapperBuilder.json().build();
		objectMapper.getFactory().setCharacterEscapes(new HTMLCharacterExcapes());
		return new MappingJackson2HttpMessageConverter(objectMapper);
	}
}

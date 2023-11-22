package com.ssafy.house.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig implements WebMvcConfigurer {

	/**
	 * 이 코드 스니펫은 Spring Framework에서 CORS (Cross-Origin Resource Sharing) 구성을 설정하는 메서드를 나타냅니다. 
	 * CORS는 웹 애플리케이션에서 다른 도메인으로부터 리소스 요청을 허용하도록하는 보안 정책입니다.
	 * 
	 * 해당 코드는 WebMvcConfigurer 인터페이스를 구현하는 클래스에서 addCorsMappings 메서드를 재정의한 것으로 추정됩니다. 
	 * addCorsMappings 메서드는 CORS 구성을 정의하고 클라이언트와 서버 간의 HTTP 요청 및 응답에 대한 제어를 제공합니다. 이 메서드를 통해 CORS 정책을 구성합니다.
	 * 해당 코드의 내용을 해석하면:
	 * registry.addMapping("/**"): 이 줄은 CORS 정책을 모든 엔드포인트에 적용하도록 지정합니다. /**는 모든 경로 및 하위 경로를 나타냅니다. 따라서 이 CORS 정책은 모든 엔드포인트에 적용됩니다.
	 * allowedMethods("*"): 이 줄은 모든 HTTP 메서드 (GET, POST, PUT, DELETE 등)를 허용하는 것을 나타냅니다. "*"를 사용하면 모든 HTTP 메서드가 허용됩니다. 
	 * 이는 클라이언트가 어떤 HTTP 메서드를 사용하여 서버에 요청해도 허용된다는 것을 의미합니다.
	 * allowedOrigins("*"): 이 줄은 모든 출처 (origin)에서의 요청을 허용하는 것을 나타냅니다. "*"을 사용하면 모든 출처에서의 요청이 허용됩니다. 이는 다른 도메인에서의 요청도 허용된다는 것을 의미합니다.
	 * 즉, 위의 코드는 모든 엔드포인트에서 모든 HTTP 메서드 및 모든 출처에서의 요청을 허용하는 매우 개방적인 CORS 정책을 설정하고 있습니다. 
	 * 이것은 개발 또는 테스트 목적으로 사용될 수 있으며, 실제 프로덕션 환경에서는 더 엄격한 CORS 정책을 적용하는 것이 좋습니다.
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
		.addMapping("/**")
		.allowedMethods("*") // get post put delete
		.allowedOrigins("*");
	}

}

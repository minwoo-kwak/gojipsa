package com.ssafy.house.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.house.annotation.AuthRequired;
import com.ssafy.house.util.JWTUtil;

@Component
public class JWTInterceptor implements HandlerInterceptor {
	private static final String AUTHORIZATION_HEADER = "Authorization";

	private final JWTUtil jwtUtil;

	public JWTInterceptor(JWTUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		// handler가 메소드에서 호출된 것인지 확인.
		// 그렇지 않다면 모두 패스.
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		HandlerMethod handlerMethod = (HandlerMethod) handler;

		// 호출된 메소드의 Annotation이 AuthRequired인지 확인.
		if (handlerMethod.getMethodAnnotation(AuthRequired.class) != null || 
			handlerMethod.getBeanType().getAnnotation(AuthRequired.class) != null) {
			
			// accessToken 확인
			String jwt = request.getHeader(AUTHORIZATION_HEADER);
			
			System.out.println("jwt token == " + jwt);

			// jwt를 가지고 있을 때
			if (jwt != null) {
				// 예외가 발생하면 끝나버린다.
				try {
					jwtUtil.valid(jwt);
				} catch (Exception e) {
					System.out.println("jwtUtil.valid 에러발생");
					e.printStackTrace();
				}

				// 토큰이 있다
				return true;
			}

			// 권한이 없음을 클라이언트에 전송
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			System.out.println("권한 없음");
			return false;
		}

		// AuthRequired가 붙지 않은 메소드인 경우 모두 통과
		return true;
	}

}

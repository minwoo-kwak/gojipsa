package com.ssafy.house.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		HandlerMethod handlerMethod = (HandlerMethod) handler;

		if (handlerMethod.getMethodAnnotation(AuthRequired.class) != null
				|| handlerMethod.getBeanType().getAnnotation(AuthRequired.class) != null) {
			String jwt = request.getHeader(AUTHORIZATION_HEADER);

			// jwt를 가지고 있을 때
			if (jwt != null) {
				// 예외가 발생하면 끝나버린다.
				try {
					jwtUtil.valid(jwt);
				} catch (Exception e) {
					System.out.println("jwtUtil.valid 에러남");
					e.printStackTrace();
				}

				// 토큰이 있다
				return true;
			}

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			// 그렇지 않을 경우는 허용 x
			return false;
		}

		return true;
	}

}

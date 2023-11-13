package com.ssafy.house.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
@PropertySource("classpath:application.properties")
public class JWTUtil {

	private final Environment environment;

	public JWTUtil(Environment environment) {
		this.environment = environment;
	}

	/**
	 * 토큰 생성 과정
	 * 
	 * @param userId - 사용자 ID (클레임에 저장될 정보)
	 * @return - 생성된 JWT 토큰
	 * @throws UnsupportedEncodingException - 지원되지 않는 문자 인코딩 예외 처리
	 */
	public String createToken(String userId, String grad) throws UnsupportedEncodingException {
		// 토큰의 만료 시간 (초) : 1시간
		final long expiredSecond = 60 * 60;

		// JWT 빌더 객체를 생성
		JwtBuilder jwtBuilder = Jwts.builder();

		// 클레임(claims) 추가: 사용자 ID
		jwtBuilder.claim("userId", userId);
		jwtBuilder.claim("grad", grad);

		// 현재 시간 및 토큰 만료 시간 설정
		long currentTime = System.currentTimeMillis();
		jwtBuilder.setIssuedAt(new Date(currentTime)); // 토큰 발급 시간
		jwtBuilder.setExpiration(new Date(currentTime + expiredSecond * 1000)); // 토큰 만료 시간

		// 서명 알고리즘과 비밀 키를 사용하여 서명 설정
		jwtBuilder.signWith(SignatureAlgorithm.HS256, environment.getProperty("jwt.salt").getBytes("UTF-8"));

		// JWT 토큰을 문자열로 변환하여 반환
		return jwtBuilder.compact();
	}

	/**
	 * JWT 토큰의 유효성을 검사하고 내용을 파싱
	 * 
	 * @param token - 검사할 JWT 토큰
	 * @throws Exception - 유효하지 않은 토큰 또는 서명 검증 실패 등의 예외 처리
	 */
	public void valid(String token) throws Exception {
		Jwts.parser()
				// 서명을 확인하기 위한 비밀 키 설정
				.setSigningKey(environment.getProperty("jwt.salt").getBytes("UTF-8"))
				// JWT 토큰을 파싱하고 클레임 추출
				.parseClaimsJws(token);
	}
	

	// 토큰에 담긴 정보를 가져오기 메서드
	public Map<String, Object> getInfo(String token) throws Exception {
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(environment.getProperty("jwt.salt").getBytes()).parseClaimsJws(token); // secretKey를 사용하여 복호화
		} catch(Exception e) {
			throw new Exception();
		}
		
		return claims.getBody();
	}
}

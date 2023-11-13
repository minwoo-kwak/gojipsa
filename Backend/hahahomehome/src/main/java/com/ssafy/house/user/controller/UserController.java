package com.ssafy.house.user.controller;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.house.annotation.AuthRequired;
import com.ssafy.house.board.model.dto.BoardDto;
import com.ssafy.house.user.model.dto.User;
import com.ssafy.house.user.model.service.UserService;
import com.ssafy.house.util.JWTUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
	private final UserService userService;
	private final JWTUtil jwtUtil;

	public UserController(UserService userService, JWTUtil jwtUtil) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}

	/**
	 * 로그인
	 * 
	 * @param requestUser 로그인 아이디, 비밀번호 요청 정보
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ApiOperation(value = "로그인", notes = "userId와 password로 로그인을 합니다.")
	@ResponseBody
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody User requestUser)
			throws UnsupportedEncodingException {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		User validUser = userService.login(requestUser);

		System.out.println(validUser);

		// 해당 사용자가 없을 경우
		if (validUser == null) {
			resultMap.put("message", "아이디와 패스워드를 확인해주세요");

			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.UNAUTHORIZED);
		}

		resultMap.put("Authorization", jwtUtil.createToken(validUser.getUserId(), validUser.getGrad()));

		// 사용자가 일치하면 그 사용자를 반환
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.CREATED);
	}

	@ApiOperation(value = "마이페이지", notes = "현재 JWT이 유효한지 판별하고, 유저정보를 넘겨줍니다.")
	@AuthRequired
	@GetMapping("/mypage")
	public ResponseEntity<User> myPage(HttpServletRequest request) throws ParseException {
		String authorization = request.getHeader("Authorization");

		String[] chunks = authorization.split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String payload = new String(decoder.decode(chunks[1]));

		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(payload);
		String userId = (String) jsonObject.get("userId");

		User user = userService.myPage(userId);

		System.out.println(user);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

	/**
	 * 관리자인지 아닌지 파악하는 메서드
	 * @param request - JWT 토큰 받기위함
	 * @return 관리자이면 ACCEPTED(202), 아니면 OK(200)
	 * @throws ParseException
	 */
	@GetMapping("/valid")
	public ResponseEntity valid(HttpServletRequest request) throws ParseException {
		String authorization = request.getHeader("Authorization");
		
		if(authorization == null || authorization.equals("")) {
			return new ResponseEntity(HttpStatus.OK);
		}
		
		String[] chunks = authorization.split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String payload = new String(decoder.decode(chunks[1]));

		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(payload);
		String grad = (String) jsonObject.get("grad");

		if (grad.equals("관리자")) {
			return new ResponseEntity(HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity(HttpStatus.OK);
		}

	}
	

}

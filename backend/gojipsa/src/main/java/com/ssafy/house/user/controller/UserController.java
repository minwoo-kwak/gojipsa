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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.house.annotation.AuthRequired;
import com.ssafy.house.user.model.dto.User;
import com.ssafy.house.user.model.service.UserService;
import com.ssafy.house.util.JWTUtil;

import io.swagger.annotations.ApiOperation;

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
	@ApiOperation(value = "로그인하기", notes = "userId와 password로 로그인하는 API")
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

		resultMap.put("username", validUser.getName());
		resultMap.put("Authorization", jwtUtil.createToken(validUser.getUserId(), validUser.getGrad()));

		// 사용자가 일치하면 그 사용자를 반환
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.CREATED);
	}

	@ApiOperation(value = "마이페이지 정보 가져오기", notes = "현재 JWT이 유효한지 판별하고, 유저정보를 넘겨줍니다.")
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
	@ApiOperation(value="관리자 여부를 확인하기",notes="관리자 여부를 파악하는 API")
	public ResponseEntity valid(HttpServletRequest request) throws ParseException {
		String authorization = request.getHeader("Authorization");
		//System.out.println("valid authorization == "+ authorization);
		
		if(authorization == null || authorization.equals("")) {
			return new ResponseEntity(HttpStatus.OK);
		}
		
		String[] chunks = authorization.split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String payload = new String(decoder.decode(chunks[1]));

		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(payload);
		String grad = (String) jsonObject.get("grad");
		System.out.println("uservalid grad  == " + grad);

		if (grad.equals("관리자")) {
			return new ResponseEntity(HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity(HttpStatus.OK);
		}

	}
	
	
	
    /**
     * 회원 가입 API
     *
     * @param newUser 새로 가입할 사용자 정보
     * @return ResponseEntity<Map<String, Object>> 회원 가입 결과 및 메시지
     * @throws UnsupportedEncodingException 인코딩 예외가 발생할 경우
     */
	@ApiOperation(value = "회원 가입", notes = "새로운 사용자를 등록하는 API")
	@ResponseBody
	@PostMapping("/signup")
	public ResponseEntity<Map<String, Object>> signup(@RequestBody User newUser)
	        throws UnsupportedEncodingException {
		
	    Map<String, Object> resultMap = new HashMap<>();

	    
	    // 회원 가입 서비스 호출
	    userService.signup(newUser);

	    resultMap.put("message", "회원 가입이 완료되었습니다.");
	    return new ResponseEntity<>(resultMap, HttpStatus.CREATED);
	}
	
	  /**
     * 아이디 중복 체크
     *
     * @param userId 아이디
     * @return ResponseEntity<Map<String, Object>> 중복 여부 및 메시지
     */
    @ApiOperation(value = "아이디 중복 체크", notes = "아이디 중복을 체크하는 API")
    @GetMapping("/checkId/{userId}")
    public ResponseEntity<Map<String, Object>> checkId(@PathVariable String userId) {
        Map<String, Object> resultMap = new HashMap<>();

        // 중복 체크 서비스 호출
        boolean isDuplicated = userService.checkIdDuplication(userId);

        if (isDuplicated) {
            resultMap.put("message", "이미 사용 중인 아이디입니다.");
            return new ResponseEntity<>(resultMap, HttpStatus.CONFLICT);
        } else {
            resultMap.put("message", "사용 가능한 아이디입니다.");
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }
    }
	
	
    /**
     * 회원 정보 수정 API
     *
     * @param updatedUser 수정할 사용자 정보
     * @param request     HTTP 요청 객체
     * @return ResponseEntity<Map<String, Object>> 회원 정보 수정 결과 및 메시지
     * @throws Exception 예외가 발생할 경우
     */
	@ApiOperation(value = "회원 정보 수정", notes = "현재 로그인한 사용자의 정보를 수정하는 API")
	@AuthRequired
	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> updateUserInfo(@RequestBody User updatedUser,
	        HttpServletRequest request) throws Exception {
		
		System.out.println("updatedUser in controller == " + updatedUser);
	    Map<String, Object> resultMap = new HashMap<>();

	    // 여기서 필요한 유효성 검사 등을 수행하십시오.

	    // 현재 로그인한 사용자의 ID를 얻어옴
	    String userId = getUserIdFromToken(request);

	    // 회원 정보 수정 서비스 호출
	    userService.updateUserInfo(userId, updatedUser);

	    resultMap.put("message", "회원 정보가 수정되었습니다.");
	    return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
    /**
     * 회원 탈퇴 API
     *
     * @param request HTTP 요청 객체
     * @return ResponseEntity<Map<String, Object>> 회원 탈퇴 결과 및 메시지
     * @throws Exception 예외가 발생할 경우
     */
	@ApiOperation(value = "회원 탈퇴", notes = "현재 로그인한 사용자를 탈퇴하는 API")
	@AuthRequired
	@DeleteMapping("/delete")
	public ResponseEntity<Map<String, Object>> deleteUser(HttpServletRequest request) throws Exception {
	    Map<String, Object> resultMap = new HashMap<>();

	    // 현재 로그인한 사용자의 ID를 얻어옴
	    String userId = getUserIdFromToken(request);

	    // 회원 탈퇴 서비스 호출
	    userService.deleteUser(userId);

	    resultMap.put("message", "회원 탈퇴가 완료되었습니다.");
	    return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	 /**
     * JWT 토큰에서 사용자 ID를 추출합니다.
     *
     * @param request Authorization 헤더를 포함한 HttpServletRequest
     * @return JWT 토큰에서 추출한 사용자 ID
	 * @throws Exception 
     */
    private String getUserIdFromToken(HttpServletRequest request) throws Exception {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || authorization.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않거나 누락된 Authorization 헤더입니다.");
        }

        return jwtUtil.extractUserId(authorization);
    }

    /**
     * 현재 비밀번호의 유효성을 확인하는 API
     *
     * @param userId           사용자 ID
     * @param currentPassword  현재 비밀번호
     * @return 유효 여부
     */
    @PostMapping("/checkPassword")
    public ResponseEntity<Map<String, Boolean>> checkCurrentPassword(@RequestBody Map<String, Object> map) {
        Map<String, Boolean> resultMap = new HashMap<>();
        boolean isValid = userService.checkCurrentPassword((String) map.get("userId"),(String) map.get("currentPassword"));
        resultMap.put("isValid", isValid);
        return ResponseEntity.ok(resultMap);
    }

	

}

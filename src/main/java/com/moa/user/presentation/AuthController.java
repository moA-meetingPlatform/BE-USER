package com.moa.user.presentation;


import com.moa.global.vo.ApiResult;
import com.moa.user.application.AuthService;
import com.moa.user.dto.UserSignUpDto;
import com.moa.user.dto.UserSignUpResultDto;
import com.moa.user.vo.req.UserSignUpRequest;
import com.moa.user.vo.res.UserSignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Auth Controller", description = "로그인, 회원가입 API")
@RestController
@RequestMapping("/api/v1/user/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

	private final ModelMapper modelMapper;
	private final AuthService authService;


	@Operation(summary = "회원가입", description = "유저 회원가입")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "409", description = "유저 아이디 중복"),
		@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
	})
	@PostMapping("/signup")
	public ResponseEntity<ApiResult<UserSignUpResponse>> signUp(@RequestBody UserSignUpRequest request) {
		log.debug("userSignUpIn: {}", request);
		UserSignUpResultDto userSignUpResultDto = authService.signUp(modelMapper.map(request, UserSignUpDto.class));
		return ResponseEntity.ok(ApiResult.ofSuccess(modelMapper.map(userSignUpResultDto, UserSignUpResponse.class)));
	}

}

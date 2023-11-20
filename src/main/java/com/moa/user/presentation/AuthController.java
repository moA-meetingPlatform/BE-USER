package com.moa.user.presentation;


import com.moa.certificate.dto.CreateUserCompanyCertificateDto;
import com.moa.global.vo.ApiResult;
import com.moa.user.application.AuthService;
import com.moa.user.application.OauthService;
import com.moa.user.dto.*;
import com.moa.user.vo.LoginRequest;
import com.moa.user.vo.LoginResponse;
import com.moa.user.vo.request.OauthLoginRequest;
import com.moa.user.vo.request.OauthSignUpRequest;
import com.moa.user.vo.request.UserSignUpRequest;
import com.moa.user.vo.response.UserSignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

	private final ModelMapper modelMapper;  // modelMapper 주입
	private final AuthService authService;
	private final OauthService oauthService;


	@Operation(summary = "로그인", description = "유저 일반 로그인")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "400", description = "FAIL_LOGIN, 로그인 실패", content = @Content(schema = @Schema(implementation = ApiResult.class))),
		@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(schema = @Schema(implementation = ApiResult.class)))
	})
	@PostMapping("/login")
	public ResponseEntity<ApiResult<LoginResponse>> login(@RequestBody LoginRequest request) {
		LoginDto loginDto = modelMapper.map(request, LoginDto.class);
		LoginResultInfoDto loginResultInfoDto = authService.login(loginDto);
		return ResponseEntity.ok(ApiResult.ofSuccess(modelMapper.map(loginResultInfoDto, LoginResponse.class)));
	}


	@Operation(summary = "회원가입", description = "유저 회원가입")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "409", description = "유저 아이디 중복"),
		@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
	})
	@PostMapping("/signup")
	public ResponseEntity<ApiResult<UserSignUpResponse>> signUp(@RequestBody UserSignUpRequest request) {
		UserSignUpResultDto userSignUpResultDto;

		UserSignUpDto userSignUpDto = modelMapper.map(request, UserSignUpDto.class);

		if (request.getVerifyCompanyEmailRequest() != null && request.getVerifyCompanyEmailRequest().getCompanyEmail() != null) {
			log.debug("회사 이메일 인증");
			userSignUpDto.setSignUpVerifyCompanyEmailDto(modelMapper.map(request.getVerifyCompanyEmailRequest(), SignUpVerifyCompanyEmailDto.class));
			userSignUpResultDto = authService.signUp(userSignUpDto);
		} else {
			log.debug("회사 증명서 인증");
			userSignUpDto.setCreateUserCompanyCertificateDto(modelMapper.map(request.getVerifyCompanyCertificateRequest(), CreateUserCompanyCertificateDto.class));
			userSignUpResultDto = authService.signUpCertificate(userSignUpDto);
		}

		return ResponseEntity.ok(ApiResult.ofSuccess(modelMapper.map(userSignUpResultDto, UserSignUpResponse.class)));
	}


	@Operation(summary = "간편로그인", description = "간편로그인, provider : NAVER, KAKAO, APPLE (대문자 필수)")
	@PostMapping("/oauth-login")
	public ResponseEntity<ApiResult<LoginResponse>> oauthLogin(@RequestBody OauthLoginRequest request) {
		log.debug("INPUT Object Data is : {}", request);
		OauthLoginDto oauthLoginDto = modelMapper.map(request, OauthLoginDto.class);
		LoginResultInfoDto loginResultInfoDto = oauthService.oauthLogin(oauthLoginDto);
		return ResponseEntity.ok(ApiResult.ofSuccess(modelMapper.map(loginResultInfoDto, LoginResponse.class)));
	}


	@Operation(summary = "간편로그인 생성(첫 로그인)", description = "간편로그인 회원가입(첫 로그인시), provider : NAVER, KAKAO, APPLE (대문자 필수)")
	@PostMapping("/oauth-login-create")
	public ResponseEntity<ApiResult<UserSignUpResponse>> oauthLoginCreate(@RequestBody OauthSignUpRequest request) {
		log.debug("INPUT Object Data is : {}", request);

		UserSignUpResultDto userSignUpResultDto;

		OauthSignUpDto oauthSignUpDto = modelMapper.map(request, OauthSignUpDto.class);

		if (request.getVerifyCompanyEmailRequest() != null && request.getVerifyCompanyEmailRequest().getCompanyEmail() != null) {
			log.debug("회사 이메일 인증");
			oauthSignUpDto.setSignUpVerifyCompanyEmailDto(modelMapper.map(request.getVerifyCompanyEmailRequest(), SignUpVerifyCompanyEmailDto.class));
			userSignUpResultDto = oauthService.createOauthLogin(oauthSignUpDto);
		} else {
			log.debug("회사 증명서 인증");
			oauthSignUpDto.setCreateUserCompanyCertificateDto(modelMapper.map(request.getVerifyCompanyCertificateRequest(), CreateUserCompanyCertificateDto.class));
			userSignUpResultDto = oauthService.createOauthLoginCertificate(oauthSignUpDto);
		}

		return ResponseEntity.ok(ApiResult.ofSuccess(modelMapper.map(userSignUpResultDto, UserSignUpResponse.class)));
	}

}

package com.moa.user.presentation;


import com.moa.global.vo.ApiResult;
import com.moa.user.application.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "User Controller", description = "로그인, 회원가입 API")
@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;


	@Operation(summary = "아이디 중복 확인", description = "유저의 아이디 중복 확인")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "409", description = "DUPLICATE_RESOURCE, 중복된 id", content = @Content(schema = @Schema(implementation = ApiResult.class))),
		@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(schema = @Schema(implementation = ApiResult.class)))
	})
	@Parameters({
		@Parameter(in = ParameterIn.QUERY, name = "id", description = "아이디(이메일형식)", required = true, example = "example@moa.com")
	})
	@GetMapping("/id-check")
	public ResponseEntity<ApiResult<Void>> idCheck(@RequestParam("id") String loginId) {
		userService.checkCanUseLoginId(loginId);
		return ResponseEntity.ok(ApiResult.ofSuccess(null));
	}

}

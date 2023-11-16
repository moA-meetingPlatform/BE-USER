package com.moa.user.presentation;


import com.moa.global.vo.ApiResult;
import com.moa.user.application.UserService;
import com.moa.user.config.security.JwtTokenProvider;
import com.moa.user.dto.UserGetProfileDto;
import com.moa.user.dto.UserModifyDto;
import com.moa.user.dto.UserSearchInfoDto;
import com.moa.user.vo.request.UserModifyRequest;
import com.moa.user.vo.response.UserGetProfileResponse;
import com.moa.user.vo.response.UserSearchInfoResponse;
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
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Tag(name = "User Controller", description = "로그인, 회원가입 API")
@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

	private final ModelMapper modelMapper;
	private final UserService userService;

	private final JwtTokenProvider jwtTokenProvider;


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


	@Operation(summary = "유저 프로필 조회", description = "유저 프로필 조회")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 유저", content = @Content(schema = @Schema(implementation = ApiResult.class))),
		@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(schema = @Schema(implementation = ApiResult.class)))
	})
	@Parameters({
		@Parameter(in = ParameterIn.PATH, name = "userUuid", description = "uuid", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
	})
	@GetMapping("/profile/{userUuid}")
	public ResponseEntity<ApiResult<UserGetProfileResponse>> getUserProfile(@PathVariable("userUuid") UUID userUuid, @RequestHeader("Authorization") String token) {
		UUID loginUserUuid = UUID.fromString(jwtTokenProvider.getUuidString(token.substring("Bearer ".length())));
		UserGetProfileDto dto = userService.getUserProfileByUUID(userUuid);
		if (dto != null && loginUserUuid.equals(dto.getUserUuid())) {
			dto.setSameWithLoggedInUser(true);
		}
		return ResponseEntity.ok(ApiResult.ofSuccess(modelMapper.map(dto, UserGetProfileResponse.class)));
	}


	@Operation(summary = "프로필 수정", description = "유저 프로필 수정")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "404", description = "없는 유저", content = @Content(schema = @Schema(implementation = ApiResult.class))),
		@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(schema = @Schema(implementation = ApiResult.class)))
	})
	@PatchMapping("/profile")
	public ResponseEntity<ApiResult<Void>> modifyUserProfile(@RequestBody UserModifyRequest request) {
		userService.modifyUser(modelMapper.map(request, UserModifyDto.class));
		return ResponseEntity.ok(ApiResult.ofSuccess(null));
	}


	@Operation(summary = "유저 검색", description = "유저 검색")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(schema = @Schema(implementation = ApiResult.class)))
	})
	@Parameters({
		@Parameter(in = ParameterIn.QUERY, name = "searchWord", description = "검색어", required = true, example = "비누")
	})
	@GetMapping("")
	public ResponseEntity<ApiResult<List<UserSearchInfoResponse>>> searchUser(@RequestParam("searchWord") String searchWord) {
		List<UserSearchInfoDto> dtoList = userService.searchUser(searchWord);
		List<UserSearchInfoResponse> userSearchInfoResponses = dtoList.stream()
			.map(dto -> modelMapper.map(dto, UserSearchInfoResponse.class))
			.toList();
		return ResponseEntity.ok(ApiResult.ofSuccess(userSearchInfoResponses));
	}

}

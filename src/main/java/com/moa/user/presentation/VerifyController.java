package com.moa.user.presentation;


import com.moa.global.vo.ApiResult;
import com.moa.user.application.VerifyService;
import com.moa.user.dto.VerifyEmailDto;
import com.moa.user.vo.request.SendEmailRequest;
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
import org.springframework.web.bind.annotation.*;


@Tag(name = "Verify Controller", description = "외부 인증을 사용하는 API")
@RestController
@RequestMapping("/api/v1/user/verify")
@Slf4j
@RequiredArgsConstructor
public class VerifyController {

	private final ModelMapper modelMapper;
	private final VerifyService verifyService;


	@Operation(summary = "이메일 인증 요청", description = "이메일 인증 요청 -> 인증 코드를 포함한 이메일을 전송합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "400", description = "이메일 전송 실패", content = @Content(schema = @Schema(implementation = ApiResult.class))),
	})
	@PostMapping("/email-send")
	public ResponseEntity<ApiResult<Void>> sendVerifyEmail(@RequestBody SendEmailRequest request) {
		verifyService.sendEmail(request.getEmail());
		return ResponseEntity.ok(ApiResult.ofSuccess(null));
	}


	@Operation(summary = "이메일 인증 번호 확인", description = "이메일 인증번호를 비교하여 인증을 완료")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "400", description = "이메일 인증 실패", content = @Content(schema = @Schema(implementation = ApiResult.class))),
	})
	@GetMapping("/email-confirm")
	public ResponseEntity<ApiResult<Void>> confirmEmail(@RequestParam("email") String email, @RequestParam("code") String code) {
		verifyService.verifyEmailByCode(VerifyEmailDto.builder()
			.email(email)
			.code(code)
			.build());
		return ResponseEntity.ok(ApiResult.ofSuccess(null));
	}

}

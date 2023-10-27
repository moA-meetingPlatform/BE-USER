package com.moa.user.presentation;


import com.moa.global.vo.ApiResult;
import com.moa.user.application.VerifyService;
import com.moa.user.vo.request.SendEmailRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Verify Controller", description = "외부 인증을 사용하는 API")
@RestController
@RequestMapping("/api/v1/user/verify")
@Slf4j
@RequiredArgsConstructor
public class VerifyController {

	private final VerifyService verifyService;


	@Operation(summary = "이메일 인증 요청", description = "이메일 인증 요청을 받아 인증 코드를 포함한 이메일을 전송합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "400", description = "이메일 전송 실패", content = @Content(schema = @Schema(implementation = ApiResult.class))),
	})
	@PostMapping("/send-email")
	public ResponseEntity<ApiResult<Void>> sendVerifyEmail(@RequestBody SendEmailRequest request) {
		verifyService.verifyEmail(request.getEmail());
		return ResponseEntity.ok(ApiResult.ofSuccess(null));
	}

}

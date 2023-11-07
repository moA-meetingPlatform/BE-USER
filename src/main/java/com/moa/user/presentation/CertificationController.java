package com.moa.user.presentation;


import com.moa.global.vo.ApiResult;
import com.moa.user.application.CertificationService;
import com.moa.user.dto.CertificationCompanyEmailDto;
import com.moa.user.vo.request.SendEmailConfirmRequest;
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


@Tag(name = "Certification Controller", description = "외부 인증을 사용하는 API")
@RestController
@RequestMapping("/api/v1/user/certification")
@Slf4j
@RequiredArgsConstructor
public class CertificationController {

	private final ModelMapper modelMapper;
	private final CertificationService certificationService;


	@Operation(summary = "회사 이메일 인증 요청", description = "이메일 인증 요청 -> 인증 코드를 포함한 이메일을 전송합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "400", description = "이메일 전송 실패", content = @Content(schema = @Schema(implementation = ApiResult.class))),
		@ApiResponse(responseCode = "404", description = "저장된 회사 이메일이 아님", content = @Content(schema = @Schema(implementation = ApiResult.class)))
	})
	@PostMapping("/email-send")
	public ResponseEntity<ApiResult<Void>> sendCertificationEmail(@RequestBody SendEmailRequest request) {
		certificationService.sendEmail(request.getCompanyEmail());
		return ResponseEntity.ok(ApiResult.ofSuccess(null));
	}


	@Operation(summary = "이메일 인증 번호 확인", description = "이메일 인증번호를 비교하여 인증을 완료")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "400", description = "이메일 인증 실패", content = @Content(schema = @Schema(implementation = ApiResult.class))),
	})
	@PatchMapping("/email-confirm")
	public ResponseEntity<ApiResult<Void>> confirmCertificationEmail(@RequestBody SendEmailConfirmRequest request) {
		certificationService.confirmEmailAndCertificationCompany(modelMapper.map(request, CertificationCompanyEmailDto.class));
		return ResponseEntity.ok(ApiResult.ofSuccess(null));
	}

}

package com.moa.certificate.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자의 접근 권한을 protected로 제한
@Table(name = "user_company_certificate")
public class UserCompanyCertificate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "company_name", nullable = false)
	private String companyName;

	@Column(name = "certificate_image_url", nullable = false)
	private String certificateImageUrl;


	public UserCompanyCertificate(Long userId, String companyName, String certificateImageUrl) {
		this.userId = userId;
		this.companyName = companyName;
		this.certificateImageUrl = certificateImageUrl;
	}

}

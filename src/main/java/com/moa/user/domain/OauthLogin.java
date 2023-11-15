package com.moa.user.domain;


import com.moa.global.domain.BaseEntity;
import com.moa.user.domain.converter.OauthProviderTypeConverter;
import jakarta.persistence.*;
import lombok.*;


@Builder
@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자의 접근 권한을 protected로 제한
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "oauth_login")
public class OauthLogin extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Convert(converter = OauthProviderTypeConverter.class)
	@Column(name = "oauth_provider", nullable = false)
	private OauthProviderType oauthProvider;

	@Column(name = "oauth_user_id", nullable = false)
	private String oauthUserId;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

}

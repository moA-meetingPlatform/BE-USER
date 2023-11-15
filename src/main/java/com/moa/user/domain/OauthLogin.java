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
	@Column(columnDefinition = "char(2)", name = "oauth_provider", nullable = false, length = 2)
	private OauthProviderType oauthProvider;

	@Column(name = "oauth_user_id", nullable = false)
	private String oauthUserId;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;


	public OauthLogin(OauthProviderType oauthProvider, String oauthUserId, User user) {
		this.oauthProvider = oauthProvider;
		this.oauthUserId = oauthUserId;
		this.user = user;
	}

}

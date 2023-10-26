package com.moa.user.domain;


import com.moa.global.domain.BaseEntity;
import com.moa.workplace.domain.Workplace;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


/**
 * 사용자 정보를 담는 도메인 및 엔티티 클래스
 * Spring Security에서 사용자의 정보를 담는 인터페이스인 UserDetails를 구현함
 */
@Builder
@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자의 접근 권한을 protected로 제한
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseEntity implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_uuid", unique = true, nullable = false)
	private UUID userUuid;

	@Column(length = 45, name = "login_id", unique = true)
	private String loginId;

	@Column(length = 100, name = "password")
	private String password;

	@Column(nullable = false, length = 100, name = "name")
	private String name;

	@Column(nullable = false, length = 100, name = "birth_date")
	private LocalDate birthDate;

	@Column(columnDefinition = "tinyint default 1", nullable = false, name = "gender")
	private Gender gender;

	@Column(nullable = false, length = 15, name = "phone_number")
	private String phoneNumber;

	@Column(nullable = false, length = 100, name = "nickname")
	private String nickname;

	@Column(columnDefinition = "tinyint default 1", name = "account_use")
	private Boolean accountUse;

	@Column(length = 100, name = "introduction")
	private String introduction;

	@Column(length = 100, name = "profile_image_url")
	private String profileImage;

	@Column(name = "email_notifications")
	private Boolean emailNotifications;

	@Column(name = "sms_notifications")
	private Boolean smsNotifications;

	@Column(name = "push_notifications")
	private Boolean pushNotifications;

	@Column(name = "device_token")
	private String deviceToken;

	@Column(name = "workplace_verification_date")
	private LocalDate workplaceVerificationDate;

	@Column(columnDefinition = "tinyint default 1", name = "workplace_verified")
	private Boolean workplaceVerified;

	// 외래키
	@ManyToOne
	@JoinColumn(name = "workplace_id")
	private Workplace workplaceId;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}


	@Override
	public String getUsername() {
		return userUuid.toString();
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}


	@Override
	public boolean isEnabled() {
		return this.accountUse;
	}


	public void encodePassword(String password) {
		this.password = new BCryptPasswordEncoder().encode(password);
	}

}

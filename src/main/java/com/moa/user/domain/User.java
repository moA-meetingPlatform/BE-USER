package com.moa.user.domain;


import com.moa.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

	@Column(columnDefinition = "tinyint default 0", name = "user_soft_delete")
	private Boolean userSoftDelete;

	@Column(length = 100, name = "user_introduce")
	private String userIntroduce;

	@Column(length = 100, name = "profile_image_url")
	private String profileImage;

	@Column(name = "email_notification_status")
	private Boolean emailNotificationStatus;

	@Column(name = "sms_notification_status")
	private Boolean smsNotificationStatus;

	@Column(name = "push_notification_status")
	private Boolean pushNotificationStatus;

	@Column(name = "user_device_token")
	private String userDeviceToken;

	@Column(name = "workplace_verification_date")
	private LocalDate workplaceVerificationDate;

	@Column(columnDefinition = "tinyint default 1", name = "workplace_verified")
	private Boolean workplaceVerified;

	@Column(name = "company_id", nullable = false)
	private Integer companyId;  // Company 테이블의 id 저장됨


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}


	@Override
	public String getUsername() {
		return userUuid.toString();
	}


	/**
	 * workplaceVerified가 false이면 계정 만료
	 *
	 * @return 계정 만료 여부 (true: 만료 안됨, false: 만료됨)
	 */
	@Override
	public boolean isAccountNonExpired() {
		return this.workplaceVerified;
	}


	@Override
	public boolean isAccountNonLocked() {
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}


	/**
	 * 탈퇴처리가 완료된 계정은 비활성화
	 *
	 * @return 계정 활성화 여부 (true: 활성화, false: 비활성화)
	 */
	@Override
	public boolean isEnabled() {
		// 탈퇴처리가 완료되었는지 확인
		// userSoftDelete가 true이고, 탈퇴 날짜(updateDatetime)가 7일 이전이면 비활성화
		return !(this.userSoftDelete && this.getUpdateDatetime().isBefore(LocalDateTime.now().minusDays(7)));
	}


	public void encodePassword(String password) {
		this.password = new BCryptPasswordEncoder().encode(password);
	}

}

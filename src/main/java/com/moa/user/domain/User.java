package com.moa.user.domain;

import com.moa.global.domain.BaseEntity;
import com.moa.workplace.domain.Workplace;
import jakarta.persistence.*;
import lombok.*;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@ToString
@Entity
@Getter
@NoArgsConstructor // new User() 막는 용도
@AllArgsConstructor
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 100, name = "user_uuid")
    private String userUuid;
    @Column(length = 45, name = "login_id", unique = true)
    private String loginId;
    @Column(length = 100, name = "password")
    private String password;
    @Column(nullable = false, length = 100, name = "user_name")
    private String userName;
    @Column(nullable = false, length = 100, name = "birth_date")
    private LocalDateTime birthDate;
    @Column(nullable = false, length = 25, name = "gender")
    private int gender;
    @Column(nullable = false, length = 15, name = "phone_number")
    private String phoneNumber;
    @Column(nullable = false, length = 100, name = "nick_name")
    private String nickname;
    @Column(nullable = false, length = 1, name = "status", columnDefinition = "int default 1")
    private int status;
    @Column(length = 100, name = "introduction")
    private String introduction;
    @Column(length = 100, name = "profileImage_url")
    private String profileImage;
    @Column(name = "email_notifications")
    private Boolean emailNotifications;
    @Column(name = "sms_notifications")
    private Boolean smsNotifications;
    @Column(name = "push_notifications")
    private Boolean pushNotifications;
    @Column(name = "device_token")
    private String deviceToken;
    @Column(name = "employment_verification_date")
    private LocalDate employmentVerificationDate;
    // 외래키
    @ManyToOne
    @JoinColumn(name="workplace_id")
    private Workplace workplaceId;
    @Column(name = "employment_verified")
    private Boolean employmentVerified;
}

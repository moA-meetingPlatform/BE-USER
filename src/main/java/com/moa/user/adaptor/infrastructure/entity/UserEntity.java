package com.moa.user.adaptor.infrastructure.entity;



import com.moa.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_uuid", nullable = false, unique = true)
    private UUID userUuid;

    @NotNull
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Size(min = 8, max = 50)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "birth")
    private LocalDateTime birthDate;

    @Column(name = "gender")
    private int gender;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "account_use")
    private int account_use;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "profile_image_url")
    private String profileImage;

    @Column(name = "created_datetime", nullable = false, updatable = false)
    private LocalDateTime createdDatetime;

    @Column(name = "updated_datetime")
    private LocalDateTime updatedDatetime;

    @Column(name = "rating_count")
    private Integer ratingCount;

    @Column(name = "manners_temperature")
    private Double mannersTemperature;

    @Column(name = "warning_points")
    private Integer warningPoints;

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

    @Column(name = "employment_verified")
    private Boolean employmentVerified;

    private static UserEntity signInUser(String id, String password) {
        return UserEntity.builder()
                .id(id)
                .password(password)
                .build();

    }

    private static UserEntity signUpUser(String id, String password, String name, LocalDateTime birthDate, int gender, String phoneNumber, String nickname, int account_use, String introduction, String profileImage, LocalDateTime createdDatetime, LocalDateTime updatedDatetime, Integer ratingCount, Double mannersTemperature, Integer warningPoints, Boolean emailNotifications, Boolean smsNotifications, Boolean pushNotifications, String deviceToken, LocalDate employmentVerificationDate, Boolean employmentVerified) {
        return UserEntity.builder()
                .id(id)
                .password(password)
                .name(name)
                .birthDate(birthDate)
                .gender(gender)
                .phoneNumber(phoneNumber)
                .nickname(nickname)
                .account_use(account_use)
                .introduction(introduction)
                .profileImage(profileImage)
                .createdDatetime(createdDatetime)
                .updatedDatetime(updatedDatetime)
                .ratingCount(ratingCount)
                .mannersTemperature(mannersTemperature)
                .warningPoints(warningPoints)
                .emailNotifications(emailNotifications)
                .smsNotifications(smsNotifications)
                .pushNotifications(pushNotifications)
                .deviceToken(deviceToken)
                .employmentVerificationDate(employmentVerificationDate)
                .employmentVerified(employmentVerified)
                .build();
    }



}
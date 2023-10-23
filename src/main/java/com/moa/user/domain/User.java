package com.moa.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class User {


    private UUID userUuid;

    private String id;

    private String password;

    private String name;

    private LocalDateTime birthDate;

    private int gender;

    private String phoneNumber;

    private String nickname;

    private int account_use;

    private String introduction;

    private String profileImage;

    private LocalDateTime createdDatetime;

    private LocalDateTime updatedDatetime;

    private Integer ratingCount;

    private Double mannersTemperature;

    private Integer warningPoints;

    private Boolean emailNotifications;

    private Boolean smsNotifications;

    private Boolean pushNotifications;

    private String deviceToken;

    private LocalDate employmentVerificationDate;

    private Boolean employmentVerified;

    public static User signInUser(String id, String password) {
        return User.builder()
                .id(id)
                .password(password)
                .build();

    }

    public static User signUpUser(String id, String password, String name, LocalDateTime birthDate, int gender, String phoneNumber, String nickname, int account_use, String introduction, String profileImage, LocalDateTime createdDatetime, LocalDateTime updatedDatetime, Integer ratingCount, Double mannersTemperature, Integer warningPoints, Boolean emailNotifications, Boolean smsNotifications, Boolean pushNotifications, String deviceToken, LocalDate employmentVerificationDate, Boolean employmentVerified) {
        return User.builder()
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

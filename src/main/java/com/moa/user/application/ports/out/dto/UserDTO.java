package com.moa.user.application.ports.out.dto;

import com.moa.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class UserDTO {


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

    public static UserDTO fromUser(User user) {
        return UserDTO.builder()
                .userUuid(user.getUserUuid())
                .id(user.getId())
                .password(user.getPassword())
                .name(user.getName())
                .birthDate(user.getBirthDate())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .nickname(user.getNickname())
                .account_use(user.getAccount_use())
                .introduction(user.getIntroduction())
                .profileImage(user.getProfileImage())
                .createdDatetime(user.getCreatedDatetime())
                .updatedDatetime(user.getUpdatedDatetime())
                .ratingCount(user.getRatingCount())
                .mannersTemperature(user.getMannersTemperature())
                .warningPoints(user.getWarningPoints())
                .emailNotifications(user.getEmailNotifications())
                .smsNotifications(user.getSmsNotifications())
                .pushNotifications(user.getPushNotifications())
                .deviceToken(user.getDeviceToken())
                .employmentVerificationDate(user.getEmploymentVerificationDate())
                .employmentVerified(user.getEmploymentVerified())
                .build();
    }

}

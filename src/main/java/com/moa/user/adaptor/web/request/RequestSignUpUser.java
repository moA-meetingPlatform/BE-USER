package com.moa.user.adaptor.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestSignUpUser {

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

}

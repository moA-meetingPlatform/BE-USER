package com.moa.user.application.ports.in;

import com.moa.user.adaptor.web.request.RequestSignUpUser;
import com.moa.user.adaptor.web.request.RequestUser;
import com.moa.user.application.ports.out.dto.UserDTO;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface UserUseCase {

    UserDTO signInUser(SignInQuery signInQuery);
    UserDTO signUpUser(SignUpQuery signUpQuery);  // updated method signature

    @Getter
    @Builder
    class SignInQuery {

        private String id;
        private String password;

        public static SignInQuery toQuery(RequestUser requestUser) {
            return SignInQuery.builder()
                    .id(requestUser.getId())
                    .password(requestUser.getPassword())
                    .build();
        }

    }

    @Getter
    @Builder
    class SignUpQuery {

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
        private LocalDateTime employmentVerificationDate;
        private Boolean employmentVerified;

        public static SignUpQuery toQuery(RequestSignUpUser requestUser) {
            return SignUpQuery.builder()
                    .id(requestUser.getId())
                    .password(requestUser.getPassword())
                    // ... other field mappings ...
                    .build();
        }

    }

}

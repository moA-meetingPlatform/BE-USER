package com.moa.user.application.service;

import com.moa.user.application.ports.in.UserUseCase;
import com.moa.user.application.ports.out.dto.UserDTO;
import com.moa.user.application.ports.out.port.UserPort;
import com.moa.user.adaptor.infrastructure.entity.UserEntity;
import com.moa.user.adaptor.infrastructure.repository.UserRepository;
import com.moa.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserPort, UserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO signInUser(SignInQuery signInQuery) {
        UserEntity userEntity = userRepository.findById(signInQuery.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID or password"));

        if (!passwordEncoder.matches(signInQuery.getPassword(), userEntity.getPassword())) {
            throw new IllegalArgumentException("Invalid user ID or password");
        }

        return UserDTO.fromUser(new User(userEntity));
    }

    @Override
    public UserDTO signUpUser(SignUpQuery signUpQuery) {
        if (userRepository.existsById(signUpQuery.getId())) {
            throw new IllegalArgumentException("User ID already exists");
        }

        UserEntity userEntity = UserEntity.signUpUser(
                signUpQuery.getId(),
                passwordEncoder.encode(signUpQuery.getPassword()),
                signUpQuery.getName(),
                signUpQuery.getBirthDate(),
                signUpQuery.getGender(),
                signUpQuery.getPhoneNumber(),
                signUpQuery.getNickname(),
                signUpQuery.getAccount_use(),
                signUpQuery.getIntroduction(),
                signUpQuery.getProfileImage(),
                signUpQuery.getCreatedDatetime(),
                signUpQuery.getUpdatedDatetime(),
                signUpQuery.getRatingCount(),
                signUpQuery.getMannersTemperature(),
                signUpQuery.getWarningPoints(),
                signUpQuery.getEmailNotifications(),
                signUpQuery.getSmsNotifications(),
                signUpQuery.getPushNotifications(),
                signUpQuery.getDeviceToken(),
                signUpQuery.getEmploymentVerificationDate(),
                signUpQuery.getEmploymentVerified()

                // ... other fields ...
        );

        userRepository.save(userEntity);

        return UserDTO.fromUser(new User(userEntity));
    }

    // ... other necessary methods ...

}

package com.moa.user.application;

import com.moa.user.domain.Roll;
import com.moa.user.domain.User;
import com.moa.user.dto.LoginDto;
import com.moa.user.dto.UserGetDto;
import com.moa.user.dto.UserSignUpDto;
import com.moa.user.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{


    @Override
    @Transactional(readOnly = false)
    public Long createUser(UserSignUpDto userSignUpDto) {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        String hashedPassword = new BCryptPasswordEncoder().encode(userSignUpDto.getPassword());

        User user = User.builder()
                .loginId(userSignUpDto.getLoginId())
                .userUuid(uuidString)
                .userName(userSignUpDto.getUserName())
                .password(hashedPassword)
                .phone(userSignUpDto.getPhone())
                .build();
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public UserGetDto getUserByLoginId(String loginId) {
        return null;
    }

    @Override
    public UserGetDto getUserByUUID(String UUID) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void modify(String UUID, UserModifyIn userModifyIn) {

    }

    @Override
    public LoginDto loginUser(UserLoginIn userLoginIn) {
        return null;
    }

    @Override
    public UserGetDto getUserDtoFromToken(String token) {
        return null;
    }

    @Override
    public Long getUserId(String loginId) {
        return null;
    }

    @Override
    public Long getUserIdFromToken(String token) {
        return null;
    }

    @Override
    public FindIDOut findID(String userName, String phone) {
        return null;
    }

    @Override
    public void changePassword(String token, String oldPwd, String newPwd) {

    }

    @Override
    public void searchPassword(String loginId, String newPwd) {

    }

    @Override
    public void withdrawal(String token) {

    }

    @Override
    public void authenticatePassword(String token, AuthenticatePasswordIn authenticatePasswordIn) {

    }

    @Override
    public CheckUserOut getOtherUserInfo(CheckUserIn checkUserIn) {
        return null;
    }

    @Override
    public User getUserFromToken(String token) {
        return null;
    }

    @Override
    public LoginDto findOauth(OauthIn oauthIn) {
        return null;
    }

    @Override
    public void createOauth(String token, OauthIn oauthIn) {

    }

}

package com.moa.user.application.ports.out.port;

import org.apache.catalina.User;

public interface UserPort {

    User signInUsers(User user);
    User signUpUsers(User user);


}

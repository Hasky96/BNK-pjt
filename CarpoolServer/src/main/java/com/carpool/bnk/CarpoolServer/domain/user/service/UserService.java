package com.carpool.bnk.CarpoolServer.domain.user.service;

import com.carpool.bnk.CarpoolServer.domain.user.db.entity.User;
import com.carpool.bnk.CarpoolServer.domain.user.request.UserLoginReq;
import com.carpool.bnk.CarpoolServer.domain.user.request.UserPwUpdateReq;
import com.carpool.bnk.CarpoolServer.domain.user.request.UserSignupReq;
import org.springframework.stereotype.Service;

public interface UserService {
    User signup(UserSignupReq body);

    boolean idCheck(String id);

    User login(UserLoginReq body);

    int pwUpdate(UserPwUpdateReq body, User user);
}

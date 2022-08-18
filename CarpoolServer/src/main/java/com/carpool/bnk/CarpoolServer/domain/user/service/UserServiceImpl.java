package com.carpool.bnk.CarpoolServer.domain.user.service;

import com.carpool.bnk.CarpoolServer.domain.user.db.entity.User;
import com.carpool.bnk.CarpoolServer.domain.user.db.repository.UserRepository;
import com.carpool.bnk.CarpoolServer.domain.user.request.UserLoginReq;
import com.carpool.bnk.CarpoolServer.domain.user.request.UserSignupReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Lazy
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User signup(UserSignupReq body) {
        if(userRepository.getUserByUserId(body.getUserId()) !=null)
            return null;
        User user = new User(body.getUserId(), body.getUserCarInfo(), body.getUserCarNo());
        user.setUserPw(body.getUserPw());
        user.setUserPw(passwordEncoder.encode(body.getUserPw()));
        userRepository.save(user);
        return user;
    }

    @Override
    public boolean idCheck(String id) {
        if(userRepository.getUserByUserId(id) == null) return true; // 아이디 사용가능
        return false;
    }

    @Override
    public User login(UserLoginReq body) {
        User userInfo = userRepository.getUserByUserId(body.getUserId());
        if(userInfo == null) return null;
        else if(passwordEncoder.matches( body.getUserPw(), userInfo.getUserPw())){
            return userInfo;
        }
        return null;
    }
}
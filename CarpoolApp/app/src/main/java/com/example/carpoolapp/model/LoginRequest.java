package com.example.carpoolapp.model;

public class LoginRequest {
    private String userId;
    private String userPw;

    public LoginRequest(String userId, String userPw) {
        this.userId = userId;
        this.userPw = userPw;
    }
}

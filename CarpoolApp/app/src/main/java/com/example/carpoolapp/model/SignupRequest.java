package com.example.carpoolapp.model;

public class SignupRequest {
    private String userId;
    private String userPw;
    private String userCarInfo;
    private String userCarNo;


    public SignupRequest(String userId, String userPw, String userCarInfo, String userCarNo) {
        this.userId = userId;
        this.userPw = userPw;
        this.userCarInfo = userCarInfo;
        this.userCarNo = userCarNo;
    }
}

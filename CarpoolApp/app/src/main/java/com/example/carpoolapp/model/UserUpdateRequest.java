package com.example.carpoolapp.model;

public class UserUpdateRequest {
    private String userCarNo;
    private String userCarInfo;

    public UserUpdateRequest(String userCarNo, String userCarInfo) {
        this.userCarNo = userCarNo;
        this.userCarInfo = userCarInfo;
    }

    public String getUserCarNo() {
        return userCarNo;
    }

    public String getUserCarInfo() {
        return userCarInfo;
    }
}

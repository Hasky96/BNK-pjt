package com.example.carpoolapp.model;

public class LoginResponse {
    private String message;
    private Integer statusCode;
    private String accessToken;
    private String userId;
    private Integer userNo;
    private String userCarInfo;
    private String userCarNo;

    public LoginResponse(String message, Integer statusCode, String accessToken, String userId, Integer userNo, String userCarInfo, String userCarNo) {
        this.message = message;
        this.statusCode = statusCode;
        this.accessToken = accessToken;
        this.userId = userId;
        this.userNo = userNo;
        this.userCarInfo = userCarInfo;
        this.userCarNo = userCarNo;
    }

    public String getMessage() {
        return message;
    }
    public Integer getStatusCode() {
        return statusCode;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public String getUserId() {
        return userId;
    }
    public Integer getUserNo() {
        return userNo;
    }
    public String getUserCarInfo() {return userCarInfo;}
    public String getUserCarNo() {return userCarNo;}
}


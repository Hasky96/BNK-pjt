package com.example.carpoolapp.model;

public class LoginResponse {
    private String message;
    private Integer statusCode;
    private String accessToken;
    private String userId;
    private Integer userNo;


    public LoginResponse(String message, Integer statusCode, String accessToken, String userId, Integer userNo) {
        this.message = message;
        this.statusCode = statusCode;
        this.accessToken = accessToken;
        this.userId = userId;
        this.userNo = userNo;
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
}


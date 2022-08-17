package com.example.carpoolapp.model;

public class SignupResponse {
    private String userId;
    private Integer userNo;
    private String msg;

    public SignupResponse(String userId, Integer userNo, String msg) {
        this.userId = userId;
        this.userNo = userNo;
        this.msg = msg;
    }

    public String getUserId() {return userId;}
    public Integer getUserNo() {return userNo;}
    public String getMsg() {return msg;}
}

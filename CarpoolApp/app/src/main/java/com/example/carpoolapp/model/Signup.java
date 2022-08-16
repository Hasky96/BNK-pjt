package com.example.carpoolapp.model;

public class Signup {
    private String userId;
    private String userPw;
    private String userCarInfo;
    private String userCarNO;


    public Signup() {
    }
    public Signup(String userId, String userPw, String userCarInfo, String userCarNO) {
        this.userId = userId;
        this.userPw = userPw;
        this.userCarInfo = userCarInfo;
        this.userCarNO = userCarNO;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUserCarInfo() {
        return userCarInfo;
    }

    public void setUserCarInfo(String userCarInfo) {
        this.userCarInfo = userCarInfo;
    }

    public String getUserCarNO() {
        return userCarNO;
    }

    public void setUserCarNO(String userCarNO) {
        this.userCarNO = userCarNO;
    }
}

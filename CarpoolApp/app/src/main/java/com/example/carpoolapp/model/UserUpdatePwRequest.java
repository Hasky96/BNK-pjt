package com.example.carpoolapp.model;

public class UserUpdatePwRequest {
    private String oldPw;
    private String newPw;

    public UserUpdatePwRequest(String oldPw, String newPw) {
        this.oldPw = oldPw;
        this.newPw = newPw;
    }
}

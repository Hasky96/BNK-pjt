package com.example.carpoolapp.model;

// 카풀 생성 응답
public class CarpoolResponse {
    int carpoolNo;
    String msg;

    public int getCarpoolNo() {
        return carpoolNo;
    }

    public void setCarpoolNo(int carpoolNo) {
        this.carpoolNo = carpoolNo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

package com.example.carpoolapp.model;

import java.util.Date;

public class Carpool {

    private Date depatureTime;
    private String location;
    private Long personNum;
    private String content;

    public Carpool(Date depatureTime, String location, Long personNum, String content) {
        this.depatureTime = depatureTime;
        this.location = location;
        this.personNum = personNum;
        this.content = content;
    }

    public Date getDepatureTime() {
        return depatureTime;
    }

    public void setDepatureTime(Date depatureTime) {
        this.depatureTime = depatureTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getPersonNum() {
        return personNum;
    }

    public void setPersonNum(Long personNum) {
        this.personNum = personNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

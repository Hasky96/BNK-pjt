package com.example.carpoolapp.model;

import java.util.Date;

public class CarpoolRequest {

    int carpoolWriter;
    int carpoolDriver;
    String carpoolTime ;
    int carpoolFee=123;
    boolean carpoolType;
    String carpoolLocation;
    int carpoolQuota;
    String carpoolInfo;

    public CarpoolRequest() {
    }

    public CarpoolRequest(int carpoolWriter, int carpoolDriver, String carpoolTime, int carpoolFee, boolean carpoolType, String carpoolLocation, int carpoolQuota, String carpoolInfo) {
        this.carpoolWriter = carpoolWriter;
        this.carpoolDriver = carpoolDriver;
        this.carpoolTime = carpoolTime;
        this.carpoolFee = carpoolFee;
        this.carpoolType = carpoolType;
        this.carpoolLocation = carpoolLocation;
        this.carpoolQuota = carpoolQuota;
        this.carpoolInfo = carpoolInfo;
    }

    public int getCarpoolWriter() {
        return carpoolWriter;
    }

    public void setCarpoolWriter(int carpoolWriter) {
        this.carpoolWriter = carpoolWriter;
    }

    public int getCarpoolDriver() {
        return carpoolDriver;
    }

    public void setCarpoolDriver(int carpoolDriver) {
        this.carpoolDriver = carpoolDriver;
    }

    public String getCarpoolTime() {
        return carpoolTime;
    }

    public void setCarpoolTime(String carpoolTime) {
        this.carpoolTime = carpoolTime;
    }

    public int getCarpoolFee() {
        return carpoolFee;
    }

    public void setCarpoolFee(int carpoolFee) {
        this.carpoolFee = carpoolFee;
    }

    public boolean isCarpoolType() {
        return carpoolType;
    }

    public void setCarpoolType(boolean carpoolType) {
        this.carpoolType = carpoolType;
    }

    public String getCarpoolLocation() {
        return carpoolLocation;
    }

    public void setCarpoolLocation(String carpoolLocation) {
        this.carpoolLocation = carpoolLocation;
    }

    public int getCarpoolQuota() {
        return carpoolQuota;
    }

    public void setCarpoolQuota(int carpoolQuota) {
        this.carpoolQuota = carpoolQuota;
    }

    public String getCarpoolInfo() {
        return carpoolInfo;
    }

    public void setCarpoolInfo(String carpoolInfo) {
        this.carpoolInfo = carpoolInfo;
    }
}

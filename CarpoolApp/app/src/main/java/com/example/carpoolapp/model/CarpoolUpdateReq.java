package com.example.carpoolapp.model;

public class CarpoolUpdateReq {

	private boolean carpoolType;
	private String carpoolLocation;
	private int carpoolQuota;
	private String carpoolInfo;
	private String carpoolTime;


	public CarpoolUpdateReq(){

	}

	public CarpoolUpdateReq(boolean carpoolType, String carpoolLocation, int carpoolQuota, String carpoolInfo, String carpoolTime) {
		this.carpoolType = carpoolType;
		this.carpoolLocation = carpoolLocation;
		this.carpoolQuota = carpoolQuota;
		this.carpoolInfo = carpoolInfo;
		this.carpoolTime = carpoolTime;
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

	public String getCarpoolTime() {
		return carpoolTime;
	}

	public void setCarpoolTime(String carpoolTime) {
		this.carpoolTime = carpoolTime;
	}
}

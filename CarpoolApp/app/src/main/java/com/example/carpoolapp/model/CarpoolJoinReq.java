package com.example.carpoolapp.model;

public class CarpoolJoinReq {
	boolean driverCheck;

	public CarpoolJoinReq(boolean driverCheck) {
		this.driverCheck = driverCheck;
	}

	public boolean isDriverCheck() {
		return driverCheck;
	}

	public void setDriverCheck(boolean driverCheck) {
		this.driverCheck = driverCheck;
	}
}

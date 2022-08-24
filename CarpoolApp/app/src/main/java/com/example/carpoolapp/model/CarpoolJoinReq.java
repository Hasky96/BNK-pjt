package com.example.carpoolapp.model;

public class CarpoolJoinReq {
	boolean isDriver;

	public CarpoolJoinReq(boolean isDriver) {
		this.isDriver = isDriver;
	}

	public boolean isDriver() {
		return isDriver;
	}

	public void setDriver(boolean driver) {
		isDriver = driver;
	}
}

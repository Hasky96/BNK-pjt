package com.example.carpoolapp.model;

import java.util.List;

public class CarpoolsResponse {
	private List<Carpool> carpools;
	private String msg;

	public CarpoolsResponse() {
	}

	public CarpoolsResponse(List<Carpool> carpools, String msg) {
		this.carpools = carpools;
		this.msg = msg;
	}

	public List<Carpool> getCarpools() {
		return carpools;
	}

	public void setCarpools(List<Carpool> carpools) {
		this.carpools = carpools;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}

package com.example.carpoolapp.model;

import java.util.List;

public class CarpoolsResponse {
	private List<CarpoolAllDetailRes> carpools;
	private String msg;

	public List<CarpoolAllDetailRes> getCarpools() {
		return carpools;
	}

	public void setCarpools(List<CarpoolAllDetailRes> carpools) {
		this.carpools = carpools;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}

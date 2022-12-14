package com.example.carpoolapp.model;

import java.time.LocalDateTime;
import java.util.List;

public class CarpoolAllDetailRes {
	private int carpoolNo;
	private int writerNo;
	private int driverNo;
	private boolean type;
	private String location;
	private int quota;
	private String info;
	private int fee;
	private String created;
	private String time;
	private String carNo;
	private String occupants;

	public CarpoolAllDetailRes() {

	}

	public CarpoolAllDetailRes(int carpoolNo, int writerNo, int driverNo, boolean type, String location, int quota, String info, int fee, String created, String time, String carNo, String occupants) {
		this.carpoolNo = carpoolNo;
		this.writerNo = writerNo;
		this.driverNo = driverNo;
		this.type = type;
		this.location = location;
		this.quota = quota;
		this.info = info;
		this.fee = fee;
		this.created = created;
		this.time = time;
		this.carNo = carNo;
		this.occupants = occupants;
	}

	public int getCarpoolNo() {
		return carpoolNo;
	}

	public void setCarpoolNo(int carpoolNo) {
		this.carpoolNo = carpoolNo;
	}

	public int getWriterNo() {
		return writerNo;
	}

	public void setWriterNo(int writerNo) {
		this.writerNo = writerNo;
	}

	public int getDriverNo() {
		return driverNo;
	}

	public void setDriverNo(int driverNo) {
		this.driverNo = driverNo;
	}

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getQuota() {
		return quota;
	}

	public void setQuota(int quota) {
		this.quota = quota;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getOccupants() {
		return occupants;
	}

	public void setOccupants(String occupants) {
		this.occupants = occupants;
	}
}

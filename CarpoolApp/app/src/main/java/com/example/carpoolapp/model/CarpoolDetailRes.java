package com.example.carpoolapp.model;

import java.io.Serializable;
import java.util.List;

public class CarpoolDetailRes implements Serializable {

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
	private String occupants;
	private String writerId;
	private String carNo;
	private String carInfo;
	private List<CommentDto> comments;

	public CarpoolDetailRes(){

	}

	public CarpoolDetailRes(int carpoolNo, int writerNo, int driverNo, boolean type, String location, int quota, String info, int fee, String created, String time, String occupants, String writerId, String carNo, String carInfo, List<CommentDto> comments) {
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
		this.occupants = occupants;
		this.writerId = writerId;
		this.carNo = carNo;
		this.carInfo = carInfo;
		this.comments = comments;
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

	public String getOccupants() {
		return occupants;
	}

	public void setOccupants(String occupants) {
		this.occupants = occupants;
	}

	public String getWriterId() {
		return writerId;
	}

	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(String carInfo) {
		this.carInfo = carInfo;
	}

	public List<CommentDto> getComments() {
		return comments;
	}

	public void setComments(List<CommentDto> comments) {
		this.comments = comments;
	}
}

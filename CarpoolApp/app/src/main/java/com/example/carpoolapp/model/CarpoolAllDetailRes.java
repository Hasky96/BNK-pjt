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
	private LocalDateTime created;
	private LocalDateTime time;
	private String occupants;
	private List<CommentDto> comments;

	public CarpoolAllDetailRes() {

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

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public String getOccupants() {
		return occupants;
	}

	public void setOccupants(String occupants) {
		this.occupants = occupants;
	}

	public List<CommentDto> getComments() {
		return comments;
	}

	public void setComments(List<CommentDto> comments) {
		this.comments = comments;
	}
}

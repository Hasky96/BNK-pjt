package com.example.carpoolapp.model;

import java.time.LocalDateTime;

public class Carpool {
	int carpoolNo;
	int writerNo;
	int driverNo;
	boolean type;
	String location;
	int quota;
	String info;
	int fee;
	LocalDateTime created;
	LocalDateTime time;
	String occupants;

	public Carpool() {

	}

	public Carpool(int carpoolNo, int writerNo, int driverNo, boolean type, String location, int quota, String info, int fee, LocalDateTime created, LocalDateTime time, String occupants) {
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
	}

	public int getCarpoolNo() {
		return carpoolNo;
	}

	public int getWriterNo() {
		return writerNo;
	}

	public int getDriverNo() {
		return driverNo;
	}

	public boolean isType() {
		return type;
	}

	public String getLocation() {
		return location;
	}

	public int getQuota() {
		return quota;
	}

	public String getInfo() {
		return info;
	}

	public int getFee() {
		return fee;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public String getOccupants() {
		return occupants;
	}
}

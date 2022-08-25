package com.example.carpoolapp.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CommentDto implements Serializable {
	int commentNo;
	int userNo;
	String userId;
	String comment;
	String created;

	public int getCommentNo() {
		return commentNo;
	}

	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return commentNo+ " " + userNo+ " " + userId+ " "+ comment+ " " + created ;
	}

	public CommentDto(int commentNo, int userNo, String userId, String comment, String created) {
		this.commentNo = commentNo;
		this.userNo = userNo;
		this.userId = userId;
		this.comment = comment;
		this.created = created;
	}
}

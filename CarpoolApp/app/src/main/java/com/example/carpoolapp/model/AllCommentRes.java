package com.example.carpoolapp.model;

import java.util.ArrayList;
import java.util.List;



public class AllCommentRes {

    int carpoolNo;
    List<CommentDto> comments = new ArrayList<>();

    public int getCarpoolNo() {
        return carpoolNo;
    }

    public void setCarpoolNo(int carpoolNo) {
        this.carpoolNo = carpoolNo;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    public AllCommentRes(int carpoolNo, List<CommentDto> comments) {
        this.carpoolNo = carpoolNo;
        this.comments = comments;
    }

    public AllCommentRes() {
    }
}

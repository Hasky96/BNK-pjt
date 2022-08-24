package com.carpool.bnk.CarpoolServer.domain.carpool.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {

    private  int commentNo;

    private int userNo;

    private String userId;

    private String comment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime created;

    @Builder
    public CommentDto(int commentNo, int userNo, String userId, String comment, LocalDateTime created){
        this.setCommentNo(commentNo);
        this.setUserNo(userNo);
        this.setUserId(userId);
        this.setComment(comment);
        this.setCreated(created);
    }
}

package com.carpool.bnk.CarpoolServer.domain.carpool.db.entity;

import com.carpool.bnk.CarpoolServer.domain.user.db.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@Table(name = "comments")
public class Comments {

    @Id
    @Column(name = "comment_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentNo;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "carpool_no")
    private Carpool carpool;

    @Column(name = "created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime created;

    @JsonBackReference()
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    @Column(name = "comment_content")
    private String commentContent;

    @Builder
    public Comments(Carpool carpool, User user, String commentContent){
        this.carpool = carpool;
        this.user = user;
        this.commentContent = commentContent;
        this.created = LocalDateTime.now();
    }
}



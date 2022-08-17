package com.carpool.bnk.CarpoolServer.domain.carpool.db.entity;

import com.carpool.bnk.CarpoolServer.domain.user.db.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "carpool_no")
    private Carpool carpool;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    @Column(name = "comment_content")
    private String commentContent;
}



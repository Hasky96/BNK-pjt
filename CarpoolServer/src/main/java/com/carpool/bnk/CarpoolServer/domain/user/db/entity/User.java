package com.carpool.bnk.CarpoolServer.domain.user.db.entity;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private int userNo;
}

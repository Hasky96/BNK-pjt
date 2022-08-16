package com.carpool.bnk.CarpoolServer.domain.user.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private int userNo;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_password")
    private String userPw;

    @Column(name = "user_car_info")
    private String userCarInfo;

    @Column(name = "user_car_no")
    private String userCarNo;

    @Builder
    public User(String userId, String userPw, String userCarInfo, String userCarNo){
        this.userId = userId;
        this.userPw = userPw;
        this.userCarInfo = userCarInfo;
        this.userCarNo = userCarNo;
    }

    @Override
    public String toString() {
        return userId;
    }
}

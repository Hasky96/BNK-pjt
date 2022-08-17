package com.carpool.bnk.CarpoolServer.domain.user.db.entity;

import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Carpool;
import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Comments;
import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Occupants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.internal.build.AllowPrintStacktrace;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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

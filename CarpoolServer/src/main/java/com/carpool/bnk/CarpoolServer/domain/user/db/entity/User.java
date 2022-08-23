package com.carpool.bnk.CarpoolServer.domain.user.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonIgnoreProperties(value = "userPw")
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private int userNo;

    @Column(name = "user_id",unique = true, length = 20)
    private String userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "user_password", length = 20)
    private String userPw;

    @Column(name = "user_car_info", length = 20)
    private String userCarInfo;

    @Column(name = "user_car_no", length = 10)
    private String userCarNo;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "user_mileage")
    private int mileage;


    @Builder
    public User(String userId, String userCarInfo, String userCarNo){
        this.userId = userId;
        this.userCarInfo = userCarInfo;
        this.userCarNo = userCarNo;
    }

    public User userDetail(){
        User user = new User();
        user.setUserPw(null);
        user.setUserId(this.userId);
        user.setUserNo(this.userNo);
        user.setUserCarNo(this.userCarNo);
        user.setUserCarInfo(this.userCarInfo);
        user.setMileage(this.mileage);
        return user;
    }
    @Override
    public String toString() {
        return userId;
    }
}

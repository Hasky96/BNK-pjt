package com.carpool.bnk.CarpoolServer.domain.user.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSignupReq {
    private String userId;

    private String userPw;

    private String userCarInfo;

    private String userCarNo;
}

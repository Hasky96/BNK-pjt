package com.carpool.bnk.CarpoolServer.domain.user.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSignupRes {
    String userId;
    int userNo;
    String msg;

    @Builder
    public UserSignupRes(String userId, int userNo, String msg){
        this.userId = userId;
        this.userNo = userNo;
        this.msg = msg;
    }
}

package com.carpool.bnk.CarpoolServer.domain.user.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UserPwUpdateReq {
    private String oldPw;

    private String newPw;
}

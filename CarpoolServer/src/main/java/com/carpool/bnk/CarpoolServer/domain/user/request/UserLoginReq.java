package com.carpool.bnk.CarpoolServer.domain.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserLoginReq {
    String UserId;
    String UserPw;
}

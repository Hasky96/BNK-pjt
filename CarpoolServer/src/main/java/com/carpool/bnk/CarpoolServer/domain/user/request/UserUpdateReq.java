package com.carpool.bnk.CarpoolServer.domain.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateReq {

    String userCarInfo;

    String userCarNo;

}

package com.carpool.bnk.CarpoolServer.domain.user.response;

import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Carpool;
import com.carpool.bnk.CarpoolServer.domain.carpool.dto.CarpoolsDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class UserInfoRes {

    private int userNo;

    private String userId;

    private String userCarInfo;

    private String userCarNo;

    private int mileage;

    private int carpoolCnt;

    private List<CarpoolsDto> carpools;

    @Builder
    public UserInfoRes(int userNo, String userId, String userCarInfo, String userCarNo, int mileage, List<CarpoolsDto> list ){
        this.userNo = userNo;
        this.userId = userId;
        this.userCarInfo = userCarInfo;
        this.userCarNo = userCarNo;
        this.mileage = mileage;
        this.carpools = list;
    }


}

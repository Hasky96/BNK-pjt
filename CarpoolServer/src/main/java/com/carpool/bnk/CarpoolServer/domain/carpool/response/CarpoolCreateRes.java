package com.carpool.bnk.CarpoolServer.domain.carpool.response;

import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Carpool;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CarpoolCreateRes {

    private int capoolNo;
    private String msg;

    public CarpoolCreateRes(int carpoolNo, String msg) {
        this.capoolNo = capoolNo;
        this.msg = msg;
    }

    public static CarpoolCreateRes of(int carpoolNo, String msg){
        CarpoolCreateRes res = new CarpoolCreateRes();
        res.capoolNo = carpoolNo;
        res.msg = msg;
        return res;
    }
}

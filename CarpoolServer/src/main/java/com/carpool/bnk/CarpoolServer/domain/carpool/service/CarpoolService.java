package com.carpool.bnk.CarpoolServer.domain.carpool.service;

import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Carpool;
import com.carpool.bnk.CarpoolServer.domain.carpool.dto.CarpoolsDto;
import com.carpool.bnk.CarpoolServer.domain.carpool.request.CarpoolCreateReq;
import com.carpool.bnk.CarpoolServer.domain.carpool.request.CarpoolUpdateReq;
import com.carpool.bnk.CarpoolServer.domain.user.db.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CarpoolService {
    public Carpool carpoolCreate(CarpoolCreateReq body);

    Carpool carpoolUpdate(Carpool carpool, CarpoolUpdateReq body);

    boolean joinCarpool(Carpool carpool, User use);

    boolean leaveCarpool(Carpool carpool, User user);

    List<CarpoolsDto> getUserCarpools(int userNo);

    int carpoolDone(Carpool carpool);

    int getCarpoolCnt(int userNo);
}

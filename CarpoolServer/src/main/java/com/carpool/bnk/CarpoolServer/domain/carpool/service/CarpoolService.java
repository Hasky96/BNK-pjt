package com.carpool.bnk.CarpoolServer.domain.carpool.service;

import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Carpool;
import com.carpool.bnk.CarpoolServer.domain.carpool.request.CarpoolCreateReq;
import org.springframework.stereotype.Service;


public interface CarpoolService {
    public Carpool carpoolCreate(CarpoolCreateReq body);

}

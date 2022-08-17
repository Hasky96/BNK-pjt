package com.carpool.bnk.CarpoolServer.domain.carpool.service;

import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Carpool;
import com.carpool.bnk.CarpoolServer.domain.carpool.db.repository.CarpoolRepository;
import com.carpool.bnk.CarpoolServer.domain.carpool.request.CarpoolCreateReq;
import com.carpool.bnk.CarpoolServer.domain.user.db.repository.UserRepository;
import com.carpool.bnk.CarpoolServer.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarpoolServiceImpl implements CarpoolService{

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CarpoolRepository carpoolRepository;

    @Override
    public Carpool carpoolCreate(CarpoolCreateReq body) {
        Carpool newCarpool = new Carpool(userRepository.getUserByUserNo(body.getCarpoolWriter()),
                                        userRepository.getUserByUserNo(body.getCarpoolDriver()),
                                        body.isCarpoolType(),
                                        body.getCarpoolLocation(),
                                        body.getCarpoolQuota(),
                                        body.getCarpoolInfo(),
                                        body.getCarpoolFee(),
                                        body.getCarpoolTime()
        );
        carpoolRepository.save(newCarpool);
        return newCarpool;
    }
}

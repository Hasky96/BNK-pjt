package com.carpool.bnk.CarpoolServer.domain.carpool.service;

import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Carpool;
import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Occupants;
import com.carpool.bnk.CarpoolServer.domain.carpool.db.repository.CarpoolRepository;
import com.carpool.bnk.CarpoolServer.domain.carpool.db.repository.CarpoolRepositorySpp;
import com.carpool.bnk.CarpoolServer.domain.carpool.db.repository.OccupantsRepository;
import com.carpool.bnk.CarpoolServer.domain.carpool.request.CarpoolCreateReq;
import com.carpool.bnk.CarpoolServer.domain.carpool.request.CarpoolUpdateReq;
import com.carpool.bnk.CarpoolServer.domain.user.db.entity.User;
import com.carpool.bnk.CarpoolServer.domain.user.db.repository.UserRepository;
import com.carpool.bnk.CarpoolServer.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CarpoolServiceImpl implements CarpoolService{

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CarpoolRepository carpoolRepository;

    @Autowired
    OccupantsRepository occupantsRepository;

    @Autowired
    CarpoolRepositorySpp carpoolRepositorySpp;

    @Override
    public Carpool carpoolCreate(CarpoolCreateReq body) {
        User writer = userRepository.getUserByUserNo(body.getCarpoolWriter());
        User driver = null;
        if(body.getCarpoolDriver()==0) driver = userRepository.getUserByUserNo(body.getCarpoolDriver());
        Carpool newCarpool = new Carpool(driver,
                                        writer,
                                        body.isCarpoolType(),
                                        body.getCarpoolLocation(),
                                        body.getCarpoolQuota(),
                                        body.getCarpoolInfo(),
                                        body.getCarpoolTime()
        );
        newCarpool.setCarpoolFee(100);
        newCarpool.setDone(false);
        carpoolRepository.save(newCarpool);
        Occupants join = new Occupants(newCarpool, userRepository.getUserByUserNo(body.getCarpoolWriter()));
        occupantsRepository.save(join);
        return newCarpool;
    }

    @Override
    public Carpool carpoolUpdate(Carpool carpool, CarpoolUpdateReq body) {
        try {
            carpool.setCarpoolInfo(body.getCarpoolInfo());
            carpool.setCarpoolQuota(body.getCarpoolQuota());
            carpool.setCarpoolTime(body.getCarpoolTime());
            carpool.setCarpoolLocation(body.getCarpoolLocation());
            carpool.setCarpoolType(body.isCarpoolType());
            return carpool;
        }catch (Exception e){
            System.err.println(e);
            return null;
        }
    }

    @Override
    public boolean joinCarpool(Carpool carpool, User user) {
        boolean ret = true;
        try {
            Occupants joinUser = new Occupants(carpool, user);
            occupantsRepository.save(joinUser);
            carpool.setCarpoolFee(carpool.getCarpoolFee()+100);
        }catch (Exception e){
            ret = false;
            System.err.println(e);
        }
        return ret;
    }

    @Override
    public boolean leaveCarpool(Carpool carpool, User user) {
        List<Occupants> list = carpool.getOccupants();
        int relationNo = 0;
        boolean flag = false;
        for(Occupants occu:list){
            if(occu.getUser().getUserNo() == user.getUserNo()){
                relationNo = occu.getRelationNo();
                flag = true;
                break;
            }
        }
        if (!flag) return false; //
        occupantsRepository.deleteByRelationNo(relationNo);
        return true;//
    }

    public static List<String> getOccuUserIds(List<Occupants> list){
        List<String> ret = new ArrayList<>();
        for (Occupants occu: list){
            ret.add(occu.getUser().getUserId());
        }
        return ret;
    }

    @Override
    public List<Carpool> getUserCarpools(int userNo) {
        List<Carpool> carpools = carpoolRepositorySpp.userCarpools(userNo);
        return carpools;
    }
}

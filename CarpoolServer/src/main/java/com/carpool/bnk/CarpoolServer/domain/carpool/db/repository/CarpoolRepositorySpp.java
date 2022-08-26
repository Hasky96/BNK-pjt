package com.carpool.bnk.CarpoolServer.domain.carpool.db.repository;

import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Carpool;
import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.QCarpool;
import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.QOccupants;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public class CarpoolRepositorySpp{

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    QCarpool qCarpool= QCarpool.carpool;
    QOccupants qOccupants = QOccupants.occupants;

    public List<Carpool> userCarpools(int userNo){

        return jpaQueryFactory.select(qCarpool)
                .from(qCarpool)
                .leftJoin(qOccupants).on(qCarpool.carpoolNo.eq(qOccupants.carpool.carpoolNo))
                .where(qCarpool.done.eq(false), qOccupants.user.userNo.eq(userNo)).fetch();
    }


    public int getCarpoolCnt(int userNo) {

        return (int) jpaQueryFactory.select(qCarpool)
                .from(qCarpool)
                .where(qCarpool.carpoolDriver.userNo.eq(userNo), qCarpool.done.eq(true))
                .fetchCount();
    }

    public List<Carpool> getAllCarpools(){
        return jpaQueryFactory.select(qCarpool)
                .from(qCarpool)
                .where(qCarpool.carpoolTime.after(LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(18,00))), qCarpool.done.eq(false))
                .orderBy(qCarpool.carpoolTime.desc()).fetch();
    }
}

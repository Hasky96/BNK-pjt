package com.carpool.bnk.CarpoolServer.domain.carpool.db.repository;

import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Carpool;
import com.carpool.bnk.CarpoolServer.domain.user.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarpoolRepository extends JpaRepository<Carpool, Integer> {
    Carpool findCarpoolByCarpoolWriter(int carpoolWriter);
}

package com.carpool.bnk.CarpoolServer.domain.carpool.db.repository;

import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Occupants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OccupantsRepository extends JpaRepository<Occupants, Integer> {
    void deleteOccupantsByRelationNo(int relationNo);
    void removeOccupantsByRelationNo(int relationNo);
    void deleteByRelationNo(int relationNo);
}

package com.carpool.bnk.CarpoolServer.domain.user.db.repository;

import com.carpool.bnk.CarpoolServer.domain.user.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User getUserByUserNo(int userNo);
    User getUserByUserId(String Id);
}

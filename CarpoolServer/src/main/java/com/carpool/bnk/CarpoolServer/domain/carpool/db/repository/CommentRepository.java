package com.carpool.bnk.CarpoolServer.domain.carpool.db.repository;

import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments, Integer> {
    Comments getCommentsByCommentNo(int commentNo);
}

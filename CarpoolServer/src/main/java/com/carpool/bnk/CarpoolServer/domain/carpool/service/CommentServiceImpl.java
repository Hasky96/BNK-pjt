package com.carpool.bnk.CarpoolServer.domain.carpool.service;

import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Carpool;
import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Comments;
import com.carpool.bnk.CarpoolServer.domain.carpool.db.repository.CarpoolRepository;
import com.carpool.bnk.CarpoolServer.domain.carpool.db.repository.CarpoolRepositorySpp;
import com.carpool.bnk.CarpoolServer.domain.carpool.request.CarpoolCommentReq;
import com.carpool.bnk.CarpoolServer.domain.user.db.entity.User;
import com.carpool.bnk.CarpoolServer.domain.user.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{

    @Autowired
    CarpoolRepository carpoolRepository;

    @Autowired
    CarpoolRepositorySpp carpoolRepositorySpp;

    @Autowired
    UserRepository userRepository;

    @Override
    public Comments addComment(int carpoolNo, int userNo, CarpoolCommentReq body) {
        Carpool carpool = carpoolRepository.getCarpoolByCarpoolNo(carpoolNo);
        User writer = userRepository.getUserByUserNo(userNo);
        Comments comment = new Comments(carpool, writer, body.getComment());
        return comment;
    }
}

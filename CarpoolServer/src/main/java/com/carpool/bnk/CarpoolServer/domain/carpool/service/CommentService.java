package com.carpool.bnk.CarpoolServer.domain.carpool.service;

import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Comments;
import com.carpool.bnk.CarpoolServer.domain.carpool.request.CarpoolCommentReq;

public interface CommentService {
    Comments addComment(int carpoolNo, int userNo, CarpoolCommentReq body);
}

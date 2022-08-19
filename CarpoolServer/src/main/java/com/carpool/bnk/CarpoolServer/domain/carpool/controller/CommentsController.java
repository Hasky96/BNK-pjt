package com.carpool.bnk.CarpoolServer.domain.carpool.controller;

import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Carpool;
import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Comments;
import com.carpool.bnk.CarpoolServer.domain.carpool.db.repository.CommentRepository;
import com.carpool.bnk.CarpoolServer.domain.carpool.request.CarpoolCommentReq;
import com.carpool.bnk.CarpoolServer.domain.carpool.service.CommentService;
import com.carpool.bnk.CarpoolServer.domain.user.db.entity.User;
import com.carpool.bnk.CarpoolServer.global.auth.UserDetails;
import com.carpool.bnk.CarpoolServer.global.util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carpool")
public class CommentsController {

        @Autowired
        CommentService commentService;

        @Autowired
        CommentRepository commentRepository;

        @PostMapping("/{carpoolNo}/comment")
        public ResponseEntity<?> addComment(@PathVariable("carpoolNo")int carpoolNo, Authentication authentication, @RequestBody CarpoolCommentReq body){
            UserDetails userDetails = (UserDetails) authentication.getDetails();

            Comments comment = commentService.addComment(carpoolNo, userDetails.getUser().getUserNo(), body);
            commentRepository.save(comment);
            return ResponseEntity.status(200).body(new CommonResponse("Success!"));
        }

        @DeleteMapping("comment/{commentNo}")
        public ResponseEntity<?> delComment(@PathVariable("commentNo")int commentNo, Authentication authentication){
            Comments comment = commentRepository.getCommentsByCommentNo(commentNo);
            UserDetails userDetails = (UserDetails) authentication.getDetails();
            User user = userDetails.getUser();
            if(user.getUserNo() != comment.getUser().getUserNo()) return ResponseEntity.status(400).body(new CommonResponse("forbidden!(not Owner)"));
            commentRepository.delete(comment);
            return ResponseEntity.status(200).body(new CommonResponse("Success!"));
        }
}

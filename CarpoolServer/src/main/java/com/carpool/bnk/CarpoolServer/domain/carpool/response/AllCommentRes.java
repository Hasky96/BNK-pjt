package com.carpool.bnk.CarpoolServer.domain.carpool.response;

import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Comments;
import com.carpool.bnk.CarpoolServer.domain.carpool.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class AllCommentRes {

    int carpoolNo;
    List<CommentDto> comments = new ArrayList<>();

    @Builder
    public AllCommentRes(List<Comments> list, int carpoolNo){
        if(list != null){
            for(Comments temp:list){
                this.comments.add(new CommentDto(temp.getCommentNo(),
                        temp.getUser().getUserNo(),
                        temp.getUser().getUserId(),
                        temp.getCommentContent(),
                        temp.getCreated()));

            }
        }
        this.carpoolNo = carpoolNo;
    }
}

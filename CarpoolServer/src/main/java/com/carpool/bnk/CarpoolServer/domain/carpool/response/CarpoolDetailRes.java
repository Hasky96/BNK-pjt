package com.carpool.bnk.CarpoolServer.domain.carpool.response;

import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Carpool;
import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Comments;
import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Occupants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CarpoolDetailRes {

    private int carpoolNo;

    private int writerNo;

    private int driverNo;

    private boolean type;

    private String location;

    private int quota;

    private String info;

    private int fee;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime created;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime time;

    private List<Occupants> occupants;

    private List<Comments> comments;

    @Builder
    public CarpoolDetailRes(Carpool carpool){
        this.carpoolNo = carpool.getCarpoolNo();
        this.writerNo = carpool.getCarpoolWriter().getUserNo();
        this.driverNo = carpool.getCarpoolDriver().getUserNo();
        this.created = carpool.getCarpoolCreated();
        this.comments = carpool.getComments();
        this.fee = carpool.getCarpoolFee();
        this.info = carpool.getCarpoolInfo();
        this.location = carpool.getCarpoolLocation();
        this.quota = carpool.getCarpoolQuota();
        this.type = carpool.isCarpoolType();
        this.occupants = carpool.getOccupants();
        this.time = carpool.getCarpoolTime();
    }


}
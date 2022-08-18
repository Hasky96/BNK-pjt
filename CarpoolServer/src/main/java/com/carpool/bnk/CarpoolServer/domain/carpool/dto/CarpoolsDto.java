package com.carpool.bnk.CarpoolServer.domain.carpool.dto;

import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Comments;
import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Occupants;
import com.carpool.bnk.CarpoolServer.domain.user.db.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CarpoolsDto {

    private int carpoolNo;

    private int carpoolWriter;

    private int carpoolDriver;

    private boolean carpoolType;  // true : 퇴근, false : 출근

    private String carpoolLocation;

    private int carpoolQuota;

    private String carpoolInfo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime carpoolTime;

    private boolean done;

}

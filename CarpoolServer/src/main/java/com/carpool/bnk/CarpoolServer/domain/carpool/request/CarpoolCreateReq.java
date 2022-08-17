package com.carpool.bnk.CarpoolServer.domain.carpool.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CarpoolCreateReq {

    private int carpoolWriter;

    private int carpoolDriver;

    private boolean carpoolType;

    private String carpoolLocation;

    private int carpoolQuota;

    private String carpoolInfo;

    private int carpoolFee;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime carpoolTime;


}

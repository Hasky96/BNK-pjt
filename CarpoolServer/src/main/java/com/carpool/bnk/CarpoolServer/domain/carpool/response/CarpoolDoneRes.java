package com.carpool.bnk.CarpoolServer.domain.carpool.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class CarpoolDoneRes {
    String UserId;

    int mileage;

    int totalMileage;

    String Msg;
}

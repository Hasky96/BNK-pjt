package com.carpool.bnk.CarpoolServer.domain.Map.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Station {

//    private String name; => map key

    private String lat;
    private String lng;
    private String loca;


}

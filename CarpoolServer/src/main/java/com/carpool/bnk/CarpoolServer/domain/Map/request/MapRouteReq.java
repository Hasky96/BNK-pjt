package com.carpool.bnk.CarpoolServer.domain.Map.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MapRouteReq {

    private String sLat;

    private String sLng;

    private String dLat;

    private String dLng;
}

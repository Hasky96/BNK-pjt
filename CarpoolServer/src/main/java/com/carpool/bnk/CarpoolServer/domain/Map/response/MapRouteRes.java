package com.carpool.bnk.CarpoolServer.domain.Map.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MapRouteRes {
    private String msg;

    private String path;
}

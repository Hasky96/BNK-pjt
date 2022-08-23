package com.carpool.bnk.CarpoolServer.domain.Map.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    private String locationRoad;

    private String locationJibun;

    private String locaLat; // x naverApi

    private String locaLng; // y naverApi
}

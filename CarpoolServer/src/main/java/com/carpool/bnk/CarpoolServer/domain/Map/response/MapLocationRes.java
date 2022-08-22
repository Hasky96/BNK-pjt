package com.carpool.bnk.CarpoolServer.domain.Map.response;

import com.carpool.bnk.CarpoolServer.domain.Map.dto.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MapLocationRes {
    private String msg;

    private List<Location> locations;
}

package com.carpool.bnk.CarpoolServer.domain.Map.service;

import com.carpool.bnk.CarpoolServer.domain.Map.dto.Location;

import java.io.IOException;
import java.util.List;

public interface MapService {

    String getRoute(String sLng, String sLat) throws Exception;

    List<Location> getLocations(String query) throws Exception;
}

package com.carpool.bnk.CarpoolServer.domain.Map.service;

import java.io.IOException;

public interface MapService {

    String getRoute(String sLng, String sLat) throws Exception;
}

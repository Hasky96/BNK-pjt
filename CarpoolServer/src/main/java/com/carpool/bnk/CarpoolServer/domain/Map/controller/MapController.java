package com.carpool.bnk.CarpoolServer.domain.Map.controller;

import com.carpool.bnk.CarpoolServer.domain.Map.request.MapRouteReq;
import com.carpool.bnk.CarpoolServer.domain.Map.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/map")
public class MapController {

    @Autowired
    MapService mapService;

    @PostMapping("/route")
//    public ResponseEntity<?> getRoute(@RequestBody MapRouteReq body){
    public ResponseEntity<?> getRoute() throws Exception {
        mapService.test();
        return null;
    }
}

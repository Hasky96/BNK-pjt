package com.carpool.bnk.CarpoolServer.domain.Map.controller;

import com.carpool.bnk.CarpoolServer.domain.Map.dto.Location;
import com.carpool.bnk.CarpoolServer.domain.Map.request.MapRouteReq;
import com.carpool.bnk.CarpoolServer.domain.Map.response.MapLocationRes;
import com.carpool.bnk.CarpoolServer.domain.Map.response.MapRouteRes;
import com.carpool.bnk.CarpoolServer.domain.Map.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/map")
public class MapController {

    @Autowired
    MapService mapService;

    @PostMapping("/route")
//    public ResponseEntity<?> getRoute() throws Exception{
    public ResponseEntity<?> getRoute(@RequestBody MapRouteReq body) throws Exception {
        String path = mapService.getRoute(body.getQuery());
        return ResponseEntity.status(200).body(new MapRouteRes("SUCCESS!", path));
    }

    @GetMapping("/loca")
    public ResponseEntity<?> getLocation(@RequestParam String query) throws Exception {
        List<Location> locas = mapService.getLocations(query);

        if(locas.isEmpty()){
            return ResponseEntity.status(400).body(new MapLocationRes("Wrong query", null));
        }
        return ResponseEntity.status(400).body(new MapLocationRes("Success!", locas));

    }
}

package com.carpool.bnk.CarpoolServer.domain.carpool.controller;

import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Carpool;
import com.carpool.bnk.CarpoolServer.domain.carpool.db.repository.CarpoolRepository;
import com.carpool.bnk.CarpoolServer.domain.carpool.request.CarpoolCreateReq;
import com.carpool.bnk.CarpoolServer.domain.carpool.response.CarpoolCreateRes;
import com.carpool.bnk.CarpoolServer.domain.carpool.service.CarpoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/carpool")
public class CarpoolController {

    @Autowired
    CarpoolService carpoolService;

    @Autowired
    CarpoolRepository carpoolRepository;

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.status(200).body("Good");
    }

    @PostMapping("/create")
    public ResponseEntity<CarpoolCreateRes> carpoolCreate(@RequestBody CarpoolCreateReq body){
        Carpool carpool = carpoolService.carpoolCreate(body);

        if(carpool==null){
            return ResponseEntity.status(400).body(new CarpoolCreateRes(0,"Exist ID"));
        }

        return ResponseEntity.status(200).body(CarpoolCreateRes.of(carpool.getCarpoolNo(), "Success"));
    }
}

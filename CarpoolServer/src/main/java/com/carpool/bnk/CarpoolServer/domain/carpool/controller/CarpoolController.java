package com.carpool.bnk.CarpoolServer.domain.carpool.controller;

import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Carpool;
import com.carpool.bnk.CarpoolServer.domain.carpool.db.repository.CarpoolRepository;
import com.carpool.bnk.CarpoolServer.domain.carpool.request.CarpoolCreateReq;
import com.carpool.bnk.CarpoolServer.domain.carpool.request.CarpoolUpdateReq;
import com.carpool.bnk.CarpoolServer.domain.carpool.response.CarpoolCreateRes;
import com.carpool.bnk.CarpoolServer.domain.carpool.response.CarpoolDetailRes;
import com.carpool.bnk.CarpoolServer.domain.carpool.service.CarpoolService;
import com.carpool.bnk.CarpoolServer.domain.carpool.service.CarpoolServiceImpl;
import com.carpool.bnk.CarpoolServer.domain.user.db.entity.User;
import com.carpool.bnk.CarpoolServer.global.auth.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @GetMapping("/{carpoolNo}")
    public ResponseEntity<?> detail(@PathVariable("carpoolNo") int carpoolNo){

        Carpool carpool = carpoolRepository.getCarpoolByCarpoolNo(carpoolNo);
        if(carpool == null) return ResponseEntity.status(400).body("Not Exist!");
        return ResponseEntity.status(200).body(new CarpoolDetailRes(carpool));
    }

    @DeleteMapping("/{carpoolNo}")
    public ResponseEntity<?> delete(@PathVariable("carpoolNo")int carpoolNo){
        Carpool carpool = carpoolRepository.getCarpoolByCarpoolNo(carpoolNo);
        if(carpool==null) return ResponseEntity.status(400).body("Not Exist carpool..!");
        carpoolRepository.delete(carpool);
        return ResponseEntity.status(200).body("Successfully deleted!");
    }

    @PutMapping("/{carpoolNo}")
    public ResponseEntity<?> update(@PathVariable("carpoolNo")int carpoolNo, @RequestBody CarpoolUpdateReq body){
        Carpool carpool = carpoolRepository.getCarpoolByCarpoolNo(carpoolNo);
        if(carpool == null){
            return ResponseEntity.status(400).body("Not Exist!");
        }
        carpool = carpoolService.carpoolUpdate(carpool, body);
        if(carpool==null){
            return ResponseEntity.status(400).body("Update Failed!!!\nPlease put right value");
        }
        return ResponseEntity.status(200).body("Updated Successfully!");
    }

    @PostMapping("/join/{carpoolNo}")
    public ResponseEntity<?> join(@PathVariable("carpoolNo")int carpoolNo, Authentication authentication){
        Carpool carpool = carpoolRepository.getCarpoolByCarpoolNo(carpoolNo);
        if(carpool.getCarpoolQuota() == carpool.getOccupants().size()) return ResponseEntity.status(500).body("Already Full!");
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        User user = userDetails.getUser();
        if(CarpoolServiceImpl.getOccuUserIds(carpool.getOccupants()).contains(user.getUserId())){
            return ResponseEntity.status(400).body("Already in List");
        }
        boolean result = carpoolService.joinCarpool(carpool, user);
        if(result) return ResponseEntity.status(200).body("Successfully Added");
        return ResponseEntity.status(500).body("Server Error!");
    }

    @DeleteMapping("leave/{carpoolNo}")
    public ResponseEntity<?> leave(@PathVariable("carpoolNo")int carpoolNo, Authentication authentication){
        Carpool carpool = carpoolRepository.getCarpoolByCarpoolNo(carpoolNo);
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        User user = userDetails.getUser();
        boolean status = carpoolService.leaveCarpool(carpool, user);
        int statusCode = status ? 200:400;
        String msg = status ? "Successfully Deleted!":"user not in the carpool.";
        return ResponseEntity.status(statusCode).body(msg);
    }
}

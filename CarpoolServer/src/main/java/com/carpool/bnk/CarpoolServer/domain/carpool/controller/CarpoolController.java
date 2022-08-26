package com.carpool.bnk.CarpoolServer.domain.carpool.controller;

import com.carpool.bnk.CarpoolServer.domain.carpool.db.entity.Carpool;
import com.carpool.bnk.CarpoolServer.domain.carpool.db.repository.CarpoolRepository;
import com.carpool.bnk.CarpoolServer.domain.carpool.request.CarpoolCreateReq;
import com.carpool.bnk.CarpoolServer.domain.carpool.request.CarpoolJoinReq;
import com.carpool.bnk.CarpoolServer.domain.carpool.request.CarpoolUpdateReq;
import com.carpool.bnk.CarpoolServer.domain.carpool.response.*;
import com.carpool.bnk.CarpoolServer.domain.carpool.service.CarpoolService;
import com.carpool.bnk.CarpoolServer.domain.carpool.service.CarpoolServiceImpl;
import com.carpool.bnk.CarpoolServer.domain.user.db.entity.User;
import com.carpool.bnk.CarpoolServer.domain.user.db.repository.UserRepository;
import com.carpool.bnk.CarpoolServer.global.auth.UserDetails;
import com.carpool.bnk.CarpoolServer.global.util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/carpool")
public class CarpoolController {

    @Autowired
    CarpoolService carpoolService;

    @Autowired
    CarpoolRepository carpoolRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.status(200).body("Good");
    }

    @PostMapping("/create")
    public ResponseEntity<CarpoolCreateRes> carpoolCreate(@RequestBody CarpoolCreateReq body){
        Carpool carpool = carpoolService.carpoolCreate(body);

        if(carpool==null){
            return ResponseEntity.status(400).body(new CarpoolCreateRes(0,"ERROR \nSet time before now\netc..."));
        }

        return ResponseEntity.status(200).body(CarpoolCreateRes.of(carpool.getCarpoolNo(), "Success"));
    }

    @GetMapping("/{carpoolNo}")
    public ResponseEntity<?> detail(@PathVariable("carpoolNo") int carpoolNo){

        Carpool carpool = carpoolRepository.getCarpoolByCarpoolNo(carpoolNo);
        if(carpool == null) return ResponseEntity.status(400).body(new CommonResponse("Not Exist!"));
        return ResponseEntity.status(200).body(new CarpoolDetailRes(carpool));
    }

    @DeleteMapping("/{carpoolNo}")
    public ResponseEntity<?> delete(@PathVariable("carpoolNo")int carpoolNo){
        Carpool carpool = carpoolRepository.getCarpoolByCarpoolNo(carpoolNo);
        if(carpool==null) return ResponseEntity.status(400).body(new CommonResponse("Not Exist carpool..!"));
        carpoolRepository.delete(carpool);
        return ResponseEntity.status(200).body(new CommonResponse("Successfully deleted!"));
    }

    @PutMapping("/{carpoolNo}")
    public ResponseEntity<?> update(@PathVariable("carpoolNo")int carpoolNo, @RequestBody CarpoolUpdateReq body, Authentication authentication){
        Carpool carpool = carpoolRepository.getCarpoolByCarpoolNo(carpoolNo);
        User user = ((UserDetails) authentication.getDetails()).getUser();
        if(body.isDriverCheck() && carpool.getCarpoolDriver()!=null) return ResponseEntity.status(400).body(new CommonResponse("이미 드라이버 있음"));
        if(carpool == null){
            return ResponseEntity.status(400).body(new CommonResponse("Not Exist!"));
        }
        carpool = carpoolService.carpoolUpdate(carpool, body);
        if(body.isDriverCheck()){
            carpool.setCarpoolDriver(user);
            carpoolRepository.save(carpool);
        }
        if(carpool==null){
            return ResponseEntity.status(400).body(new CommonResponse("Update Failed!!!\nPlease put right value"));
        }
        return ResponseEntity.status(200).body(new CommonResponse("Updated Successfully!"));
    }

    @PostMapping("/join/{carpoolNo}")
    public ResponseEntity<?> join(@PathVariable("carpoolNo")int carpoolNo, Authentication authentication, @RequestBody CarpoolJoinReq body){

        Carpool carpool = carpoolRepository.getCarpoolByCarpoolNo(carpoolNo);
        boolean isDriver = body.isDriverCheck();
        if(isDriver && carpool.getCarpoolDriver()!=null) return ResponseEntity.status(400).body(new CommonResponse("이미 드라이버 있음"));
        if(carpool.getCarpoolQuota() == carpool.getOccupants().size()) return ResponseEntity.status(500).body(new CommonResponse("Already Full!"));
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        User user = userDetails.getUser();
        if(CarpoolServiceImpl.getOccuUserIds(carpool.getOccupants()).contains(user.getUserId())){
            return ResponseEntity.status(400).body(new CommonResponse("Already in List"));
        }
        boolean result = carpoolService.joinCarpool(carpool, user);
        if(isDriver){
            carpool.setCarpoolDriver(user);
            carpoolRepository.save(carpool);
        }

        if(result) return ResponseEntity.status(200).body(new CommonResponse("Successfully Added"));
        return ResponseEntity.status(500).body(new CommonResponse("Server Error!"));
    }

    @DeleteMapping("/leave/{carpoolNo}")
    public ResponseEntity<?> leave(@PathVariable("carpoolNo")int carpoolNo, Authentication authentication){
        Carpool carpool = carpoolRepository.getCarpoolByCarpoolNo(carpoolNo);
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        User user = userDetails.getUser();
        if(carpool.getCarpoolWriter().getUserNo() == user.getUserNo() ) return ResponseEntity.status(400).body(new CommonResponse("You are carpool writer! Delete the carpool!"));
        if(carpool.getCarpoolDriver() != null &&carpool.getCarpoolDriver().getUserNo() == user.getUserNo() ) return ResponseEntity.status(406).body(new CommonResponse("You are carpool Driver! You cannot cancel!"));
        boolean status = carpoolService.leaveCarpool(carpool, user);
        int statusCode = status ? 200:400;
        String msg = status ? "Successfully Deleted!":"user not in the carpool.";
        return ResponseEntity.status(statusCode).body(new CommonResponse(msg));
    }

    @PostMapping("/{carpoolNo}/done")
    public ResponseEntity<?> done(@PathVariable("carpoolNo")int carpoolNo, Authentication authentication){
        Carpool carpool = carpoolRepository.getCarpoolByCarpoolNo(carpoolNo);
        User driver = carpool.getCarpoolDriver();
        if(carpool.getCarpoolDriver().getUserNo() != ((UserDetails)authentication.getDetails()).getUser().getUserNo()){
            return ResponseEntity.status(400).body(new CommonResponse("Only Driver can finish carpool"));
        }
        int mileage = carpoolService.carpoolDone(carpool);
        return ResponseEntity.status(200).body(new CarpoolDoneRes(driver.getUserId(), carpool.getCarpoolFee(), driver.getMileage(), "정상적으로 완료되었습니다."));
    }

    @GetMapping("/all")
    public ResponseEntity<?> all(){
        List<Carpool> list = carpoolService.getAllCarpools();
        List<CarpoolAllDetailRes> ret =new ArrayList<>();
        for (Carpool carpool: list) {
                    ret.add(new CarpoolAllDetailRes(carpool));
        }
        return ResponseEntity.status(200).body(new CarpoolsRes(ret,"Success!"));
    }

}

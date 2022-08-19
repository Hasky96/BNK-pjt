package com.carpool.bnk.CarpoolServer.domain.user.controller;

import com.carpool.bnk.CarpoolServer.domain.carpool.dto.CarpoolsDto;
import com.carpool.bnk.CarpoolServer.domain.carpool.service.CarpoolService;
import com.carpool.bnk.CarpoolServer.domain.user.db.entity.User;
import com.carpool.bnk.CarpoolServer.domain.user.db.repository.UserRepository;
import com.carpool.bnk.CarpoolServer.domain.user.request.UserLoginReq;
import com.carpool.bnk.CarpoolServer.domain.user.request.UserPwUpdateReq;
import com.carpool.bnk.CarpoolServer.domain.user.request.UserSignupReq;
import com.carpool.bnk.CarpoolServer.domain.user.request.UserUpdateReq;
import com.carpool.bnk.CarpoolServer.domain.user.response.UserInfoRes;
import com.carpool.bnk.CarpoolServer.domain.user.response.UserLoginRes;
import com.carpool.bnk.CarpoolServer.domain.user.response.UserSignupRes;
import com.carpool.bnk.CarpoolServer.domain.user.service.UserService;
import com.carpool.bnk.CarpoolServer.global.auth.UserDetails;
import com.carpool.bnk.CarpoolServer.global.util.CommonResponse;
import com.carpool.bnk.CarpoolServer.global.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    CarpoolService carpoolService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.status(200).body("Hi!");
    }

    @PostMapping("/signup")
    public ResponseEntity<UserSignupRes> signup(@RequestBody UserSignupReq body){
        User user = userService.signup(body);
        if(user==null){
            return ResponseEntity.status(500).body(new UserSignupRes(null, 0, "Server Error."));
        }
        return ResponseEntity.status(200).body(new UserSignupRes(user.getUserId(), user.getUserNo(), "Success!!"));
    }

    @GetMapping("/idcheck")
    public ResponseEntity<String> idcheck(@RequestParam String id){
        if(userService.idCheck(id)) return ResponseEntity.status(200).body("Available!");
        return ResponseEntity.status(400).body("Already existing id.");
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginRes> login(@RequestBody UserLoginReq body){
        User userInfo= userService.login(body);
        if(userInfo!=null){
            return ResponseEntity.status(200).body(new UserLoginRes().of(200,"Success!", JwtTokenUtil.getToken(userInfo.getUserId()),userInfo));
        }
        return ResponseEntity.status(400).body(new UserLoginRes().of(400,"Login failed", "", null));
    }

    @GetMapping("/info")
    public ResponseEntity<?> info(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        User user = userDetails.getUser();
        List<CarpoolsDto> carpools = carpoolService.getUserCarpools(user.getUserNo());
        UserInfoRes info = new UserInfoRes(
                user.getUserNo(),
                user.getUserId(),
                user.getUserCarInfo(),
                user.getUserCarNo(),
                user.getMileage(),
                carpools
        );
        info.setCarpoolCnt(carpoolService.getCarpoolCnt(user.getUserNo()));
        return ResponseEntity.status(200).body(info);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserUpdateReq body, Authentication authentication){
        try{
            UserDetails userDetails = (UserDetails) authentication.getDetails();
            User user = userRepository.getUserByUserNo(userDetails.getUser().getUserNo());
            user.setUserCarNo(body.getUserCarNo());
            user.setUserCarInfo(body.getUserCarInfo());
            userRepository.save(user);
        }catch (Exception e){
            System.err.println(e);
            return ResponseEntity.status(400).body(new CommonResponse("wrong info"));
        }
        return ResponseEntity.status(200).body(new CommonResponse("Successfully updated"));
    }

    @PutMapping("/pwupdate")
    public ResponseEntity<?> pwCheck(@RequestBody UserPwUpdateReq body, Authentication authentication){
        UserDetails user = (UserDetails) authentication.getDetails();
        int res = userService.pwUpdate(body, userRepository.getUserByUserNo(user.getUser().getUserNo()));
        int status = res==1?200:400;
        String msg = res==1?"Success!":"Wrong Password";
        return ResponseEntity.status(status).body(new CommonResponse(msg));
    }

}

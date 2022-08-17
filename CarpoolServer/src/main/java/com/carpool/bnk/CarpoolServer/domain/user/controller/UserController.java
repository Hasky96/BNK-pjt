package com.carpool.bnk.CarpoolServer.domain.user.controller;

import com.carpool.bnk.CarpoolServer.domain.user.db.entity.User;
import com.carpool.bnk.CarpoolServer.domain.user.request.UserLoginReq;
import com.carpool.bnk.CarpoolServer.domain.user.request.UserSignupReq;
import com.carpool.bnk.CarpoolServer.domain.user.response.UserLoginRes;
import com.carpool.bnk.CarpoolServer.domain.user.response.UserSignupRes;
import com.carpool.bnk.CarpoolServer.domain.user.service.UserService;
import com.carpool.bnk.CarpoolServer.global.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

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

//    @PostMapping("/login")
//    public ResponseEntity<UserLoginRes>
}

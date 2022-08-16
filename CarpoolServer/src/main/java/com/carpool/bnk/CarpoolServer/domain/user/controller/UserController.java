package com.carpool.bnk.CarpoolServer.domain.user.controller;

import com.carpool.bnk.CarpoolServer.domain.user.request.UserSignupReq;
import com.carpool.bnk.CarpoolServer.domain.user.response.UserSignupRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.status(200).body("Hi!");
    }

    @PostMapping("/signup")
    public ResponseEntity<UserSignupRes> signup(@RequestBody UserSignupReq body){



        return ResponseEntity.status(200).body(new UserSignupRes());
    }
}

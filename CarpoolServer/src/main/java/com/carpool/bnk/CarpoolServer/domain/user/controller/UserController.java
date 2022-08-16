package com.carpool.bnk.CarpoolServer.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carpool.bnk.CarpoolServer.domain.user.db.entity.Message;

@RestController
// @RequestMapping("/api/user")
public class UserController {
    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.status(200).body("Hi!");
    }
    
    @GetMapping("/connect")
    public Message connect() {
    	Message message=new Message();
    	message.setMessage("연결 성공");
    	System.out.println("연결 성공");
    	return message;
    }
}

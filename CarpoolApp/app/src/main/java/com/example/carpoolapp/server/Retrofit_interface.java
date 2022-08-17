package com.example.carpoolapp.server;

import com.example.carpoolapp.model.LoginRequest;
import com.example.carpoolapp.model.LoginResponse;
import com.example.carpoolapp.model.SignupRequest;
import com.example.carpoolapp.model.SignupResponse;

import retrofit2.Call;
import retrofit2.http.*;

public interface Retrofit_interface {

    @POST("/api/user/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("/api/user/signup")
    Call<SignupResponse> signup(@Body SignupRequest signupRequest);
    
}

package com.example.carpoolapp.server;

import androidx.lifecycle.LiveData;

import com.example.carpoolapp.model.Carpool;
import com.example.carpoolapp.model.CarpoolRequest;
import com.example.carpoolapp.model.CarpoolResponse;
import com.example.carpoolapp.model.CarpoolsResponse;
import com.example.carpoolapp.model.LoginRequest;
import com.example.carpoolapp.model.LoginResponse;
import com.example.carpoolapp.model.SignupRequest;
import com.example.carpoolapp.model.SignupResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface Retrofit_interface {

    //카풀 등록
    @POST("api/carpool/create")
    Call<CarpoolResponse> insertCarpool (@Header("Authorization") String Authorization,@Body CarpoolRequest carpool);

    @GET("api/carpool/all")
    Call<CarpoolsResponse> getAllCarpool();

    @POST("/api/user/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("/api/user/signup")
    Call<SignupResponse> signup(@Body SignupRequest signupRequest);

}

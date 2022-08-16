package com.example.carpoolapp.server;

import retrofit2.Call;
import retrofit2.http.*;

public interface Retrofit_interface {

    @GET("/api/user/login")
    Call<String> login(@Body String userId, @Body String userPw);

    
}

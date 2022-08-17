package com.example.carpoolapp.server;

import retrofit2.Call;
import retrofit2.http.*;

public interface Retrofit_interface {
    @FormUrlEncoded
    @POST("/api/user/login")
    Call<String> login(@Field("userId") String userId,
                       @Field("userPw") String userPw);

    @FormUrlEncoded
    @POST("/api/user/signup")
    Call<String> signup(@Field("userId") String userId,
                        @Field("userPw") String userPw,
                        @Field("userCarInfo") String userCarInfo,
                        @Field("userCarNo") String userCarNo);
    
}

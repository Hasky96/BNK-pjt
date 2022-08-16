package com.example.carpoolapp.server;

import com.example.carpoolapp.model.Message;

import retrofit2.Call;
import retrofit2.http.*;

public interface Retrofit_interface {
    @GET("connect")
    Call<Message> test_api_get();
}

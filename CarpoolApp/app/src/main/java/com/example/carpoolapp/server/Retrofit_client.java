package com.example.carpoolapp.server;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_client {
    private static final String BASE_URL = "http://192.168.0.121:8080/"; //IPv4 주소
//    private static final String BASE_URL = "http://192.168.0.6:8080/"; //IPv4 주소
//    private static final String BASE_URL = "http://192.168.0.122:8080/"; //IPv4 주소

    public static Retrofit_interface getApiService() {
        return getInstance().create(Retrofit_interface.class);
    }

    private static Retrofit getInstance() {
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

}

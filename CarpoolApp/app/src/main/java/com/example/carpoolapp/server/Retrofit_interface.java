package com.example.carpoolapp.server;

import com.example.carpoolapp.model.CarpoolRequest;
import com.example.carpoolapp.model.CarpoolResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface Retrofit_interface {

    //카풀 등록
    @POST("api/carpool/create")
    Call<CarpoolResponse> insertCarpool (@Body CarpoolRequest carpool);

}

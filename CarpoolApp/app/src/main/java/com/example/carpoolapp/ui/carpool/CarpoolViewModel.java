package com.example.carpoolapp.ui.carpool;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.carpoolapp.model.Carpool;
import com.example.carpoolapp.model.CarpoolResponse;
import com.example.carpoolapp.server.Retrofit_client;
import com.example.carpoolapp.server.Retrofit_interface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarpoolViewModel extends ViewModel {

    private Retrofit_interface carpoolService;
    public LiveData<List<Carpool>> carpoolList;

    public CarpoolViewModel() {
        carpoolService = Retrofit_client.getApiService();
        Call<LiveData<List<Carpool>>> callCarpool = carpoolService.getAllCarpool();
        callCarpool.enqueue(new Callback<LiveData<List<Carpool>>>() {
            @Override
            public void onResponse(Call<LiveData<List<Carpool>>> call, Response<LiveData<List<Carpool>>> response) {
                carpoolList = response.body();
            }

            @Override
            public void onFailure(Call<LiveData<List<Carpool>>> call, Throwable t) {

            }
        });

    }
    public LiveData<List<Carpool>>  getAllCarpoolSortedByDate(){
        Call<LiveData<List<Carpool>>> callCarpool = carpoolService.getAllCarpool();
        callCarpool.enqueue(new Callback<LiveData<List<Carpool>>>() {
            @Override
            public void onResponse(Call<LiveData<List<Carpool>>> call, Response<LiveData<List<Carpool>>> response) {
                carpoolList = response.body();
            }

            @Override
            public void onFailure(Call<LiveData<List<Carpool>>> call, Throwable t) {

            }
        });
        return carpoolList;
    }

}
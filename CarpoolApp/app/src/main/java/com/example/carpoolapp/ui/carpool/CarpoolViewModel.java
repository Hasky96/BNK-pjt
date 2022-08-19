package com.example.carpoolapp.ui.carpool;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.carpoolapp.model.CarpoolAllDetailRes;
import com.example.carpoolapp.model.CarpoolsResponse;
import com.example.carpoolapp.server.Retrofit_client;
import com.example.carpoolapp.server.Retrofit_interface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarpoolViewModel extends ViewModel {

	private Retrofit_interface carpoolService;
	public MutableLiveData<List<CarpoolAllDetailRes>> carpoolList;
	public LiveData<List<CarpoolAllDetailRes>> getCarpools() {
		if (carpoolList == null) {
			carpoolList = new MutableLiveData<>();
			loadCarpools();
		}
		return carpoolList;
	}
	public void loadCarpools() {
		carpoolService = Retrofit_client.getApiService();
		Call<CarpoolsResponse> callCarpool = carpoolService.getAllCarpool();
		callCarpool.enqueue(new Callback<CarpoolsResponse>() {
			@Override
			public void onResponse(Call<CarpoolsResponse> call, Response<CarpoolsResponse> response) {
				Log.d(">>>", "sucess " + response.body());
//				carpoolList.setValue(response.body().getCarpools());
			}

			@Override
			public void onFailure(Call<CarpoolsResponse> call, Throwable t) {

			}
		});

	}

}
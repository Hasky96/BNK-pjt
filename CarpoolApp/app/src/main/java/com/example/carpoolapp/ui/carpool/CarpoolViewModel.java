package com.example.carpoolapp.ui.carpool;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.carpoolapp.model.CarpoolAllDetailRes;
import com.example.carpoolapp.model.CarpoolDetailRes;
import com.example.carpoolapp.model.CarpoolsResponse;
import com.example.carpoolapp.server.Retrofit_client;
import com.example.carpoolapp.server.Retrofit_interface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarpoolViewModel extends AndroidViewModel {

	private Retrofit_interface carpoolService;
	public MutableLiveData<List<CarpoolAllDetailRes>> carpoolList;
	private SharedPreferences preferences;
	private String Authorization;
	public MutableLiveData<CarpoolDetailRes> carpoolDetail;

	public CarpoolViewModel(@NonNull Application application) {
		super(application);
		preferences = getApplication().getSharedPreferences("User", Context.MODE_PRIVATE);
		Authorization = preferences.getString("Authorization",null);
	}

	public LiveData<List<CarpoolAllDetailRes>> getCarpools() {
		if (carpoolList == null) {
			carpoolList = new MutableLiveData<>();
			loadCarpools();
		}

		return carpoolList;
	}

	public LiveData<CarpoolDetailRes> getCarpoolDetail(int carpoolNo) {
		if (carpoolDetail == null) {
			carpoolDetail = new MutableLiveData<>();
			loadCarpoolDetail(carpoolNo);
		}

		return carpoolDetail;
	}

	public void loadCarpools() {

		carpoolService = Retrofit_client.getApiService();
		Call<CarpoolsResponse> callCarpool = carpoolService.getAllCarpool(Authorization);
		callCarpool.enqueue(new Callback<CarpoolsResponse>() {
			@Override
			public void onResponse(Call<CarpoolsResponse> call, Response<CarpoolsResponse> response) {
				Log.d(">>>", "sucess load carpool " + response.body());
				carpoolList.setValue(response.body().getCarpools());
			}

			@Override
			public void onFailure(Call<CarpoolsResponse> call, Throwable t) {
				Log.d(">>>", "fail " + t.getMessage() );
			}
		});
	}


	public void loadCarpoolDetail(int carpoolNo){
		carpoolService = Retrofit_client.getApiService();
		Call<CarpoolDetailRes> callCarpool = carpoolService.getCarpoolDetail(Authorization, carpoolNo);
		callCarpool.enqueue(new Callback<CarpoolDetailRes>() {
			@Override
			public void onResponse(Call<CarpoolDetailRes> call, Response<CarpoolDetailRes> response) {
				Log.d(">>","carpool detaili success");
				carpoolDetail.setValue(response.body());
			}

			@Override
			public void onFailure(Call<CarpoolDetailRes> call, Throwable t) {
				Log.d(">>","carpool detaili fail");
			}
		});
	}

}
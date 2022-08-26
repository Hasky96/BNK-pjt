package com.example.carpoolapp.ui.carpool;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.carpoolapp.model.CarpoolJoinReq;
import com.example.carpoolapp.model.CommonResponse;
import com.example.carpoolapp.model.CarpoolAllDetailRes;
import com.example.carpoolapp.model.CarpoolDetailRes;
import com.example.carpoolapp.model.CarpoolsResponse;
import com.example.carpoolapp.server.Retrofit_client;
import com.example.carpoolapp.server.Retrofit_interface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CarpoolViewModel extends AndroidViewModel {

	private Retrofit_interface carpoolService;
	public MutableLiveData<List<CarpoolAllDetailRes>> carpoolList;
	private SharedPreferences preferences;
	private String Authorization;
	public MutableLiveData<CarpoolDetailRes> carpoolDetail;
	public MutableLiveData<String> msg;

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

	public LiveData<String> getMsg(){
		if (msg == null) {
			msg = new MutableLiveData<>();
		}

		return msg;
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
				Log.d(">>>", "fail load carpoolss " + t.getMessage() );
			}
		});
	}


	public void loadCarpoolDetail(int carpoolNo){
		carpoolService = Retrofit_client.getApiService();
		Call<CarpoolDetailRes> callCarpool = carpoolService.getCarpoolDetail(Authorization, carpoolNo);
		callCarpool.enqueue(new Callback<CarpoolDetailRes>() {
			@Override
			public void onResponse(Call<CarpoolDetailRes> call, Response<CarpoolDetailRes> response) {
				Log.d(">>","carpool detail success");
				carpoolDetail.setValue(response.body());
			}

			@Override
			public void onFailure(Call<CarpoolDetailRes> call, Throwable t) {
				Log.d(">>","carpool detail fail" + t.getMessage());
			}
		});
	}

	public void joinCarpool(int carpoolNo, boolean isDriverExist){
		CarpoolJoinReq joinReq = new CarpoolJoinReq(isDriverExist);
		Log.d(">>", "isDriverExist "+ isDriverExist );
		carpoolService = Retrofit_client.getApiService();
		Call<CommonResponse> joinCarpool = carpoolService.joinCarpool(Authorization, carpoolNo, joinReq);
		joinCarpool.enqueue(new Callback<CommonResponse>() {
			@Override
			public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

				Log.d(">>", "joinReq.isDriverCheck "+joinReq.isDriverCheck());
				Log.d(">>","carpool join success " +response.code());
				if(response.code() == 200){
					msg.setValue("카풀에 참여되었습니다");
				}else if( response.code() == 500) {
					msg.setValue("인원이 찼습니다");
				}else{
					msg.setValue("이미 참여중입니다");
				}
				loadCarpoolDetail(carpoolNo);
			}

			@Override
			public void onFailure(Call<CommonResponse> call, Throwable t) {
				Log.d(">>","carpool join fail " + t.getMessage());
				msg.setValue("not found");

			}
		});
	}

	public void cancelCarpool(int carpoolNo){
		carpoolService = Retrofit_client.getApiService();
		Call<CommonResponse> cancelCarpool = carpoolService.cancelCarpool(Authorization, carpoolNo);
		cancelCarpool.enqueue(new Callback<CommonResponse>() {
			@Override
			public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
				if(response.code() == 200){
					Log.d(">>", "carpool leave code " + response.code() + "/" +  response.body().getMsg());
					msg.postValue("카풀 참여를 취소하였습니다");
				}else {
						Gson gson = new GsonBuilder().create();
						Type type = new TypeToken<CommonResponse>() {}.getType();
						CommonResponse errorResponse = gson.fromJson(response.errorBody().charStream(),type);

						if( response.code() == 400){
							if(errorResponse.getMsg().equals("You are carpool writer! Delete the carpool!")){
								msg.postValue("카풀 생성자 입니다. 취소할 수 없습니다");
							}else if( errorResponse.getMsg().equals("user not in the carpool.") ) {
								msg.postValue("참여하지 않았습니다. 취소할 수 없습니다");
							}
						}
						else if( response.code() == 406 ){
							msg.postValue("카풀 운전자입니다. 취소할 수 없습니다");
						}
					}

				loadCarpoolDetail(carpoolNo);
			}

			@Override
			public void onFailure(Call<CommonResponse> call, Throwable t) {

			}
		});
	}

}
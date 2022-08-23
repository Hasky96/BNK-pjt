package com.example.carpoolapp.server;

import com.example.carpoolapp.model.CommonResponse;
import com.example.carpoolapp.model.CarpoolDetailRes;
import com.example.carpoolapp.model.CarpoolMapRequest;
import com.example.carpoolapp.model.CarpoolMapResponse;
import com.example.carpoolapp.model.CarpoolRequest;
import com.example.carpoolapp.model.CarpoolResponse;
import com.example.carpoolapp.model.CarpoolsResponse;
import com.example.carpoolapp.model.LoginRequest;
import com.example.carpoolapp.model.LoginResponse;
import com.example.carpoolapp.model.SignupRequest;
import com.example.carpoolapp.model.SignupResponse;
import com.example.carpoolapp.model.UserInfoResponse;
import com.example.carpoolapp.model.UserUpdatePwRequest;
import com.example.carpoolapp.model.UserUpdatePwResponse;
import com.example.carpoolapp.model.UserUpdateRequest;
import com.example.carpoolapp.model.UserUpdateResponse;

import retrofit2.Call;
import retrofit2.http.*;

public interface Retrofit_interface {

	//카풀 등록
	@POST("api/carpool/create")
	Call<CarpoolResponse> insertCarpool(@Header("Authorization") String Authorization, @Body CarpoolRequest carpool);

	@GET("api/carpool/all")
	Call<CarpoolsResponse> getAllCarpool(@Header("Authorization") String Authorization);

	@GET("api/carpool/{carpoolNo}")
	Call<CarpoolDetailRes> getCarpoolDetail(@Header("Authorization") String Authorization, @Path("carpoolNo") int carpoolNo);

	@POST("api/carpool/join/{carpoolNo}")
	Call<CommonResponse> joinCarpool(@Header("Authorization") String Authorization, @Path("carpoolNo") int carpoolNo);

	@DELETE("api/carpool/leave/{carpoolNo}")
	Call<CommonResponse> cancelCarpool(@Header("Authorization") String Authorization, @Path("carpoolNo") int carpoolNo);

	@PUT("api/carpool/{carpoolNo}")
	Call<CommonResponse> updateCarpool(@Header("Authorization") String Authorization, @Path("carpoolNo") int carpoolNo, @Body CarpoolRequest detailRes);

	@POST("/api/user/login")
	Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("/api/user/info")
    Call<UserInfoResponse> userInfo(@Header("Authorization") String Authorization);

    @POST("/api/map/route")
    Call<CarpoolMapResponse> mapList(@Header("Authorization") String Authorization, @Body CarpoolMapRequest carpoolMapRequest);

	@POST("/api/user/signup")
	Call<SignupResponse> signup(@Body SignupRequest signupRequest);

	@PUT("/api/user/update")
	Call<UserUpdateResponse> updateUser(@Header("Authorization") String Authorization, @Body UserUpdateRequest userUpdateRequest);

	@PUT("/api/user/pwupdate")
	Call<UserUpdatePwResponse> updatePwUser(@Header("Authorization") String Authorization, @Body UserUpdatePwRequest userUpdatePwRequest);


}

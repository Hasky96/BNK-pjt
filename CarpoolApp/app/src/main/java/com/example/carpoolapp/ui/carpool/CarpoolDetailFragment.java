package com.example.carpoolapp.ui.carpool;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.carpoolapp.R;
import com.example.carpoolapp.databinding.FragmentCarpoolDetailBinding;
import com.example.carpoolapp.model.CarpoolDetailRes;
import com.example.carpoolapp.model.CarpoolMapRequest;
import com.example.carpoolapp.model.CarpoolMapResponse;
import com.example.carpoolapp.model.CommonResponse;
import com.example.carpoolapp.server.Retrofit_client;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.example.carpoolapp.util.CarpoolUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarpoolDetailFragment extends Fragment implements OnMapReadyCallback {

	CarpoolViewModel carpoolViewModel;
	FragmentCarpoolDetailBinding binding;
	int carpoolNo;
	private MapView mapView;
	private Call<CarpoolMapResponse> call;
	private SharedPreferences preferences;
	private String Authorization;
	private String location;
	private String pathList = null;

	CarpoolDetailRes cdetail;
	String cmsg;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		binding = FragmentCarpoolDetailBinding.inflate(inflater, container, false);
		View root = binding.getRoot();
		mapView = (MapView) binding.mapFragment;
		mapView.onCreate(savedInstanceState);
		mapView.onResume();
		mapView.getMapAsync(this);

		binding.tvDetailLoc.setSelected(true);
		binding.btnCarpoolJoin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

				if( preferences.getString("userCarNo",null) == null ) {
					builder.setTitle("참여하시겠습니까?");
					builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							// 차 없으면 그냥 참여
							carpoolViewModel.joinCarpool(carpoolNo, false);
						}
					});
					builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
						}
					});
				}else{
					builder.setTitle("운전자로 참여하시겠습니까?");
					builder.setPositiveButton("운전자로 참여", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							// 운전자로 참여
							carpoolViewModel.joinCarpool(carpoolNo, true);
						}
					});
					builder.setNegativeButton("일반참여", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							// 운전자지만 일반 참여
							carpoolViewModel.joinCarpool(carpoolNo, false);
						}
					});
					builder.setNeutralButton("취소", new DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							// 참여 취소
						}
					});
				}
				AlertDialog alertDialog = builder.create();
				alertDialog.show();

			}
		});

		binding.btnCarpoolCancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				carpoolViewModel.cancelCarpool(carpoolNo);
			}
		});

		binding.btnCarpoolUpdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), CarpoolRegisterActivity.class);
				intent.putExtra("cdetail", cdetail);
				intent.putExtra("from", "detail");
				startActivity(intent);

			}
		});

		binding.btnCarpoolDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Call<CommonResponse> deletecall = Retrofit_client.getApiService().deleteCarpool(Authorization, carpoolNo);
				deletecall.enqueue(new Callback<CommonResponse>() {
					@Override
					public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
						Toast.makeText(getActivity().getApplicationContext(), "삭제하였습니다.", Toast.LENGTH_SHORT).show();
						Navigation.findNavController(view).navigate(R.id.action_carpoolDetailFragment2_to_navigation_carpool);
					}

					@Override
					public void onFailure(Call<CommonResponse> call, Throwable t) {

					}
				});
			}
		});

		return root;
	}


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		preferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
		Authorization = preferences.getString("Authorization", null);

		carpoolNo = getArguments().getInt("carpoolNo");
		carpoolViewModel = new ViewModelProvider(this).get(CarpoolViewModel.class);

		carpoolViewModel.getCarpoolDetail(carpoolNo).observe(this, carpoolDetail -> {
			cdetail = carpoolDetail;
			binding.tvDetailLoc.setText(carpoolDetail.getLocation());
			location = carpoolDetail.getLocation();
			binding.ttvDetailPerson.setText(carpoolDetail.getOccupants().split(",").length + "/" + carpoolDetail.getQuota());
			binding.tvDetailCarInfo.setText(carpoolDetail.getInfo());
			binding.tvDetailTime.setText(carpoolDetail.getTime().split("T")[1].substring(0, 5));
			binding.tvDetailDate.setText(carpoolDetail.getTime().split("T")[0]);
			binding.tvDetailWriter.setText(carpoolDetail.getWriterId());

			CarpoolMapRequest carpoolMapRequest = new CarpoolMapRequest(location);
			//폴리라인
			call = Retrofit_client.getApiService().mapList(Authorization, carpoolMapRequest);
			call.enqueue(new Callback<CarpoolMapResponse>() {
				@Override
				public void onResponse(Call<CarpoolMapResponse> call, Response<CarpoolMapResponse> response) {
					if (response.body() != null) {
						String path = response.body().getPath();
						pathList = path.substring(2, path.length() - 2);
					}

				}
				@Override
				public void onFailure(Call<CarpoolMapResponse> call, Throwable t) {
				}
			});

			if (CarpoolUtil.isUserInCarpool(cdetail, preferences.getString("userId", null))) {
				binding.btnCarpoolJoin.setVisibility(View.INVISIBLE);
				binding.btnCarpoolCancle.setVisibility(View.VISIBLE);
			} else {
				binding.btnCarpoolJoin.setVisibility(View.VISIBLE);
				binding.btnCarpoolCancle.setVisibility(View.INVISIBLE);
			}

			if (preferences.getInt("userNo", 1) == cdetail.getWriterNo()) {
				binding.btnCarpoolUpdate.setVisibility(View.VISIBLE);
				binding.btnCarpoolDelete.setVisibility(View.VISIBLE);
				binding.btnCarpoolJoin.setVisibility(View.INVISIBLE);
				binding.btnCarpoolCancle.setVisibility(View.INVISIBLE);
			}
		});


		carpoolViewModel.getMsg().observe(this, msg -> {
			cmsg = msg;
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle(cmsg);
			builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
				}
			});
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
		});

	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d(">>", "carpool detail fg start");
		carpoolViewModel.loadCarpoolDetail(carpoolNo);
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(">>", "carpool detail fg resume");
	}

	@Override
	public void onMapReady(@NonNull GoogleMap googleMap) {

		CameraPosition cameraOption = CameraPosition.builder()
				.target(new LatLng(35.15995278, 129.0553194))
				.zoom(10.4f)
				.build();
		CameraUpdate defaultCamera = CameraUpdateFactory.newCameraPosition(cameraOption);
		googleMap.moveCamera(defaultCamera);

			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					if (pathList != null) {
						LatLngBounds.Builder bounds = LatLngBounds.builder();
						String[] list = pathList.split("\\],\\[");
						List<LatLng> gpsList = new ArrayList<>();
						for (String gps : list) {
							String[] latlon = gps.split(",");
							LatLng latLng = new LatLng(Double.valueOf(latlon[1]), Double.valueOf(latlon[0]));
							gpsList.add(latLng);
							bounds.include(latLng);
						}
						Polyline polyline = googleMap.addPolyline(new PolylineOptions().color(Color.parseColor("#D7191F"))
								.clickable(true).addAll(gpsList));
						//카메라이동
						int all = gpsList.size();
						int half = all / 2;

						googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100));
						//출발,도착 마커
						BitmapDrawable bitmapStart = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_start);
						BitmapDrawable bitmapEnd = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_end);

						Bitmap b = bitmapStart.getBitmap();
						Bitmap smallStartMarker = Bitmap.createScaledBitmap(b, 140, 210, false);

						Bitmap b2 = bitmapEnd.getBitmap();
						Bitmap smallEndMarker = Bitmap.createScaledBitmap(b2, 120, 210, false);

						googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallStartMarker))
								.position(gpsList.get(0)).title("출발"));
						googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallEndMarker))
								.position(gpsList.get(all - 1)).title("도착"));
					}
					}
			}, 1000);



	}


}
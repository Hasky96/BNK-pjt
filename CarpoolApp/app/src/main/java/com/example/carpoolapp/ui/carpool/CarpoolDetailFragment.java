package com.example.carpoolapp.ui.carpool;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.widget.Button;
import android.widget.EditText;
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

import java.time.LocalDateTime;

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
	ActivityResultLauncher<Intent> intentActivityResultLauncher;

	CarpoolDetailRes cdetail;
	String cmsg;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		Log.d(">>", "detail onCreateView");



		binding = FragmentCarpoolDetailBinding.inflate(inflater, container, false);
		View root = binding.getRoot();
		mapView = (MapView) binding.mapFragment;
		mapView.onCreate(savedInstanceState);
		mapView.onResume();
		mapView.getMapAsync(this);
		;

		binding.tvDetailLoc.setSelected(true);
		binding.btnCarpoolJoin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

				// ????????? ???????????? ????????? ????????? ????????????
				if (cdetail.getDriverNo() == 0) {
					//  ?????? ?????? ?????????
					if (preferences.getString("userCarNo", null).equals("")) {
						builder.setTitle("?????? ????????? ??? ????????????");
						builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								// ????????? ???????????? ?????? ??? ,
								//  ?????? ?????? ???????????? ????????? ??? ??????.
							}
						});
					}else{
						builder.setTitle("?????????????????????.");
						builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								carpoolViewModel.joinCarpool(carpoolNo, false);
							}
						});
					}
				} else {
					builder.setTitle("?????????????????????????");
					builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							carpoolViewModel.joinCarpool(carpoolNo, false);
						}
					});
					builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
						}
					});
				}

				AlertDialog alertDialog = builder.create();
				alertDialog.show();

			}
		});

		binding.btnCarpoolDriver.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				// ????????? ???????????? ????????? ????????? ????????????
				Log.d(">>","detail driverNo "+cdetail.getDriverNo());
				Log.d(">>","detail usercarNo "+ preferences.getString("userCarNo", "")+" /"+preferences.getString("userCarNo", "").equals(""));
				if (cdetail.getCarNo() == null || cdetail.getCarNo().equals("")) {
					// ????????? ???????????? ??????, ???????????? ???????????? ????????? ????????? ????????? ??????????????? ????????????
					builder.setTitle("???????????? ?????????????????????????");
					builder.setPositiveButton("???????????? ??????", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							// ???????????? ??????
							carpoolViewModel.joinCarpool(carpoolNo, true);
						}
					});
					builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
						}
					});

				}else{
					builder.setTitle("?????? ???????????? ????????????");
					builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
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
				// ?????? ??????????????? ?????? ?????? ?????????
				carpoolViewModel.cancelCarpool(carpoolNo);

			}
		});

		binding.btnCarpoolUpdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), CarpoolRegisterActivity.class);
				intent.putExtra("cdetail", cdetail);
				intent.putExtra("from", "detail");
				intentActivityResultLauncher.launch(intent);

			}
		});

		binding.btnCarpoolDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Call<CommonResponse> deletecall = Retrofit_client.getApiService().deleteCarpool(Authorization, carpoolNo);
				deletecall.enqueue(new Callback<CommonResponse>() {
					@Override
					public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
						Toast.makeText(getActivity().getApplicationContext(), "?????????????????????.", Toast.LENGTH_SHORT).show();
						Navigation.findNavController(view).navigate(R.id.action_carpoolDetailFragment2_to_navigation_carpool);
					}

					@Override
					public void onFailure(Call<CommonResponse> call, Throwable t) {

					}
				});
			}
		});

		binding.btnCarpoolComplete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Call<CommonResponse> doneCall = Retrofit_client.getApiService().doneCarpool(Authorization,carpoolNo);
				doneCall.enqueue(new Callback<CommonResponse>() {
					@Override
					public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
						builder.setTitle("?????? ?????????????????????");
						builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
							}
						});
						AlertDialog alertDialog = builder.create();
						alertDialog.show();
					}

					@Override
					public void onFailure(Call<CommonResponse> call, Throwable t) {
						Log.d(">>", "done carpool fail");
					}
				});
			}
		});


		return root;
	}


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(">>", "detail onCreate");
		preferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
		Authorization = preferences.getString("Authorization", null);

		carpoolNo = getArguments().getInt("carpoolNo");
		carpoolViewModel = new ViewModelProvider(this).get(CarpoolViewModel.class);

		intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
			@Override
			public void onActivityResult(ActivityResult result) {
				if (result.getResultCode() == 807) {
					carpoolViewModel.loadCarpoolDetail(carpoolNo);
				}

			}
		});

		carpoolViewModel.loadCarpoolDetail(carpoolNo);

		carpoolViewModel.getCarpoolDetail(carpoolNo).observe(this, carpoolDetail -> {

			cdetail = carpoolDetail;
			binding.tvDetailLoc.setText(carpoolDetail.getLocation());
			location = carpoolDetail.getLocation();
			binding.tvDetailPerson.setText(carpoolDetail.getOccupants().split(",").length + "/" + carpoolDetail.getQuota());
			binding.tvDetailCarInfo.setText(carpoolDetail.getInfo());
			binding.tvDetailTime.setText(carpoolDetail.getTime().split("T")[1].substring(0, 5));
			binding.tvDetailDate.setText(carpoolDetail.getTime().split("T")[0]);
			binding.tvDetailWriter.setText(carpoolDetail.getWriterId());

			CarpoolMapRequest carpoolMapRequest = new CarpoolMapRequest(location);
			//????????????
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


			// ????????? ?????? ??????
			if( cdetail.getCarNo() == null || cdetail.getCarNo().equals("")){
//				if(!preferences.getString("userCarNo","").equals("")
//				|| !preferences.getString("userCarInfo","").equals("")){
					binding.tvDetailDriver.setText("??????");
//					binding.btnCarpoolDriver.setVisibility(View.INVISIBLE);
//				}
//				else binding.btnCarpoolDriver.setVisibility(View.VISIBLE);
			}else{
				binding.tvDetailDriver.setText(carpoolDetail.getCarNo());
//				binding.btnCarpoolDriver.setVisibility(View.INVISIBLE);
			}

			// ?????? ??????
			if (cdetail.getDriverNo() == 0  && !(preferences.getString("userCarNo", "").equals("")) ) {
				binding.btnCarpoolDriver.setVisibility(View.VISIBLE);
			}


			 // Comments
             Bundle bundle = new Bundle();
			 bundle.putInt("carpoolNo",carpoolNo);
			 bundle.putInt("carpoolWriterNo",carpoolDetail.getWriterNo());

			 CarpoolCommentsFragment carpoolCommentsFragement = new CarpoolCommentsFragment();
			 carpoolCommentsFragement.setArguments(bundle);
			 requireActivity().getSupportFragmentManager().beginTransaction()
					 .replace(R.id.commentFragment, carpoolCommentsFragement).commit();

			 EditText eTComment=getActivity().findViewById(R.id.eTComment);
			 Button commentRegisterButton=getActivity().findViewById(R.id.commentRegisterButton);

			 // ??????, ?????? ??????
			if (CarpoolUtil.isUserInCarpool(cdetail, preferences.getString("userId", null))) {
				binding.btnCarpoolJoin.setVisibility(View.INVISIBLE);
				binding.btnCarpoolCancle.setVisibility(View.VISIBLE);
			} else {
				binding.btnCarpoolJoin.setVisibility(View.VISIBLE);
				binding.btnCarpoolCancle.setVisibility(View.INVISIBLE);
			}

			// ??????, ?????? ??????
			if (preferences.getInt("userNo", 1) == cdetail.getWriterNo()) {
				binding.btnCarpoolUpdate.setVisibility(View.VISIBLE);
				binding.btnCarpoolDelete.setVisibility(View.VISIBLE);
				binding.btnCarpoolJoin.setVisibility(View.INVISIBLE);
				binding.btnCarpoolCancle.setVisibility(View.INVISIBLE);
			}



			LocalDateTime carpoolTime = LocalDateTime.parse(cdetail.getTime());

			if( LocalDateTime.now().isAfter(carpoolTime)) {
//				binding.btnCarpoolWaiting.setVisibility(View.INVISIBLE);
				binding.btnCarpoolComplete.setVisibility(View.VISIBLE);
				binding.btnCarpoolCancle.setVisibility(View.INVISIBLE);
				binding.tvDetailDriver.setVisibility(View.INVISIBLE);
			}else{
				binding.btnCarpoolWaiting.setVisibility(View.VISIBLE);
			}

		});


		carpoolViewModel.getMsg().observe(this, msg -> {
			cmsg = msg;
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle(cmsg);
			builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
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

	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(">>", "carpool detail fg resume");
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(">>", "detail onSaveInstanceState");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.d(">>", "detail onStop");
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
					//???????????????
					int all = gpsList.size();
					int half = all / 2;

					googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100));
					//??????,?????? ??????
					BitmapDrawable bitmapStart = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_start);
					BitmapDrawable bitmapEnd = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_end);

					Bitmap b = bitmapStart.getBitmap();
					Bitmap smallStartMarker = Bitmap.createScaledBitmap(b, 140, 210, false);

					Bitmap b2 = bitmapEnd.getBitmap();
					Bitmap smallEndMarker = Bitmap.createScaledBitmap(b2, 120, 210, false);

					if (cdetail.isType()){
						googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallEndMarker))
								.position(gpsList.get(0)).title("??????"));
						googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallStartMarker))
								.position(gpsList.get(all - 1)).title("??????"));
					}else {
						googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallStartMarker))
								.position(gpsList.get(0)).title("??????"));
						googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallEndMarker))
								.position(gpsList.get(all - 1)).title("??????"));
					}
				}
			}
		}, 1000);


	}

}
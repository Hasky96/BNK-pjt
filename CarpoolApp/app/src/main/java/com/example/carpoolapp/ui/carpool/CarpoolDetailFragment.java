package com.example.carpoolapp.ui.carpool;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import com.example.carpoolapp.server.Retrofit_client;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.example.carpoolapp.util.CarpoolUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarpoolDetailFragment extends Fragment implements OnMapReadyCallback{

    CarpoolViewModel carpoolViewModel;
    FragmentCarpoolDetailBinding binding;
    int carpoolNo;
    CarpoolDetailRes carpoolDetail;
    private MapView mapView;
    private Call<CarpoolMapResponse> call;
    private SharedPreferences preferences;
    private String Authorization;

    CarpoolDetailRes cdetail;
    String cmsg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCarpoolDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mapView=(MapView) binding.mapFragment;
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        binding.tvDetailLoc.setSelected(true);
        binding.btnCarpoolJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carpoolViewModel.joinCarpool(carpoolNo);
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
                intent.putExtra("from","detail");
                startActivity(intent);

            }
        });


        return root;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences= getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        Authorization=preferences.getString("Authorization",null);

        carpoolNo = getArguments().getInt("carpoolNo");
        carpoolViewModel = new ViewModelProvider(this).get(CarpoolViewModel.class);

        carpoolViewModel.getCarpoolDetail(carpoolNo).observe(this, carpoolDetail -> {
            cdetail = carpoolDetail;
            binding.tvDetailLoc.setText(carpoolDetail.getLocation());
            binding.ttvDetailPerson.setText(carpoolDetail.getOccupants().split(",").length +"/" + carpoolDetail.getQuota());
            binding.tvDetailCarInfo.setText(carpoolDetail.getInfo());
            binding.tvDetailTime.setText(carpoolDetail.getTime().split("T")[1].substring(0,5));
            binding.tvDetailDate.setText(carpoolDetail.getTime().split("T")[0]);
            binding.tvDetailWriter.setText(carpoolDetail.getWriterId());

            if( CarpoolUtil.isUserInCarpool(cdetail,preferences.getString("userId",null)) ){
                binding.btnCarpoolJoin.setVisibility(View.INVISIBLE);
                binding.btnCarpoolCancle.setVisibility(View.VISIBLE);
            }else{
                binding.btnCarpoolJoin.setVisibility(View.VISIBLE);
                binding.btnCarpoolCancle.setVisibility(View.INVISIBLE);
            }

            if( preferences.getInt("userNo",1) == cdetail.getWriterNo()){
                binding.btnCarpoolUpdate.setVisibility(View.VISIBLE);
                binding.btnCarpoolDelete.setVisibility(View.VISIBLE);
            }
        });

        carpoolViewModel.getMsg().observe(this, msg ->{
            cmsg = msg;
            AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
            builder.setTitle(cmsg);
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {}
            });
            AlertDialog alertDialog=builder.create();
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

        String query="부산광역시 사하구 하단동";
        CarpoolMapRequest carpoolMapRequest=new CarpoolMapRequest(query);
        //폴리라인
        call= Retrofit_client.getApiService().mapList(Authorization,carpoolMapRequest);
        call.enqueue(new Callback<CarpoolMapResponse>() {
            @Override
            public void onResponse(Call<CarpoolMapResponse> call, Response<CarpoolMapResponse> response) {
                String path=response.body().getPath();
                String pathList=path.substring(2,path.length()-2);
                String[] list=pathList.split("\\],\\[");
                List<LatLng> gpsList=new ArrayList<>();
                for(String gps:list){
                    String[] latlon=gps.split(",");
                    gpsList.add(new LatLng(Double.valueOf(latlon[1]),Double.valueOf(latlon[0])));
                }
                Polyline polyline=googleMap.addPolyline(new PolylineOptions()
                        .clickable(true).addAll(gpsList));
                //카메라이동
                int all=gpsList.size();
                int half=all/2;
                CameraPosition cameraOption=CameraPosition.builder()
                        .target(gpsList.get(half))
                        .zoom(10.4f)
                        .build();
                CameraUpdate camera=CameraUpdateFactory.newCameraPosition(cameraOption);
                googleMap.moveCamera(camera);

                //출발,도착 마커
                BitmapDrawable bitmapStart=(BitmapDrawable)getResources().getDrawable(R.drawable.marker_start);
                BitmapDrawable bitmapEnd=(BitmapDrawable)getResources().getDrawable(R.drawable.marker_end);

                Bitmap b=bitmapStart.getBitmap();
                Bitmap smallStartMarker = Bitmap.createScaledBitmap(b, 140, 210, false);

                Bitmap b2=bitmapEnd.getBitmap();
                Bitmap smallEndMarker = Bitmap.createScaledBitmap(b2, 120, 210, false);

                googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallStartMarker))
                        .position(gpsList.get(0)).title("출발"));
                googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallEndMarker))
                        .position(gpsList.get(all-1)).title("도착"));
            }

            @Override
            public void onFailure(Call<CarpoolMapResponse> call, Throwable t) {
                Log.d("jjk","안됨");
            }
        });

    }
}
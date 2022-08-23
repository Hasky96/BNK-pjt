package com.example.carpoolapp.ui.myReservation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.carpoolapp.R;
import com.example.carpoolapp.databinding.FragmentMyreservationBinding;
import com.example.carpoolapp.model.CarpoolAllDetailRes;
import com.example.carpoolapp.model.CarpoolsResponse;
import com.example.carpoolapp.server.Retrofit_client;
import com.example.carpoolapp.ui.carpool.CarpoolViewModel;
import com.example.carpoolapp.ui.splash.SplashActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class myReservationFragment extends Fragment {

    private SharedPreferences preferences;
    private CarpoolViewModel carpoolViewModel;
    private String userId;

    private FragmentMyreservationBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("jjk","0");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myReservationViewModel homeViewModel =
                new ViewModelProvider(this).get(myReservationViewModel.class);

        binding = FragmentMyreservationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.constraintLayoutGo.setVisibility(View.INVISIBLE);
        binding.constraintLayoutOut.setVisibility(View.INVISIBLE);
        binding.tvNoReserve.setVisibility(View.VISIBLE);

        preferences= getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        userId=preferences.getString("userId",null);

        carpoolViewModel = new ViewModelProvider(this).get(CarpoolViewModel.class);
        carpoolViewModel.getCarpools().observe(getViewLifecycleOwner(),carpoolList ->{

            long now=System.currentTimeMillis();
            Date date=new Date(now);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String carpoolToday=sdf.format(date);
            binding.tvReserveDate.setText(carpoolToday);

            List<CarpoolAllDetailRes> list = new ArrayList<>();
            for (CarpoolAllDetailRes temp: carpoolList) {
                if(!temp.isType()) list.add(temp);
            }

            for (CarpoolAllDetailRes carpool:carpoolList){
                String carpoolTime=carpool.getTime(); //카풀시간
                String[] datetime=carpoolTime.split("T");
                String today=datetime[0];
                String time=datetime[1].substring(0,5);

                if(today!=null&carpoolToday!=null&&today.equals(carpoolToday)){
                    String occupantList=carpool.getOccupants().substring(1,carpool.getOccupants().length()-1);
                    String[] occupants=occupantList.split(",");

                    for(String occupant:occupants){
                        if(occupant.equals(userId)){
                            //퇴근
                            if (carpool.isType()){
                                binding.constraintLayoutOut.setVisibility(View.VISIBLE);
                                binding.tvNoReserve.setVisibility(View.INVISIBLE);
                                binding.tvReserveOutTime.setText(time);
                                binding.tvReserveOutDestinationLoc.setText(carpool.getLocation());
                                binding.tvReserveOutAll.setText(""+carpool.getQuota());
                                binding.tvReserveOutCount.setText(""+occupants.length);
                                binding.tvReserveOutButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("carpoolNo", carpool.getCarpoolNo());
                                        Navigation.findNavController(view).navigate(R.id.action_navigation_myReservation_to_carpoolDetailFragment2, bundle);
                                    }
                                });
                            }
                            //출근
                            else {
                                binding.constraintLayoutGo.setVisibility(View.VISIBLE);
                                binding.tvNoReserve.setVisibility(View.INVISIBLE);
                                binding.tvReserveGoTime.setText(time);
                                binding.tvReserveGoDepartLoc.setText(carpool.getLocation());
                                binding.tvReserveGoAll.setText(""+carpool.getQuota());
                                binding.tvReserveGoCount.setText(""+occupants.length);
                                binding.tvReserveGoButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                });
                            }
                        }
                    }

                }
            }

        } );
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        carpoolViewModel.loadCarpools();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
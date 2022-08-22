package com.example.carpoolapp.ui.myPage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.carpoolapp.MainActivity;
import com.example.carpoolapp.R;
import com.example.carpoolapp.databinding.FragmentMypageBinding;
import com.example.carpoolapp.model.UserInfoResponse;
import com.example.carpoolapp.server.Retrofit_client;
import com.example.carpoolapp.ui.splash.SplashActivity;
import com.example.carpoolapp.ui.user.LoginFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MypageFragment extends Fragment {

    private FragmentMypageBinding binding;
    private SharedPreferences preferences;
    private Call<UserInfoResponse> call;
    private String Authorization;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMypageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        preferences= getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        binding.tvProfileId.setText(preferences.getString("userId",null));
        binding.tvProfileCarNo.setText(preferences.getString("userCarNo",null));
        binding.tvProfileCarInfo.setText(preferences.getString("userCarInfo",null));
        Authorization=preferences.getString("Authorization",null);
        Log.d("jjk",Authorization);
        call= Retrofit_client.getApiService().userInfo(Authorization);
        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                SharedPreferences.Editor editor=preferences.edit();

                binding.tvProfileId.setText(response.body().getUserId());
                editor.putString("userId",response.body().getUserId());
                editor.putInt("userCarNo",response.body().getUserNo());

                binding.tvProfileCarNo.setText(response.body().getUserCarNo());
                editor.putString("userCarNo",response.body().getUserCarNo());

                binding.tvProfileCarInfo.setText(response.body().getUserCarInfo());
                editor.putString("userCarInfo",response.body().getUserCarInfo());

                if(response.body().getMileage()==0){
                    binding.tvProfileMileage.setText("0");
                    editor.putInt("mileage",0);
                }else {
                    binding.tvProfileMileage.setText(""+response.body().getMileage());
                    editor.putInt("mileage",response.body().getMileage());
                }
                if (response.body().getCarpoolCnt()==0){
                    binding.tvProfileCount.setText("0");
                    editor.putInt("count", 0);
                }else {
                    binding.tvProfileCount.setText(""+response.body().getCarpoolCnt());
                    editor.putInt("count", response.body().getCarpoolCnt());
                }
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {

            }
        });
        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=preferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(getContext(), SplashActivity.class);
                intent.putExtra("status","logout");
                startActivity(intent);
            }
        });

        binding.buttonUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.mypageUpdateFragment);
            }
        });

        binding.buttonDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("정말로 탈퇴하시겠습니까?");
                builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity().getApplicationContext(), "탈퇴 완료", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
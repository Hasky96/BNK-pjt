package com.example.carpoolapp.ui.myPage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.carpoolapp.R;
import com.example.carpoolapp.databinding.FragmentMypageUpdateBinding;
import com.example.carpoolapp.model.UserUpdateRequest;
import com.example.carpoolapp.server.Retrofit_client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MypageUpdateFragment extends Fragment {

    private FragmentMypageUpdateBinding binding;
    private SharedPreferences preferences;
    private String userCarNo;
    private String userCarInfo;
    private Call<String> call;
    private String Authorization;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentMypageUpdateBinding.inflate(getLayoutInflater());
        preferences= getContext().getSharedPreferences("User", Context.MODE_PRIVATE);

        Authorization=preferences.getString("Authorization",null);
        binding.tvProfileUpdateId.setText(preferences.getString("userId",null));
        binding.eTProfileUpdateCarNo.setText(preferences.getString("userCarNo",null));
        userCarNo=preferences.getString("userCarNo",null);
        binding.eTProfileUpdateCarInfo.setText(preferences.getString("userCarInfo",null));
        userCarInfo=preferences.getString("userCarInfo",null);

        binding.tvProfileUpdatePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity().getApplicationContext());
                builder.setTitle("다이얼로그").setMessage("다디얼로그가 보인다면 성공");
                builder.setPositiveButton("성공", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity().getApplicationContext(), "성공!!", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("실패", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity().getApplicationContext(), "실패!!", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNeutralButton("중간", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity().getApplicationContext(), "중간!!", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog alertDialog= builder.create();;
                alertDialog.show();

            }
        });

        binding.eTProfileUpdateCarNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                userCarNo=editable.toString();
            }
        });
        binding.eTProfileUpdateCarInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                userCarInfo=editable.toString();
            }
        });
        binding.buttonUpdateUserCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserUpdateRequest userUpdateRequest=new UserUpdateRequest(userCarNo,userCarInfo);
                call= Retrofit_client.getApiService().updateUser(Authorization,userUpdateRequest);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("jjk",response.body());
                        Toast.makeText(getActivity(), "수정 완료", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("jjk",userUpdateRequest.getUserCarNo()+"/"+userUpdateRequest.getUserCarInfo());
                        Log.d("jjk","실패");
                    }
                });
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_mypage);
            }
        });


        return binding.getRoot();
    }
}
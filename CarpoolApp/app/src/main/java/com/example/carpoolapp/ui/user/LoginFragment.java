package com.example.carpoolapp.ui.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.carpoolapp.MainActivity;
import com.example.carpoolapp.R;
import com.example.carpoolapp.databinding.FragmentLoginBinding;
import com.example.carpoolapp.model.LoginRequest;
import com.example.carpoolapp.model.LoginResponse;
import com.example.carpoolapp.server.Retrofit_client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private String userId;
    private String userPw;
    private Call<LoginResponse> call;
    private LoginResponse loginResponse;
    private String token;
    private String Authorization;
    private SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentLoginBinding.inflate(getLayoutInflater());

        binding.eTLoginId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable editable) {
                userId=editable.toString();
            }
        });
        binding.eTLoginPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                userPw=editable.toString();
            }
        });

        preferences= getContext().getSharedPreferences("User", Context.MODE_PRIVATE);

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean autoLogin;
                if (binding.checkBox.isChecked()){autoLogin=true;}
                else {autoLogin=false;}

                LoginRequest loginRequest=new LoginRequest(userId, userPw);
                call=Retrofit_client.getApiService().login(loginRequest);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginResponse> call, Response<LoginResponse> response) {
                        //?????????????????? ????????? ?????????, ????????????, ????????????
                        loginResponse=response.body();
                        if(loginResponse!=null){
                            Authorization="Bearer "+loginResponse.getAccessToken();
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("userId",loginResponse.getUserId());
                            editor.putInt("userNo",loginResponse.getUserNo());
                            editor.putString("Authorization",Authorization);
                            editor.putString("userCarInfo",loginResponse.getUserCarInfo());
                            editor.putString("userCarNo",loginResponse.getUserCarNo());
                            editor.putBoolean("autoLogin",autoLogin);
                            editor.remove("status");
                            editor.commit();
                            //editor.clear(); //?????????
                            //?????????????????? ?????? ???????????????
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(getActivity().getApplicationContext(), "?????????, ??????????????? ???????????? ????????????.", Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(getActivity().getApplicationContext(), "?????? ??????", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        binding.clickSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //???????????? ??????????????? ??? ????????????
                SignupFragment fragment=new SignupFragment();
                Bundle bundle=new Bundle();
                fragment.setArguments(bundle);
                //????????????????????? ?????????????????? ??????
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null) //???????????? ???????????? ??????
                        .setCustomAnimations(R.anim.slide_page_in, R.anim.slide_page_out)
                        .replace(R.id.ConstraintFragment, fragment).commit();
            }
        });




        return binding.getRoot();
    }
}
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
                LoginRequest loginRequest=new LoginRequest(userId, userPw);
                call=Retrofit_client.getApiService().login(loginRequest);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginResponse> call, Response<LoginResponse> response) {
                        //자동로그인을 위해서 아이디, 비밀번호, 토큰저장
                        loginResponse=response.body();
                        if(loginResponse!=null){
                            Authorization="Bearer "+loginResponse.getAccessToken();
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("userId",loginResponse.getUserId());
                            editor.putInt("userNo",loginResponse.getUserNo());
                            editor.putString("Authorization",Authorization);
                            editor.putString("userCarInfo",loginResponse.getUserCarInfo());
                            editor.putString("userCarNo",loginResponse.getUserCarNo());
                            editor.commit();
                            //editor.clear(); //초기화
                            //로그인처리가 되면 카풀앱으로
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(getActivity().getApplicationContext(), "아이디, 비밀번호가 유효하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("jjk","실패");
                    }
                });

            }
        });

        binding.clickSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //데이터를 넘겨줘야할 때 사용하기
                SignupFragment fragment=new SignupFragment();
                Bundle bundle=new Bundle();
                fragment.setArguments(bundle);
                //프래그먼트에서 프래그먼트로 이동
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null) //뒤로가기 가능하게 만듬
                        .replace(R.id.ConstraintFragment, fragment).commit();
            }
        });



        return binding.getRoot();
    }
}
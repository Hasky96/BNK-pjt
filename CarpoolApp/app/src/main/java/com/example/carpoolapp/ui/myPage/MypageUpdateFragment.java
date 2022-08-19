package com.example.carpoolapp.ui.myPage;

import android.app.AlertDialog;
import android.app.Dialog;
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
import com.example.carpoolapp.databinding.DialoguePasswordBinding;
import com.example.carpoolapp.databinding.FragmentMypageUpdateBinding;
import com.example.carpoolapp.model.UserUpdatePwRequest;
import com.example.carpoolapp.model.UserUpdatePwResponse;
import com.example.carpoolapp.model.UserUpdateRequest;
import com.example.carpoolapp.model.UserUpdateResponse;
import com.example.carpoolapp.server.Retrofit_client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MypageUpdateFragment extends Fragment {
    private FragmentMypageUpdateBinding binding;
    private SharedPreferences preferences;
    private String userCarNo;
    private String userCarInfo;
    private Call<UserUpdateResponse> call;
    private String Authorization;
    private String oldPw;
    private String newPw;
    private String newPwCheck;
    private Call<UserUpdatePwResponse> callPw;

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
                dialogFun();
            }
        });

        binding.buttonUpdateUserCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_mypage);
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
                call.enqueue(new Callback<UserUpdateResponse>() {
                    @Override
                    public void onResponse(Call<UserUpdateResponse> call, Response<UserUpdateResponse> response) {
                        Log.d("jjk",String.valueOf(response.code()));
                        int code=response.code();
                        if(code==200){
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("userCarInfo",userUpdateRequest.getUserCarInfo());
                            editor.putString("userCarNo",userUpdateRequest.getUserCarNo());
                            editor.commit();
                            Toast.makeText(getActivity(), "수정 완료", Toast.LENGTH_SHORT).show();
                            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                            navController.navigate(R.id.navigation_mypage);
                        }else {
                            Toast.makeText(getActivity(), "잘못된 형식으로 입력했습니다. 다시 입력하세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<UserUpdateResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), "수정 실패", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        return binding.getRoot();
    }

    public void dialogFun(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
        DialoguePasswordBinding binding2=DialoguePasswordBinding.inflate(getLayoutInflater());
        builder.setView(binding2.getRoot());
        AlertDialog alertDialog=builder.create();
        binding2.eTProfileOldPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                oldPw=editable.toString();
            }
        });
        binding2.eTProfileNewPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                newPw=editable.toString();
            }
        });
        binding2.eTProfileNewPwCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                newPwCheck=editable.toString();
            }
        });
        binding2.buttonUpdateUserPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oldPw!=null && newPw!=null && newPwCheck!=null){
                    if(newPw.equals(newPwCheck)){
                        UserUpdatePwRequest userUpdatePwRequest=new UserUpdatePwRequest(oldPw, newPw);
                        callPw=Retrofit_client.getApiService().updatePwUser(Authorization, userUpdatePwRequest);
                        callPw.enqueue(new Callback<UserUpdatePwResponse>() {
                            @Override
                            public void onResponse(Call<UserUpdatePwResponse> call, Response<UserUpdatePwResponse> response) {
                                if(response.code()==200){
                                    Toast.makeText(getActivity(), "비밀번호 변경 완료", Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                }else {
                                    Toast.makeText(getActivity(), "잘못된 형식입니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<UserUpdatePwResponse> call, Throwable t) {
                                Toast.makeText(getActivity(), "변경 실패", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        Toast.makeText(getActivity(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding2.buttonUpdateUserPwCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
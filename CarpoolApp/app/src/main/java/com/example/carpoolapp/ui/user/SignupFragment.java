package com.example.carpoolapp.ui.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carpoolapp.R;
import com.example.carpoolapp.databinding.FragmentSignupBinding;


public class SignupFragment extends Fragment {
    private FragmentSignupBinding binding;
    private String userId;
    private String userPw;
    private String userCarInfo;
    private String userCarNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentSignupBinding.inflate(getLayoutInflater());

        //더 간단한 방법이 있긴한데 엔터를 안치고 로그인버튼 누르면 값 저장이 안됨.
        binding.eTSignupId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {userId=editable.toString();}
        });
        binding.eTSignupPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {userPw=editable.toString();}
        });
        binding.eTSignupCarNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {userCarNo=editable.toString();}
        });
        binding.eTSignupCarInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {userCarInfo=editable.toString();}
        });

        binding.buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("jjk",userId+userPw+userCarNo+userCarInfo);
                //데이터를 넘겨줘야할 때 사용하기
                LoginFragment fragment=new LoginFragment();
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
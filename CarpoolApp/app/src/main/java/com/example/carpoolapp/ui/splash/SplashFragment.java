package com.example.carpoolapp.ui.splash;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carpoolapp.R;
import com.example.carpoolapp.databinding.FragmentSplashBinding;
import com.example.carpoolapp.ui.user.LoginFragment;
import com.example.carpoolapp.ui.user.SignupFragment;

public class SplashFragment extends Fragment {

    private FragmentSplashBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentSplashBinding.inflate(getLayoutInflater());

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //딜레이 후 시작할 코드 작성
                //데이터를 넘겨줘야할 때 사용하기
                LoginFragment fragment=new LoginFragment();
                Bundle bundle=new Bundle();
                fragment.setArguments(bundle);
                //프래그먼트에서 프래그먼트로 이동
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ConstraintFragment, fragment).commit();
            }
        }, 3000);


        return binding.getRoot();
    }
}
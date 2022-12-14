package com.example.carpoolapp.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.carpoolapp.MainActivity;
import com.example.carpoolapp.R;
import com.example.carpoolapp.databinding.FragmentSplashBinding;
import com.example.carpoolapp.ui.user.LoginFragment;

public class SplashFragment extends Fragment {

    private FragmentSplashBinding binding;
    private SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(getLayoutInflater());

        preferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        String Authorization = preferences.getString("Authorization", null);
        Animation animUp = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_logo_up);
        Animation animDown = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_logo_down);

        binding.SplashCarpoolLogo.startAnimation(animDown);
        binding.SplashBNKLogo.startAnimation(animUp);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Authorization != null) {
                    Log.d("jjk", Authorization);
                    if (getActivity()!=null){
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    }else {
                        
                    }
                } else {
                    //????????? ??? ????????? ?????? ??????
                    //???????????? ??????????????? ??? ????????????
                    LoginFragment fragment = new LoginFragment();
                    Bundle bundle = new Bundle();
                    fragment.setArguments(bundle);
                    //????????????????????? ?????????????????? ??????
                    requireActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.slide_page_in, R.anim.slide_page_out)
                            .replace(R.id.ConstraintFragment, fragment).commit();
                }
            }
        }, 3000);
        return binding.getRoot();
    }
}
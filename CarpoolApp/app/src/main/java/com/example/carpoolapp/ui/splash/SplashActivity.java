package com.example.carpoolapp.ui.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.example.carpoolapp.R;
import com.example.carpoolapp.databinding.ActivitySplashBinding;
import com.example.carpoolapp.ui.user.LoginFragment;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //데이터를 넘겨줘야할 때 사용하기
        SplashFragment fragment=new SplashFragment();
        Bundle bundle=new Bundle();
        fragment.setArguments(bundle);
        //프래그먼트 이동
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null) //뒤로가기 가능하게 만듬
                .replace(R.id.ConstraintFragment, fragment).commit();

    }
}
package com.example.carpoolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.example.carpoolapp.databinding.ActivityAnimationBinding;

public class AnimationActivity extends AppCompatActivity {

    private ActivityAnimationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAnimationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.imgCar.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.car_move));
                        binding.imgCar.setVisibility(View.INVISIBLE);
                    }
                }, 1000);
            }
        });
    }
}
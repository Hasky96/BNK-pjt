package com.example.carpoolapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.carpoolapp.service.ForcedTerminationService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.carpoolapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences= getSharedPreferences("User", Context.MODE_PRIVATE);
        startService(new Intent(this, ForcedTerminationService.class));
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        overridePendingTransition(R.anim.slide_page_up,R.anim.slide_page_down);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        boolean autoLogin=preferences.getBoolean("autoLogin",false);
        if (!autoLogin){
            SharedPreferences.Editor editor=preferences.edit();
            editor.clear();
            editor.commit();
        }
    }
}
package com.example.carpoolapp.ui.myReservation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.carpoolapp.R;
import com.example.carpoolapp.databinding.FragmentMyreservationBinding;
import com.example.carpoolapp.ui.splash.SplashActivity;

public class myReservationFragment extends Fragment {

    private FragmentMyreservationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myReservationViewModel homeViewModel =
                new ViewModelProvider(this).get(myReservationViewModel.class);

        binding = FragmentMyreservationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
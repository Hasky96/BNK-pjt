package com.example.carpoolapp.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carpoolapp.R;
import com.example.carpoolapp.databinding.FragmentCarpoolCreateBinding;

public class CarpoolCreateFragment extends Fragment {
    private FragmentCarpoolCreateBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentCarpoolCreateBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }
}
package com.example.carpoolapp.ui.carpool;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carpoolapp.R;
import com.example.carpoolapp.databinding.FragmentBackHomeBinding;
import com.example.carpoolapp.databinding.FragmentCarpoolDetailBinding;
import com.example.carpoolapp.model.CarpoolDetailRes;

public class CarpoolDetailFragment extends Fragment {

    CarpoolViewModel carpoolViewModel;
    FragmentCarpoolDetailBinding binding;
    int carpoolNo;
    CarpoolDetailRes carpoolDetail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCarpoolDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carpoolNo = getArguments().getInt("carpoolNo");
        carpoolViewModel = new ViewModelProvider(this).get(CarpoolViewModel.class);
        carpoolViewModel.getCarpoolDetail(carpoolNo).observe(this, carpoolDetail -> {
            this.carpoolDetail = carpoolDetail;
            Log.d(">>", "detail fragment" + carpoolNo  + "/" + carpoolDetail.getCarpoolNo());

        });

    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
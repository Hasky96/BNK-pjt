package com.example.carpoolapp.ui.carpool;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carpoolapp.R;
import com.example.carpoolapp.databinding.FragmentBackHomeBinding;
import com.example.carpoolapp.databinding.FragmentCarpoolBinding;
import com.example.carpoolapp.model.CarpoolAllDetailRes;

import java.util.ArrayList;
import java.util.List;


public class BackHomeFragment extends Fragment {

	FragmentBackHomeBinding binding;
	CarpoolAdapter carpoolAdapter;
	CarpoolViewModel carpoolViewModel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		binding = FragmentBackHomeBinding.inflate(inflater, container, false);
		View root = binding.getRoot();

		carpoolAdapter = new CarpoolAdapter(getActivity(), true);
		binding.rvCarpool.setAdapter(carpoolAdapter);
		binding.rvCarpool.setLayoutManager(new LinearLayoutManager(getContext()));

		return root;
	}


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		carpoolViewModel = new ViewModelProvider(this).get(CarpoolViewModel.class);
		carpoolViewModel.getCarpools().observe(this, carpoolList -> {
			List<CarpoolAllDetailRes> list = new ArrayList<>();
			for (CarpoolAllDetailRes temp: carpoolList) {
				if(temp.isType()) list.add(temp);
			}
			carpoolAdapter.submitList(list);
		});

	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d(">>", "onstart");
		carpoolViewModel.loadCarpools();

	}
}
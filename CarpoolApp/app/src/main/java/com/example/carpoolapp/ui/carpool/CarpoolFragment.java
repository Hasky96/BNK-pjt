package com.example.carpoolapp.ui.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpoolapp.R;
import com.example.carpoolapp.databinding.FragmentCarpoolBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

// 카풀 리스트 프래그먼트
public class CarpoolFragment extends Fragment {

    private FragmentCarpoolBinding binding;
    FloatingActionButton flbCarpoolRegister;
    RecyclerView rvCarpool;
    CarpoolViewModel carpoolViewModel;
    CarpoolAdapter carpoolAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCarpoolBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        carpoolViewModel = new ViewModelProvider(this).get(CarpoolViewModel.class);

        carpoolAdapter = new CarpoolAdapter();
        binding.rvCarpool.setAdapter(carpoolAdapter);
        binding.rvCarpool.setLayoutManager(new LinearLayoutManager(getContext()));

        carpoolViewModel.carpoolList.observe(getViewLifecycleOwner(), carpoolList->{
            carpoolAdapter.submitList(carpoolList);
        });

        binding.flbCarpoolRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CarpoolRegisterActivity.class);
                startActivity(intent);
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
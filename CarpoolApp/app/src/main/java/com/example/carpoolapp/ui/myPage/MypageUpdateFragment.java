package com.example.carpoolapp.ui.myPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carpoolapp.R;
import com.example.carpoolapp.databinding.FragmentMypageUpdateBinding;


public class MypageUpdateFragment extends Fragment {

    private FragmentMypageUpdateBinding binding;
    private SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentMypageUpdateBinding.inflate(getLayoutInflater());
        preferences= getContext().getSharedPreferences("User", Context.MODE_PRIVATE);

        binding.eTProfileUpdateId.setText(preferences.getString("userId",null));
        binding.eTProfileUpdateCarNo.setText(preferences.getString("userCarNo",null));
        binding.eTProfileUpdateCarInfo.setText(preferences.getString("userCarInfo",null));

        binding.buttonUpdateUserCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_mypage);
            }
        });


        return binding.getRoot();
    }
}
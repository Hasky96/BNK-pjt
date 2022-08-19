package com.example.carpoolapp.ui.myPage;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.carpoolapp.MainActivity;
import com.example.carpoolapp.R;
import com.example.carpoolapp.databinding.FragmentMypageBinding;
import com.example.carpoolapp.ui.splash.SplashActivity;
import com.example.carpoolapp.ui.user.LoginFragment;

public class MypageFragment extends Fragment {

    private FragmentMypageBinding binding;
    private SharedPreferences preferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMypageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        preferences= getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        binding.tvProfileId.setText(preferences.getString("userId",null));
        binding.tvProfileCarNo.setText(preferences.getString("userCarNo",null));
        binding.tvProfileCarInfo.setText(preferences.getString("userCarInfo",null));

        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=preferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(getContext(), SplashActivity.class);
                intent.putExtra("status","logout");
                startActivity(intent);
            }
        });

        binding.buttonUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.mypageUpdateFragment);
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
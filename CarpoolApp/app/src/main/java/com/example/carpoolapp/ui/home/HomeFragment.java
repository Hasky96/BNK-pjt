package com.example.carpoolapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.carpoolapp.databinding.FragmentHomeBinding;
import com.example.carpoolapp.model.Message;
import com.example.carpoolapp.server.Retrofit_client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        final Call<Message> call= Retrofit_client.getApiService().test_api_get();
        call.enqueue(new Callback<Message>() {
            //콜백 받는 부분
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Message message=response.body();
                textView.setText(message.getMessage());
                Log.d("jjk","성공");
            }
            //실패
            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.d("jjk","실패");
            }
        });
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
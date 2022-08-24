package com.example.carpoolapp.ui.carpool;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carpoolapp.databinding.FragmentCarpoolCommentsBinding;
import com.example.carpoolapp.model.AllCommentRes;
import com.example.carpoolapp.model.CommentDto;
import com.example.carpoolapp.server.Retrofit_client;

import java.util.List;

import retrofit2.Call;

public class CarpoolCommentsFragement extends Fragment {

    FragmentCarpoolCommentsBinding binding;
    Bundle bundle;

    int carpoolNo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        bundle = getArguments();
        super.onCreate(savedInstanceState);
        binding = FragmentCarpoolCommentsBinding.inflate(getLayoutInflater());
        SharedPreferences preferences= getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        String Authorization=preferences.getString("Authorization",null);
        Log.d("qq", bundle.getInt("carpoolWriterNo")+"");
        CommentsAdapter adapter = new CommentsAdapter(getActivity(),bundle.getInt("carpoolWriterNo"));
        adapter.submitList((List<CommentDto>) bundle.getSerializable("comments"));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return binding.getRoot();
    }
}
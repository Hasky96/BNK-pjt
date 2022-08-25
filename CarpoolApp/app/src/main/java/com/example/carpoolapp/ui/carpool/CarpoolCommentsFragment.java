package com.example.carpoolapp.ui.carpool;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.carpoolapp.R;
import com.example.carpoolapp.databinding.FragmentCarpoolCommentsBinding;
import com.example.carpoolapp.model.AllCommentRes;
import com.example.carpoolapp.model.CommentDto;
import com.example.carpoolapp.model.CommentRequest;
import com.example.carpoolapp.model.CommonResponse;
import com.example.carpoolapp.server.Retrofit_client;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarpoolCommentsFragment extends Fragment {

    FragmentCarpoolCommentsBinding binding;
    Bundle bundle;
    Call<AllCommentRes> call;
    Call<CommonResponse> call_comment;

    int carpoolNo;
    int carpoolWriterNo;
    String comment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        bundle = getArguments();
        super.onCreate(savedInstanceState);
        binding = FragmentCarpoolCommentsBinding.inflate(getLayoutInflater());
        SharedPreferences preferences= getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        String Authorization=preferences.getString("Authorization",null);
        carpoolWriterNo=bundle.getInt("carpoolWriterNo");
        carpoolNo=bundle.getInt("carpoolNo");
        CommentsAdapter adapter = new CommentsAdapter(getActivity(),carpoolWriterNo);

        call=Retrofit_client.getApiService().allComments(Authorization,carpoolNo);
        call.enqueue(new Callback<AllCommentRes>() {
            @Override
            public void onResponse(Call<AllCommentRes> call, Response<AllCommentRes> response) {
                List<CommentDto> commentList= response.body().getComments();
                adapter.submitList(commentList);
                binding.rvComments.setAdapter(adapter);
                binding.rvComments.setLayoutManager(new LinearLayoutManager(getContext()));
            }
            @Override
            public void onFailure(Call<AllCommentRes> call, Throwable t) {

            }
        });

        binding.eTComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                comment=editable.toString();
            }
        });

        binding.commentRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comment!=null && !comment.equals("")){
                    CommentRequest commentRequest=new CommentRequest(comment);
                    call_comment=Retrofit_client.getApiService().registerComment(Authorization,carpoolNo,commentRequest);
                    call_comment.enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                            Log.d("jjk",response.code()+"");
                            Bundle bundle1=new Bundle();
                            bundle1.putInt("carpoolNo",carpoolNo);
                            bundle1.putInt("carpoolWriterNo",carpoolWriterNo);

                            CarpoolCommentsFragment carpoolCommentsFragement = new CarpoolCommentsFragment();
                            carpoolCommentsFragement.setArguments(bundle1);

                            requireActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.commentFragment, carpoolCommentsFragement).commit();
                        }
                        @Override
                        public void onFailure(Call<CommonResponse> call, Throwable t) {
                            Toast.makeText(getActivity(),"댓글등록 실패",Toast.LENGTH_SHORT);
                        }
                    });
                }
                else {
                    Toast.makeText(getActivity(),"채팅을 입력하세요.",Toast.LENGTH_SHORT);
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return binding.getRoot();
    }
}
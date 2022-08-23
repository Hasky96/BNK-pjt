package com.example.carpoolapp.ui.carpool;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.carpoolapp.R;
import com.example.carpoolapp.databinding.FragmentBackHomeBinding;
import com.example.carpoolapp.databinding.FragmentCarpoolDetailBinding;
import com.example.carpoolapp.model.CarpoolDetailRes;
import com.example.carpoolapp.util.CarpoolUtil;

public class CarpoolDetailFragment extends Fragment {

    CarpoolViewModel carpoolViewModel;
    FragmentCarpoolDetailBinding binding;
    int carpoolNo;
    CarpoolDetailRes cdetail;
    private SharedPreferences preferences;
    String cmsg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCarpoolDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        preferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        binding.tvDetailLoc.setSelected(true);
        binding.btnCarpoolJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carpoolViewModel.joinCarpool(carpoolNo);
            }
        });

        binding.btnCarpoolCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carpoolViewModel.cancelCarpool(carpoolNo);
            }
        });

        binding.btnCarpoolUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CarpoolRegisterActivity.class);
                intent.putExtra("cdetail", cdetail);
                intent.putExtra("from","detail");
                startActivity(intent);

            }
        });


        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        carpoolNo = getArguments().getInt("carpoolNo");
        carpoolViewModel = new ViewModelProvider(this).get(CarpoolViewModel.class);

        carpoolViewModel.getCarpoolDetail(carpoolNo).observe(this, carpoolDetail -> {
            cdetail = carpoolDetail;
            binding.tvDetailLoc.setText(carpoolDetail.getLocation());
            binding.ttvDetailPerson.setText(carpoolDetail.getOccupants().split(",").length +"/" + carpoolDetail.getQuota());
            binding.tvDetailCarInfo.setText(carpoolDetail.getInfo());
            binding.tvDetailTime.setText(carpoolDetail.getTime().split("T")[1].substring(0,5));
            binding.tvDetailDate.setText(carpoolDetail.getTime().split("T")[0]);
            binding.tvDetailWriter.setText(carpoolDetail.getWriterId());

            if( CarpoolUtil.isUserInCarpool(cdetail,preferences.getString("userId",null)) ){
                binding.btnCarpoolJoin.setVisibility(View.INVISIBLE);
                binding.btnCarpoolCancle.setVisibility(View.VISIBLE);
            }else{
                binding.btnCarpoolJoin.setVisibility(View.VISIBLE);
                binding.btnCarpoolCancle.setVisibility(View.INVISIBLE);
            }

            if( preferences.getInt("userNo",1) == cdetail.getWriterNo()){
                binding.btnCarpoolUpdate.setVisibility(View.VISIBLE);
                binding.btnCarpoolDelete.setVisibility(View.VISIBLE);
            }
        });

        carpoolViewModel.getMsg().observe(this, msg ->{
            cmsg = msg;
            AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
            builder.setTitle(cmsg);
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {}
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(">>", "carpool detail fg start");
        carpoolViewModel.loadCarpoolDetail(carpoolNo);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(">>", "carpool detail fg resume");
    }
}
package com.example.carpoolapp.ui.carpool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpoolapp.R;

public class CarpoolFragmentAdapter extends RecyclerView.Adapter<CarpoolFragmentAdapter.CarpoolViewHolder> {


    @NonNull
    @Override
    public CarpoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View carpoolItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carpool, parent, false);
        return new CarpoolViewHolder(carpoolItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarpoolViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class CarpoolViewHolder extends RecyclerView.ViewHolder {


        public CarpoolViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

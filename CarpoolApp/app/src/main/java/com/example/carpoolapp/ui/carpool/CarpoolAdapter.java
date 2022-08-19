package com.example.carpoolapp.ui.carpool;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carpoolapp.R;
import com.example.carpoolapp.model.Carpool;
import com.example.carpoolapp.model.CarpoolResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CarpoolAdapter extends RecyclerView.Adapter<CarpoolAdapter.CarpoolViewHolder> {


	ArrayList<CarpoolResponse> carpoolResponseArrayList;
	DiffUtil.ItemCallback<Carpool> diffCallback = new DiffUtil.ItemCallback<Carpool>() {

		@Override
		public boolean areItemsTheSame(@NonNull Carpool oldItem, @NonNull Carpool newItem) {
			return oldItem.getCarpoolNo() == newItem.getCarpoolNo();
		}

		@SuppressLint("DiffUtilEquals")
		@Override
		public boolean areContentsTheSame(@NonNull Carpool oldItem, @NonNull Carpool newItem) {
			return oldItem.hashCode() == newItem.hashCode();
		}

	};

	AsyncListDiffer<Carpool> differ = new AsyncListDiffer<>(this, diffCallback);

	public void submitList(List<Carpool> carpoolList) {
		differ.submitList(carpoolList);
	}

	@NonNull
	@Override
	public CarpoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View carpoolItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carpool, parent, false);
		return new CarpoolViewHolder(carpoolItemView);
	}

	@Override
	public void onBindViewHolder(@NonNull CarpoolViewHolder holder, int position) {
		Carpool carpool = differ.getCurrentList().get(position);
		// holder에 계속 set

		holder.tvItemDepartTime.setText(carpool.getTime().toString());


	}

	@Override
	public int getItemCount() {
		return differ.getCurrentList().size();
	}

	class CarpoolViewHolder extends RecyclerView.ViewHolder {
		TextView tvItemDepartTime;

		public CarpoolViewHolder(@NonNull View itemView) {
			super(itemView);
			tvItemDepartTime = itemView.findViewById(R.id.tvItemDepartTime);
		}
	}
}

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

import com.example.carpoolapp.R;
import com.example.carpoolapp.model.CarpoolAllDetailRes;
import com.example.carpoolapp.model.CarpoolResponse;

import java.util.ArrayList;
import java.util.List;

public class CarpoolAdapter extends RecyclerView.Adapter<CarpoolAdapter.CarpoolViewHolder> {


	ArrayList<CarpoolResponse> carpoolResponseArrayList;
	DiffUtil.ItemCallback<CarpoolAllDetailRes> diffCallback = new DiffUtil.ItemCallback<CarpoolAllDetailRes>() {

		@Override
		public boolean areItemsTheSame(@NonNull CarpoolAllDetailRes oldItem, @NonNull CarpoolAllDetailRes newItem) {
			return oldItem.getCarpoolNo() == newItem.getCarpoolNo();
		}

		@SuppressLint("DiffUtilEquals")
		@Override
		public boolean areContentsTheSame(@NonNull CarpoolAllDetailRes oldItem, @NonNull CarpoolAllDetailRes newItem) {
			return oldItem.hashCode() == newItem.hashCode();
		}

	};

	AsyncListDiffer<CarpoolAllDetailRes> differ = new AsyncListDiffer<>(this, diffCallback);

	public void submitList(List<CarpoolAllDetailRes> carpoolList) {
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
		CarpoolAllDetailRes carpool = differ.getCurrentList().get(position);
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

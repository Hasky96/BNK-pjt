package com.example.carpoolapp.ui.carpool;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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
	FragmentActivity activity;

	public CarpoolAdapter(FragmentActivity activity){
		this.activity = activity;
	}

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
		holder.tvItemDepartTime.setText(carpool.getTime().split("T")[1].substring(0,5));

		if( carpool.isType() ){
			//퇴근
			holder.tvItemcarpoolType.setText("퇴근");
			holder.tvDepartLoc.setText("회사");
			holder.tvDestinationLoc.setText(carpool.getLocation());
			holder.itemLine.setBackgroundResource(R.drawable.gray_round_square);
			holder.itembar.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#B7A997")));
		}else{
			//출근
			holder.tvItemcarpoolType.setText("출근");
			holder.tvDepartLoc.setText(carpool.getLocation());
			holder.tvDestinationLoc.setText("회사");
		}

		holder.tvItemCarNum.setText("123");
		Log.d(">>",carpool.getOccupants() + " " + carpool.getQuota());
		holder.tvCurperson.setText(Integer.toString(carpool.getOccupants().split(",").length));
		holder.tvTotalPerson.setText(Integer.toString(carpool.getQuota()));
		holder.itemLine.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment_activity_main);
				navController.navigate(R.id.carpoolDetailFragment);
			}
		});

	}

	@Override
	public int getItemCount() {
		return differ.getCurrentList().size();
	}

	class CarpoolViewHolder extends RecyclerView.ViewHolder {
		TextView tvItemDepartTime, // 출발시간
				tvDepartLoc, // 출발지
				tvDestinationLoc,  // 도착지
				tvItemCarNum, // 차번호
				tvItemcarpoolType, // 출근. 퇴근
				tvCurperson, // 현재인원
				tvTotalPerson; // 수용인원
		ConstraintLayout itemLine;
		LinearLayout itembar;

		public CarpoolViewHolder(@NonNull View itemView) {
			super(itemView);
			tvItemDepartTime = itemView.findViewById(R.id.tvItemDepartTime);
			tvDepartLoc = itemView.findViewById(R.id.tvDepartLoc);
			tvDestinationLoc = itemView.findViewById(R.id.tvDestinationLoc);
			tvItemCarNum = itemView.findViewById(R.id.tvItemCarNum);
			tvItemcarpoolType = itemView.findViewById(R.id.tvItemcarpoolType);
			tvCurperson = itemView.findViewById(R.id.tvCurperson);
			tvTotalPerson = itemView.findViewById(R.id.tvTotalPerson);
			itemLine = itemView.findViewById(R.id.itemLine);
			itembar = itemView.findViewById(R.id.itemBar);


		}
	}
}

package com.example.carpoolapp.ui.carpool;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
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
	boolean carpoolType;

	public CarpoolAdapter(FragmentActivity activity, boolean carpoolType){
		this.activity = activity;
		this.carpoolType = carpoolType;
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
		Log.d(">>", activity.getCallingActivity() + "");
		holder.tvDepartLoc.setSelected(true);
		holder.tvDestinationLoc.setSelected(true);
		// 가지고 오는 데이터 개수가 3개여서 출근, 퇴근으로 색을 나누어도 각 탭에는 3개의 아이템이 만들어진다
		// 결국 출근, 퇴근 나누어서 가지고 오는게 나을 듯??
		if( carpool.isType() && carpoolType ){
			//퇴근
			holder.tvItemcarpoolType.setText("퇴근");
			holder.tvDepartLoc.setText("회사");
			holder.tvDestinationLoc.setText(carpool.getLocation());
			holder.itemLine.setBackgroundResource(R.drawable.gray_round_square);
			holder.itembar.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#B7A997")));
		}else if( !carpool.isType() && !carpoolType){
			//출근
			holder.tvItemcarpoolType.setText("출근");
			holder.tvDepartLoc.setText(carpool.getLocation());
			holder.tvDestinationLoc.setText("회사");
		}

		if( carpool.getDriverNo() != 0){
			holder.tvItemCarNum.setText(carpool.getCarNo());
		}else{
			holder.tvItemCarNum.setText("");
		}

		Log.d(">>",carpool.getOccupants() + " " + carpool.getQuota());
		holder.tvCurperson.setText(Integer.toString(carpool.getOccupants().split(",").length));
		holder.tvTotalPerson.setText(Integer.toString(carpool.getQuota()));
		holder.itemLine.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Bundle bundle = new Bundle();
				bundle.putInt("carpoolNo", carpool.getCarpoolNo());
				Navigation.findNavController(view).navigate(R.id.action_navigation_carpool_to_carpoolDetailFragment2, bundle);
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

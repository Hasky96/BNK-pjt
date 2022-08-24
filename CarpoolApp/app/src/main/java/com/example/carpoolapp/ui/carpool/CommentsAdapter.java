package com.example.carpoolapp.ui.carpool;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpoolapp.R;
import com.example.carpoolapp.model.CommentDto;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CarpoolViewHolder> {


	ArrayList<CommentDto> commentDtoArrayList;
	FragmentActivity activity;
	int carpoolWriterNo;

	public CommentsAdapter(FragmentActivity activity, int carpoolWriterNo){
		this.activity = activity;
		this.carpoolWriterNo = carpoolWriterNo;
	}

	DiffUtil.ItemCallback<CommentDto> diffCallback = new DiffUtil.ItemCallback<CommentDto>() {

		@Override
		public boolean areItemsTheSame(@NonNull CommentDto oldItem, @NonNull CommentDto newItem) {
			return oldItem.getCommentNo() == newItem.getCommentNo();
		}

		@SuppressLint("DiffUtilEquals")
		@Override
		public boolean areContentsTheSame(@NonNull CommentDto oldItem, @NonNull CommentDto newItem) {
			return oldItem.getCommentNo() == newItem.getCommentNo();
		}
	};

	AsyncListDiffer<CommentDto> differ = new AsyncListDiffer(this, diffCallback);

	public void submitList(List<CommentDto> commentDtoList) {
		differ.submitList(commentDtoList);
	}

	@NonNull
	@Override
	public CarpoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View carpoolItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carpool, parent, false);
		return new CarpoolViewHolder(carpoolItemView);
	}

	@Override
	public void onBindViewHolder(@NonNull CarpoolViewHolder holder, int position) {
		CommentDto comment = differ.getCurrentList().get(position);

		if(carpoolWriterNo == comment.getUserNo()){
			holder.tvMyCommentWriter.setText(comment.getUserId());
			holder.tvMyCommentContent.setText(comment.getComment());
			holder.tvMyCommentCreated.setText(comment.getCreated().toString().replace("T", " ").substring(0,16));
		}else{
			holder.tvCommentWriter.setText(comment.getUserId());
			holder.tvCommentContent.setText(comment.getComment());
			holder.tvCommentCreated.setText(comment.getCreated().toString().replace("T", " ").substring(0,16));
		}
	}

	@Override
	public int getItemCount() {
		return differ.getCurrentList().size();
	}

	class CarpoolViewHolder extends RecyclerView.ViewHolder {
		TextView tvMyCommentWriter,
				tvCommentWriter,
				tvMyCommentContent,
				tvCommentContent,
				tvMyCommentCreated,
				tvCommentCreated;

		public CarpoolViewHolder(@NonNull View itemView) {
			super(itemView);
			tvMyCommentWriter = itemView.findViewById(R.id.myCommentWriter);
			tvCommentWriter = itemView.findViewById(R.id.commentWriter);
			tvMyCommentContent = itemView.findViewById(R.id.myCommentContent);
			tvCommentContent = itemView.findViewById(R.id.commentContent);
			tvMyCommentCreated = itemView.findViewById(R.id.myCommentCreated);
			tvCommentCreated = itemView.findViewById(R.id.commentCreated);


		}
	}
}

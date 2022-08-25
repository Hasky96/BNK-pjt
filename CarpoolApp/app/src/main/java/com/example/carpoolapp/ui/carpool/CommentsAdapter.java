package com.example.carpoolapp.ui.carpool;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpoolapp.R;
import com.example.carpoolapp.model.CommentDto;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {


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

	CommentViewHolder holder1;

	@NonNull
	@Override
	public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		holder1 = new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_comment, parent, false));
		View carpoolItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
		return new CommentViewHolder(carpoolItemView);
	}

	@Override
	public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
		CommentDto comment = differ.getCurrentList().get(position);

		if(carpoolWriterNo == comment.getUserNo()){
			holder.commentLayout.setGravity(Gravity.RIGHT);
			holder.shapeLayout.setBackgroundResource(R.drawable.my_comment);
			holder.shapeLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFe812")));
		}else{
			holder.commentLayout.setGravity(Gravity.LEFT);
			holder.shapeLayout.setBackgroundResource(R.drawable.comment);
		}
			holder.tvCommentWriter.setText(comment.getUserId());
			holder.tvCommentContent.setText(comment.getComment());
			holder.tvCommentCreated.setText(comment.getCreated().toString().replace("T", " ").substring(0,16));
	}

	@Override
	public int getItemCount() {
		return differ.getCurrentList().size();
	}

	class CommentViewHolder extends RecyclerView.ViewHolder {
		TextView tvMyCommentWriter,
				tvCommentWriter,
				tvMyCommentContent,
				tvCommentContent,
				tvMyCommentCreated,
				tvCommentCreated;
		LinearLayout commentLayout, shapeLayout;

		public CommentViewHolder(@NonNull View itemView) {
			super(itemView);
			tvMyCommentWriter = itemView.findViewById(R.id.myCommentWriter);
			tvCommentWriter = itemView.findViewById(R.id.commentWriter);
			tvMyCommentContent = itemView.findViewById(R.id.myCommentContent);
			tvCommentContent = itemView.findViewById(R.id.commentContent);
			tvMyCommentCreated = itemView.findViewById(R.id.myCommentCreated);
			tvCommentCreated = itemView.findViewById(R.id.commentCreated);
			commentLayout = itemView.findViewById(R.id.commentLayout);
			shapeLayout = itemView.findViewById(R.id.shapeLayout);

		}
	}
}

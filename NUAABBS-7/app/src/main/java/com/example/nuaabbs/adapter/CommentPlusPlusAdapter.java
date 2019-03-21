package com.example.nuaabbs.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nuaabbs.R;
import com.example.nuaabbs.common.MyApplication;
import com.example.nuaabbs.object.Comment;
import com.example.nuaabbs.util.HelperUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentPlusPlusAdapter extends RecyclerView.Adapter<CommentPlusPlusAdapter.ViewHolder> {

    private Context mContext;
    private List<Comment> commentList;


    public CommentPlusPlusAdapter(Context context, List<Comment> commentList){
        this.mContext = context;
        this.commentList = commentList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View commentView;

        CircleImageView head;
        TextView user;

        LinearLayout smallLayout;
        TextView replyLabel;
        TextView sendToUser;

        TextView info;
        TextView timeText;
        TextView replyTextBtn;


        public ViewHolder(View view){
            super(view);
            commentView = view;

            head = view.findViewById(R.id.comment_detail2_head);
            user = view.findViewById(R.id.comment_detail2_user);

            smallLayout = view.findViewById(R.id.small_layout);
            replyLabel = view.findViewById(R.id.comment_detail2_reply_label);
            sendToUser = view.findViewById(R.id.comment_detail2_send_user);

            info = view.findViewById(R.id.comment_detail2_info);
            timeText = view.findViewById(R.id.comment_detail2_time);
            replyTextBtn = view.findViewById(R.id.reply_text_btn2);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item_plus2, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.sendToUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.replyTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = commentList.get(position);

        //Glide.with(mContext).load(HelperUtil.getRandomHeadID()).into(holder.head);
        holder.user.setText(comment.getCommentUser());

        holder.info.setText(comment.getCommentInfo());
        holder.info.setVisibility(View.VISIBLE);

        holder.timeText.setText(comment.getTime());

        if(comment.getBelongCommentID() == comment.getFollowCommentID()){
            holder.smallLayout.setVisibility(View.GONE);
        }else{
            holder.sendToUser.setText(getSendToUserName(comment.getFollowCommentID()));
        }

        if(comment.getCommentUserID() == MyApplication.userInfo.getId()){
            holder.replyTextBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    private String getSendToUserName(int followCommentID){
        String name = "";
        for(Comment comment : commentList){
            if(comment.getCommentID() == followCommentID){
                name = comment.getCommentUser();
                break;
            }
        }
        return name;
    }

}

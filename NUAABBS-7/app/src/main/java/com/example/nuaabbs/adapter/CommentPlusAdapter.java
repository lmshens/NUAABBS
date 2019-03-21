package com.example.nuaabbs.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nuaabbs.R;
import com.example.nuaabbs.common.MyApplication;
import com.example.nuaabbs.object.Comment;
import com.example.nuaabbs.util.HelperUtil;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentPlusAdapter extends RecyclerView.Adapter<CommentPlusAdapter.ViewHolder> {

    private Context mContext;
    private List<Comment> allComments;
    private List<Comment> dependentCommentList = new ArrayList<>();


    public CommentPlusAdapter(Context context, List<Comment> commentList){
        this.mContext = context;
        this.allComments = commentList;

        selectDependentComment();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View commentView;

        CircleImageView head;
        TextView user;
        TextView info;
        TextView timeText;
        TextView replyTextBtn;

        RecyclerView sonCommentList;

        public ViewHolder(View view){
            super(view);
            commentView = view;

            head = view.findViewById(R.id.comment_detail_head);
            user = view.findViewById(R.id.comment_detail_user);
            info = view.findViewById(R.id.comment_detail_info);
            timeText = view.findViewById(R.id.comment_detail_time);
            replyTextBtn = view.findViewById(R.id.reply_text_btn);

            sonCommentList = view.findViewById(R.id.son_comment_list);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item_plus, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.user.setOnClickListener(new View.OnClickListener() {
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
        Comment comment = dependentCommentList.get(position);

        //Glide.with(mContext).load(HelperUtil.getRandomHeadID()).into(holder.head);
        holder.user.setText(comment.getCommentUser());
        holder.info.setText(comment.getCommentInfo());
        holder.timeText.setText(comment.getTime());

        if(comment.getCommentUserID() == MyApplication.userInfo.getId())
            holder.replyTextBtn.setVisibility(View.GONE);

        List<Comment> followCommentList = getFollowComments(comment.getCommentID());
        if(followCommentList.size() > 0){
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            holder.sonCommentList.setLayoutManager(layoutManager);
            holder.sonCommentList.setNestedScrollingEnabled(false);
            CommentPlusPlusAdapter adapter = new CommentPlusPlusAdapter(mContext, followCommentList);
            holder.sonCommentList.setAdapter(adapter);
            holder.sonCommentList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return dependentCommentList.size();
    }

    private void selectDependentComment(){
        for(Comment comment:allComments){
            if(comment.isDependent())
                dependentCommentList.add(comment);
        }
    }

    private List<Comment> getFollowComments(int followCommentID){
        List<Comment> list = new ArrayList<>();
        for(Comment comment: allComments){
            if(!comment.isDependent() && comment.getBelongCommentID() == followCommentID){
                list.add(comment);
            }
        }
        return list;
    }
}

package com.example.nuaabbs.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nuaabbs.R;
import com.example.nuaabbs.asyncNetTask.PostRelatedActionTask;
import com.example.nuaabbs.common.CommonCache;
import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.common.MyApplication;
import com.example.nuaabbs.object.Comment;
import com.example.nuaabbs.object.Post;
import com.example.nuaabbs.util.HelperUtil;
import com.example.nuaabbs.util.LogUtil;
import com.example.nuaabbs.util.PopUpEditWindow;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private Context mContext;
    private Post post;
    private PostAdapter.ViewHolder postViewHolder;

    PopUpEditWindow window;
    private int clickedCommentID = 0;
    private int belongCommentID = 0;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View commentView;
        TextView callbackView;

        TextView commentUser;
        TextView commentInfo;
        TextView responseLabel;
        TextView sendToUser;

        TextView commentInfoPlus;

        public ViewHolder(View view){
            super(view);
            commentView = view;
            callbackView = view.findViewById(R.id.callback_view);

            commentUser = view.findViewById(R.id.comment_user);
            commentInfo = view.findViewById(R.id.comment_info);
            responseLabel = view.findViewById(R.id.response_label);
            sendToUser = view.findViewById(R.id.send_to_user);

            commentInfoPlus = view.findViewById(R.id.comment_info_plus);
        }
    }

    public CommentAdapter(Context mContext, Post post, PostAdapter.ViewHolder postViewHolder){
        this.mContext = mContext;
        this.post = post;
        this.postViewHolder = postViewHolder;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.commentView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!MyApplication.loginState){
                    Toast.makeText(mContext, "你还没有登录，无法评论哦！", Toast.LENGTH_SHORT).show();
                    return;
                }

                Comment comment = post.getComments().get(holder.getAdapterPosition());
                clickedCommentID = comment.getCommentID();
                belongCommentID = comment.getBelongCommentID();

                int[] location = new int[2];
                v.getLocationOnScreen(location);
                int vBottomHeight = location[1]+v.getHeight();
                LogUtil.i("you click comment", "bottom height = "+vBottomHeight);


                final View view = LayoutInflater.from(mContext).inflate(R.layout.comment_popupwindow, null);
                window = new PopUpEditWindow(mContext, view, holder.callbackView,
                        "回复 " + comment.getCommentUser());
                window.show();

                if(CommonCache.CurrentActivity.getActivityNum() == Constant.MainActivityNum){
                    CommonCache.AdjustHeight.setAdjustParam(true, vBottomHeight);
                }
            }
        });

        holder.commentUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "你点击了 评论人", Toast.LENGTH_SHORT).show();
            }
        });

        holder.sendToUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "你点击了 被评论人", Toast.LENGTH_SHORT).show();
            }
        });

        holder.callbackView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Comment replyComment = CommonCache.NewComment.getNewComment();
                replyComment.setFollowPostID(post.getPostID());
                replyComment.setCommentInfo(window.getInputContent());
                replyComment.setDependent(false);
                replyComment.setFollowCommentID(clickedCommentID);
                replyComment.setBelongCommentID(belongCommentID);

                HelperUtil.InsertComment(replyComment, post.getComments());

                post.addCommentNum();
                post.calculateHotDegree();
                CommentAdapter.this.notifyDataSetChanged();
                postViewHolder.updateCommentNum(post.getCommentNum());
                String param = Constant.gson.toJson(replyComment);

                PostRelatedActionTask task = new PostRelatedActionTask(mContext);
                task.execute(Constant.REQCODE_COMMENT, param);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = post.getComments().get(position);
        if(comment.isDependent()){
            holder.commentUser.setText(comment.getCommentUser() + " : ");
        }else{
            holder.commentUser.setText(comment.getCommentUser());
            holder.responseLabel.setVisibility(View.VISIBLE);
            holder.sendToUser.setVisibility(View.VISIBLE);
            holder.sendToUser.setText(GetFollowCommentUser(comment.getFollowCommentID()) + " : ");
        }

        holder.commentInfo.setText(comment.getCommentInfo());
        /*
        final String info = comment.getCommentInfo();
        holder.commentInfo.setText(info);

        /*以下处理过程意在：当评论内容一行显示不完时，获取被省略的评论内容并显示在第二评论显示区，以获得更好的显示效果
        final TextView commentInfo = holder.commentInfo;
        final TextView commentInfoPlus = holder.commentInfoPlus;
        commentInfo.post(new Runnable() {
            @Override
            public void run() {
                //LogUtil.d("getEllipsisCount","start");
                int ellipsisCount = commentInfo.getLayout().getEllipsisCount(0);
                if(ellipsisCount > 0){
                    //LogUtil.d("ellipsisCount = ", ""+ellipsisCount);
                    int ellipsisIndex = info.length()-ellipsisCount+1;
                    commentInfo.setText(info.substring(0, ellipsisIndex));
                    commentInfoPlus.setText(info.substring(ellipsisIndex));
                    commentInfo.setVisibility(View.VISIBLE);
                    commentInfoPlus.setVisibility(View.VISIBLE);
                }else{
                    commentInfo.setVisibility(View.VISIBLE);
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return post.getComments().size();
    }

    private String GetFollowCommentUser(int followCommentID){
        for (Comment comment: post.getComments()) {
            if(comment.getCommentID() == followCommentID)
                return comment.getCommentUser();
        }
        return "";
    }
}

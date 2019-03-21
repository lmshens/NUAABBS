package com.example.nuaabbs.action;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nuaabbs.R;
import com.example.nuaabbs.adapter.CommentPlusAdapter;
import com.example.nuaabbs.common.CommonCache;
import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.object.Post;


import de.hdodenhof.circleimageview.CircleImageView;

public class PostContentActivity extends BaseActivity implements View.OnClickListener{

    Post post;

    CircleImageView headPortrait;
    TextView sender;
    TextView time;
    TextView label;
    TextView postInfo;

    ImageView views;
    TextView viewNum;
    ImageView thumb_up;
    TextView thumb_up_Num;
    ImageView comment;
    TextView commentNum;

    RecyclerView commentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_content);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        sender = findViewById(R.id.post_content_sender);
        time = findViewById(R.id.post_content_time);
        label = findViewById(R.id.post_content_label);
        headPortrait = findViewById(R.id.post_content_head);
        postInfo = findViewById(R.id.post_content_info);
        views = findViewById(R.id.post_content_views);
        viewNum = findViewById(R.id.post_content_view_num);
        thumb_up = findViewById(R.id.post_content_thumb_up);
        thumb_up_Num = findViewById(R.id.post_content_thumb_up_num);
        comment = findViewById(R.id.post_content_comment);
        commentNum = findViewById(R.id.post_content_comment_num);
        commentView = findViewById(R.id.post_content_comment_list);

        post = (Post)getIntent().getSerializableExtra("post_data");

        //Glide.with(this).load(HelperUtil.getRandomHeadID()).into(headPortrait);
        sender.setText(post.getPoster());
        time.setText(post.getDateStr());
        label.setText(post.getLabel());
        postInfo.setText(post.getPostContent());

        viewNum.setText(post.getViewNum()+"");
        thumb_up_Num.setText(post.getThumb_upNum()+"");
        commentNum.setText(post.getCommentNum()+"");

        if(post.getComments().size() > 0){
            commentView.setVisibility(View.VISIBLE);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            commentView.setLayoutManager(layoutManager);
            commentView.setNestedScrollingEnabled(false);
            CommentPlusAdapter adapter = new CommentPlusAdapter(this, post.getComments());
            commentView.setAdapter(adapter);
        }

        headPortrait.setOnClickListener(this);
        sender.setOnClickListener(this);
        postInfo.setOnClickListener(this);
        thumb_up.setOnClickListener(this);
        comment.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CommonCache.CurrentActivity.setCurrentActivity(Constant.PostContentActivityNum, this);
    }

    public static void actionStart(Context context, Post post){
        Intent intent = new Intent(context, PostContentActivity.class);
        intent.putExtra("post_data", post);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.post_content_head:
                break;
            case R.id.post_content_sender:
                break;
            case R.id.post_content_thumb_up:
                break;
            case R.id.post_content_comment:
                break;
            default:
                break;
        }
    }
}

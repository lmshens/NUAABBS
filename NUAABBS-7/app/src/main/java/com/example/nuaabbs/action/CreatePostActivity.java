package com.example.nuaabbs.action;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.nuaabbs.R;
import com.example.nuaabbs.common.CommonCache;
import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.common.MyApplication;
import com.example.nuaabbs.object.CommonRequest;
import com.example.nuaabbs.object.CommonResponse;
import com.example.nuaabbs.object.Post;
import com.example.nuaabbs.util.LogUtil;
import com.example.nuaabbs.util.OkHttpUtil;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class CreatePostActivity extends BaseActivity implements View.OnClickListener{
    EditText postContent;
    Post newPost;
    long lastRequestTime = 0l;

    ProgressBar progressBar;
    OkHttpUtil okHttpUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        progressBar = findViewById(R.id.create_post_bar);
        postContent = findViewById(R.id.create_post_content);
        findViewById(R.id.action_create).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CommonCache.CurrentActivity.setCurrentActivity(Constant.CreatePostActivityNum, this);
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context, CreatePostActivity.class);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_create:
                boolean timeAllow = false;
                if(lastRequestTime == 0)timeAllow = true;
                else{
                    if(System.currentTimeMillis() - lastRequestTime > 2000)
                        timeAllow = true;
                }
                if(timeAllow){
                    if(TextUtils.isEmpty(postContent.getText().toString())){
                        Toast.makeText(this, "帖子内容不能为空！", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    lastRequestTime = System.currentTimeMillis();
                    CreatePost();
                }
                break;
            default:
        }
    }

    private void CreatePost(){
        newPost = new Post(true);
        String param = MyApplication.userInfo.getUserName() + ":&:" + MyApplication.userInfo.getId();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tmp = dateFormat.format(new Date(System.currentTimeMillis()));
        newPost.setDateStr(tmp);
        param += ":&:" + tmp;

        int labelID = ((RadioGroup)findViewById(R.id.create_post_label)).getCheckedRadioButtonId();
        if(labelID == R.id.label_life){
            param += ":&:" + Constant.LIFE_LABEL;
            newPost.setLabel(Constant.LIFE_LABEL);
        }
        else{
            param += ":&:" + Constant.STUDY_LABEL;
            newPost.setLabel(Constant.STUDY_LABEL);
        }

        tmp = postContent.getText().toString();
        newPost.setPostContent(tmp);
        param += ":&:" + tmp;
        LogUtil.d("createNewPost", param);

        executeCreatePostTask(param);
    }

    private void executeCreatePostTask(final String param){
        progressBar.setVisibility(View.VISIBLE);
        if(okHttpUtil == null)
            okHttpUtil = OkHttpUtil.GetOkHttpUtil();

        new Thread(new Runnable() {
            @Override
            public void run() {
                CommonRequest commonRequest = new CommonRequest();
                commonRequest.setParam1(param);
                okHttpUtil.executeTask(commonRequest, Constant.URL_CreateNewPost);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CommonResponse commonResponse = okHttpUtil.getCommonResponse();
                        String resCode = commonResponse.getResCode();
                        if(resCode.equals(Constant.RESCODE_SUCCESS)){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(CreatePostActivity.this, commonResponse.getResMsg(), Toast.LENGTH_SHORT).show();
                            CreatePostActivity.this.CreateSuccess(Integer.parseInt(commonResponse.getResParam()));
                        }else {
                            progressBar.setVisibility(View.INVISIBLE);
                            okHttpUtil.stdDealResult("CreateNewPostTask");
                        }
                    }
                });
            }
        }).start();
    }

    public void CreateSuccess(int postID){
        newPost.setPostID(postID);
        MyApplication.postListManager.AddNewCreatePost(newPost);
        this.finish();
    }
}

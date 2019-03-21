package com.example.nuaabbs.action;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nuaabbs.R;
import com.example.nuaabbs.common.CommonCache;
import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.common.MyApplication;
import com.example.nuaabbs.util.LogUtil;

public class UserDetailActivity extends BaseActivity implements View.OnClickListener{

    TextView userName;
    TextView userSchoolID;
    TextView userSex;
    TextView userBirthday;
    TextView userIdiograph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        userName = findViewById(R.id.detail_user_name);
        userSchoolID = findViewById(R.id.detail_user_schoolID);
        userSex = findViewById(R.id.detail_user_sex);
        userBirthday = findViewById(R.id.detail_user_birthday);
        userIdiograph = findViewById(R.id.detail_user_idiograph);

        findViewById(R.id.change_user_detail).setOnClickListener(this);
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context, UserDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        userName.setText(MyApplication.userInfo.getUserName());
        userSchoolID.setText(MyApplication.userInfo.getSchoolID());
        userSex.setText(MyApplication.userInfo.getSex());
        if(MyApplication.userInfo.isBirthdayEffect())
            userBirthday.setText(MyApplication.userInfo.getBirthday().toString());
        userIdiograph.setText(MyApplication.userInfo.getIdioGraph());

        CommonCache.CurrentActivity.setCurrentActivity(Constant.UserDetailActivityNum, this);
        super.onResume();
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
            case R.id.change_user_detail:
                ChangeUserDetailActivity.actionStart(this);
                break;
            default:
        }
    }
}

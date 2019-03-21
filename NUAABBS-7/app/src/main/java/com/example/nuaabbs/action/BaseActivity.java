package com.example.nuaabbs.action;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.nuaabbs.util.ActivityCollector;
import com.example.nuaabbs.util.LogUtil;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("BaseActivity create", getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        LogUtil.d("BaseActivity finish", getClass().getSimpleName());
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}

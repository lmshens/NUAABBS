package com.example.nuaabbs.action;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.nuaabbs.R;
import com.example.nuaabbs.adapter.PostAdapter;
import com.example.nuaabbs.common.CommonCache;
import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.common.MyApplication;
import com.example.nuaabbs.common.MyHandle;

public class MyCollectActivity extends BaseActivity {

    PostAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        swipeRefresh = findViewById(R.id.my_collect_swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MyApplication.postListManager.refreshMyPostList();
            }
        });

        MyApplication.myHandle.setMyCollectUpdateLister(new MyHandle.HandleListener() {
            @Override
            public void OnHandleMsg(int msgArg) {
                if(msgArg == MyHandle.SUCCESS){
                    if(adapter != null)
                        adapter.notifyDataSetChanged();
                    closeRefreshBar(true);
                }else {
                    closeRefreshBar(false);
                }
            }
        });

        RecyclerView myPostRecyclerView = findViewById(R.id.my_collect_RecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        myPostRecyclerView.setLayoutManager(layoutManager);
        adapter = new PostAdapter(this,
                MyApplication.postListManager.getMyCollectList(), true, true, false);
        myPostRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CommonCache.CurrentActivity.setCurrentActivity(Constant.MyCollectActivityNum, this);
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context, MyCollectActivity.class);
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

    public void closeRefreshBar(boolean showToast){
        if(swipeRefresh != null && swipeRefresh.isRefreshing()){
            swipeRefresh.setRefreshing(false);
            if(showToast)
                Toast.makeText(this, "更新成功!", Toast.LENGTH_SHORT).show();
        }
    }
}

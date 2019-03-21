package com.example.nuaabbs.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nuaabbs.R;
import com.example.nuaabbs.adapter.PostAdapter;
import com.example.nuaabbs.common.MyApplication;
import com.example.nuaabbs.common.MyHandle;
import com.example.nuaabbs.util.LogUtil;

public class StudyPostFragment extends BaseFragment {

    private PostAdapter postAdapter;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;

    public StudyPostFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
        closeRefreshBar(false);
        if(MyApplication.postListManager.isStudyPostListChanged()){
            if(this.postAdapter != null)
                this.postAdapter.notifyDataSetChanged();
            MyApplication.postListManager.setStudyPostListChanged(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study_post, container, false);

        swipeRefresh = view.findViewById(R.id.study_swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MyApplication.postListManager.refreshStudyPostList();
            }
        });

        if(MyApplication.postListManager.getStudyPostList().isEmpty()){
            MyApplication.postListManager.refreshStudyPostList();
            swipeRefresh.setRefreshing(true);
        }

        MyApplication.myHandle.setStudyPostFragmentUpdateListener(new MyHandle.HandleListener() {
            @Override
            public void OnHandleMsg(int msgArg) {
                LogUtil.d("begin handle study update msg");
                if(msgArg == MyHandle.SUCCESS){
                    if(StudyPostFragment.this.postAdapter != null)
                        StudyPostFragment.this.postAdapter.notifyDataSetChanged();
                    closeRefreshBar(true);
                }else{
                    StudyPostFragment.this.closeRefreshBar(false);
                }
            }
        });

        recyclerView = view.findViewById(R.id.study_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        postAdapter = new PostAdapter(getContext(),
                MyApplication.postListManager.getStudyPostList(), false);
        recyclerView.setAdapter(postAdapter);
        return view;
    }

    public void closeRefreshBar(boolean showToast){
        if(swipeRefresh != null && swipeRefresh.isRefreshing()){
            swipeRefresh.setRefreshing(false);
            if(showToast)
                Toast.makeText(getActivity(), "更新成功!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void SmoothRecycle(int distance){
        if(recyclerView != null){
            recyclerView.smoothScrollBy(distance, distance);
        }
    }
}

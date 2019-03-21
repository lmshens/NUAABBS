package com.example.nuaabbs.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.nuaabbs.fragment.BaseFragment;
import com.example.nuaabbs.util.LogUtil;

import java.util.List;

public class MyPageFragmentAdapter extends FragmentPagerAdapter {
    List<BaseFragment> list;
    String[] titles;

    public MyPageFragmentAdapter(FragmentManager fm, List<BaseFragment> list, String[] titles) {
        super(fm);
        this.list = list;
        this.titles = titles; // 把tab名字通过数组传进来
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String tmp = titles[position];
        //LogUtil.d("MainActivity TabLayout", position + "  " + tmp);
        return tmp; // 在这里返回每个tab的名字
    }
}

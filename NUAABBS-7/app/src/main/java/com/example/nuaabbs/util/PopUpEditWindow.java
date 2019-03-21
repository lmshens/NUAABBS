package com.example.nuaabbs.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nuaabbs.MainActivity;
import com.example.nuaabbs.R;
import com.example.nuaabbs.action.BaseActivity;
import com.example.nuaabbs.common.CommonCache;
import com.example.nuaabbs.common.Constant;


public class PopUpEditWindow extends PopupWindow{

    private Context mContext;
    private View callBackView;
    private String param;

    private View view;
    private EditText inputComment;
    private String inputContent = "$#!cancel!#$";

    public PopUpEditWindow(Context context, View view, View callBackView, String param){
        super(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);

        this.mContext = context;
        this.callBackView = callBackView;
        this.param = param;
        this.view = view;
    }

    public void show(){
        PrepareWindow();
    }

    public String getInputContent(){
        return inputContent;
    }

    private void PrepareWindow(){

        setFocusable(true);   //设置popupWindow能够获得焦点(默认是不能的)，从而让EditText能够获得焦点
        //设置可以点击popupWindow的外面, 点击外面时关闭本窗体, android 6.0 之前版本需下面一行代码配合才能生效
        setOutsideTouchable(true);
        //设置让popupWindow能够相应返回键，当按下返回键将关闭当前活动
        setBackgroundDrawable(new BitmapDrawable());

        //popupWindow设置了可点击窗体外部之后就已经能响应点击外部关闭本窗体了，下面的代码目的是
        //让用户点击两次返回键才关闭本窗体
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                    dismiss();
                return false;
            }
        });

        inputComment = view.findViewById(R.id.et_discuss);
        inputComment.setHint(param);

        TextView btn_submit = view.findViewById(R.id.tv_confirm);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentInfo = inputComment.getText().toString().trim();
                if (!commentInfo.isEmpty()) {
                    inputContent = commentInfo;
                    dismiss();
                    callBackView.performClick();
                }else{
                    Toast.makeText(mContext, "空评论！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(CommonCache.CurrentActivity.getActivityNum() == Constant.MainActivityNum)
                    ((MainActivity)mContext).ShowFAB();
                InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(((BaseActivity)mContext).getCurrentFocus().getWindowToken(), 0);
            }
        });

        //隐藏主活动中的悬浮按钮
        if(CommonCache.CurrentActivity.getActivityNum() == Constant.MainActivityNum)
            ((MainActivity)mContext).HideFAB();
        //弹出系统软键盘
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }
}

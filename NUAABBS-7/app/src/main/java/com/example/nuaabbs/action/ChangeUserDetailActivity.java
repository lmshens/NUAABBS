package com.example.nuaabbs.action;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nuaabbs.R;
import com.example.nuaabbs.common.CommonCache;
import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.common.MyApplication;
import com.example.nuaabbs.common.MyHandle;
import com.example.nuaabbs.object.CommonRequest;
import com.example.nuaabbs.object.CommonResponse;
import com.example.nuaabbs.util.LogUtil;
import com.example.nuaabbs.util.OkHttpUtil;

import java.sql.Date;


public class ChangeUserDetailActivity extends BaseActivity
        implements View.OnClickListener, View.OnFocusChangeListener{

    EditText userName;
    EditText schoolID;
    EditText sex;
    EditText birthday;
    EditText idiograph;
    Date nowDate;
    DatePickerDialog datePickerDialog;

    ProgressBar progressBar;
    OkHttpUtil okHttpUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_detail);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        userName = findViewById(R.id.change_user_name);
        userName.setText(MyApplication.userInfo.getUserName());

        schoolID = findViewById(R.id.change_user_schoolID);
        String tmp = MyApplication.userInfo.getSchoolID();
        if(tmp != null) schoolID.setText(tmp);

        sex = findViewById(R.id.change_user_sex);
        sex.setText(MyApplication.userInfo.getSex());

        birthday = findViewById(R.id.change_user_birthday);
        if(MyApplication.userInfo.isBirthdayEffect()){
            nowDate = new Date(MyApplication.userInfo.getBirthday().getTime());
            birthday.setText(nowDate.toString());
        }

        idiograph = findViewById(R.id.change_user_idiograph);
        idiograph.setText(MyApplication.userInfo.getIdioGraph());

        findViewById(R.id.push_change).setOnClickListener(this);

        birthday.setClickable(true);
        birthday.setOnClickListener(this);
        birthday.setOnFocusChangeListener(this);
        birthday.setInputType(InputType.TYPE_NULL);

        progressBar = findViewById(R.id.update_process_bar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CommonCache.CurrentActivity.setCurrentActivity(Constant.ChangeUserDetailActivityNum, this);
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context, ChangeUserDetailActivity.class);
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
            case R.id.change_user_birthday:
                ShowDatePickerDialog();
                break;
            case R.id.push_change:
                if(!isChanged()){
                    Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
                    this.finish();
                }else executeUpdateUserDetailTask(GetNewUserDetail());
                break;
            default:
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            ShowDatePickerDialog();
        }
    }

    private void ShowDatePickerDialog(){
        Date tmpDate;
        if(nowDate != null) tmpDate = nowDate;
        else tmpDate = new Date(Constant.dataDefault);

        if(datePickerDialog == null){
            datePickerDialog = new DatePickerDialog(ChangeUserDetailActivity.this,
                    AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    if(nowDate == null) nowDate = new Date(0l);
                    nowDate.setYear(year - 1900);
                    nowDate.setMonth(month);
                    nowDate.setDate(dayOfMonth);
                    birthday.setText(nowDate.toString());
                }
            }, tmpDate.getYear()+1900, tmpDate.getMonth(), tmpDate.getDate());
        }

        datePickerDialog.show();
    }

    private boolean isChanged(){
        if(!userName.getText().toString().equals(MyApplication.userInfo.getUserName()))
            return true;
        if(!schoolID.getText().toString().equals(MyApplication.userInfo.getSchoolID()))
            return true;
        if(!sex.getText().toString().equals(MyApplication.userInfo.getSex()))
            return true;
        if(nowDate != null && !nowDate.equals(MyApplication.userInfo.getBirthday()))
            return true;
        if(!idiograph.getText().toString().equals(MyApplication.userInfo.getIdioGraph()))
            return true;

        return false;
    }

    private String GetNewUserDetail(){
        String newUserDetail = MyApplication.userInfo.getId()
                + ":&:" + userName.getText().toString()
                + ":&:" + schoolID.getText().toString()
                + ":&:" + sex.getText().toString();
        if(nowDate != null)
            newUserDetail += ":&:" + nowDate.toString();
        else newUserDetail += ":&:" + "null";

        newUserDetail += ":&:" + idiograph.getText().toString();
        LogUtil.d("changeUserDetail", newUserDetail);

        return newUserDetail;
    }

    private void executeUpdateUserDetailTask(final String param){
        progressBar.setVisibility(View.VISIBLE);
        if(okHttpUtil == null)
            okHttpUtil = OkHttpUtil.GetOkHttpUtil();

        new Thread(new Runnable() {
            @Override
            public void run() {
                CommonRequest commonRequest = new CommonRequest();
                commonRequest.setParam1(param);
                okHttpUtil.executeTask(commonRequest, Constant.URL_UpdateUserDetail);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CommonResponse commonResponse = okHttpUtil.getCommonResponse();
                        if(commonResponse.getResCode().equals(Constant.RESCODE_SUCCESS)){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ChangeUserDetailActivity.this, commonResponse.getResMsg(), Toast.LENGTH_SHORT).show();
                            ChangeUserDetailActivity.this.UpdateSuccess();
                        }else {
                            progressBar.setVisibility(View.INVISIBLE);
                            okHttpUtil.stdDealResult("UpdateUserDetailTask");
                        }
                    }
                });
            }
        }).start();
    }

    public void UpdateSuccess(){
        MyApplication.userInfo.setUserName(userName.getText().toString());
        MyApplication.userInfo.setSchoolID(schoolID.getText().toString());
        MyApplication.userInfo.setSex(sex.getText().toString());
        MyApplication.userInfo.setBirthday(nowDate);
        MyApplication.userInfo.setIdioGraph(idiograph.getText().toString());

        Message message = new Message();
        message.what = MyHandle.UserInfoChanged;
        message.arg1 = MyHandle.SUCCESS;
        MyApplication.myHandle.sendMessage(message);

        this.finish();
    }
}

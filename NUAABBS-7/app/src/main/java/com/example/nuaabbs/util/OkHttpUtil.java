package com.example.nuaabbs.util;

import android.widget.Toast;

import com.example.nuaabbs.action.BaseActivity;
import com.example.nuaabbs.common.CommonCache;
import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.common.MyApplication;
import com.example.nuaabbs.object.CommonRequest;
import com.example.nuaabbs.object.CommonResponse;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {

    private OkHttpClient client;
    private CommonResponse commonResponse;

    private OkHttpUtil(){
        if(client != null) return;

        client = new OkHttpClient();
        client.newBuilder()
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);
    }

    public static OkHttpUtil GetOkHttpUtil(){
        return new OkHttpUtil();
    }

    public CommonResponse executeTask(CommonRequest commonRequest, String url){
        if(!MyApplication.isNetworkAvailable()){
            commonResponse = new CommonResponse(Constant.RESCODE_NET_FAIL,Constant.NET_UNABLE,"");
            return commonResponse;
        }

        try{
            String json = Constant.gson.toJson(commonRequest);

            RequestBody requestBody = FormBody.create(MediaType.parse(Constant.CONNECT_CHARSET), json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody).build();
            Response response = client.newCall(request).execute();

            if(response.isSuccessful()){
                String responseData = response.body().string();
                commonResponse = Constant.gson.fromJson(responseData, CommonResponse.class);
                if(response.body() != null) response.body().close();
            }else{
                commonResponse = new CommonResponse(Constant.RESCODE_SYSTEM_ERROR,Constant.SYSTEM_ERROR,"");
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.e("OkHttpUtil", e.toString());

            if (e.getCause().equals(SocketTimeoutException.class)){
                commonResponse = new CommonResponse(Constant.RESCODE_NET_TIMEOUT,"","");
            }else{
                commonResponse = new CommonResponse(Constant.RESCODE_SYSTEM_ERROR,Constant.SYSTEM_ERROR,"");
            }
        }

        return commonResponse;
    }

    public CommonResponse getCommonResponse(){
        return commonResponse;
    }

    public void stdDealResult(String tag){

        final String logTag = tag;
        ((BaseActivity)CommonCache.CurrentActivity.getActivityContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String resCode = commonResponse.getResCode();
                if(resCode.equals(Constant.RESCODE_NET_FAIL)){
                    Toast.makeText(CommonCache.CurrentActivity.getActivityContext(), Constant.NET_UNABLE, Toast.LENGTH_SHORT).show();
                }else if(resCode.equals(Constant.RESCODE_NET_TIMEOUT)){
                    Toast.makeText(CommonCache.CurrentActivity.getActivityContext(), Constant.NET_TIMEOUT, Toast.LENGTH_SHORT).show();
                }else{
                    LogUtil.e(logTag + Constant.OKHTTP_TAG, Constant.SYSTEM_ERROR);
                    Toast.makeText(CommonCache.CurrentActivity.getActivityContext(), Constant.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    
}

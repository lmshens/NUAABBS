package com.example.nuaabbs.asyncNetTask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.nuaabbs.common.CommonCache;
import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.common.MyApplication;
import com.example.nuaabbs.object.CommonRequest;
import com.example.nuaabbs.object.CommonResponse;
import com.example.nuaabbs.util.OkHttpUtil;

public class PostRelatedActionTask extends AsyncTask<String, String, Boolean> {
    private String reqCode;
    private CommonResponse commonResponse;
    OkHttpUtil okHttpUtil = OkHttpUtil.GetOkHttpUtil();

    public PostRelatedActionTask(Context context/*, PostAdapter adapter*/){
        //this.adapter = adapter;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try{
            CommonRequest commonRequest = new CommonRequest();
            commonRequest.setRequestCode(params[0]);
            commonRequest.setParam1(params[1]);
            commonRequest.setParam2(MyApplication.userInfo.getId()+":&:"+MyApplication.userInfo.getUserName());
            reqCode = params[0];

            commonResponse = okHttpUtil.executeTask(commonRequest, Constant.URL_PostRelatedAction);

            publishProgress(params[0], params[1]);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        String resCode = commonResponse.getResCode();
        if(resCode.equals(Constant.RESCODE_SUCCESS)){
            if(reqCode.equals(Constant.REQCODE_COMMENT)){
                int id = Integer.parseInt(commonResponse.getResParam());
                CommonCache.NewComment.setNewCommentID(id);
            }
        }else{
            okHttpUtil.stdDealResult("PostRelatedAction");
        }
    }
}

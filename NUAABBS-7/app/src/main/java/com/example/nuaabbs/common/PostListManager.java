package com.example.nuaabbs.common;

import android.os.Message;

import com.example.nuaabbs.MainActivity;
import com.example.nuaabbs.action.BaseActivity;
import com.example.nuaabbs.action.MyPostActivity;
import com.example.nuaabbs.action.PersonalPageActivity;
import com.example.nuaabbs.object.CommonRequest;
import com.example.nuaabbs.object.CommonResponse;
import com.example.nuaabbs.object.Post;
import com.example.nuaabbs.util.LogUtil;
import com.example.nuaabbs.util.OkHttpUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class PostListManager {

    private boolean myPostListChanged;
    private boolean myCollectListChanged;
    private boolean hotPostListChanged;
    private boolean studyPostListChanged;
    private boolean lifePostListChanged;

    private List<Post> allPosts;
    private List<Post> myPostList;
    private List<Post> myCollectList;
    private List<Post> hotPostList;
    private List<Post> studyPostList;
    private List<Post> lifePostList;

    private String requestPostLabel;
    private List<Integer> requestTask;

    public PostListManager(){
        allPosts = new ArrayList<>();
        myPostList = new ArrayList<>();
        myCollectList = new ArrayList<>();
        hotPostList = new ArrayList<>();
        studyPostList = new ArrayList<>();
        lifePostList = new ArrayList<>();

        requestTask = new ArrayList<>();

        myPostListChanged = false;
        myCollectListChanged = false;
        hotPostListChanged = false;
        studyPostListChanged = false;
        lifePostListChanged = false;

        requestPostLabel = "";
    }

    public void refreshMyPostList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtil okHttpUtil = OkHttpUtil.GetOkHttpUtil();

                try{
                    CommonRequest commonRequest = new CommonRequest();
                    commonRequest.setParam1(MyApplication.userInfo.getId()+"");
                    commonRequest.setParam2(MyApplication.userInfo.getUserName());

                    CommonResponse commonResponse = okHttpUtil.executeTask(commonRequest, Constant.URL_RequestMyPost);

                    String resCode = commonResponse.getResCode();
                    if(resCode.equals(Constant.RESCODE_SUCCESS)){
                        List<Post> newPostList = Constant.gson.fromJson(commonResponse.getResParam(),
                                new TypeToken<List<Post>>(){}.getType());
                        MyApplication.postListManager.DealRefreshMyPostList(newPostList, true);
                    }else {
                        MyApplication.postListManager.DealRefreshMyPostList(null, false);
                        okHttpUtil.stdDealResult("RequestMyPost");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void refreshHotPostList(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpUtil okHttpUtil = OkHttpUtil.GetOkHttpUtil();
                try{
                    CommonRequest commonRequest = new CommonRequest();
                    if(MyApplication.loginState){

                        commonRequest.setParam1(MyApplication.userInfo.getId()+"");
                        commonRequest.setParam2(MyApplication.userInfo.getUserName());
                    }
                    CommonResponse commonResponse = okHttpUtil.executeTask(commonRequest, Constant.URL_RequestHotPost);

                    String resCode = commonResponse.getResCode();
                    if(resCode.equals(Constant.RESCODE_SUCCESS)){
                        List<Post> newPostList = Constant.gson.fromJson(commonResponse.getResParam(),
                                new TypeToken<List<Post>>(){}.getType());
                        MyApplication.postListManager.DealRefreshHotPostList(newPostList, true);
                    }else {
                        MyApplication.postListManager.DealRefreshHotPostList(null, false);
                        okHttpUtil.stdDealResult("RequestHotPost");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void refreshLifePostList(){
        requestPostLabel = Constant.REQCODE_LIFE_Post;
        refreshLabelPostList();
    }

    public void refreshStudyPostList(){

        requestPostLabel = Constant.REQCODE_STUDY_Post;
        refreshLabelPostList();
    }

    private void refreshLabelPostList(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtil okHttpUtil = OkHttpUtil.GetOkHttpUtil();
                try{
                    CommonRequest commonRequest = new CommonRequest();
                    commonRequest.setRequestCode(requestPostLabel);
                    if(MyApplication.loginState){

                        commonRequest.setParam1(MyApplication.userInfo.getId()+"");
                        commonRequest.setParam2(MyApplication.userInfo.getUserName());
                    }

                    CommonResponse commonResponse = okHttpUtil.executeTask(commonRequest, Constant.URL_RequestLabelPost);

                    String resCode = commonResponse.getResCode();
                    if(resCode.equals(Constant.RESCODE_SUCCESS)){
                        List<Post> newPostList = Constant.gson.fromJson(commonResponse.getResParam(),
                                new TypeToken<List<Post>>(){}.getType());
                        MyApplication.postListManager.DealRefreshLabelPostList(newPostList, true);
                    }else {
                        MyApplication.postListManager.DealRefreshLabelPostList(null, false);
                        okHttpUtil.stdDealResult("RequestLabelPost");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void DealRefreshHotPostList(List<Post> newPosts, boolean successFlag){
        if(successFlag){
            this.hotPostListChanged = true;
            // TODO
            for(Post newPost : newPosts){
                deletePost(newPost.getPostID(), allPosts);
                allPosts.add(newPost);
                deletePost(newPost.getPostID(), hotPostList);
                InsertPostFromHotDegree(newPost, hotPostList);
            }
        }else{
            LogUtil.e("System Error","Request hot posts fail");
        }

        Message message = new Message();
        message.what = MyHandle.HotPostListUpdate;
        message.arg1 = (successFlag)?MyHandle.SUCCESS:MyHandle.FAIL;
        MyApplication.myHandle.sendMessage(message);
    }

    public void DealRefreshMyPostList(List<Post> newPosts, boolean successFlag){
        if(successFlag){
            this.myPostListChanged = true;
            //TODO
            for(Post newPost : newPosts){
                deletePost(newPost.getPostID(), allPosts);
                allPosts.add(newPost);
                deletePost(newPost.getPostID(), myPostList);
                InsertPostFromTime(newPost, myPostList);
            }
        }else{
            LogUtil.e("System Error","Request my posts fail");
        }

        Message message = new Message();
        message.what = MyHandle.MyPostListUpdate;
        message.arg1 = (successFlag)?MyHandle.SUCCESS:MyHandle.FAIL;
        MyApplication.myHandle.sendMessage(message);
    }

    public void DealRefreshLabelPostList(List<Post> newPosts, boolean successFlag){
        if(successFlag){
            if(requestPostLabel.equals(Constant.LIFE_LABEL)){
                this.lifePostListChanged = true;
                //TODO
                for(Post newPost : newPosts){
                    deletePost(newPost.getPostID(), allPosts);
                    allPosts.add(newPost);
                    deletePost(newPost.getPostID(), lifePostList);
                    InsertPostFromTime(newPost, lifePostList);
                }
            }else if(requestPostLabel.equals(Constant.STUDY_LABEL)){
                this.studyPostListChanged = true;
                //TODO
                for(Post newPost : newPosts){
                    deletePost(newPost.getPostID(), allPosts);
                    allPosts.add(newPost);
                    deletePost(newPost.getPostID(), studyPostList);
                    InsertPostFromTime(newPost, studyPostList);
                }
            }
        }else{
            LogUtil.e("System Error","Request label posts fail");
        }

        Message message = new Message();
        if(requestPostLabel.equals(Constant.LIFE_LABEL))
            message.what = MyHandle.LifePostListUpdate;
        else message.what = MyHandle.StudyPostListUpdate;
        message.arg1 = (successFlag)?MyHandle.SUCCESS:MyHandle.FAIL;
        MyApplication.myHandle.sendMessage(message);
    }

    public void AddNewCreatePost(Post newPost) {
        allPosts.add(newPost);
        myPostList.add(0, newPost);
        myPostListChanged = true;

        InsertPostFromHotDegree(newPost, hotPostList);
        hotPostListChanged = true;

        if (newPost.getLabel().equals(Constant.LIFE_LABEL)) {
            lifePostList.add(0, newPost);
            lifePostListChanged = true;
        } else if (newPost.getLabel().equals(Constant.STUDY_LABEL)) {
            studyPostList.add(0, newPost);
            studyPostListChanged = true;
        }
    }

    // if have then delete
    private void deletePost(int postID, List<Post> postList){
        int index = 0;
        for(Post post:postList){
            if(post.getPostID() == postID){
                postList.remove(index);
                return ;
            }
            ++index;
        }
    }

    private void InsertPostFromHotDegree(Post newPost, List<Post> postList){
        int newPostHotDegree = newPost.getHotDegree();
        int index = 0;
        for(Post post: postList){
            if(newPostHotDegree > post.getHotDegree()){
                postList.add(index, newPost);
                return;
            }
            ++index;
        }
        postList.add(newPost);
    }

    private void InsertPostFromTime(Post newPost, List<Post> postList){
        int newPostID = newPost.getPostID();
        int index = 0;
        for(Post post: postList){
            if(newPostID > post.getPostID()){
                postList.add(index, newPost);
                return;
            }
            ++index;
        }
        postList.add(newPost);
    }

    public boolean isMyPostListChanged() {
        return myPostListChanged;
    }

    public void setMyPostListChanged(boolean myPostListChanged) {
        this.myPostListChanged = myPostListChanged;
    }

    public boolean isMyCollectListChanged() {
        return myCollectListChanged;
    }

    public void setMyCollectListChanged(boolean myCollectListChanged) {
        this.myCollectListChanged = myCollectListChanged;
    }

    public boolean isHotPostListChanged() {
        return hotPostListChanged;
    }

    public void setHotPostListChanged(boolean hotPostListChanged) {
        this.hotPostListChanged = hotPostListChanged;
    }

    public boolean isStudyPostListChanged() {
        return studyPostListChanged;
    }

    public void setStudyPostListChanged(boolean studyPostListChanged) {
        this.studyPostListChanged = studyPostListChanged;
    }

    public boolean isLifePostListChanged() {
        return lifePostListChanged;
    }

    public void setLifePostListChanged(boolean lifePostListChanged) {
        this.lifePostListChanged = lifePostListChanged;
    }

    public void ClearMyPostsWhenLogout(){
        myPostList.clear();
    }

    public List<Post> getMyPostList() {
        return myPostList;
    }

    public void setMyPostList(List<Post> myPostList) {
        this.myPostList = myPostList;
    }

    public void ClearMyCollectWhenLogout(){
        myCollectList.clear();
    }

    public List<Post> getMyCollectList() {
        return myCollectList;
    }

    public void setMyCollectList(List<Post> myCollectList) {
        this.myCollectList = myCollectList;
    }

    public List<Post> getHotPostList() {
        return hotPostList;
    }

    public void setHotPostList(List<Post> hotPostList) {
        this.hotPostList = hotPostList;
    }

    public List<Post> getStudyPostList() {
        return studyPostList;
    }

    public void setStudyPostList(List<Post> studyPostList) {
        this.studyPostList = studyPostList;
    }

    public List<Post> getLifePostList() {
        return lifePostList;
    }

    public void setLifePostList(List<Post> lifePostList) {
        this.lifePostList = lifePostList;
    }
}

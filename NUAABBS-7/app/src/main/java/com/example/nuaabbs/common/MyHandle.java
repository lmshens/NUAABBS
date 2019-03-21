package com.example.nuaabbs.common;

import android.os.Handler;
import android.os.Message;

public class MyHandle extends Handler{

    public final static  int HotPostListUpdate = 0;
    public final static int LifePostListUpdate = 1;
    public final static int StudyPostListUpdate = 2;
    public final static int MyPostListUpdate = 3;
    public final static int UserInfoChanged = 4;
    public final static int MyCollectListUpdate = 5;

    public static int SUCCESS = 1;
    public static int FAIL = 2;

    public interface HandleListener{
        void OnHandleMsg(int msgArg);
    }

    private HandleListener hotPostFragmentUpdateListener;
    private HandleListener lifePostFragmentUpdateListener;
    private HandleListener studyPostFragmentUpdateListener;
    private HandleListener myPostUpdateListener;
    private HandleListener personalPageListener;
    private HandleListener userInfoChangedListerForPersonal;
    private HandleListener myCollectUpdateLister;

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case HotPostListUpdate:
                if(hotPostFragmentUpdateListener != null)
                    hotPostFragmentUpdateListener.OnHandleMsg(msg.arg1);
                break;
            case LifePostListUpdate:
                if(lifePostFragmentUpdateListener != null)
                    lifePostFragmentUpdateListener.OnHandleMsg(msg.arg1);
                break;
            case StudyPostListUpdate:
                if(studyPostFragmentUpdateListener != null)
                    studyPostFragmentUpdateListener.OnHandleMsg(msg.arg1);
                break;
            case MyPostListUpdate:
                if(myPostUpdateListener != null)
                    myPostUpdateListener.OnHandleMsg(msg.arg1);
                if(personalPageListener != null)
                    personalPageListener.OnHandleMsg(msg.arg1);
                break;
            case UserInfoChanged:
                if(userInfoChangedListerForPersonal != null)
                    userInfoChangedListerForPersonal.OnHandleMsg(msg.arg1);
            case MyCollectListUpdate:
                if(myCollectUpdateLister != null)
                    myCollectUpdateLister.OnHandleMsg(msg.arg1);
                break;
            default:
                super.handleMessage(msg);
        }
    }

    public void setHotPostFragmentUpdateListener(HandleListener hotPostListUpdateListener) {
        this.hotPostFragmentUpdateListener = hotPostListUpdateListener;
    }

    public void setLifePostFragmentUpdateListener(HandleListener lifePostListUpdateListener) {
        this.lifePostFragmentUpdateListener = lifePostListUpdateListener;
    }

    public void setStudyPostFragmentUpdateListener(HandleListener studyPostListUpdateListener) {
        this.studyPostFragmentUpdateListener = studyPostListUpdateListener;
    }

    public void setMyPostUpdateListener(HandleListener myPostListUpdateListener) {
        this.myPostUpdateListener = myPostListUpdateListener;
    }

    public void setPersonalPageListener(HandleListener personalPageListener) {
        this.personalPageListener = personalPageListener;
    }

    public void setUserInfoChangedListerForPersonal(HandleListener userInfoChangedListerForPersonal) {
        this.userInfoChangedListerForPersonal = userInfoChangedListerForPersonal;
    }

    public void setMyCollectUpdateLister(HandleListener myCollectUpdateLister) {
        this.myCollectUpdateLister = myCollectUpdateLister;
    }
}

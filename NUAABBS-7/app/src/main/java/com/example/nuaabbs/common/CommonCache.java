package com.example.nuaabbs.common;

import android.content.Context;

import com.example.nuaabbs.object.Comment;
import com.example.nuaabbs.util.LogUtil;

public class CommonCache {

    public static class NewComment{
        private static Comment newComment;

        public static Comment getNewComment(){
            newComment = new Comment();
            return newComment;
        }

        public static void setNewCommentID(int id){
            newComment.setCommentID(id);
            LogUtil.d("newComment.id = ", ""+id);
            LogUtil.d("newComment.info = ", newComment.getCommentInfo());
        }
    }

    public static class CurrentActivity{
        static int activityNum = 0;
        static Context activityContext;

        public static void setCurrentActivity(int activityNum, Context context){
            CurrentActivity.activityNum = activityNum;
            activityContext = context;
        }

        public static void setActivityNum(int num){
            activityNum = num;
        }

        public static int getActivityNum(){
            return activityNum;
        }

        public static Context getActivityContext() {
            return activityContext;
        }

        public static void setActivityContext(Context activityContext) {
            CurrentActivity.activityContext = activityContext;
        }
    }

    public static class AdjustHeight{
        private static boolean isNeedAdjust = false;
        private static int adjustHeight = 0;

        public static boolean isIsNeedAdjust() {
            return isNeedAdjust;
        }

        public static void setIsNeedAdjust(boolean need) {
            isNeedAdjust = need;
        }

        public static int getAdjustHeight() {
            return adjustHeight;
        }

        public static void setAdjustHeight(int adjustHeight) {
            AdjustHeight.adjustHeight = adjustHeight;
        }

        public static void setAdjustParam(boolean need, int height){
            isNeedAdjust = need;
            adjustHeight = height;
        }
    }
}

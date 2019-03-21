package com.example.nuaabbs.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.nuaabbs.R;
import com.example.nuaabbs.object.Comment;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;

public class HelperUtil {

    public static boolean InsertComment(Comment newComment, List<Comment> commentList){

        int index = 0;
        boolean flag = false;

        int id = newComment.getFollowCommentID();
        for(Comment comment : commentList){
            if(!flag){
                if(comment.getCommentID() == id){
                    flag = true;
                }
            }else{
                if(comment.isDependent() && comment.getCommentID() != id)
                    break;
            }

            ++index;
        }

        if(flag) commentList.add(index, newComment);

        return flag;
    }

    public static int getRandomHeadID(){
        int[] IDs = {R.drawable.ic_head_1, R.drawable.ic_head_2, R.drawable.ic_head_3,
                R.drawable.ic_head_4, R.drawable.ic_head_5, R.drawable.ic_head_6,
                R.drawable.ic_head_7, R.drawable.ic_head_8, R.drawable.ic_head_9,
                R.drawable.ic_head_10};
        int index = new Random().nextInt(10);
        return IDs[index];
    }

    public static class GetHeight{
        public static int getStatusBarHeight(Context context){
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
            int height = resources.getDimensionPixelSize(resourceId);
            LogUtil.i("GetHeight", "StatusBar height = " + height);
            return height;
        }

        public static int getNavigationBarHeight(Context context){
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
            int height = resources.getDimensionPixelSize(resourceId);
            LogUtil.i("GetHeight", "Navigation height:" + height);
            return height;
        }

        public static int getScreenHeightExceptionNavigationBar(Context context){
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            LogUtil.i("GetHeight", "screen height:" + dm.heightPixels);
            return dm.heightPixels;
        }

        public static boolean isHasNavigationBar(Context context){
            boolean hasNavigationBar = false;
            Resources rs = context.getResources();
            int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
            if (id > 0) {
                hasNavigationBar = rs.getBoolean(id);
            }
            try {
                Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
                Method m = systemPropertiesClass.getMethod("get", String.class);
                String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
                if (navBarOverride.equals("1")) {
                    hasNavigationBar = false;
                } else if (navBarOverride.equals("0")) {
                    hasNavigationBar = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(hasNavigationBar){
                LogUtil.i("GetHeight", "now has navigationBar");
            }else{
                LogUtil.i("GetHeight", "now has no navigationBar");
            }
            return hasNavigationBar;
        }
    }
}

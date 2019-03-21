package com.example.nuaabbs.common;

import com.google.gson.Gson;

public class Constant {

    /* Some URL for connection with server */
    public static String URL = "http://118.25.131.221:8080/BBSSever/";
    public static String URL_Login = URL + "login";
    public static String URL_Register = URL + "register";
    public static String URL_Logout = URL + "logout";
    public static String URL_UpdateUserDetail = URL + "updateUserDetail";
    public static String URL_CreateNewPost = URL + "createNewPost";
    public static String URL_RequestLabelPost = URL + "requestLabelPost";
    public static String URL_RequestMyPost = URL + "requestMyPost";
    public static String URL_RequestHotPost = URL + "requestHotPost";
    public static String URL_PostRelatedAction = URL + "postRelatedAction";

    // charset of connection with server
    public static String CONNECT_CHARSET = "application/json; charset=utf-8";

    public static String LIFE_LABEL = "生活";
    public static String STUDY_LABEL = "学习";

    public static String REQCODE_LIFE_Post = LIFE_LABEL;
    public static String REQCODE_STUDY_Post = STUDY_LABEL;
    public static String REQCODE_DELETEPOST = "delete post";
    public static String REQCODE_VIEW = "add view num";
    public static String REQCODE_THUMBUP = "add thumb_up num";
    public static String REQCODE_COMMENT = "add comment";

    // constant string for response code
    public static String RESCODE_SUCCESS = "100";
    public static String RESCODE_PASSWORD_FALSE = "200";
    public static String RESCODE_REGISTERED = "201";
    public static String RESCODE_SQLEXCEPTION = "202";
    public static String RESCODE_NET_FAIL = "400";
    public static String RESCODE_SYSTEM_ERROR = "403";
    public static String RESCODE_NET_TIMEOUT = "500";


    public static String REGISTERED = "该用户名已存在";
    public static String SYSTEM_ERROR = "system error";
    public static String NET_UNABLE = "网络不可用！";
    public static String NET_TIMEOUT = "连接超时！";

    public static final int TAKE_PHOTO = 1024;
    public static final int CHOOSE_PHOTO = 1025;

    // 自定义日志标题
    public static String APP_LOG_TAG = "NUAA-BBS";
    public static String OKHTTP_TAG = " OkHttp";

    // default date
    public static long dataDefault = 946703833254l;

    public static Gson gson = new Gson();

    // activity num
    public static int NullActivityNum = 0;
    public static int MainActivityNum = 1;
    public static int LoginActivityNum = 2;
    public static int RegisterActivityNum = 3;
    public static int MyPostActivityNum = 4;
    public static int MyDraftActivityNum = 5;
    public static int MyCareActivityNum = 6;
    public static int MyCollectActivityNum = 7;
    public static int PersonalPageActivityNum = 8;
    public static int UserDetailActivityNum = 9;
    public static int ChangeUserDetailActivityNum = 10;
    public static int PostContentActivityNum = 11;
    public static int CreatePostActivityNum = 12;
    public static int SearchActivityNum = 13;
    public static int SystemSettingActivityNum = 14;
}

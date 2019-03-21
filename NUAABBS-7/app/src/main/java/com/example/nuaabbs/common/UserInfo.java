package com.example.nuaabbs.common;

import com.example.nuaabbs.util.LogUtil;

import java.sql.Date;

public class UserInfo {
    private int id;

    private String userName;
    private String password;
    private String schoolID;
    private String sex;

    private Date birthday;

    private String headPortrait;
    private String idioGraph;
    private int postNum;
    private int draftNum;
    private int collectPostNum;
    private int careUserNum;
    private int fansNum;


    public UserInfo(){
        initUserInfo();
    }

    public void PrintUserInfo(){

        LogUtil.d("UserInfo", "id = " + Integer.toString(this.id));
        LogUtil.d("UserInfo", "userName = " + this.userName);
        LogUtil.d("UserInfo", "password = " + this.password);

        if(this.schoolID == null) LogUtil.d("UserInfo", "schoolID = ");
        LogUtil.d("UserInfo", "schoolID = " + this.schoolID);

        LogUtil.d("UserInfo", "headPortrait = " + this.headPortrait);
        LogUtil.d("UserInfo", "sex = " + this.sex);

        if(this.birthday == null)
            LogUtil.d("UserInfo", "birthday = null");
        else LogUtil.d("UserInfo", "birthday = " + this.birthday.toString());
        LogUtil.d("UserInfo", "idiograph = " + this.idioGraph);
        LogUtil.d("UserInfo", "postNum = " + Integer.toString(this.postNum));
        LogUtil.d("UserInfo", "draftNum = " + Integer.toString(this.draftNum));
        LogUtil.d("UserInfo", "collectNum = " + Integer.toString(this.collectPostNum));
        LogUtil.d("UserInfo", "careUserNum = " + Integer.toString(this.careUserNum));
        LogUtil.d("UserInfo", "fansNum = " + Integer.toString(this.fansNum));
    }

    public void initUserInfo(){
        id = 0;

        if(userName != null && !userName.isEmpty())
            userName = "";
        if(password != null && !password.isEmpty())
            password = "";
        if(schoolID != null && !schoolID.isEmpty())
            schoolID = "";
        if(sex != null && !sex.isEmpty())
            sex = "";
        if(birthday != null) birthday = null;

        headPortrait = "default.jpg";
        idioGraph = "此人很懒，什么都没写哦";
        postNum = draftNum = collectPostNum = careUserNum = fansNum = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchoolID() {
        if(this.schoolID == null)
            return "";
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getSex() {
        if(sex == null)
            return "未填写";
        else return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isBirthdayEffect(){
        if(this.birthday == null)
            return false;
        else return true;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setBirthday(int year, int month, int day) {
        this.birthday.setYear(year - 1900);
        this.birthday.setMonth(month);
        this.birthday.setDate(day);
    }

    public String getIdioGraph() {
        return idioGraph;
    }

    public void setIdioGraph(String idioGraph) {
        this.idioGraph = idioGraph;
    }

    public int getPostNum() {
        return postNum;
    }

    public void setPostNum(int postNum) {
        this.postNum = postNum;
    }

    public int getDraftNum() {
        return draftNum;
    }

    public void setDraftNum(int draftNum) {
        this.draftNum = draftNum;
    }

    public int getCollectPostNum() {
        return collectPostNum;
    }

    public void setCollectPostNum(int collectPostNum) {
        this.collectPostNum = collectPostNum;
    }

    public int getCareUserNum() {
        return careUserNum;
    }

    public void setCareUserNum(int careUserNum) {
        this.careUserNum = careUserNum;
    }

    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }
}

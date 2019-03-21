package com.example.nuaabbs.object;

import com.example.nuaabbs.common.MyApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Post implements Serializable {
    private int postID;
    private String poster;
    private int posterID;
    private String dateStr;
    private String label;
    private String postContent;

    private int thumb_upNum = 0;
    private int viewNum = 0;
    private int commentNum = 0;
    private int hotDegree = 5;
    private List<Comment> comments;

    public Post(){

    }

    public Post(boolean newPost){
        poster = MyApplication.userInfo.getUserName();
        posterID = MyApplication.userInfo.getId();
        comments = new ArrayList<>();
    }

    public void addThumb_upNum(){
        this.thumb_upNum += 1;
    }

    public void decThumb_upNum(){
        this.thumb_upNum -= 1;
    }

    public void addViewNum(){
        this.viewNum += 1;
    }

    public void decViewNum(){
        this.viewNum -= 1;
    }

    public void addCommentNum(){
        this.commentNum += 1;
    }

    public void decCommentNum(){
        this.commentNum -= 1;
    }

    public void calculateHotDegree(){
        this.hotDegree = this.thumb_upNum*2 + this.viewNum + this.commentNum*3+5;
    }

    public void addComments(Comment comment){
        this.comments.add(comment);
    }

    public void deleteLastComment(){
        this.comments.remove(this.comments.size()-1);
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getPosterID() {
        return posterID;
    }

    public void setPosterID(int posterID) {
        this.posterID = posterID;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public int getThumb_upNum() {
        return thumb_upNum;
    }

    public void setThumb_upNum(int thumb_upNum) {
        this.thumb_upNum = thumb_upNum;
    }

    public int getViewNum() {
        return viewNum;
    }

    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getHotDegree() {
        return hotDegree;
    }

    public void setHotDegree(int hotDegree) {
        this.hotDegree = hotDegree;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
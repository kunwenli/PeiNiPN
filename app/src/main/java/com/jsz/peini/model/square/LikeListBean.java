package com.jsz.peini.model.square;

/**
 * Created by th on 2016/12/26.
 */

public class LikeListBean {
    private String userNickname;
    private String imageHead;
    private String userId;

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getImageHead() {
        return imageHead;
    }

    public void setImageHead(String imageHead) {
        this.imageHead = imageHead;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "LikeListBean{" +
                "userNickname='" + userNickname + '\'' +
                ", imageHead='" + imageHead + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
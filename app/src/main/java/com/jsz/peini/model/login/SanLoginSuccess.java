package com.jsz.peini.model.login;

/**
 * Created by th on 2016/12/13.
 */

public class SanLoginSuccess {
    private String isExist;
    private String userToken;
    private int resultCode;
    private String resultDesc;
    private UserInfoBean userInfo;

    public String getIsExist() {
        return isExist;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "SanLoginSuccess{" +
                ", mUserToken='" + userToken + '\'' +
                ", resultCode=" + resultCode +
                ", resultDesc='" + resultDesc + '\'' +
                ", userInfo=" + userInfo +
                '}';
    }

    public static class UserInfoBean {
        private String id;
        private int userLoginId;
        private String nickname;
        private String imageHead;
        private int sex;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getUserLoginId() {
            return userLoginId;
        }

        public void setUserLoginId(int userLoginId) {
            this.userLoginId = userLoginId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getImageHead() {
            return imageHead;
        }

        public void setImageHead(String imageHead) {
            this.imageHead = imageHead;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        @Override
        public String toString() {
            return "UserInfoBean{" +
                    "id='" + id + '\'' +
                    ", userLoginId=" + userLoginId +
                    ", nickname='" + nickname + '\'' +
                    ", imageHead='" + imageHead + '\'' +
                    ", sex=" + sex +
                    '}';
        }
    }
}

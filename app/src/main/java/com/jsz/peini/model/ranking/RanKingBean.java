package com.jsz.peini.model.ranking;

import java.util.List;

/**
 * Created by th on 2017/1/19.
 */

public class RanKingBean {

    private int resultCode;
    private String resultDesc;
    private MyRankBean myRank;
    private List<RankListBean> rankList;

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

    public MyRankBean getMyRank() {
        return myRank;
    }

    public void setMyRank(MyRankBean myRank) {
        this.myRank = myRank;
    }

    public List<RankListBean> getRankList() {
        return rankList;
    }

    public void setRankList(List<RankListBean> rankList) {
        this.rankList = rankList;
    }

    @Override
    public String toString() {
        return "RanKingBean{" +
                "resultCode=" + resultCode +
                ", resultDesc='" + resultDesc + '\'' +
                ", myRank=" + myRank +
                ", rankList=" + rankList +
                '}';
    }

    public static class MyRankBean {
        private int rowNo;
        private int num;
        private int sex;
        private int age;
        private String nickname;
        private String imageHead;
        private String industry;
        private String userId;

        public int getRowNo() {
            return rowNo;
        }

        public void setRowNo(int rowNo) {
            this.rowNo = rowNo;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
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

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "MyRankBean{" +
                    "rowNo=" + rowNo +
                    ", num=" + num +
                    ", sex=" + sex +
                    ", age=" + age +
                    ", nickname='" + nickname + '\'' +
                    ", imageHead='" + imageHead + '\'' +
                    ", industry='" + industry + '\'' +
                    ", userId='" + userId + '\'' +
                    '}';
        }
    }

    public static class RankListBean {
        private int num;
        private String userId;
        private String nickname;
        private String imageHead;
        private int age;
        private int sex;
        private String industry;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        @Override
        public String toString() {
            return "RankListBean{" +
                    "num=" + num +
                    ", userId='" + userId + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", imageHead='" + imageHead + '\'' +
                    ", age=" + age +
                    ", sex=" + sex +
                    ", industry='" + industry + '\'' +
                    '}';
        }
    }
}

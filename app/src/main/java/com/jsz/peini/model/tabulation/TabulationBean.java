package com.jsz.peini.model.tabulation;

import java.util.List;

/**
 * Created by th on 2016/12/20.
 */

public class TabulationBean {
    private int resultCode;
    private String resultDesc;
    private List<TaskAllListBean> taskAllList;

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

    public List<TaskAllListBean> getTaskAllList() {
        return taskAllList;
    }

    public void setTaskAllList(List<TaskAllListBean> taskAllList) {
        this.taskAllList = taskAllList;
    }

    @Override
    public String toString() {
        return "TabulationBean{" +
                "resultCode=" + resultCode +
                ", resultDesc='" + resultDesc + '\'' +
                ", taskAllList=" + taskAllList +
                '}';
    }

    public static class TaskAllListBean {
        private int id;
        private String userId;
        private String sellerInfoName;
        private String taskAppointedTime;
        private int sellerInfoLabelsId;
        private String sellerSmallName;
        private int otherLowAge;
        private int otherHignAge;
        private int otherSex;
        private int otherGo;
        private int otherBuy;
        private int taskStatus;
        private String xpoint;
        private String ypoint;
        private String imageHead;
        private String othernickName;
        private int sex;
        private int age;
        private int reputation;

        public String getSellerSmallName() {
            return sellerSmallName;
        }

        public void setSellerSmallName(String sellerSmallName) {
            this.sellerSmallName = sellerSmallName;
        }

        private int distanceNum;
        private int goldList;
        private int buyList;
        private int integrityList;
        private List<UserLabelBean> userLabel;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getSellerInfoName() {
            return sellerInfoName;
        }

        public void setSellerInfoName(String sellerInfoName) {
            this.sellerInfoName = sellerInfoName;
        }

        public String getTaskAppointedTime() {
            return taskAppointedTime;
        }

        public void setTaskAppointedTime(String taskAppointedTime) {
            this.taskAppointedTime = taskAppointedTime;
        }

        public int getSellerInfoLabelsId() {
            return sellerInfoLabelsId;
        }

        public void setSellerInfoLabelsId(int sellerInfoLabelsId) {
            this.sellerInfoLabelsId = sellerInfoLabelsId;
        }


        public int getOtherLowAge() {
            return otherLowAge;
        }

        public void setOtherLowAge(int otherLowAge) {
            this.otherLowAge = otherLowAge;
        }

        public int getOtherHignAge() {
            return otherHignAge;
        }

        public void setOtherHignAge(int otherHignAge) {
            this.otherHignAge = otherHignAge;
        }

        public int getOtherSex() {
            return otherSex;
        }

        public void setOtherSex(int otherSex) {
            this.otherSex = otherSex;
        }

        public int getOtherGo() {
            return otherGo;
        }

        public void setOtherGo(int otherGo) {
            this.otherGo = otherGo;
        }

        public int getOtherBuy() {
            return otherBuy;
        }

        public void setOtherBuy(int otherBuy) {
            this.otherBuy = otherBuy;
        }

        public int getTaskStatus() {
            return taskStatus;
        }

        public void setTaskStatus(int taskStatus) {
            this.taskStatus = taskStatus;
        }

        public String getXpoint() {
            return xpoint;
        }

        public void setXpoint(String xpoint) {
            this.xpoint = xpoint;
        }

        public String getYpoint() {
            return ypoint;
        }

        public void setYpoint(String ypoint) {
            this.ypoint = ypoint;
        }

        public String getImageHead() {
            return imageHead;
        }

        public void setImageHead(String imageHead) {
            this.imageHead = imageHead;
        }

        public String getOthernickName() {
            return othernickName;
        }

        public void setOthernickName(String othernickName) {
            this.othernickName = othernickName;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getReputation() {
            return reputation;
        }

        public void setReputation(int reputation) {
            this.reputation = reputation;
        }

        public int getDistanceNum() {
            return distanceNum;
        }

        public void setDistanceNum(int distanceNum) {
            this.distanceNum = distanceNum;
        }

        public int getGoldList() {
            return goldList;
        }

        public void setGoldList(int goldList) {
            this.goldList = goldList;
        }

        public int getBuyList() {
            return buyList;
        }

        public void setBuyList(int buyList) {
            this.buyList = buyList;
        }

        public int getIntegrityList() {
            return integrityList;
        }

        public void setIntegrityList(int integrityList) {
            this.integrityList = integrityList;
        }

        public List<UserLabelBean> getUserLabel() {
            return userLabel;
        }

        public void setUserLabel(List<UserLabelBean> userLabel) {
            this.userLabel = userLabel;
        }

        @Override
        public String toString() {
            return "TaskAllListBean{" +
                    "id=" + id +
                    ", userId=" + userId +
                    ", sellerInfoName='" + sellerInfoName + '\'' +
                    ", taskAppointedTime='" + taskAppointedTime + '\'' +
                    ", sellerInfoLabelsId=" + sellerInfoLabelsId +
                    ", sellerSmallName='" + sellerSmallName + '\'' +
                    ", otherLowAge=" + otherLowAge +
                    ", otherHignAge=" + otherHignAge +
                    ", otherSex=" + otherSex +
                    ", otherGo=" + otherGo +
                    ", otherBuy=" + otherBuy +
                    ", taskStatus=" + taskStatus +
                    ", xpoint='" + xpoint + '\'' +
                    ", ypoint='" + ypoint + '\'' +
                    ", imageHead='" + imageHead + '\'' +
                    ", othernickName='" + othernickName + '\'' +
                    ", sex=" + sex +
                    ", age=" + age +
                    ", reputation=" + reputation +
                    ", distanceNum=" + distanceNum +
                    ", goldList=" + goldList +
                    ", buyList=" + buyList +
                    ", integrityList=" + integrityList +
                    ", userLabel=" + userLabel +
                    '}';
        }

        public static class UserLabelBean {
            /**
             * id : 1
             * labelName : 帅哥
             */

            private int id;
            private String labelName;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLabelName() {
                return labelName;
            }

            public void setLabelName(String labelName) {
                this.labelName = labelName;
            }

            @Override
            public String toString() {
                return "UserLabelBean{" +
                        "id=" + id +
                        ", labelName='" + labelName + '\'' +
                        '}';
            }
        }
    }
}

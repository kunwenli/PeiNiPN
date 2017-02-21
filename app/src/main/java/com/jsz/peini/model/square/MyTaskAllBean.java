package com.jsz.peini.model.square;

import java.util.List;

/**
 * Created by th on 2017/2/3.
 */
public class MyTaskAllBean {


    private int resultCode;
    private String resultDesc;
    private List<TaskInfoByUserIdListBean> taskInfoByUserIdList;

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

    public List<TaskInfoByUserIdListBean> getTaskInfoByUserIdList() {
        return taskInfoByUserIdList;
    }

    public void setTaskInfoByUserIdList(List<TaskInfoByUserIdListBean> taskInfoByUserIdList) {
        this.taskInfoByUserIdList = taskInfoByUserIdList;
    }

    @Override
    public String toString() {
        return "MyTaskAllBean{" +
                "resultCode=" + resultCode +
                ", resultDesc='" + resultDesc + '\'' +
                ", taskInfoByUserIdList=" + taskInfoByUserIdList +
                '}';
    }

    public static class TaskInfoByUserIdListBean {
        private int id;
        private String nickName;
        private int sex;
        private int age;
        private String industry;
        private String imageHead;
        private int goldList;
        private int buyList;
        private int integrityList;
        private String userId;
        private int publishType;
        private int sellerInfoId;
        private String sellerInfoName;
        private int taskStatus;
        private int otherLowAge;
        private int otherHighAge;
        private int otherSex;
        private int otherGo;
        private int otherBuy;
        private String userPhone;
        private String otherUserId;
        private String taskAppointedTime;
        private String otherPhone;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
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

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getImageHead() {
            return imageHead;
        }

        public void setImageHead(String imageHead) {
            this.imageHead = imageHead;
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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getPublishType() {
            return publishType;
        }

        public void setPublishType(int publishType) {
            this.publishType = publishType;
        }

        public int getSellerInfoId() {
            return sellerInfoId;
        }

        public void setSellerInfoId(int sellerInfoId) {
            this.sellerInfoId = sellerInfoId;
        }

        public String getSellerInfoName() {
            return sellerInfoName;
        }

        public void setSellerInfoName(String sellerInfoName) {
            this.sellerInfoName = sellerInfoName;
        }

        public int getTaskStatus() {
            return taskStatus;
        }

        public void setTaskStatus(int taskStatus) {
            this.taskStatus = taskStatus;
        }

        public int getOtherLowAge() {
            return otherLowAge;
        }

        public void setOtherLowAge(int otherLowAge) {
            this.otherLowAge = otherLowAge;
        }

        public int getOtherHighAge() {
            return otherHighAge;
        }

        public void setOtherHighAge(int otherHighAge) {
            this.otherHighAge = otherHighAge;
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

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getOtherUserId() {
            return otherUserId;
        }

        public void setOtherUserId(String otherUserId) {
            this.otherUserId = otherUserId;
        }

        public String getTaskAppointedTime() {
            return taskAppointedTime;
        }

        public void setTaskAppointedTime(String taskAppointedTime) {
            this.taskAppointedTime = taskAppointedTime;
        }

        public String getOtherPhone() {
            return otherPhone;
        }

        public void setOtherPhone(String otherPhone) {
            this.otherPhone = otherPhone;
        }

        @Override
        public String toString() {
            return "TaskInfoByUserIdListBean{" +
                    "id=" + id +
                    ", nickName='" + nickName + '\'' +
                    ", sex=" + sex +
                    ", age=" + age +
                    ", industry='" + industry + '\'' +
                    ", imageHead='" + imageHead + '\'' +
                    ", goldList=" + goldList +
                    ", buyList=" + buyList +
                    ", integrityList=" + integrityList +
                    ", userId='" + userId + '\'' +
                    ", publishType=" + publishType +
                    ", sellerInfoId=" + sellerInfoId +
                    ", sellerInfoName='" + sellerInfoName + '\'' +
                    ", taskStatus=" + taskStatus +
                    ", otherLowAge=" + otherLowAge +
                    ", otherHighAge=" + otherHighAge +
                    ", otherSex=" + otherSex +
                    ", otherGo=" + otherGo +
                    ", otherBuy=" + otherBuy +
                    ", userPhone='" + userPhone + '\'' +
                    ", otherUserId='" + otherUserId + '\'' +
                    ", taskAppointedTime='" + taskAppointedTime + '\'' +
                    ", otherPhone='" + otherPhone + '\'' +
                    '}';
        }
    }
}

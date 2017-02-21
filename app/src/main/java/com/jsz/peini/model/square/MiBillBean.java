package com.jsz.peini.model.square;

import java.util.List;

/**
 * Created by 15089 on 2017/2/16.
 */
public class MiBillBean {
    private int resultCode;
    private String resultDesc;
    public List<OrderInfoList> orderInfoList;

    public class OrderInfoList {
        private String orderType;
        private String orderTime;
        private String payType;
        private String totalPay;
        private String payMoney;
        private String orderId;
        private String orderStatus;
        private String orderCode;
        private String title;
        private String imgSrc;

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getTotalPay() {
            return totalPay;
        }

        public void setTotalPay(String totalPay) {
            this.totalPay = totalPay;
        }

        public String getPayMoney() {
            return payMoney;
        }

        public void setPayMoney(String payMoney) {
            this.payMoney = payMoney;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgSrc() {
            return imgSrc;
        }

        public void setImgSrc(String imgSrc) {
            this.imgSrc = imgSrc;
        }

        @Override
        public String toString() {
            return "OrderInfoList{" +
                    "orderType='" + orderType + '\'' +
                    ", orderTime='" + orderTime + '\'' +
                    ", payType='" + payType + '\'' +
                    ", totalPay='" + totalPay + '\'' +
                    ", payMoney='" + payMoney + '\'' +
                    ", orderId='" + orderId + '\'' +
                    ", orderStatus='" + orderStatus + '\'' +
                    ", orderCode='" + orderCode + '\'' +
                    ", title='" + title + '\'' +
                    ", imgSrc='" + imgSrc + '\'' +
                    '}';
        }
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

    public List<OrderInfoList> getOrderInfoList() {
        return orderInfoList;
    }

    public void setOrderInfoList(List<OrderInfoList> orderInfoList) {
        this.orderInfoList = orderInfoList;
    }

    @Override
    public String toString() {
        return "MiBillBean{" +
                "resultCode=" + resultCode +
                ", resultDesc='" + resultDesc + '\'' +
                ", orderInfoList=" + orderInfoList +
                '}';
    }
}

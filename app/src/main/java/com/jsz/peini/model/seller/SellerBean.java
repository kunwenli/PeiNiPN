package com.jsz.peini.model.seller;

import java.util.List;

/**
 * Created by th on 2016/12/16.
 */

public class SellerBean {


    private int resultCode;
    private String resultDesc;
    private List<SellerInfoBean> sellerInfo;

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

    public List<SellerInfoBean> getSellerInfo() {
        return sellerInfo;
    }

    public void setSellerInfo(List<SellerInfoBean> sellerInfo) {
        this.sellerInfo = sellerInfo;
    }

    @Override
    public String toString() {
        return "SellerBean{" +
                "resultCode=" + resultCode +
                ", resultDesc='" + resultDesc + '\'' +
                ", sellerInfo=" + sellerInfo +
                '}';
    }

    public static class SellerInfoBean {
        private int id;
        private String sellerName;
        private int sellerScore;
        private int districtCode;
        private String districtName;
        private String labelsName;
        private int price;
        private String imageSrc;
        private int distance;
        private String countMJ;
        private String countJB;

        public String getCountJB() {
            return countJB;
        }

        public void setCountJB(String countJB) {
            this.countJB = countJB;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSellerName() {
            return sellerName;
        }

        public void setSellerName(String sellerName) {
            this.sellerName = sellerName;
        }

        public int getSellerScore() {
            return sellerScore;
        }

        public void setSellerScore(int sellerScore) {
            this.sellerScore = sellerScore;
        }

        public int getDistrictCode() {
            return districtCode;
        }

        public void setDistrictCode(int districtCode) {
            this.districtCode = districtCode;
        }

        public String getDistrictName() {
            return districtName;
        }

        public void setDistrictName(String districtName) {
            this.districtName = districtName;
        }

        public String getLabelsName() {
            return labelsName;
        }

        public void setLabelsName(String labelsName) {
            this.labelsName = labelsName;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getImageSrc() {
            return imageSrc;
        }

        public void setImageSrc(String imageSrc) {
            this.imageSrc = imageSrc;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public String getCountMJ() {
            return countMJ;
        }

        public void setCountMJ(String countMJ) {
            this.countMJ = countMJ;
        }

        @Override
        public String toString() {
            return "SellerInfoBean{" +
                    "id=" + id +
                    ", sellerName='" + sellerName + '\'' +
                    ", sellerScore=" + sellerScore +
                    ", districtCode=" + districtCode +
                    ", districtName='" + districtName + '\'' +
                    ", labelsName='" + labelsName + '\'' +
                    ", price=" + price +
                    ", imageSrc='" + imageSrc + '\'' +
                    ", distance=" + distance +
                    ", countMJ='" + countMJ + '\'' +
                    ", countJB='" + countJB + '\'' +
                    '}';
        }
    }
}

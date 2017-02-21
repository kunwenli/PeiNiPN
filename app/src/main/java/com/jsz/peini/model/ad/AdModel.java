package com.jsz.peini.model.ad;

import java.util.List;

/**
 * Created by th on 2017/1/11.
 */

public class AdModel {


    private int resultCode;
    private String resultDesc;
    private List<AdvertiseListBean> advertiseList;

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

    public List<AdvertiseListBean> getAdvertiseList() {
        return advertiseList;
    }

    public void setAdvertiseList(List<AdvertiseListBean> advertiseList) {
        this.advertiseList = advertiseList;
    }

    @Override
    public String toString() {
        return "AdModel{" +
                "resultCode=" + resultCode +
                ", resultDesc='" + resultDesc + '\'' +
                ", advertiseList=" + advertiseList +
                '}';
    }

    public static class AdvertiseListBean {
        private String adId;
        private int adType;
        private String adTitle;
        private String adLink;
        private String adImgUrl;
        private int adStatus;

        public String getAdId() {
            return adId;
        }

        public void setAdId(String adId) {
            this.adId = adId;
        }

        public int getAdType() {
            return adType;
        }

        public void setAdType(int adType) {
            this.adType = adType;
        }

        public String getAdTitle() {
            return adTitle;
        }

        public void setAdTitle(String adTitle) {
            this.adTitle = adTitle;
        }

        public String getAdLink() {
            return adLink;
        }

        public void setAdLink(String adLink) {
            this.adLink = adLink;
        }

        public String getAdImgUrl() {
            return adImgUrl;
        }

        public void setAdImgUrl(String adImgUrl) {
            this.adImgUrl = adImgUrl;
        }

        public int getAdStatus() {
            return adStatus;
        }

        public void setAdStatus(int adStatus) {
            this.adStatus = adStatus;
        }

        @Override
        public String toString() {
            return "AdvertiseListBean{" +
                    "adId='" + adId + '\'' +
                    ", adType=" + adType +
                    ", adTitle='" + adTitle + '\'' +
                    ", adLink='" + adLink + '\'' +
                    ", adImgUrl='" + adImgUrl + '\'' +
                    ", adStatus=" + adStatus +
                    '}';
        }
    }
}

package com.jsz.peini.model.seller;

import java.util.List;

/**
 * Created by th on 2017/2/9.
 */
public class SellerMessageInfoBean {

    private int resultCode;
    private String resultDesc;
    private SellerInfoBean sellerInfo;

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

    public SellerInfoBean getSellerInfo() {
        return sellerInfo;
    }

    public void setSellerInfo(SellerInfoBean sellerInfo) {
        this.sellerInfo = sellerInfo;
    }

    public static class SellerInfoBean {
        private int id;
        private String sellerName;
        private String sellerHead;
        private Object weatherorder;
        private String sellerPhone;
        private Object weathersms;
        private int price;
        private int sellerScore;
        private Object provinceid;
        private Object cityCode;
        private Object distid;
        private int districtCode;
        private String districtName;
        private String sellerAddress;
        private String xpoint;
        private String ypoint;
        private int sellerType;
        private String labelsId;
        private String labelsName;
        private int sellerServer;
        private int sellerCondition;
        private int sellerMeal;
        private int status;
        private String information;
        private Object companyId;
        private Object sellerKey;
        private int isWifi;
        private int isParking;
        private Object smsmobile;
        private Object accname;
        private Object accpass;
        private Object accmail;
        private Object accmobile;
        private Object sellerstatus;
        private Object remindtext;
        private Object sellerImg;
        private String imageSrc;
        private String couponMj;
        private String couponJb;
        private int distance;
        private Object sort;
        private Object searchWord;
        private int searchType;
        private Object listNum;
        private List<ImageListBean> imageList;

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

        public String getSellerHead() {
            return sellerHead;
        }

        public void setSellerHead(String sellerHead) {
            this.sellerHead = sellerHead;
        }

        public Object getWeatherorder() {
            return weatherorder;
        }

        public void setWeatherorder(Object weatherorder) {
            this.weatherorder = weatherorder;
        }

        public String getSellerPhone() {
            return sellerPhone;
        }

        public void setSellerPhone(String sellerPhone) {
            this.sellerPhone = sellerPhone;
        }

        public Object getWeathersms() {
            return weathersms;
        }

        public void setWeathersms(Object weathersms) {
            this.weathersms = weathersms;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getSellerScore() {
            return sellerScore;
        }

        public void setSellerScore(int sellerScore) {
            this.sellerScore = sellerScore;
        }

        public Object getProvinceid() {
            return provinceid;
        }

        public void setProvinceid(Object provinceid) {
            this.provinceid = provinceid;
        }

        public Object getCityCode() {
            return cityCode;
        }

        public void setCityCode(Object cityCode) {
            this.cityCode = cityCode;
        }

        public Object getDistid() {
            return distid;
        }

        public void setDistid(Object distid) {
            this.distid = distid;
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

        public String getSellerAddress() {
            return sellerAddress;
        }

        public void setSellerAddress(String sellerAddress) {
            this.sellerAddress = sellerAddress;
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

        public int getSellerType() {
            return sellerType;
        }

        public void setSellerType(int sellerType) {
            this.sellerType = sellerType;
        }

        public String getLabelsId() {
            return labelsId;
        }

        public void setLabelsId(String labelsId) {
            this.labelsId = labelsId;
        }

        public String getLabelsName() {
            return labelsName;
        }

        public void setLabelsName(String labelsName) {
            this.labelsName = labelsName;
        }

        public int getSellerServer() {
            return sellerServer;
        }

        public void setSellerServer(int sellerServer) {
            this.sellerServer = sellerServer;
        }

        public int getSellerCondition() {
            return sellerCondition;
        }

        public void setSellerCondition(int sellerCondition) {
            this.sellerCondition = sellerCondition;
        }

        public int getSellerMeal() {
            return sellerMeal;
        }

        public void setSellerMeal(int sellerMeal) {
            this.sellerMeal = sellerMeal;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getInformation() {
            return information;
        }

        public void setInformation(String information) {
            this.information = information;
        }

        public Object getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Object companyId) {
            this.companyId = companyId;
        }

        public Object getSellerKey() {
            return sellerKey;
        }

        public void setSellerKey(Object sellerKey) {
            this.sellerKey = sellerKey;
        }

        public int getIsWifi() {
            return isWifi;
        }

        public void setIsWifi(int isWifi) {
            this.isWifi = isWifi;
        }

        public int getIsParking() {
            return isParking;
        }

        public void setIsParking(int isParking) {
            this.isParking = isParking;
        }

        public Object getSmsmobile() {
            return smsmobile;
        }

        public void setSmsmobile(Object smsmobile) {
            this.smsmobile = smsmobile;
        }

        public Object getAccname() {
            return accname;
        }

        public void setAccname(Object accname) {
            this.accname = accname;
        }

        public Object getAccpass() {
            return accpass;
        }

        public void setAccpass(Object accpass) {
            this.accpass = accpass;
        }

        public Object getAccmail() {
            return accmail;
        }

        public void setAccmail(Object accmail) {
            this.accmail = accmail;
        }

        public Object getAccmobile() {
            return accmobile;
        }

        public void setAccmobile(Object accmobile) {
            this.accmobile = accmobile;
        }

        public Object getSellerstatus() {
            return sellerstatus;
        }

        public void setSellerstatus(Object sellerstatus) {
            this.sellerstatus = sellerstatus;
        }

        public Object getRemindtext() {
            return remindtext;
        }

        public void setRemindtext(Object remindtext) {
            this.remindtext = remindtext;
        }

        public Object getSellerImg() {
            return sellerImg;
        }

        public void setSellerImg(Object sellerImg) {
            this.sellerImg = sellerImg;
        }

        public String getImageSrc() {
            return imageSrc;
        }

        public void setImageSrc(String imageSrc) {
            this.imageSrc = imageSrc;
        }

        public String getCouponMj() {
            return couponMj;
        }

        public void setCouponMj(String couponMj) {
            this.couponMj = couponMj;
        }

        public String getCouponJb() {
            return couponJb;
        }

        public void setCouponJb(String couponJb) {
            this.couponJb = couponJb;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public Object getSearchWord() {
            return searchWord;
        }

        public void setSearchWord(Object searchWord) {
            this.searchWord = searchWord;
        }

        public int getSearchType() {
            return searchType;
        }

        public void setSearchType(int searchType) {
            this.searchType = searchType;
        }

        public Object getListNum() {
            return listNum;
        }

        public void setListNum(Object listNum) {
            this.listNum = listNum;
        }

        public List<ImageListBean> getImageList() {
            return imageList;
        }

        public void setImageList(List<ImageListBean> imageList) {
            this.imageList = imageList;
        }

        public static class ImageListBean {
            private String imageUrl;

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }
        }
    }
}

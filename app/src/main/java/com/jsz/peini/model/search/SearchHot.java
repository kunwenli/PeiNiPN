package com.jsz.peini.model.search;

import java.util.List;

/**
 * Created by th on 2016/12/16.
 */

public class SearchHot {

    /**
     * resultCode : 1
     * resultDesc : 成功
     * sellerList : [{"id":null,"sellerName":"大","sellerHead":null,"sellerScore":0,"sellerPhone":null,"sellerAddress":null,"xpoint":null,"ypoint":null,"sellerType":null,"sellerServer":0,"sellerCondition":0,"sellerMeal":0,"status":null,"districtCode":null,"districtName":null,"information":null,"labelsId":null,"labelsName":null,"price":null,"cityCode":null,"imageSrc":null,"couponMj":null,"couponJb":null,"distance":null,"sort":null,"searchWord":null,"searchType":2,"listNum":4},{"id":null,"sellerName":"大驴","sellerHead":null,"sellerScore":0,"sellerPhone":null,"sellerAddress":null,"xpoint":null,"ypoint":null,"sellerType":null,"sellerServer":0,"sellerCondition":0,"sellerMeal":0,"status":null,"districtCode":null,"districtName":null,"information":null,"labelsId":null,"labelsName":null,"price":null,"cityCode":null,"imageSrc":null,"couponMj":null,"couponJb":null,"distance":null,"sort":null,"searchWord":null,"searchType":2,"listNum":4},{"id":1,"sellerName":"大驴的肉食","sellerHead":"d:/1234.jpg","sellerScore":71,"sellerPhone":"15933312963","sellerAddress":"东尚9楼","xpoint":"38.048521","ypoint":"114.517432","sellerType":101,"sellerServer":75,"sellerCondition":75,"sellerMeal":65,"status":1,"districtCode":13010202,"districtName":"北国","information":"这是世界第一家","labelsId":"1","labelsName":"西餐","price":66,"cityCode":null,"imageSrc":"d:/1234.jpg","couponMj":"满300减30","couponJb":"金币消费8折优惠","distance":1241,"sort":null,"searchWord":null,"searchType":1,"listNum":null},{"id":2,"sellerName":"大驴的素食","sellerHead":"d:/132132132.jpg","sellerScore":78,"sellerPhone":"13120515000","sellerAddress":"北国5楼","xpoint":"38.048343","ypoint":"114.517105","sellerType":101,"sellerServer":3,"sellerCondition":3,"sellerMeal":3,"status":1,"districtCode":13010202,"districtName":"东尚","information":"中国最素","labelsId":"2","labelsName":"特色餐饮","price":23,"cityCode":null,"imageSrc":null,"couponMj":null,"couponJb":null,"distance":1264,"sort":null,"searchWord":null,"searchType":1,"listNum":null},{"id":3,"sellerName":"大驴健身","sellerHead":"d:/dasd.jpg","sellerScore":24,"sellerPhone":"15642320212","sellerAddress":"友谊公园","xpoint":"38.053007","ypoint":"114.458989","sellerType":104,"sellerServer":1,"sellerCondition":1,"sellerMeal":1,"status":1,"districtCode":13010204,"districtName":"东胜","information":"健身第一","labelsId":"1","labelsName":"健身房","price":65,"cityCode":null,"imageSrc":null,"couponMj":null,"couponJb":null,"distance":6376,"sort":null,"searchWord":null,"searchType":1,"listNum":null},{"id":4,"sellerName":"大驴KTV","sellerHead":"sdlhifsdlhkf","sellerScore":99,"sellerPhone":"15831211212","sellerAddress":"益友购物中心","xpoint":"38.040301","ypoint":"114.458072","sellerType":102,"sellerServer":2,"sellerCondition":2,"sellerMeal":2,"status":1,"districtCode":13010407,"districtName":"北辰广场","information":"oneKTV","labelsId":"2","labelsName":"KTV","price":12,"cityCode":null,"imageSrc":null,"couponMj":null,"couponJb":null,"distance":6434,"sort":null,"searchWord":null,"searchType":1,"listNum":null}]
     */

    private int resultCode;
    private String resultDesc;
    private List<SellerListBean> sellerList;

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

    public List<SellerListBean> getSellerList() {
        return sellerList;
    }

    @Override
    public String toString() {
        return "SearchHot{" +
                "resultCode=" + resultCode +
                ", resultDesc='" + resultDesc + '\'' +
                ", sellerList=" + sellerList +
                '}';
    }

    public void setSellerList(List<SellerListBean> sellerList) {
        this.sellerList = sellerList;

    }

    public static class SellerListBean {
        /**
         * id : null
         * sellerName : 大
         * sellerHead : null
         * sellerScore : 0
         * sellerPhone : null
         * sellerAddress : null
         * xpoint : null
         * ypoint : null
         * sellerType : null
         * sellerServer : 0
         * sellerCondition : 0
         * sellerMeal : 0
         * status : null
         * districtCode : null
         * districtName : null
         * information : null
         * labelsId : null
         * labelsName : null
         * price : null
         * cityCode : null
         * imageSrc : null
         * couponMj : null
         * couponJb : null
         * distance : null
         * sort : null
         * searchWord : null
         * searchType : 2
         * listNum : 4
         */

        private Object id;
        private String sellerName;
        private Object sellerHead;
        private int sellerScore;
        private Object sellerPhone;
        private Object sellerAddress;
        private Object xpoint;
        private Object ypoint;
        private Object sellerType;
        private int sellerServer;
        private int sellerCondition;
        private int sellerMeal;
        private Object status;
        private Object districtCode;
        private Object districtName;
        private Object information;
        private Object labelsId;
        private Object labelsName;
        private Object price;
        private Object cityCode;
        private Object imageSrc;
        private Object couponMj;
        private Object couponJb;
        private Object distance;
        private Object sort;
        private Object searchWord;
        private int searchType;
        private int listNum;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public String getSellerName() {
            return sellerName;
        }

        public void setSellerName(String sellerName) {
            this.sellerName = sellerName;
        }

        public Object getSellerHead() {
            return sellerHead;
        }

        public void setSellerHead(Object sellerHead) {
            this.sellerHead = sellerHead;
        }

        public int getSellerScore() {
            return sellerScore;
        }

        public void setSellerScore(int sellerScore) {
            this.sellerScore = sellerScore;
        }

        public Object getSellerPhone() {
            return sellerPhone;
        }

        public void setSellerPhone(Object sellerPhone) {
            this.sellerPhone = sellerPhone;
        }

        public Object getSellerAddress() {
            return sellerAddress;
        }

        public void setSellerAddress(Object sellerAddress) {
            this.sellerAddress = sellerAddress;
        }

        public Object getXpoint() {
            return xpoint;
        }

        public void setXpoint(Object xpoint) {
            this.xpoint = xpoint;
        }

        public Object getYpoint() {
            return ypoint;
        }

        public void setYpoint(Object ypoint) {
            this.ypoint = ypoint;
        }

        public Object getSellerType() {
            return sellerType;
        }

        public void setSellerType(Object sellerType) {
            this.sellerType = sellerType;
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

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getDistrictCode() {
            return districtCode;
        }

        public void setDistrictCode(Object districtCode) {
            this.districtCode = districtCode;
        }

        public Object getDistrictName() {
            return districtName;
        }

        public void setDistrictName(Object districtName) {
            this.districtName = districtName;
        }

        public Object getInformation() {
            return information;
        }

        public void setInformation(Object information) {
            this.information = information;
        }

        public Object getLabelsId() {
            return labelsId;
        }

        public void setLabelsId(Object labelsId) {
            this.labelsId = labelsId;
        }

        public Object getLabelsName() {
            return labelsName;
        }

        public void setLabelsName(Object labelsName) {
            this.labelsName = labelsName;
        }

        public Object getPrice() {
            return price;
        }

        public void setPrice(Object price) {
            this.price = price;
        }

        public Object getCityCode() {
            return cityCode;
        }

        public void setCityCode(Object cityCode) {
            this.cityCode = cityCode;
        }

        public Object getImageSrc() {
            return imageSrc;
        }

        public void setImageSrc(Object imageSrc) {
            this.imageSrc = imageSrc;
        }

        public Object getCouponMj() {
            return couponMj;
        }

        public void setCouponMj(Object couponMj) {
            this.couponMj = couponMj;
        }

        public Object getCouponJb() {
            return couponJb;
        }

        public void setCouponJb(Object couponJb) {
            this.couponJb = couponJb;
        }

        public Object getDistance() {
            return distance;
        }

        public void setDistance(Object distance) {
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

        public int getListNum() {
            return listNum;
        }

        public void setListNum(int listNum) {
            this.listNum = listNum;
        }

        @Override
        public String toString() {
            return "SellerListBean{" +
                    "id=" + id +
                    ", sellerName='" + sellerName + '\'' +
                    ", sellerHead=" + sellerHead +
                    ", sellerScore=" + sellerScore +
                    ", sellerPhone=" + sellerPhone +
                    ", sellerAddress=" + sellerAddress +
                    ", xpoint=" + xpoint +
                    ", ypoint=" + ypoint +
                    ", sellerType=" + sellerType +
                    ", sellerServer=" + sellerServer +
                    ", sellerCondition=" + sellerCondition +
                    ", sellerMeal=" + sellerMeal +
                    ", status=" + status +
                    ", districtCode=" + districtCode +
                    ", districtName=" + districtName +
                    ", information=" + information +
                    ", labelsId=" + labelsId +
                    ", labelsName=" + labelsName +
                    ", price=" + price +
                    ", cityCode=" + cityCode +
                    ", imageSrc=" + imageSrc +
                    ", couponMj=" + couponMj +
                    ", couponJb=" + couponJb +
                    ", distance=" + distance +
                    ", sort=" + sort +
                    ", searchWord=" + searchWord +
                    ", searchType=" + searchType +
                    ", listNum=" + listNum +
                    '}';
        }
    }
}

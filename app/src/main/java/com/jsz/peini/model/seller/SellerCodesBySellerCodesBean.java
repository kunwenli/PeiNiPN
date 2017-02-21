package com.jsz.peini.model.seller;

import java.util.List;

/**
 * Created by th on 2016/12/15.
 */

public class SellerCodesBySellerCodesBean {

    /**
     * resultCode : 1
     * resultDesc : 成功
     * sellerCodes : [{"id":1,"codesName":"美食"},{"id":2,"codesName":"唱歌"},{"id":3,"codesName":"电影"},{"id":4,"codesName":"运动健身"},{"id":5,"codesName":"休闲娱乐"},{"id":6,"codesName":"酒店"},{"id":7,"codesName":"丽人"},{"id":8,"codesName":"生活服务"},{"id":10,"codesName":"小吃快餐"},{"id":11,"codesName":"自助餐"},{"id":12,"codesName":"火锅"},{"id":13,"codesName":"烧烤"},{"id":14,"codesName":"西餐"},{"id":15,"codesName":"冀菜"},{"id":16,"codesName":"日韩菜"},{"id":17,"codesName":"海鲜粤菜"},{"id":18,"codesName":"川湘菜"},{"id":19,"codesName":"东北菜"},{"id":20,"codesName":"清真菜"},{"id":21,"codesName":"特色餐饮"},{"id":22,"codesName":"面包甜品"},{"id":23,"codesName":"其他（北京菜/沪菜）"},{"id":24,"codesName":"Ktv"},{"id":25,"codesName":"电影院"},{"id":26,"codesName":"台球"},{"id":27,"codesName":"健身房"},{"id":28,"codesName":"瑜伽教室"},{"id":29,"codesName":"体育场馆"},{"id":30,"codesName":"咖啡"},{"id":31,"codesName":"酒吧"},{"id":32,"codesName":"茶楼"},{"id":33,"codesName":"密室逃脱"},{"id":34,"codesName":"洗浴汗蒸"},{"id":35,"codesName":"温泉"},{"id":36,"codesName":"中医养生"},{"id":37,"codesName":"足疗按摩"},{"id":38,"codesName":"酒店"},{"id":39,"codesName":"美容美体"},{"id":40,"codesName":"美发"},{"id":41,"codesName":"汽车维修"},{"id":42,"codesName":"衣物清洗"},{"id":43,"codesName":"珠宝"},{"id":9,"codesName":"其他"},{"id":44,"codesName":"网吧"},{"id":45,"codesName":"连锁超市"}]
     */

    private int resultCode;
    private String resultDesc;
    private List<SellerCodesBean> sellerCodes;

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

    public List<SellerCodesBean> getSellerCodes() {
        return sellerCodes;
    }

    public void setSellerCodes(List<SellerCodesBean> sellerCodes) {
        this.sellerCodes = sellerCodes;
    }

    public static class SellerCodesBean {
        /**
         * id : 1
         * codesName : 美食
         */

        private int id;
        private String codesName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCodesName() {
            return codesName;
        }

        public void setCodesName(String codesName) {
            this.codesName = codesName;
        }
    }
}

package com.jsz.peini.model.pay;

/**
 * Created by th on 2017/2/6.
 */
public class PayJinBiOrderIdStrBean {
    private JsonObjBean data;
    private int resultCode;
    private String resultDesc;

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public JsonObjBean getData() {
        return data;
    }

    public void setData(JsonObjBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PayJinBiOrderIdStrBean{" +
                "data=" + data +
                ", resultCode=" + resultCode +
                ", resultDesc='" + resultDesc + '\'' +
                '}';
    }

    public static class JsonObjBean {
        private String payId;
        private int serverB;

        @Override
        public String toString() {
            return "JsonObjBean{" +
                    "payId='" + payId + '\'' +
                    ", serverB='" + serverB + '\'' +
                    '}';
        }

        public String getPayId() {
            return payId;
        }

        public void setPayId(String payId) {
            this.payId = payId;
        }

        public int getServerB() {
            return serverB;
        }

        public void setServerB(int serverB) {
            this.serverB = serverB;
        }
    }
}

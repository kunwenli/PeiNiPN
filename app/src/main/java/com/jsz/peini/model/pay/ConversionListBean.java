package com.jsz.peini.model.pay;

import java.util.List;

/**
 * Created by 15089 on 2017/2/17.
 */
public class ConversionListBean {
    private int resultCode;
    private String resultDesc;
    private List<ListBean> data;

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

    public List<ListBean> getData() {
        return data;
    }

    public void setData(List<ListBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ConversionListBean{" +
                "resultCode=" + resultCode +
                ", resultDesc='" + resultDesc + '\'' +
                ", data=" + data +
                '}';
    }

    public class ListBean {
        private int goldNum;
        private int id;
        private int payNum;

        @Override
        public String toString() {
            return "ListBean{" +
                    "goldNum=" + goldNum +
                    ", id=" + id +
                    ", payNum=" + payNum +
                    '}';
        }

        public int getGoldNum() {
            return goldNum;
        }

        public void setGoldNum(int goldNum) {
            this.goldNum = goldNum;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPayNum() {
            return payNum;
        }

        public void setPayNum(int payNum) {
            this.payNum = payNum;
        }
    }
}

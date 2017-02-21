package com.jsz.peini.model.square;

/**
 * Created by th on 2017/1/17.
 */

public class MyCreditBean {

    private int resultCode;
    private String resultDesc;
    private isMyCreditBean myCredit;

    public int getResultCode() {
        return resultCode;
    }


    public String getResultDesc() {
        return resultDesc;
    }

    public isMyCreditBean getMyCredit() {
        return myCredit;
    }

    @Override
    public String toString() {
        return "MyCreditBean{" +
                "resultCode=" + resultCode +
                ", resultDesc='" + resultDesc + '\'' +
                ", myCredit=" + myCredit +
                '}';
    }

    public static class isMyCreditBean {
        private int idcardNum;
        private int creditNum;
        private int selfNum;
        private int taskNum;

        public int getIdcardNum() {
            return idcardNum;
        }

        public int getCreditNum() {
            return creditNum;
        }

        public int getSelfNum() {
            return selfNum;
        }

        public int getTaskNum() {
            return taskNum;
        }

        @Override
        public String toString() {
            return "isMyCreditBean{" +
                    "idcardNum=" + idcardNum +
                    ", creditNum=" + creditNum +
                    ", selfNum=" + selfNum +
                    ", taskNum=" + taskNum +
                    '}';
        }
    }
}

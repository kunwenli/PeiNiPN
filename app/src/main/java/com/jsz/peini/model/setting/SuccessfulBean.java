package com.jsz.peini.model.setting;

/**
 * Created by th on 2016/12/17.
 */

public class SuccessfulBean {

    /**
     * resultCode : 1
     * resultDesc : 成功
     */

    private int resultCode;
    private String resultDesc;

    public int getResultCode() {
        return resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    @Override
    public String toString() {
        return "SuccessfulBean{" +
                "resultCode=" + resultCode +
                ", resultDesc='" + resultDesc + '\'' +
                '}';
    }
}

package com.jsz.peini.model.filter;

/**
 * Created by 15089 on 2017/2/14.
 */

public class FilterRecycleviewBean {
    private int mInt;
    private String mString;

    public FilterRecycleviewBean(int anInt, String string) {
        mInt = anInt;
        mString = string;
    }

    @Override
    public String toString() {
        return "FilterRecycleviewBean{" +
                "mInt=" + mInt +
                ", mString='" + mString + '\'' +
                '}';
    }

    public int getInt() {
        return mInt;
    }

    public void setInt(int anInt) {
        mInt = anInt;
    }

    public String getString() {
        return mString;
    }

    public void setString(String string) {
        mString = string;
    }
}

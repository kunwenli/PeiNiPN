package com.jsz.peini.model.eventbus;

/**
 * Created by th on 2017/1/23.
 */

public class SelectorSellerBean {
    private String tyge;
    private String name;

    public SelectorSellerBean(String tyge, String name) {
        this.tyge = tyge;
        this.name = name;
    }

    public String getTyge() {
        return tyge;
    }

    public void setTyge(String tyge) {
        this.tyge = tyge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SelectorSellerBean{" +
                "tyge='" + tyge + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

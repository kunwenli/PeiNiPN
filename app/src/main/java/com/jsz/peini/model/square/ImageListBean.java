package com.jsz.peini.model.square;

import java.io.Serializable;

/**
 * Created by th on 2016/12/26.
 */

public class ImageListBean implements Serializable {
    private int id;
    private String imageSrc;
    private String imageTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getImageTime() {
        return imageTime;
    }

    public void setImageTime(String imageTime) {
        this.imageTime = imageTime;
    }

    @Override
    public String toString() {
        return "ImageListBean{" +
                "id=" + id +
                ", imageSrc='" + imageSrc + '\'' +
                ", imageTime='" + imageTime + '\'' +
                '}';
    }
}
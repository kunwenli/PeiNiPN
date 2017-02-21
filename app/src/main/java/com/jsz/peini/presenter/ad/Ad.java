package com.jsz.peini.presenter.ad;

import com.jsz.peini.model.ad.AdModel;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by th on 2017/1/11.
 */

public interface Ad {
    /**
     * 广告接口
     * 1启动页2首页弹窗3商家轮播4广场轮播5活动轮播
     */
    @POST("getAdvertise")
    Call<AdModel> getAdvertise(
            @Query("adType") String adType);
}

package com.jsz.peini.presenter.setting;

import com.jsz.peini.model.CrtyBean;
import com.jsz.peini.model.login.GainSmsBean;
import com.jsz.peini.model.setting.SuccessfulBean;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by th on 2016/12/17.
 */

public interface SettingService {
    @POST("sellerJoin")
    Call<SuccessfulBean> sellerJoin(
            @Query("sellerProvince") String sellerProvince,
            @Query("sellerCity") String sellerCity,
            @Query("sellerName") String sellerName,
            @Query("sellerType") String sellerType,
            @Query("contactName") String contactName,
            @Query("contactPhone") String contactPhone);

    /**
     * 获取程序城市列表
     */
    @POST("getCityList")
    Call<CrtyBean> getCityList(
            @Query("userId") String sellerName);

    /**
     * 修改原始密码
     * resetPass
     */
    @POST("resetPass")
    Call<SuccessfulBean> resetPass(@Query("userToken") String userToken,
                                   @Query("oldPass") String oldPass,
                                   @Query("newPass") String newPass);


    /**
     * 修改支付密码获取验证码
     * resetPass
     */
    @POST("smsSendPay")
    Call<GainSmsBean> smsSendPay(@Query("userToken") String userToken);

    /**
     * 修改支付密码获取验证码
     * resetPass
     */
    @POST("updatePayPassword")
    Call<SuccessfulBean> updatePayPassword(@Query("userPassword") String userPassword,
                                           @Query("userToken") String userToken,
                                           @Query("yzm") String yzm);


}

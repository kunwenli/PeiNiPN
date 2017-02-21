package com.jsz.peini.presenter;

import com.jsz.peini.model.pay.ConversionListBean;
import com.jsz.peini.model.pay.PayJinBiOrderIdStrBean;
import com.jsz.peini.model.pay.PayOrderIdStrBean;
import com.jsz.peini.model.pay.PaySuccessfulBean;
import com.jsz.peini.model.pay.WeiXinPayOrderIdStrBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by th on 2017/2/4.
 */

public interface PayService {
    /**
     * 19、提交任务订单接口
     * 接口URI	参数	参数名称	参数类型	参数说明
     * saveTaskOrder	输入参数
     * 说明	名称	类型
     * userId
     * taskId
     * sellerInfoId	Int
     * 总金额	totalMoney
     * 不参与折扣金额	exclusiveMoney		非必填
     * payType
     * couponId		非必填
     * 返回参数
     * 订单号	orderIdStr	string
     * 结果码	resultCode	String
     * 结果说明	resultDesc	String
     */
    @GET("saveTaskOrder")
    Call<PaySuccessfulBean> saveTaskOrder(
            @Query("userId") String userToken,
            @Query("taskId") String taskId,
            @Query("sellerInfoId") String sellerInfoId,
            @Query("totalMoney") String totalMoney,
            @Query("exclusiveMoney") String exclusiveMoney,
            @Query("payType") String payType,
            @Query("couponId") String couponId);

    /**
     * payAli/prePay
     * 支付宝预支付
     */
    @POST("payAli/prePay")
    Call<PayOrderIdStrBean> payAli(
            @Query("userId") String userToken,
            @Query("orderIdStr") String orderIdStr);

    /**
     * 微信预支付payWx/prePay
     */
    @POST("payWx/prePay")
    Call<WeiXinPayOrderIdStrBean> payWx(
            @Query("userId") String userToken,
            @Query("orderIdStr") String orderIdStr);

    /**
     * payAli/prePay
     * 金币预支付
     */
    @POST("payGold/prePay")
    Call<PayJinBiOrderIdStrBean> payGold(
            @Query("token") String token,
            @Query("appA") int appA,
            @Query("userId") String userId,
            @Query("orderIdStr") String orderIdStr);

    /**
     * 金币支付
     */
    @POST("payGold/pay")
    Call<PayJinBiOrderIdStrBean> payGoldPrePay(
            @Query("token") String token,
            @Query("appA") int appA,
            @Query("payId") String payId,
            @Query("payPass") String payPass);

    /*买单选择优惠券列表接口*/
    @POST("getCouponInfoByPay")
    Call<String> getCouponInfoByPay(
            @Query("userId") String userId,
            @Query("sellerInfoId") String sellerInfoId,
            @Query("totalMoney") String totalMoney,
            @Query("exclusiveMoney") String exclusiveMoney,
            @Query("payType") String payType);

    /*获取金币充值*/
    @POST("payGold/getConversionList")
    Call<ConversionListBean> getConversionList();

    /**
     * 金币订单提交
     * userId	string
     * localId
     */
    @POST("payGold/saveBuyGoldOrder")
    Call<PaySuccessfulBean> saveBuyGoldOrder(
            @Query("userId") String userId,
            @Query("localId") int localId);

}

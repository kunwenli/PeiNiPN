package com.jsz.peini.presenter.login;

import com.jsz.peini.model.login.GainSmsBean;
import com.jsz.peini.model.login.LoginSuccess;
import com.jsz.peini.model.login.SanLoginSuccess;
import com.jsz.peini.model.login.VerificationSmsBean;
import com.jsz.peini.model.login.updataPassword;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by kunwe on 2016/12/1.
 */

public interface LoginService {
    /*注册接口*/
    @POST("registerUserLogin")
    Call<LoginSuccess> regisetr(@Query("userName") String user_name,
                                @Query("userPassword") String user_password,
                                @Query("sex") String sex,
                                @Query("birthday") String birthday,
                                @Query("nowProvince") String now_province,
                                @Query("nowCity") String no_city,
                                @Query("age") String age);

    /**
     * 获取验证码
     * 新密码
     */
//    http://192.168.150.182:8080/pnservice/smsSendRegister?userName=15544771389
    @POST("smsSendRegister")
    Call<GainSmsBean> smsSendRegister(@Query("userName") String user_name);

    /**
     * 找回密码获取验证码
     * 旧密码
     */
//    http://192.168.150.182:8080/pnservice/smsSendRegister?userName=15544771389
    @POST("smsSendFind")
    Call<GainSmsBean> smsSendFind(@Query("userName") String user_name);

    /*验证码和手机号验证借口*/
//    http://192.168.150.182:8080/pnservice/smsSendRegister?userName=15544771389
    @POST("checkSmsCode")
    Call<VerificationSmsBean> checkSmsCode(@Query("userName") String user_name, @Query("yzm") String yzm);

    /*这个是确认密码的借口找回密码的接口*/
//    http://192.168.150.182:8080/pnservice/smsSendRegister?userName=15544771389
    @POST("updatePassword")
    Call<updataPassword> updatePassword(@Query("userName") String user_name, @Query("userPassword") String yzm);

    /*这个是登陆的接口*/
    @POST("userLogin")
    Call<LoginSuccess> userLogin(
            @Query("userName") String userLogin,
            @Query("userPassword") String userPassword,
            @Query("appA") int appA);

    /**
     * 第三方注册的借口
     * 2、第三方注册接口接口
     * 接口URI	参数	参数名称	参数类型	参数说明
     * registerThirdUserLogin	输入参数
     * 说明	名称	类型
     * 性别	sex		必填
     * 生日	birthday		必填
     * 省份	nowProvince		必填
     * 城市	nowCity
     * 年龄	age
     * 手机号	userName
     * 三方类别	thirdType		1QQ,2微信；3新浪微博
     * 三方唯一值	thirdName
     * 昵称	nickname		非必填
     * 头像	imageHead	File	非必填
     * 返回参数
     * userInfo	JsonObj	id:userId
     * userLoginId:帐号id
     * nickname:昵称
     * imageHead:头像
     * sex：性别
     * userToken	String
     * 结果码	resultCode	String
     * 结果说明	resultDesc	String
     */
    @Multipart
    @POST("registerThirdUserLogin")
    Call<SanLoginSuccess> registerUserLogin(@Query("thirdName") String thirdName,
                                            @Query("sex") String sex,
                                            @Query("birthday") String birthday,
                                            @Query("nowProvince") String nowProvince,
                                            @Query("nowCity") String nwCity,
                                            @Query("age") String age,
                                            @Query("userName") String userName,
                                            @Query("thirdType") String thirdType,
                                            @Query("nickname") String nickname,
                                            @Part MultipartBody.Part part);

    /**
     * 接口说明：手机第三方帐号登录接口
     * 调用方式：post
     * 接口地址：http://192.168.150.140:8089/pnservice/userLoginByThird
     * 入参：
     * 名称	类型	长度	说明
     * thirdName	String		第三方的token
     * 回参：
     * 名称	类型	长度	说明
     * mUserToken	String		用户身份验证密匙
     * userInfo	Json		用户信息
     * userInfo：
     * 名称	类型 	长度	说明
     * id	String		用户id
     * userLoginId	Int		用户帐号表id
     * nickname	String		用户昵称
     * imageHead	String		用户头像
     * sex	Int		性别
     */

    @POST("userLoginByThird")
    Call<SanLoginSuccess> userLoginByThird(
            @Query("thirdName") String thirdName);
}

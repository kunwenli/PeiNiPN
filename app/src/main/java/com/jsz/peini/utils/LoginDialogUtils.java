package com.jsz.peini.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.jsz.peini.model.login.LoginSuccess;
import com.jsz.peini.ui.activity.home.HomeActivity;
import com.jsz.peini.ui.activity.login.LoginActivity;

import retrofit2.Response;

/**
 * Created by th on 2017/2/3.
 */

public class LoginDialogUtils {

    public static Intent intent;

    /**
     * token失效问题
     */
    public static void isNewLogin(final Activity activity) {
        new AlertDialog.Builder(activity)
                .setCancelable(false)
                .setTitle("下线通知")
                .setMessage("您的账号在另一台设备上登录,如非本人操作,请修改密码")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        intent = new Intent(activity, LoginActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
                        SpUtils.remove(activity, "mUserToken");
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    /**登录成功返回的数据*/
    /**
     * 保存用户信息
     */
    public static void saveUserInformation(Activity mActivity, Response<LoginSuccess> response, String username, String password) {
        LoginSuccess loginSuccess = response.body();
        LogUtil.i("登陆界面", "登陆成功跳转界面" + loginSuccess.toString());
        SpUtils.put(mActivity, "phone", username);
        SpUtils.put(mActivity, "password", password);
        SpUtils.put(mActivity, "mUserToken", loginSuccess.getUserToken() + "");
        SpUtils.put(mActivity, "id", loginSuccess.getUserInfo().getId() + "");
        SpUtils.put(mActivity, "userLoginId", loginSuccess.getUserInfo().getUserLoginId() + "");
        SpUtils.put(mActivity, "nickname", loginSuccess.getUserInfo().getNickname() + "");
        SpUtils.put(mActivity, "imageHead", loginSuccess.getUserInfo().getImageHead() + "");
        SpUtils.put(mActivity, "sex", loginSuccess.getUserInfo().getSex() + "");
        SpUtils.put(mActivity, "serverB", loginSuccess.getServerB());
        intent = new Intent(mActivity, HomeActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(intent);
    }
}

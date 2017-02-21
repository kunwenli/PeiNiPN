package com.jsz.peini.wxapi;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.jsz.peini.PeiNiApp;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.ToastUtils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by 15089 on 2017/2/15.
 */

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private WXPayEntryActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        IWXAPI wxapi = WXAPIFactory.createWXAPI(mActivity, PeiNiApp.WXAPIPAY);
        wxapi.handleIntent(getIntent(), mActivity);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        LogUtil.d("支付回调" + baseResp.errCode);
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0) {
                ToastUtils.showToast(mActivity, "支付成功");
            } else {
                ToastUtils.showToast(mActivity, "支付失败");
            }
        }
        finish();
    }
}

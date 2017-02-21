package com.jsz.peini;

import android.content.Intent;
import android.os.Handler;

import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.ui.activity.AdvertisementActivity;

public class SplashActivity extends BaseActivity {
    private Intent mIntent;
    private SplashActivity mActivity;

    @Override
    public int initLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        ShowWindows(true);
        mActivity = this;
    }

    @Override
    public void initData() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mIntent = new Intent(mActivity, AdvertisementActivity.class);
                startActivity(mIntent);
                finish();
            }
        }, 2000);
    }
}

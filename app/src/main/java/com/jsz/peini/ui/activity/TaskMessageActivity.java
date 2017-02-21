package com.jsz.peini.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.service.FloatViewService;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 15089 on 2017/2/20.
 */

public class TaskMessageActivity extends BaseActivity {
    @InjectView(R.id.is_close)
    ImageView mIsClose;
    @InjectView(R.id.id_refuse)
    TextView mIdRefuse;
    @InjectView(R.id.Agree)
    TextView mAgree;
    private TaskMessageActivity mActivity;

    @Override
    public int initLayoutId() {
        return R.layout.activity_taskmessage;
    }

    @Override
    public void initView() {
        mActivity = this;
        stopService(new Intent(mActivity, FloatViewService.class));
    }

    @OnClick({R.id.is_close, R.id.id_refuse, R.id.Agree})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.is_close:
                startService(new Intent(mActivity, FloatViewService.class));
                finish();
                break;
            case R.id.id_refuse:
                stopService(new Intent(mActivity, FloatViewService.class));
                finish();
                break;
            case R.id.Agree:
                stopService(new Intent(mActivity, FloatViewService.class));
                finish();
                break;
        }
    }
}

package com.jsz.peini.ui.activity.setting;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.login.GainSmsBean;
import com.jsz.peini.model.login.VerificationSmsBean;
import com.jsz.peini.presenter.login.LoginService;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.StringUtils;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.utils.ZzUtils;
import com.jsz.peini.utils.time.TimeUtils;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by th on 2017/1/16.
 */
public class UpdataPasswordActivity extends BaseActivity {


    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.phone_text)
    TextView mPhoneText;
    @InjectView(R.id.phone_eit)
    EditText mPhoneEit;
    @InjectView(R.id.pay_show_sms)
    Button mPayShowSms;
    @InjectView(R.id.ok)
    Button mOk;
    public UpdataPasswordActivity mActivity;

    @Override
    public int initLayoutId() {
        return R.layout.activity_updata_password;
    }

    @Override
    public void initView() {
        mActivity = this;
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitle.setText("找回手势密码");
        if (StringUtils.isNoNull(mPhone)) {
            mPhoneText.setText("正在为" + "" + ZzUtils.phone(mPhone) + "修改手势密码密码");
        }
    }

    @OnClick({R.id.pay_show_sms, R.id.ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay_show_sms:
                if (!StringUtils.isNoNull(mPhone)) {
                    return;
                }
                TimeUtils.TimeCount(mPayShowSms, 60000, 1000);
                initNetWorkSms();
                break;
            case R.id.ok:
                String sms = mPhoneEit.getText().toString().trim();
                if (!StringUtils.isNoNull(sms)) {
                    ToastUtils.showToast(mActivity, "验证码为空");
                    return;
                }
                initNetWork(sms);
                break;

        }
    }

    private void initNetWork(String sms) {
        RetrofitUtil.createService(LoginService.class).checkSmsCode(mPhone, sms).enqueue(new Callback<VerificationSmsBean>() {
            @Override
            public void onResponse(Call<VerificationSmsBean> call, Response<VerificationSmsBean> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResultCode() == 1) {
                        startActivity(new Intent(mActivity, SetPassword.class));
                        finish();
                    } else {
                        LogUtil.d(response.body().toString() + "验证失败");
                    }
                }
            }

            @Override
            public void onFailure(Call<VerificationSmsBean> call, Throwable t) {
                LogUtil.d(t.getMessage() + "验证失败");
            }
        });
    }


    private void initNetWorkSms() {
        RetrofitUtil.createService(LoginService.class).smsSendFind(mPhone).enqueue(new Callback<GainSmsBean>() {
            @Override
            public void onResponse(Call<GainSmsBean> call, Response<GainSmsBean> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResultCode() == 1) {
                        mPhoneEit.setText(response.body().getSmsCode());
                    } else {
                        LogUtil.d(response.body().toString() + "获取验证码失败");
                    }
                }
            }

            @Override
            public void onFailure(Call<GainSmsBean> call, Throwable t) {
                LogUtil.d(t.getMessage() + "获取验证码失败");
            }
        });
    }
}

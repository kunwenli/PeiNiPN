package com.jsz.peini.ui.activity.setting;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.login.GainSmsBean;
import com.jsz.peini.model.setting.SuccessfulBean;
import com.jsz.peini.presenter.setting.SettingService;
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
 * Created by kunwe on 2016/12/6.
 */
public class PayPasswordActivity extends BaseActivity {
    @InjectView(R.id.pay_toolbar)
    Toolbar payToolbar;
    @InjectView(R.id.phone_text)
    TextView mPhoneText;
    @InjectView(R.id.phone_eit)
    EditText mPhoneEit;
    @InjectView(R.id.pay_show_sms)
    Button mPayShowSms;
    @InjectView(R.id.imageView5)
    ImageView mImageView5;
    @InjectView(R.id.password)
    EditText mPassword;
    @InjectView(R.id.nextpassword)
    EditText mNextpassword;
    @InjectView(R.id.ok)
    Button mOk;
    public PayPasswordActivity mActivity;

    @Override
    public int initLayoutId() {
        return R.layout.activity_paypassword;
    }

    @Override
    public void initView() {
        mActivity = this;
        payToolbar.setTitle("");
        setSupportActionBar(payToolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回????
        payToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        if (StringUtils.isNoNull(mPhone)) {
            mPhoneText.setText("正在为" + "" + ZzUtils.phone(mPhone) + "设置支付密码");
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
                String password = mPassword.getText().toString().trim();
                String nextPassword = mNextpassword.getText().toString().trim();
                String sms = mPhoneEit.getText().toString().trim();
                if (!StringUtils.isNoNull(sms)) {
                    ToastUtils.showToast(mActivity, "验证码为空");
                    return;
                }
                if (!StringUtils.isNoNull(password)) {
                    ToastUtils.showToast(mActivity, "请输入支付密码");
                    return;
                }
                if (!StringUtils.isNoNull(nextPassword)) {
                    ToastUtils.showToast(mActivity, "请再次输入支付密码");
                    return;
                }
                if (!password.equals(nextPassword)) {
                    ToastUtils.showToast(mActivity, "两次输入密码不一致");
                    return;
                }
                initNetWork(nextPassword, sms);
                break;
        }
    }

    private void initNetWork(String nextPassword, String sms) {
        RetrofitUtil.createService(SettingService.class).updatePayPassword(nextPassword, mUserToken, sms).enqueue(new Callback<SuccessfulBean>() {
            @Override
            public void onResponse(Call<SuccessfulBean> call, Response<SuccessfulBean> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResultCode() == 1) {
                        ToastUtils.showToast(mActivity, response.body().getResultDesc() + "");
                        LogUtil.d("修改成功" + response.body().toString());
                        finish();
                    } else {
                        LogUtil.d("修改失败" + response.body().toString());
                        ToastUtils.showToast(mActivity, response.body().getResultDesc() + "");
                    }
                }
            }

            @Override
            public void onFailure(Call<SuccessfulBean> call, Throwable t) {
                LogUtil.d("修改失败" + t.getMessage());
            }
        });
    }

    private void initNetWorkSms() {
        RetrofitUtil.createService(SettingService.class).smsSendPay(mUserToken)
                .enqueue(new Callback<GainSmsBean>() {
                    @Override
                    public void onResponse(Call<GainSmsBean> call, Response<GainSmsBean> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getResultCode() == 1) {
                                ToastUtils.showToast(mActivity, "获取验证码成功");
                                LogUtil.d("修改支付密码界面 获取验证码" + response.body().toString());
                                mPhoneEit.setText(response.body().getSmsCode());
                            } else {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GainSmsBean> call, Throwable t) {
                        LogUtil.d("设置支付密码界面" + t.getMessage());
                    }
                });
    }
}

package com.jsz.peini.ui.activity.square;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.login.GainSmsBean;
import com.jsz.peini.model.login.VerificationSmsBean;
import com.jsz.peini.presenter.login.LoginRetrofitHttp;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.SpUtils;
import com.jsz.peini.utils.ZzUtils;
import com.jsz.peini.utils.time.TimeUtils;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by th on 2017/1/3.
 */
public class PhoneAuthenticationActivity extends BaseActivity {
    @InjectView(R.id.send1)
    Button mSend1;
    @InjectView(R.id.activity_phone_authentication1)
    LinearLayout mActivityPhoneAuthentication1;
    @InjectView(R.id.edittext_cell_phone_number2)
    EditText mEdittextCellPhoneNumber2;
    @InjectView(R.id.send_verification_code2)
    Button mSendVerificationCode2;
    @InjectView(R.id.edittext_verification_code2)
    EditText mEdittextVerificationCode2;
    @InjectView(R.id.send2)
    Button mSend2;
    @InjectView(R.id.activity_phone_authentication2)
    LinearLayout mActivityPhoneAuthentication2;
    @InjectView(R.id.edittext_cell_phone_number3)
    EditText mEdittextCellPhoneNumber3;
    @InjectView(R.id.send_verification_code3)
    Button mSendVerificationCode3;
    @InjectView(R.id.edittext_verification_code3)
    EditText mEdittextVerificationCode3;
    @InjectView(R.id.send3)
    Button mSend3;
    @InjectView(R.id.activity_phone_authentication3)
    LinearLayout mActivityPhoneAuthentication3;
    @InjectView(R.id.phone_title)
    TextView mPhoneTitle;
    @InjectView(R.id.phone_toolbar)
    Toolbar mPhoneToolbar;
    @InjectView(R.id.phone_text)
    TextView mPhoneText;
    @InjectView(R.id.sendfind_text)
    TextView mSendfindText;
    private PhoneAuthenticationActivity mActivity;
    /**
     * 原手机号
     */
    private String mPhoneNumber2;
    /**
     * 新手机号
     */
    private String mPhoneNumber3;
    private String mCode2;
    private String mCode3;

    @Override
    public int initLayoutId() {
        return R.layout.activity_phone_authentication;
    }

    @Override
    public void initView() {
        super.initView();
        mActivity = this;
        mPhoneTitle.setText("手机认证");
        if (!TextUtils.isEmpty(mPhone)) {
            mPhoneText.setText("您的账号已于" + "" + ZzUtils.phone(mPhone) + "绑定");
            mSendfindText.setText("正在为" + "" + ZzUtils.phone(mPhone) + "修改手机号");
        }
        mPhoneToolbar.setTitle("");
        setSupportActionBar(mPhoneToolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回????
        mPhoneToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.send1, R.id.send2, R.id.send3, R.id.send_verification_code2, R.id.send_verification_code3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send1:
                mPhoneTitle.setText("更换绑定手机");
                mActivityPhoneAuthentication1.setVisibility(View.GONE);
                mActivityPhoneAuthentication2.setVisibility(View.VISIBLE);
                mActivityPhoneAuthentication3.setVisibility(View.GONE);
                break;
            case R.id.send2:
                mPhoneTitle.setText("更换绑定手机");
                mPhoneNumber2 = mEdittextCellPhoneNumber2.getText().toString().trim();
                mCode2 = mEdittextVerificationCode2.getText().toString().trim();
                if (TextUtils.isEmpty(mPhoneNumber2) && TextUtils.isEmpty(mCode2)) {
                    LogUtil.d(getLocalClassName(), "请输入验证和手机号");
                    return;
                }
                LoginRetrofitHttp.getHttp().checkSmsCode(mPhoneNumber2, mCode2).enqueue(new Callback<VerificationSmsBean>() {
                    @Override
                    public void onResponse(Call<VerificationSmsBean> call, Response<VerificationSmsBean> response) {
                        if (response.isSuccessful() && response.body().getResultCode() == 1) {
                            LogUtil.d(getLocalClassName(), mPhoneNumber2 + "<--验证验证码成功-->" + mCode2);
                            mActivityPhoneAuthentication1.setVisibility(View.GONE);
                            mActivityPhoneAuthentication2.setVisibility(View.GONE);
                            mActivityPhoneAuthentication3.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<VerificationSmsBean> call, Throwable t) {
                        LogUtil.d(getLocalClassName(), "验证验证吗失败" + t.getMessage());
                    }
                });
                break;
            case R.id.send3:
                mPhoneTitle.setText("更换绑定手机");
                mPhoneNumber3 = mEdittextCellPhoneNumber3.getText().toString().trim();
                mCode3 = mEdittextVerificationCode3.getText().toString().trim();
                if (TextUtils.isEmpty(mPhoneNumber3) && TextUtils.isEmpty(mCode3)) {
                    LogUtil.d(getLocalClassName(), "请输入验证和手机号");
                    return;
                }
                LoginRetrofitHttp.getHttp().checkSmsCode(mPhoneNumber3, mCode3).enqueue(new Callback<VerificationSmsBean>() {
                    @Override
                    public void onResponse(Call<VerificationSmsBean> call, Response<VerificationSmsBean> response) {
                        if (response.isSuccessful() && response.body().getResultCode() == 1) {
                            LogUtil.d(getLocalClassName(), mPhoneNumber3 + "<--验证验证码成功-->" + mCode3);
                            SpUtils.put(mActivity, "phone", mPhoneNumber3);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<VerificationSmsBean> call, Throwable t) {
                        LogUtil.d(getLocalClassName(), "验证验证吗失败" + t.getMessage());
                    }
                });
                break;
            case R.id.send_verification_code2: //获取验证码 原手机号
                mPhoneNumber2 = mEdittextCellPhoneNumber2.getText().toString().trim();
                if (TextUtils.isEmpty(mPhoneNumber2)) {
                    LogUtil.d(getLocalClassName(), "请输入手机号-");
                    return;
                }
                if (!ZzUtils.checkMobile(mPhoneNumber2)) {
                    LogUtil.d(getLocalClassName(), "请输入正确的手机号-");
                    return;
                }
                TimeUtils.TimeCount(mSendVerificationCode2, 60000, 1000);
                LogUtil.d(getLocalClassName(), "点击了倒计时的按钮--------2");
                LoginRetrofitHttp.getHttp().smsSendFind(mPhoneNumber2).enqueue(new Callback<GainSmsBean>() {
                    @Override
                    public void onResponse(Call<GainSmsBean> call, Response<GainSmsBean> response) {
                        if (response.isSuccessful()) {
                            LogUtil.d(getLocalClassName(), mPhoneNumber2 + "发送验证码成功" + response.body().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<GainSmsBean> call, Throwable t) {
                        LogUtil.d(getLocalClassName(), "获取验证吗失败" + t.getMessage());
                    }
                });
                break;
            case R.id.send_verification_code3: //获取验证码 新手机号
                mPhoneNumber3 = mEdittextCellPhoneNumber3.getText().toString().trim();
                if (TextUtils.isEmpty(mPhoneNumber3)) {
                    LogUtil.d(getLocalClassName(), "请输入手机号-");
                    return;
                }
                if (!ZzUtils.checkMobile(mPhoneNumber3)) {
                    LogUtil.d(getLocalClassName(), "请输入正确的手机号-");
                    return;
                }
                TimeUtils.TimeCount(mSendVerificationCode3, 60000, 1000);
                LoginRetrofitHttp.getHttp().smsSendRegister(mPhoneNumber3).enqueue(new Callback<GainSmsBean>() {
                    @Override
                    public void onResponse(Call<GainSmsBean> call, Response<GainSmsBean> response) {
                        if (response.isSuccessful()) {
                            LogUtil.d(getLocalClassName(), mPhoneNumber3 + "发送验证码成功" + response.body().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<GainSmsBean> call, Throwable t) {
                        LogUtil.d(getLocalClassName(), "获取验证吗失败" + t.getMessage());
                    }
                });
                break;
        }
    }
}


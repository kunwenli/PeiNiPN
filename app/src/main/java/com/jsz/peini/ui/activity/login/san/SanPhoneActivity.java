package com.jsz.peini.ui.activity.login.san;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.login.GainSmsBean;
import com.jsz.peini.model.login.VerificationSmsBean;
import com.jsz.peini.presenter.login.LoginRetrofitHttp;
import com.jsz.peini.utils.CacheActivity;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.time.TimeUtils;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jsz.peini.R.id.code_btn;
import static com.jsz.peini.R.id.code_edt;
import static com.jsz.peini.R.id.phone_edt;
import static com.jsz.peini.R.id.san_ok_next;
import static com.jsz.peini.R.id.san_phone_toolbar;

public class SanPhoneActivity extends BaseActivity {

    @InjectView(san_phone_toolbar)
    Toolbar mSanPhoneToolbar;
    @InjectView(R.id.imageView)
    ImageView imageView;
    @InjectView(phone_edt) //手机号
            EditText phoneEdt;
    @InjectView(code_btn)
    Button codeBtn;
    @InjectView(code_edt)
    EditText codeEdt;
    @InjectView(R.id.phone_code_layout)
    LinearLayout phoneCodeLayout;
    @InjectView(san_ok_next)
    Button sanOkNext;
    /**
     * 三方的 一些东西
     */
    private Intent intent;
    private String mSanToken; // 三方传递过来的 mSanToken
    private String mPlatform; //三方的平台

    private String phone;//手机号

    private SanPhoneActivity mActivity;
    private String platform;
    private String mPhone;
    public String mMProfile_image_url;
    public String sanName;


    @Override
    public int initLayoutId() {
        return R.layout.activity_san_login_phone;
    }

    @Override
    public void initView() {
        mActivity = SanPhoneActivity.this;
        if (!CacheActivity.activityList.contains(mActivity)) {
            CacheActivity.addActivity(mActivity);
        }
        mSanPhoneToolbar.setTitle("");
        setSupportActionBar(mSanPhoneToolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回????
        mSanPhoneToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        mSanToken = intent.getStringExtra("mSanToken");
        mPlatform = intent.getStringExtra("mPlatform");
        sanName = intent.getStringExtra("sanName");
        mMProfile_image_url = intent.getStringExtra("mProfile_image_url");
        LogUtil.i("三方授权返回的mSanToken 需要注册的东西\n",
                "mSanToken" + mSanToken + "mPlatform" + mPlatform + "mMProfile_image_url" + mMProfile_image_url);
    }

    @OnClick({san_ok_next, code_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case code_btn:
                mPhone = phoneEdt.getText().toString();
                LogUtil.i("三方注册界面", mPhone + "  获取验证码");
                if (TextUtils.isEmpty(mPhone) && mPhone.length() != 11) {
                    LogUtil.i("三方注册界面", "请输入手机号");
                    return;
                }
                TimeUtils.TimeCount(codeBtn, 60000, 1000);
                gainSms(mPhone);
                break;
            case san_ok_next:
                String phone2 = phoneEdt.getText().toString();
                String trim = codeEdt.getText().toString().trim();
                VerificationSms(phone2, trim);
                break;

        }
    }

    /*验证码*/
    private void gainSms(String phone) {
        LoginRetrofitHttp.getHttp().smsSendRegister(mPhone)
                .enqueue(new Callback<GainSmsBean>() {
                    @Override
                    public void onResponse(Call<GainSmsBean> call, Response<GainSmsBean> response) {
                        if (response.isSuccessful() && response.body().getResultCode() == 1) {
                            LogUtil.i("获取验证码是否成功", "" + response.body().toString());
                        } else {
                            LogUtil.i("获取验证码是否失败", "" + response.body().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<GainSmsBean> call, Throwable t) {
                        LogUtil.i("获取验证码是否成功", "" + "获取验证码失败的借口");
                    }
                });
    }

    /*认证验证码*/
    /*验证验证码借口*/
    private void VerificationSms(final String phone, String code) {
        LoginRetrofitHttp.getHttp().checkSmsCode(phone, code)
                .enqueue(new Callback<VerificationSmsBean>() {
                    @Override
                    public void onResponse(Call<VerificationSmsBean> call, Response<VerificationSmsBean> response) {
                        if (response.isSuccessful() && response.body().getResultCode() == 1) {
                            LogUtil.d("验证码验证成功" + response.body().toString());
                            intent = new Intent(mActivity, SanPhoneNextActivity.class);
                            intent.putExtra("phone", "" + phone);
                            intent.putExtra("mSanToken", mSanToken + "");
                            intent.putExtra("mPlatform", mPlatform + "");
                            intent.putExtra("sanName", sanName + "");
                            intent.putExtra("mProfile_image_url", mMProfile_image_url);
                            startActivity(intent);
                            LogUtil.i("验证码验证后", "验证成功跳转界面");
                        } else {
                            LogUtil.i("验证码验证后", "验证失败的验证码");
                        }
                    }

                    @Override
                    public void onFailure(Call<VerificationSmsBean> call, Throwable t) {

                    }
                });
    }
}

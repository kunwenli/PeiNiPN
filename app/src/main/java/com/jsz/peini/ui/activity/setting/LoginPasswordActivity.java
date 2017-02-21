package com.jsz.peini.ui.activity.setting;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.setting.SuccessfulBean;
import com.jsz.peini.presenter.setting.SettingService;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.SpUtils;
import com.jsz.peini.utils.ToastUtils;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kunwe on 2016/12/6.
 */
public class LoginPasswordActivity extends BaseActivity {
    @InjectView(R.id.aboutus_toolbar)
    Toolbar aboutusToolbar;
    @InjectView(R.id.login_new_password)
    EditText loginNewPassword;
    @InjectView(R.id.login_news_password)
    EditText loginNewsPassword;
    @InjectView(R.id.ok)
    Button ok;
    @InjectView(R.id.password)
    EditText mPassword;
    private String loginNewPasswordString;
    private String loginNewsPasswordString;
    public String mLoginNewmPassword;
    public LoginPasswordActivity mActivity;

    @Override
    public int initLayoutId() {
        return R.layout.activity_loginpassword;
    }

    @Override
    public void initView() {
        mActivity = this;
        aboutusToolbar.setTitle("");
        setSupportActionBar(aboutusToolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回????
        aboutusToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok:
                mLoginNewmPassword = mPassword.getText().toString().trim();
                loginNewPasswordString = loginNewPassword.getText().toString().trim();
                loginNewsPasswordString = loginNewsPassword.getText().toString().trim();
                String password = (String) SpUtils.get(mActivity, "password", "");
                if (!password.equals(mLoginNewmPassword)) {
                    ToastUtils.showToast(mActivity, "原始密码输入错误");
                    return;
                }
                if (!loginNewPasswordString.equals(loginNewsPasswordString)) {
                    ToastUtils.showToast(mActivity, "密码不一致!");
                    return;
                }
                initUpdataPassword(mLoginNewmPassword, loginNewsPasswordString);

                break;
        }
    }

    /*修改密码*/
    private void initUpdataPassword(String loginNewmPassword, final String loginNewsPasswordString) {
        RetrofitUtil.createService(SettingService.class).resetPass(mUserToken, loginNewmPassword, loginNewsPasswordString).enqueue(new Callback<SuccessfulBean>() {
            @Override
            public void onResponse(Call<SuccessfulBean> call, Response<SuccessfulBean> response) {
                if (response.isSuccessful()) {
                    SuccessfulBean body = response.body();
                    if (body.getResultCode() == 1) {
                        LogUtil.d(body.toString());
                        SpUtils.put(mActivity, "password", loginNewsPasswordString);
                        finish();
                    } else {
                        if (body.getResultCode() == 9) {
                            LogUtil.d(body.toString());
                        } else {
                            LogUtil.d(body.toString());
                        }
                    }
                } else {
                    LogUtil.d("修改密码失败");
                }

            }

            @Override
            public void onFailure(Call<SuccessfulBean> call, Throwable t) {
                LogUtil.d("修改密码失败" + t.getMessage());
            }
        });
    }
}

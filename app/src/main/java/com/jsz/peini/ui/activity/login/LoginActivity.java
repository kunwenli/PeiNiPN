package com.jsz.peini.ui.activity.login;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.login.LoginSuccess;
import com.jsz.peini.model.login.SanLoginSuccess;
import com.jsz.peini.presenter.login.LoginService;
import com.jsz.peini.ui.activity.home.HomeActivity;
import com.jsz.peini.ui.activity.login.password.FindPasswordActivity;
import com.jsz.peini.ui.activity.login.san.SanPhoneActivity;
import com.jsz.peini.utils.Bitmap.BitmapAndStringUtils;
import com.jsz.peini.utils.CacheActivity;
import com.jsz.peini.utils.KeyBoardUtils;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.LoginDialogUtils;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.SpUtils;
import com.jsz.peini.utils.StringUtils;
import com.jsz.peini.utils.ToastUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;
import java.util.Random;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jsz.peini.R.id.password_edt;

public class LoginActivity extends BaseActivity {
    @InjectView(R.id.login_toolbar)
    Toolbar login_toolbar;
    @InjectView(R.id.regist)
    TextView regist;
    @InjectView(R.id.username_edt)
    EditText usernameEdt;
    @InjectView(R.id.username_clean)
    Button usernameClean;
    @InjectView(password_edt)
    EditText passwordEdt;
    @InjectView(R.id.password_show)
    Button passwordShow;
    @InjectView(R.id.password_disappear)
    Button passwordDisappear;
    @InjectView(R.id.show_password_layout)
    RelativeLayout showPasswordLayout;
    @InjectView(R.id.password_clean)
    Button passwordClean;
    @InjectView(R.id.login_btn)
    Button loginBtn;
    @InjectView(R.id.remenber_check)
    CheckBox remenberCheck;
    @InjectView(R.id.find_password)
    TextView findPassword;
    @InjectView(R.id.weixin)
    Button weixin;
    @InjectView(R.id.qq)
    Button qq;
    @InjectView(R.id.weibo)
    Button weibo;
    private String password;
    private String username;
    private LoginActivity mActivity;
    private UMShareAPI mShareAPI;

    /**
     * 第三方的获取内容
     */
    private String mSanToken = ""; //三方的 token
    private Intent mIntent;
    public String mProfile_image_url;  //得到的头像
    public boolean mB;

    @Override
    public int initLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        mActivity = LoginActivity.this;
        mShareAPI = UMShareAPI.get(this);
        if (!CacheActivity.activityList.contains(LoginActivity.this)) {
            CacheActivity.addActivity(LoginActivity.this);
        }
        login_toolbar.setTitle("");
        setSupportActionBar(login_toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回????
        login_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mB = (boolean) SpUtils.get(mActivity, "b", false);
        String username = (String) SpUtils.get(mActivity, "phone", "");
        String password = (String) SpUtils.get(mActivity, "password", "");
        if (mB) {
            if (StringUtils.isNoNull(username)) {
                usernameEdt.setText(username);
            }
            if (StringUtils.isNoNull(password)) {
                passwordEdt.setText(password);
            }
        }
        remenberCheck.setChecked(mB);
        remenberCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mB = b;
                SpUtils.put(mActivity, "b", mB);
            }
        });
    }

    @OnClick({R.id.login_btn, R.id.weixin, R.id.qq, R.id.weibo, R.id.regist, R.id.find_password,})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                username = usernameEdt.getText().toString();//用户名
                password = passwordEdt.getText().toString();//密码
                if (TextUtils.isEmpty(username)) {
                    ToastUtils.showToast(mActivity, "请输入账号!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    ToastUtils.showToast(mActivity, "请输入密码!");
                    return;
                }
                initLogin(username, password);
                break;
            case R.id.weixin://微信授权
                mShareAPI.getPlatformInfo(mActivity, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            case R.id.qq://qq授权
                mShareAPI.getPlatformInfo(mActivity, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.weibo://微博授权
                mShareAPI.doOauthVerify(mActivity, SHARE_MEDIA.SINA, umAuthListener);
                break;
            case R.id.regist://注册
                startActivity(new Intent(this, RegistActivity.class));
                break;
            case R.id.find_password://忘记密码
                startActivity(new Intent(this, FindPasswordActivity.class));
                break;
        }
    }

    private String mPlatform;  //三方的平台
    private String IcUrl;
    public String sanName;

    /*三方登陆*/
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            mPlatform = platform + "";
            LogUtil.i("授权返回信息", "" + platform + " == " + action);
            for (Map.Entry<String, String> entry : data.entrySet()) {
                if (entry.getKey().equals("access_token")) {
                    mSanToken = entry.getValue();
                }
                if (entry.getKey().equals("profile_image_url")) {
                    IcUrl = entry.getValue();
                }
                if (entry.getKey().equals("screen_name")) {
                    sanName = entry.getValue();
                }
            }
            LogUtil.d("三方注册界面的接口返回的信息", "\n mSanToken \n" + mSanToken + "\n IcUrl\n" + IcUrl);
            if (IcUrl != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mProfile_image_url = BitmapAndStringUtils.saveBitmap(mActivity, IcUrl);
                        LogUtil.d("三方返回的头像信息-- Url= ", mProfile_image_url);
                    }
                }).start();
            }
            /**授权成功*/
            RetrofitUtil.createService(LoginService.class)
                    .userLoginByThird(mSanToken)
                    .enqueue(new Callback<SanLoginSuccess>() {
                                 @Override
                                 public void onResponse(Call<SanLoginSuccess> call, Response<SanLoginSuccess> response) {
                                     if (response.isSuccessful()) {
                                         ToastUtils.showToast(mActivity, response.body().getResultDesc());
                                         int resultCode = response.body().getResultCode();
                                         if (resultCode == 1) {
                                             SanLoginSuccess loginSuccess = response.body();
                                             if (loginSuccess.getIsExist().equals("1")) {
                                                 LogUtil.d("三方注册请求网络", "SanLoginSuccess" + loginSuccess.toString());
                                                 SpUtils.put(mActivity, "mUserToken", loginSuccess.getUserToken() + "");
                                                 SpUtils.put(mActivity, "id", loginSuccess.getUserInfo().getId() + "");
                                                 SpUtils.put(mActivity, "userLoginId", loginSuccess.getUserInfo().getUserLoginId() + "");
                                                 SpUtils.put(mActivity, "nickname", loginSuccess.getUserInfo().getNickname() + "");
                                                 SpUtils.put(mActivity, "imageHead", loginSuccess.getUserInfo().getImageHead() + "");
                                                 SpUtils.put(mActivity, "sex", loginSuccess.getUserInfo().getSex() + "");
                                                 Intent intent = new Intent(mActivity, HomeActivity.class)
                                                         .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                 startActivity(intent);
                                                 finish();
                                             } else {
                                                 mIntent = new Intent(mActivity, SanPhoneActivity.class);
                                                 mIntent.putExtra("mSanToken", mSanToken + "");
                                                 mIntent.putExtra("mPlatform", mPlatform + "");
                                                 mIntent.putExtra("sanName", sanName + "");
                                                 if (StringUtils.isNoNull(mProfile_image_url)) {
                                                     mIntent.putExtra("mProfile_image_url", mProfile_image_url + "");
                                                 }
                                                 startActivity(mIntent);
                                             }
                                         } else if (resultCode == 9) {
                                             LoginDialogUtils.isNewLogin(mActivity);
                                         }
                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<SanLoginSuccess> call, Throwable t) {
                                     ToastUtils.showToast(mActivity, t.getMessage());
                                 }
                             }
                    );
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    /**
     * 支付
     */
    int g = 1000001;
    int p = 3000;
    int a = 0;

    /*登陆模块*/
    private void initLogin(final String username, final String password) {
        int x = new Random().nextInt(2500) + 1;
        a = g ^ x % p;
        LogUtil.d("登录生成的随机数" + x + "登录传输的appA" + a);
        SpUtils.put(mActivity, "x", x);
        RetrofitUtil.createService(LoginService.class)
                .userLogin(username, password, a)
                .enqueue(new Callback<LoginSuccess>() {
                    @Override
                    public void onResponse(Call<LoginSuccess> call, Response<LoginSuccess> response) {
                        if (response.isSuccessful()) {
                            LoginSuccess body = response.body();
                            KeyBoardUtils.hideKeyBoard(mActivity, passwordEdt);
                            if (body.getResultCode() == 1) {
                                LoginDialogUtils.saveUserInformation(mActivity, response, username, password);
                                ToastUtils.showToast(mActivity, body.getResultDesc());
                            } else if (body.getResultCode() == 9) {
                                LoginDialogUtils.isNewLogin(mActivity);
                            } else {
                                ToastUtils.showToast(mActivity, body.getResultDesc());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginSuccess> call, Throwable t) {
                        ToastUtils.showToast(mActivity, t.getMessage());
                    }
                });
    }
}

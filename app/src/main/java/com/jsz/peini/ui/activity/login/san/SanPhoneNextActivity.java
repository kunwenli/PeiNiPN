package com.jsz.peini.ui.activity.login.san;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.login.SanLoginSuccess;
import com.jsz.peini.presenter.login.LoginRetrofitHttp;
import com.jsz.peini.ui.activity.home.HomeActivity;
import com.jsz.peini.utils.CacheActivity;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.SpUtils;
import com.jsz.peini.utils.ZzUtils;

import java.io.File;

import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SanPhoneNextActivity extends BaseActivity {

    @InjectView(R.id.back_btn)
    RelativeLayout backBtn;
    @InjectView(R.id.regist_sex_alter)
    TextView registSexAlter;
    @InjectView(R.id.regist_sex)
    LinearLayout registSex;
    @InjectView(R.id.san_sex)
    RelativeLayout sanSex;
    @InjectView(R.id.regist_birthday_alter)
    TextView registBirthdayAlter;
    @InjectView(R.id.regist_birthday)
    LinearLayout registBirthday;
    @InjectView(R.id.san_birthday)
    RelativeLayout sanBirthday;
    @InjectView(R.id.regist_address_alter)
    TextView registAddressAlter;
    @InjectView(R.id.regist_address)
    LinearLayout registAddress;
    @InjectView(R.id.san_position)
    RelativeLayout sanPosition;
    @InjectView(R.id.san_success_login)
    Button sanSuccessLogin;
    @InjectView(R.id.san_login)
    TextView san_login; //这个是是显示 text顶部文本的空间
    private DatePicker picker;


    private String nowProvince = "130000";
    private String nowCity = "130100";
    private String birthday = "1995-05-21";
    private String mAge = "15";
    String sex = "1";//性别默认


    private String mSanToken; //三方返回的took
    private String phone; //手机号
    private String mPlatform; //三方平台
    private SanPhoneNextActivity mActivity;
    public String mProfile_image_url;
    public String sanName;

    @Override
    public int initLayoutId() {
        return R.layout.activity_san_login_phone_next;
    }

    @Override
    public void initView() {
        mActivity = SanPhoneNextActivity.this;
        if (!CacheActivity.activityList.contains(mActivity)) {
            CacheActivity.addActivity(mActivity);
        }
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        mSanToken = intent.getStringExtra("mSanToken");
        mPlatform = intent.getStringExtra("mPlatform");
        sanName = intent.getStringExtra("sanName");
        mProfile_image_url = intent.getStringExtra("mProfile_image_url");
        final String codephone = ZzUtils.phone(phone);
        LogUtil.i("三方注册完成界面", "手机号:" + phone + "\n token \n" + mSanToken + "\nmProfile_image_url\n" + mProfile_image_url);
        san_login.setText("欢迎来自" + mPlatform + "登录的" + codephone + ",您好!");

    }

    @OnClick({R.id.san_sex, R.id.regist_sex, R.id.san_birthday, R.id.san_position, R.id.san_success_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.regist_sex:
            case R.id.san_sex:
                selectManOrWoMen();
                break;
            case R.id.san_birthday:
                break;
            case R.id.san_position:
                break;
            case R.id.san_success_login:
                sanLogIn();
                break;
        }
    }


    private void selectManOrWoMen() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.select_man_or_woman, null);
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);
        // 在底部显示
        window.showAtLocation(SanPhoneNextActivity.this.findViewById(R.id.san_start),
                Gravity.BOTTOM, 0, 0);
        view.findViewById(R.id.item_popupwindows_man).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex = "1";
                LogUtil.i("三方注册性别选择", "男");
                window.dismiss();
            }
        });
        view.findViewById(R.id.item_popupwindows_woman).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex = "2";
                LogUtil.i("三方注册性别选择", "女");
                window.dismiss();
            }
        });
        view.findViewById(R.id.item_popupwindows_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
                LogUtil.i("三方注册性别选择", "取消");
            }
        });
    }

    /*三方登陆接口*/
    private void sanLogIn() {
        File file = new File(mProfile_image_url);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        LoginRetrofitHttp.getHttp().registerUserLogin(mSanToken, sex, birthday, nowProvince, nowCity, mAge, phone, "1", sanName, part)
                .enqueue(new Callback<SanLoginSuccess>() {
                    @Override
                    public void onResponse(Call<SanLoginSuccess> call, Response<SanLoginSuccess> response) {
                        SanLoginSuccess loginSuccess = response.body();
                        if (response.isSuccessful() && loginSuccess.getResultCode() == 1) {
                            LogUtil.d("三方注册成功的信息", loginSuccess.toString());
                            SpUtils.put(mActivity, "mUserToken", loginSuccess.getUserToken());
                            SpUtils.put(mActivity, "id", loginSuccess.getUserInfo().getId());
                            SpUtils.put(mActivity, "userLoginId", loginSuccess.getUserInfo().getUserLoginId());
                            SpUtils.put(mActivity, "nickname", loginSuccess.getUserInfo().getNickname());
                            SpUtils.put(mActivity, "imageHead", loginSuccess.getUserInfo().getImageHead());
                            SpUtils.put(mActivity, "sex", loginSuccess.getUserInfo().getSex() + "");
                            Intent intent = new Intent(mActivity, HomeActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<SanLoginSuccess> call, Throwable t) {
                        LogUtil.d("三方注册失败的信息", t.getMessage());
                    }
                });
    }

}
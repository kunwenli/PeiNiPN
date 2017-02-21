package com.jsz.peini.ui.activity.login;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.CrtyBean;
import com.jsz.peini.model.login.GainSmsBean;
import com.jsz.peini.model.login.LoginSuccess;
import com.jsz.peini.model.login.VerificationSmsBean;
import com.jsz.peini.presenter.login.LoginRetrofitHttp;
import com.jsz.peini.presenter.setting.SettingService;
import com.jsz.peini.ui.activity.home.HomeActivity;
import com.jsz.peini.ui.view.Popwindou;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.PeiNiUtils;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.SpUtils;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.utils.UiUtils;
import com.jsz.peini.utils.time.TimeUtils;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by zhangxu on 2016/11/19.
 */
public class RegistActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.regist_sex_alter) //性别的选择
            TextView registSexAlter;
    @InjectView(R.id.regist_sex)
    LinearLayout registSex;
    @InjectView(R.id.regist_birthday_alter) //生日的标签 必填
            TextView registBirthdayAlter;
    @InjectView(R.id.regist_birthday)
    LinearLayout registBirthday;
    /**
     * 居住地
     */
    @InjectView(R.id.regist_address_alter)
    TextView registAddressAlter;
    @InjectView(R.id.regist_address)
    LinearLayout registAddress;
    @InjectView(R.id.regist_toolbar)
    Toolbar regist_toolbar;
    private LinearLayout infoCodeLayout;//完善资料
    private LinearLayout phone_code_layout;//手机验证
    private LinearLayout set_password_layout;//设置密码
    private TextView phone_code_txt;//手机验证文本
    private TextView set_password_txt;//设置密码文本
    private TextView organizing_data_txt;//完善资料文本
    private EditText phone_edt;//手机号输入
    private EditText code_edt;//验证码输入
    private Button code_btn;//获取验证码
    private Button next_btn;//下一步
    private RelativeLayout show_password_layout;
    private RelativeLayout show_password_again_layout;
    private Button password_show;
    private Button password_disappear;
    private Button password_clean;
    private Button password_again_show;
    private Button password_again_disappear;
    private Button password_again_clean;
    private EditText password_edt;
    private EditText password_again_edt;
    String allStr = "";
    private TextView service;
    private TextView text;
    private RegistActivity mActivity;
    /*========================================*/
    String userPassword; //用户密码
    String userNaem; //用户账号
    private String nowProvince = "130000";
    private String nowCity = "130100";
    private String birthday = "1995-05-21";
    private String sex = "1";//性别默认
    public String age = "19";
    public CrtyBean mCrtyBean;

    /*=========================================*/
    @Override
    public int initLayoutId() {
        return R.layout.regist_activity;
    }

    @Override
    public void initView() {
        mActivity = RegistActivity.this;
        regist_toolbar.setTitle("");
        setSupportActionBar(regist_toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回????
        regist_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        registAddressAlter.setText("河北省 石家庄市");
        initViews();
    }

    @Override
    public void initData() {
        initCity();
    }

    private TextWatcher textWatcherPhoneCode = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (phone_edt.length() == 11 && code_edt.length() == 6) {
                allStr = "1";
                VerificationSms();
            } else {
                next_btn.setBackgroundResource(R.drawable.nocheckbutton);
                next_btn.setEnabled(false);
            }
        }
    };
    private TextWatcher textWatcherSetPassword = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            //设置密码的 是否显示
            boolean showA = password_edt.length() > 0;
            password_clean.setVisibility(showA ? View.VISIBLE : View.GONE);
            show_password_layout.setVisibility(showA ? View.VISIBLE : View.GONE);
            //再次确定设置密码的是 否显示
            boolean showB = password_again_edt.length() > 0;
            password_again_clean.setVisibility(showB ? View.VISIBLE : View.GONE);
            show_password_again_layout.setVisibility(showB ? View.VISIBLE : View.GONE);
            if (password_edt.length() >= 6 && password_again_edt.length() >= 6 && password_edt.getText().toString().trim().equals(password_again_edt.getText().toString().trim())) {
                next_btn.setBackgroundResource(R.drawable.checkbutton); //这个是button  下一步的按钮
                next_btn.setEnabled(true);
                allStr = "2";
            } else {
                next_btn.setBackgroundResource(R.drawable.nocheckbutton);  //为空 就是不可以点击
                next_btn.setEnabled(false);
            }

        }
    };

    @OnClick({R.id.regist_sex, R.id.regist_birthday, R.id.regist_address})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_btn:
                if (allStr.equals("1")) {
                    phone_code_layout.setVisibility(View.GONE);
                    set_password_layout.setVisibility(View.VISIBLE);
                    phone_code_txt.setTextColor(Color.GRAY);
                    set_password_txt.setTextColor(Color.BLACK);
                    next_btn.setBackgroundResource(R.drawable.nocheckbutton);
                    next_btn.setEnabled(true);
                }
                if (allStr.equals("2")) {
                    phone_code_layout.setVisibility(View.GONE);
                    set_password_layout.setVisibility(View.GONE);
                    service.setVisibility(View.GONE);
                    text.setText("我们不会以任何形式泄露您的个人信息");
                    infoCodeLayout.setVisibility(View.VISIBLE);
                    phone_code_txt.setTextColor(Color.GRAY);
                    set_password_txt.setTextColor(Color.GRAY);
                    organizing_data_txt.setTextColor(Color.BLACK);
                    next_btn.setBackgroundResource(R.drawable.checkbutton);
                    next_btn.setText("完成注册");
                    next_btn.setEnabled(true);
                    allStr = "3";
                    return;
                }
                if (allStr.equals("3")) {
                    userNaem = phone_edt.getText().toString().trim();
                    userPassword = password_again_edt.getText().toString().trim();
                    initRegister(userNaem, userPassword);
                }
                break;
            case R.id.password_disappear:
                password_edt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                password_show.setVisibility(View.VISIBLE);
                password_disappear.setVisibility(View.GONE);
                break;
            case R.id.password_show:
                password_edt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                password_show.setVisibility(View.GONE);
                password_disappear.setVisibility(View.VISIBLE);
                break;
            case R.id.password_clean:
                password_edt.setText("");
                password_edt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                password_show.setVisibility(View.GONE);
                password_disappear.setVisibility(View.VISIBLE);
                break;
            case R.id.password_again_show:
                password_again_edt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                password_again_show.setVisibility(View.GONE);
                password_again_disappear.setVisibility(View.VISIBLE);
                break;
            case R.id.password_again_disappear:
                password_again_edt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                password_again_show.setVisibility(View.VISIBLE);
                password_again_disappear.setVisibility(View.GONE);
                break;
            case R.id.password_again_clean:
                password_again_edt.setText("");
                password_again_edt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                password_again_show.setVisibility(View.GONE);
                password_again_disappear.setVisibility(View.VISIBLE);
                break;
            case R.id.service:
                Intent intent = new Intent(this, ServiceActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.regist_sex: //选择性别
                ShowPopWindow();
                PeiNiUtils.getOffKeyset(mActivity);
                break;
            case R.id.regist_birthday: //选择出生日期

                break;
            case R.id.regist_address: //选择地址的按钮
                if (mCrtyBean != null) {
                    if (mCrtyBean.getAreaCity().size() > 1) {
                        popsselector(mCrtyBean);
                    } else {
                        ToastUtils.showToast(mActivity, "目前只在石家庄地区开放");
                    }
                }
                break;
            default:
                break;
        }
    }

    private void popsselector(CrtyBean crtyBean) {
        Popwindou popwindou = new Popwindou(mActivity, UiUtils.inflate(mActivity, R.layout.activity_filter));
        View view = UiUtils.inflate(mActivity, R.layout.pop_tow_selector);
        popwindou.init(view, Gravity.BOTTOM, true);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.holoBorderColor = getResources().getColor(R.color.backgroundf1f1f1);
        style.selectedTextSize = 20;
        style.textSize = 18;
        style.textAlpha = 0.5f;
        style.selectedTextZoom = 5;

        WheelView mainWheelView = (WheelView) view.findViewById(R.id.main_wheelview);
        mainWheelView.setWheelAdapter(new ArrayWheelAdapter(this));
        mainWheelView.setSkin(WheelView.Skin.Holo);
        mainWheelView.setWheelSize(5);
        mainWheelView.setWheelData(createMainDatas());
        mainWheelView.setStyle(style);

        WheelView subWheelView = (WheelView) view.findViewById(R.id.sub_wheelview);
        subWheelView.setWheelAdapter(new ArrayWheelAdapter(this));
        subWheelView.setSkin(WheelView.Skin.Holo);
        subWheelView.setWheelSize(5);
        subWheelView.setWheelData(createSubDatas().get(createMainDatas().get(mainWheelView.getSelection())));
        subWheelView.setStyle(style);
        mainWheelView.join(subWheelView);
        mainWheelView.joinDatas(createSubDatas());

        mainWheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                LogUtil.d("这个是城市选择的监听" + position);
            }
        });
        subWheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                LogUtil.d("这个是市区选择的监听" + position);
            }
        });

    }

    List<String> ProvinceName; //城市

    List<String> cityName;//市区

    private List<String> createMainDatas() {
        ProvinceName = new ArrayList<>();
        List<CrtyBean.AreaCityBean> areaCity = mCrtyBean.getAreaCity();
        for (int i = 0; i < areaCity.size(); i++) {
            ProvinceName.add(areaCity.get(i).getProvinceName());
        }
        return ProvinceName;
    }

    private HashMap<String, List<String>> createSubDatas() {
        HashMap<String, List<String>> map = new HashMap<>();
        cityName = new ArrayList<>();
        List<CrtyBean.AreaCityBean> areaCity = mCrtyBean.getAreaCity();
        for (int i = 0; i < areaCity.size(); i++) {
            List<CrtyBean.AreaCityBean.ProvinceObjectBean> provinceObject = areaCity.get(i).getProvinceObject();
            for (int j = 0; j < provinceObject.size(); j++) {
                cityName.add(provinceObject.get(j).getCityName());
            }
        }
        for (int i = 0; i < ProvinceName.size(); i++) {
            map.put(ProvinceName.get(i), cityName);
        }
        return map;
    }


    /**
     * 访问城市
     */
    private void initCity() {
        RetrofitUtil.createService(SettingService.class)
                .getCityList(mUserToken)
                .enqueue(new Callback<CrtyBean>() {
                    @Override
                    public void onResponse(Call<CrtyBean> call, Response<CrtyBean> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getResultCode() == 1) {
                                LogUtil.d("地址列表请求成功" + response.body().toString());
                                mCrtyBean = response.body();
                            } else {
                                LogUtil.d("地址列表请求失败" + response.body().toString());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CrtyBean> call, Throwable t) {
                        LogUtil.d("地址列表请求失败" + t.getMessage());
                    }
                });
    }

    private void gainSms(String trim) {
        LoginRetrofitHttp.getHttp().smsSendRegister(trim).
                enqueue(new Callback<GainSmsBean>() {
                    @Override
                    public void onResponse(Call<GainSmsBean> call, Response<GainSmsBean> response) {
                        if (response.isSuccessful()) {
                            LogUtil.i("获取验证码是否成功", "" + response.body().toString());
                            ToastUtils.showToast(mActivity, response.body().getResultDesc());
                        }
                    }

                    @Override
                    public void onFailure(Call<GainSmsBean> call, Throwable t) {
                        LogUtil.i("获取验证码是否成功", "" + "获取验证码失败的借口");
                    }
                });
    }

    /*验证验证码借口*/
    private void VerificationSms() {
        LoginRetrofitHttp.getHttp().checkSmsCode(phone_edt.getText().toString().trim(), code_edt.getText().toString().trim())
                .enqueue(new Callback<VerificationSmsBean>() {
                    @Override
                    public void onResponse(Call<VerificationSmsBean> call, Response<VerificationSmsBean> response) {
                        if (response.isSuccessful() && response.body().getResultCode() == 1) {
                            ToastUtils.showToast(mActivity, "验证码验证成功");
                            LogUtil.i("短信验证接口!", "" + response.body().getResultCode());
                            next_btn.setEnabled(true);
                            next_btn.setBackgroundResource(R.drawable.checkbutton);
                        } else {
                            next_btn.setEnabled(false);
                            LogUtil.i("验证码验证后", "验证失败的验证码");
                        }

                    }

                    @Override
                    public void onFailure(Call<VerificationSmsBean> call, Throwable t) {
                        next_btn.setEnabled(false);
                        LogUtil.i("验证码验证后", "验证失败的验证码");
                    }
                });
    }

    /**
     * 请求网络实现注册
     *
     * @param userNaem
     * @param userPassword
     */
    private void initRegister(final String userNaem, final String userPassword) {
        LoginRetrofitHttp.getHttp().regisetr(userNaem, userPassword, sex, birthday, nowProvince, nowCity, age)
                .enqueue(new Callback<LoginSuccess>() {
                    @Override
                    public void onResponse(Call<LoginSuccess> call, Response<LoginSuccess> response) {
                        if (response.isSuccessful() && response.body().getResultCode() == 1) {
                            LoginSuccess loginSuccess = response.body();
                            LogUtil.d("注册成功" + loginSuccess.toString());
                            SpUtils.put(mActivity, "username", userNaem);
                            SpUtils.put(mActivity, "password", userPassword);
                            SpUtils.put(mActivity, "mUserToken", loginSuccess.getUserToken());
                            SpUtils.put(mActivity, "id", loginSuccess.getUserInfo().getId());
                            SpUtils.put(mActivity, "userLoginId", loginSuccess.getUserInfo().getUserLoginId());
                            SpUtils.put(mActivity, "nickname", loginSuccess.getUserInfo().getNickname());
                            SpUtils.put(mActivity, "imageHead", loginSuccess.getUserInfo().getImageHead());
                            SpUtils.put(mActivity, "sex", loginSuccess.getUserInfo().getSex() + "");
                            Intent intent = new Intent(mActivity, HomeActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            LogUtil.d("错误的注册信息" + response.body().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginSuccess> call, Throwable t) {
                        LogUtil.d("注册失败的错误信息 " + t.getMessage());
                    }
                });
    }

    private void ShowPopWindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.select_man_or_woman, null);
        final Popwindou window = new Popwindou(mActivity, mActivity.findViewById(R.id.regist_start));
        window.init(view, Gravity.BOTTOM, true);
        view.findViewById(R.id.select_guanbi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
            }
        });
        view.findViewById(R.id.item_popupwindows_man).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex = "1";//设置男
                registSexAlter.setText("男");
                window.dismiss();
            }
        });
        view.findViewById(R.id.item_popupwindows_woman).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex = "2"; //设置女
                registSexAlter.setText("女");
                window.dismiss();
            }
        });
        view.findViewById(R.id.item_popupwindows_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
            }
        });
    }

    private void initViews() {
        phone_code_layout = (LinearLayout) findViewById(R.id.phone_code_layout);
        set_password_layout = (LinearLayout) findViewById(R.id.set_password_layout);
        infoCodeLayout = (LinearLayout) findViewById(R.id.info_code_layout);
        text = (TextView) findViewById(R.id.regist_text);
        phone_code_txt = (TextView) findViewById(R.id.phone_code_txt);
        set_password_txt = (TextView) findViewById(R.id.set_password_txt);
        organizing_data_txt = (TextView) findViewById(R.id.organizing_data_txt);
        phone_edt = (EditText) findViewById(R.id.phone_edt);
        code_edt = (EditText) findViewById(R.id.code_edt);
        code_btn = (Button) findViewById(R.id.code_btn);
        code_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(mActivity, "请稍等,正在验证验证码");
                gainSms(phone_edt.getText().toString().trim());
                TimeUtils.TimeCount(code_btn, 60000, 1000);
            }
        }); //获取短信验证码的按钮
        next_btn = (Button) findViewById(R.id.next_btn);
        next_btn.setOnClickListener(this);
        phone_edt.addTextChangedListener(textWatcherPhoneCode);
        code_edt.addTextChangedListener(textWatcherPhoneCode);
        //服务条款
        service = (TextView) findViewById(R.id.service);
        service.setOnClickListener(this);

        /**
         * 设置密码
         * */
        show_password_layout = (RelativeLayout) findViewById(R.id.show_password_layout);
        show_password_again_layout = (RelativeLayout) findViewById(R.id.show_password_again_layout);
        password_again_edt = (EditText) findViewById(R.id.password_again_edt);
        password_edt = (EditText) findViewById(R.id.password_edt);
        password_show = (Button) findViewById(R.id.password_show);
        password_disappear = (Button) findViewById(R.id.password_disappear);
        password_clean = (Button) findViewById(R.id.password_clean);
        password_again_show = (Button) findViewById(R.id.password_again_show);
        password_again_disappear = (Button) findViewById(R.id.password_again_disappear);
        password_again_clean = (Button) findViewById(R.id.password_again_clean);
        password_edt.addTextChangedListener(textWatcherSetPassword);
        password_again_edt.addTextChangedListener(textWatcherSetPassword);
        password_show.setOnClickListener(this);
        password_disappear.setOnClickListener(this);
        password_clean.setOnClickListener(this);
        password_again_show.setOnClickListener(this);
        password_again_disappear.setOnClickListener(this);
        password_again_clean.setOnClickListener(this);
    }
}


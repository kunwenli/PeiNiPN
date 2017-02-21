package com.jsz.peini.ui.activity.login.password;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.login.GainSmsBean;
import com.jsz.peini.model.login.VerificationSmsBean;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.presenter.login.LoginService;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.time.TimeUtils;

import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhangxu on 2016/11/21.
 */
public class FindPasswordActivity extends BaseActivity implements View.OnClickListener {
    private EditText phone_edt;  // 手机号
    private EditText code_edt; // 验证码
    private Button next_btn;
    @InjectView(R.id.find_toolbar)
    Toolbar find_toolbar;
    Intent intent;
    private Button code_btn;
    private String trim;
    private String trim1;


    @Override
    public int initLayoutId() {
        return R.layout.find_password_activity;
    }

    @Override
    public void initView() {
        find_toolbar.setTitle("");
        setSupportActionBar(find_toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回????
        find_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Init();
    }

    private void Init() {
        phone_edt = (EditText) findViewById(R.id.phone_edt);
        code_edt = (EditText) findViewById(R.id.code_edt);
        next_btn = (Button) findViewById(R.id.next_btn);
        phone_edt.addTextChangedListener(textWatcher);
        code_edt.addTextChangedListener(textWatcher);
        next_btn.setOnClickListener(this);
        code_btn = (Button) findViewById(R.id.code_btn);
        code_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gainSms();
                TimeUtils.TimeCount(code_btn, 60000, 1000);
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (phone_edt.length() == 11 && code_edt.length() == 6) {
                next_btn.setBackgroundResource(R.drawable.checkbutton);
                next_btn.setEnabled(true);
            } else {
                next_btn.setBackgroundResource(R.drawable.nocheckbutton);
                next_btn.setEnabled(false);
            }

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_btn:
                trim1 = code_edt.getText().toString().trim();
                trim = phone_edt.getText().toString().trim();
                if (TextUtils.isEmpty(trim) && TextUtils.isEmpty(trim1)) {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                VerificationSms(trim, trim1);
                break;
            default:
                break;
        }
    }

    /*获取验证码接口*/
//    http://192.168.150.182:8080/pnservice/smsSendRegister?userName=15544771389
//    http://192.168.150.182:8080/pnservice/smsSendRegister?userName=123456789
    private void gainSms() {
        RetrofitUtil.createService(LoginService.class).smsSendFind(phone_edt.getText().toString().trim())
                .enqueue(new Callback<GainSmsBean>() {
                    @Override
                    public void onResponse(Call<GainSmsBean> call, Response<GainSmsBean> response) {
                        LogUtil.i("获取验证码是否成功", "" + response.body().toString());

                    }

                    @Override
                    public void onFailure(Call<GainSmsBean> call, Throwable t) {

                    }
                });
    }

    /*验证验证码借口*/
    private void VerificationSms(final String trim, final String trim1) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IpConfig.HttpPeiniIp)
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginService loginService = retrofit.create(LoginService.class);//这里采用的是Java的动态代理模式
        Call<VerificationSmsBean> verificationSmsBeanCall = loginService.checkSmsCode(trim, trim1);
        LogUtil.i("这个是找回密码的借口", "手机号和验证码的验证" + trim + " " + trim1);
        verificationSmsBeanCall.enqueue(new Callback<VerificationSmsBean>() {
            @Override
            public void onResponse(Call<VerificationSmsBean> call, Response<VerificationSmsBean> response) {
                LogUtil.i("短信验证接口!", "" + response.body().getResultCode());
                if (response.body().getResultCode() == 1) {
                    intent = new Intent(FindPasswordActivity.this, RemountActivity.class);
                    intent.putExtra("phone", trim);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(FindPasswordActivity.this, "验证码输入错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VerificationSmsBean> call, Throwable t) {

            }
        });
    }

}

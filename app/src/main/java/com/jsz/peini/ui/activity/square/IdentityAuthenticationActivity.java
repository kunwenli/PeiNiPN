package com.jsz.peini.ui.activity.square;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.square.IDentityBean;
import com.jsz.peini.presenter.square.SquareRetrofitHttp;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.ZzUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by th on 2017/1/3.
 */
public class IdentityAuthenticationActivity extends BaseActivity {
    @InjectView(R.id.name)
    EditText mName;
    @InjectView(R.id.identity)
    EditText mIdentity;
    @InjectView(R.id.send)
    Button mSend;
    @InjectView(R.id.id_toolbar)
    Toolbar mIdToolbar;

    @Override
    public int initLayoutId() {
        return R.layout.activity_identity_authentication;
    }

    @Override
    public void initView() {
        super.initView();
        mIdToolbar.setTitle("");
        setSupportActionBar(mIdToolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回????
        mIdToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString().trim();
                String id = mIdentity.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    LogUtil.d(getLocalClassName(), "输入名字");
                    return;
                }
                if (TextUtils.isEmpty(id)) {
                    LogUtil.d(getLocalClassName(), "输入身份证号");
                    return;
                }
                if (!ZzUtils.checkIdCard(id)) {
                    LogUtil.d(getLocalClassName(), "输入的身份证号有误");
                    return;
                }
                SquareRetrofitHttp.getHttp().isIdcard(mUserToken, name, id).enqueue(new Callback<IDentityBean>() {
                    @Override
                    public void onResponse(Call<IDentityBean> call, Response<IDentityBean> response) {
                        if (response.isSuccessful() && response.body().getResultCode() == 1) {
                            LogUtil.d(getLocalClassName(), "身份认证成功---------");
                            finish();
                        } else {
                            LogUtil.d(getLocalClassName(), "身份认证失败");
                        }
                    }

                    @Override
                    public void onFailure(Call<IDentityBean> call, Throwable t) {
                        LogUtil.d(getLocalClassName(), "身份认证失败" + t.getMessage());
                    }
                });
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}

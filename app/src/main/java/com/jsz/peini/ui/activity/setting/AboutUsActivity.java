package com.jsz.peini.ui.activity.setting;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kunwe on 2016/12/6.
 */
public class AboutUsActivity extends BaseActivity {
    @InjectView(R.id.aboutus_toolbar)
    Toolbar aboutusToolbar;
    @InjectView(R.id.aboutus_e_mail)
    TextView aboutusEMail;
    @InjectView(R.id.aboutus_peini)
    TextView aboutusPeini;
    @InjectView(R.id.aboutus_weixin)
    TextView aboutusWeixin;
    @InjectView(R.id.aboutus_pingjia)
    TextView aboutusPingjia;
    @InjectView(R.id.aboutus_ideas)
    TextView aboutusIdeas;

    @Override
    public int initLayoutId() {
        return R.layout.activity_aboutus;
    }

    @Override
    public void initView() {
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

    @OnClick({R.id.aboutus_e_mail, R.id.aboutus_peini, R.id.aboutus_weixin, R.id.aboutus_pingjia, R.id.aboutus_ideas})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.aboutus_e_mail: //邮箱
                break;
            case R.id.aboutus_peini: //官网
                break;
            case R.id.aboutus_weixin: //微信
                break;
            case R.id.aboutus_pingjia: //评价
                break;
            case R.id.aboutus_ideas: //反馈
                break;
        }
    }
}

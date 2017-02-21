package com.jsz.peini.ui.activity.news;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.ui.adapter.news.SettingNewsRecyclerviewAdapter;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import butterknife.InjectView;

/**
 * Created by kunwe on 2016/12/5.
 */
public class SettingNesActivity extends BaseActivity {
    @InjectView(R.id.setting_news)
    PullLoadMoreRecyclerView settingnnews;
    @InjectView(R.id.setting_news_toolbar)
    Toolbar setting_news_toolbar;
    private SettingNewsRecyclerviewAdapter settingNewsRecyclerviewAdapteradapter;

    @Override
    public int initLayoutId() {
        return R.layout.activity_settingnews;
    }

    @Override
    public void initView() {
        setting_news_toolbar.setTitle("");
        setSupportActionBar(setting_news_toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回????
        setting_news_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        settingNewsRecyclerviewAdapteradapter = new SettingNewsRecyclerviewAdapter(this);
        settingnnews.setAdapter(settingNewsRecyclerviewAdapteradapter);
        settingnnews.setLinearLayout();
//        不需要下拉刷新
        settingnnews.setPullRefreshEnable(false);
//        不需要上拉刷新
        settingnnews.setPushRefreshEnable(false);
    }
}

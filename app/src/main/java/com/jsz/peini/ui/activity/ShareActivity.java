package com.jsz.peini.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;

import butterknife.InjectView;

/**
 * Created by th on 2016/12/7.
 */
public class ShareActivity extends BaseActivity {
    @InjectView(R.id.share_tollbar)
    Toolbar shareTollbar;
    @InjectView(R.id.share_webview)
    WebView shareWebview;

    @Override
    public int initLayoutId() {
        return R.layout.activity_share;
    }

    @Override
    public void initView() {
        shareTollbar.setTitle("");
        setSupportActionBar(shareTollbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        shareTollbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        shareWebview.getSettings().setJavaScriptEnabled(true);
        shareWebview.loadUrl("http://image.baidu.com/search/wiseala?tn=wiseala&active=1&ie=utf8&from=&fmpage=search&word=%E6%98%8E%E6%98%9F&pos=top");
    }
}

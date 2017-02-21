package com.jsz.peini.ui.activity.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.jsz.peini.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by jinshouzhi on 2016/11/25.
 */
public class ServiceActivity extends AppCompatActivity {

    @InjectView(R.id.back_btn)
    RelativeLayout backBtn;
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.inject(this);
        webview = (WebView) findViewById(R.id.webView);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("file:///android_asset/index.html");
    }


    @OnClick(R.id.back_btn)
    public void onClick() {
        finish();
    }
}

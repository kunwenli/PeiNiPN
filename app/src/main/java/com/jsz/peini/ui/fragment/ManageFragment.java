package com.jsz.peini.ui.fragment;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseFragment;
import com.jsz.peini.utils.UiUtils;

import butterknife.InjectView;

/**
 * Created by kunwe on 2016/11/25.
 * 管理界面
 */

public class ManageFragment extends BaseFragment {
    @InjectView(R.id.web_manage)
    WebView mWebManage;

    @Override
    public View initViews() {
        return UiUtils.inflate(mActivity, R.layout.fragment_manage);
    }

    @Override
    public void initData() {
        //设置WebView属性，能够执行Javascript脚本
        mWebManage.getSettings().setJavaScriptEnabled(true);
        mWebManage.loadUrl("http://image.baidu.com/search/wiseala?tn=wiseala&active=1&ie=utf8&from=&fmpage=search&word=%E6%98%8E%E6%98%9F&pos=top");
        //设置Web视图
        mWebManage.setWebViewClient(new HelloWebViewClient ());
        mWebManage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    if (mWebManage != null && mWebManage.canGoBack()) {
                        mWebManage.goBack();
                    }
                    return true;
                }
                return  false;
            }
        });
    }
    //Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}

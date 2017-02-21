package com.jsz.peini.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.jsz.peini.PeiNiApp;
import com.jsz.peini.R;
import com.jsz.peini.utils.SpUtils;
import com.jsz.peini.widget.Loading.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Created by kunwe on 2016/10/13.
 * 初始化 activity  删除测试my
 * activity 的基类
 */

public abstract class BaseActivity extends AppCompatActivity {
    Context context;
    /**
     * 用户mUserToken
     */
    public String mUserToken;
    /**
     * 用户mId
     */
    public String mId;
    /**
     * 头像
     */
    public String mImageHead;
    /*名字*/
    public String mNickname;
    /**
     * 用户的性别
     */
    public String mSex;
    /**
     * 手机号
     */
    public String mPhone;
    /**
     * 精度
     */
    public String mXpoint;
    /**
     * 维度
     */
    public String mYpoint;

    /**
     * 弹出加载框
     */
    public LoadingDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayoutId());
        ButterKnife.inject(this);
        context = PeiNiApp.context;


        mUserToken = (String) SpUtils.get(context, "mUserToken", "");

        mId = (String) SpUtils.get(context, "id", "");

        mNickname = (String) SpUtils.get(context, "nickname", "");

        mImageHead = (String) SpUtils.get(context, "imageHead", "");

        mSex = (String) SpUtils.get(context, "sex", "");

        mPhone = (String) SpUtils.get(context, "phone", "");

        mXpoint = (String) SpUtils.get(context, "xpoint", "");

        mYpoint = (String) SpUtils.get(context, "ypoint", "");

        mDialog = new LoadingDialog(this, R.layout.view_tips_loading, R.style.ActionSheetDialogStyle);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);

        initView();
        initData();
        initListener();
    }

    public abstract int initLayoutId();

    /**
     * 初始化view
     * 1. 如果使用注解工具，不需要重新该方法
     * 2. 如果是findById ,可以重新改方法
     */

    public void initView() {
        ShowWindows(false);
    }

    public void ShowWindows(boolean b) {
        if (b) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 根据业务需求，去实现该方法，用于初始化数据， 读取数据库，访问网络
     */
    public void initData() {
    }

    protected void initListener() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
}

package com.jsz.peini.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jsz.peini.PeiNiApp;
import com.jsz.peini.R;
import com.jsz.peini.utils.SpUtils;
import com.jsz.peini.widget.Loading.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Created by kunwe on 2016/10/14.
 */

public abstract class BaseFragment extends Fragment {
    public Activity mActivity;//当做Context去使用
    public View mRootView;//fragment的根布局
    public Context mContext;
    public String muId;
    public String mUserToken;
    public String mXpoint;
    public String mYpoint;
    /**
     * 弹出加载框
     */
    public LoadingDialog mDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();//获取fragment所依赖的activity的对象
        mContext = PeiNiApp.context;
        muId = (String) SpUtils.get(mContext, "id", "");
        mUserToken = (String) SpUtils.get(mContext, "mUserToken", "");
        mXpoint = (String) SpUtils.get(mContext, "xpoint", "");

        mYpoint = (String) SpUtils.get(mContext, "ypoint", "");

        mDialog = new LoadingDialog(mActivity, R.layout.view_tips_loading, R.style.ActionSheetDialogStyle);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setText("正在加载");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = initViews();
        ButterKnife.inject(this, mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initListentr();
    }

    public void initListentr() {

    }

    //初始化数据, 子类可以不实现
    public void initData() {

    }

    //初始化布局, 必须由子类来实现
    public abstract View initViews();

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
}

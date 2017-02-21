package com.jsz.peini.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jsz.peini.model.ad.AdModel;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.utils.Bitmap.GlideImgManager;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.List;

/**
 * Created by kunwe on 2016/11/26.
 * 商家的轮播图界面
 */

public class TestNormalAdapter extends StaticPagerAdapter {
    public final List<AdModel.AdvertiseListBean> mAdModels;
    public final Activity mActivity;

    public TestNormalAdapter(Activity activity, List<AdModel.AdvertiseListBean> adModels) {
        mAdModels = adModels;
        mActivity = activity;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        AdModel.AdvertiseListBean advertiseListBean = mAdModels.get(position);
        ImageView view = new ImageView(container.getContext());
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        GlideImgManager.loadImage(mActivity, IpConfig.HttpPic + advertiseListBean.getAdImgUrl(), view);
        return view;
    }

    @Override
    public int getCount() {
        return mAdModels.size();
    }
}

package com.jsz.peini.ui.adapter.square;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.bm.library.PhotoView;
import com.jsz.peini.R;
import com.jsz.peini.model.square.ImageListBean;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.utils.Bitmap.GlideImgManager;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.UiUtils;
import com.jude.rollviewpager.adapter.DynamicPagerAdapter;

import java.util.ArrayList;

/**
 * Created by kunwe on 2016/11/26.
 * 商家的轮播图界面
 */

public class SquarePhotoAdapter extends DynamicPagerAdapter {

    private final ArrayList<ImageListBean> mMlist;
    private final Activity mPhotoView;

    public SquarePhotoAdapter(Activity photoView, ArrayList<ImageListBean> mlist) {
        mMlist = mlist;
        mPhotoView = photoView;
    }

    @Override
    public View getView(ViewGroup container, final int position) {
        View view = UiUtils.inflate(mPhotoView,R.layout.phototview);
        PhotoView imageView = (PhotoView) view.findViewById(R.id.photoview);
        String Pic = IpConfig.HttpPic + mMlist.get(position).getImageSrc();
        LogUtil.d("空间图片的加载地址--" + Pic);
        GlideImgManager.loadImage(mPhotoView, Pic, imageView);
        imageView.enable();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(position);
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        return mMlist.size();
    }

    private OnPhotoItemClickListener mListener;

    public void setPhotoItemClickListener(OnPhotoItemClickListener listener) {
        mListener = listener;
    }

    public interface OnPhotoItemClickListener {
        void onItemClick(int position);
    }
}

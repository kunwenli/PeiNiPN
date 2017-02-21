package com.jsz.peini.ui.adapter.square;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jsz.peini.R;
import com.jsz.peini.model.square.ImageListBean;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.ui.activity.seephoto.SeePhotoActivity;
import com.jsz.peini.ui.adapter.BaseRecyclerViewAdapter;
import com.jsz.peini.utils.Bitmap.GlideImgManager;
import com.jsz.peini.utils.LogUtil;

import java.io.Serializable;
import java.util.ArrayList;

import static com.jsz.peini.utils.UiUtils.dip2px;

/**
 * Created by baiyuliang on 2016-5-27.
 */
public class SquareImageAdapter extends BaseRecyclerViewAdapter {

    private final Context mActivity;
    private final ArrayList<ImageListBean> mList;
    private final DisplayMetrics dm;

    public SquareImageAdapter(Context activity, ArrayList<ImageListBean> list) {
        super(list);
        mActivity = activity;
        mList = list;
        dm = new DisplayMetrics();
        ((Activity) mActivity).getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    @Override
    public void onBindBodyViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        MyViewHolder holder = (MyViewHolder) viewHolder;
        ImageListBean imageListBean = mList.get(position);
        GlideImgManager.loadImage(mActivity, IpConfig.HttpPic + "" + imageListBean.getImageSrc(), holder.imageView);
        LogUtil.i("获取的屏幕尺度", "" + dm.widthPixels);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.i("这个图片的点击事件", position + "条目");
                Intent intent = new Intent(mActivity, SeePhotoActivity.class);
                intent.putExtra("mlist", (Serializable) mList);
                intent.putExtra("position", position);
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder setBodyViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_grid, viewGroup, false);
        //动态设置ImageView的宽高，根据自己每行item数量计算
        //dm.widthPixels-dip2px(20)即屏幕宽度-左右10dp+10dp=20dp再转换为px的宽度，最后/3得到每个item的宽高
//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(((dm.widthPixels - dip2px(mActivity,5)) / 3), ((dm.widthPixels - dip2px(5)) / 3) - UiUtils.dip2px(mActivity, 1));
//        view.setLayoutParams(lp);
        return new MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv);
        }
    }

    OnImageClickListener mListener;

    private void setOnCommentClickListener(OnImageClickListener listener) {
        mListener = listener;
    }

    interface OnImageClickListener {
    }
}

package com.jsz.peini.ui.adapter.square;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jsz.peini.R;
import com.jsz.peini.model.square.LikeListBean;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.ui.adapter.BaseRecyclerViewAdapter;
import com.jsz.peini.utils.LogUtil;
import com.liji.circleimageview.CircleImageView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by th on 2016/12/24.
 */
public class SquareLikeAdapter extends BaseRecyclerViewAdapter {

    private List<LikeListBean> mList;
    private final Context mActivity;

    public SquareLikeAdapter(Context activity, List<LikeListBean> list) {
        super(list);
        mList = list;
        mActivity = activity;
    }

    @Override
    public void onBindBodyViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (mList.size() > 0) {
            String PIC = IpConfig.HttpPic + mList.get(position).getImageHead();
            LogUtil.i("这个是点赞的图像啦啦", "这个是网址点赞图片的" + PIC);
            Glide.with(mActivity)
                    .load(PIC + "")
//                    .placeholder(R.mipmap.ic_launchers)
//                    .error(R.mipmap.ic_launchers)
                    .into(holder.mSquareLikeImageview);
            holder.mSquareLikeImageview.setVisibility(View.VISIBLE);
        } else {
            holder.mSquareLikeImageview.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBindHeadViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        super.onBindHeadViewHolder(viewHolder, position);
        HeadViewHolder holder = (HeadViewHolder) viewHolder;
        holder.square_like_hoder.setVisibility(mList.size() > 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public RecyclerView.ViewHolder setBodyViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.square_head_like, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder setHeadViewHolder(ViewGroup viewGroup, View headView) {
        return new HeadViewHolder(headView);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.square_like_imageview)
        CircleImageView mSquareLikeImageview;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.square_like_hoder)
        LinearLayout square_like_hoder;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);

        }
    }

    OnLikeClickListener mListener;

    private void setOnCommentClickListener(OnLikeClickListener listener) {
        mListener = listener;
    }

    interface OnLikeClickListener {
    }
}

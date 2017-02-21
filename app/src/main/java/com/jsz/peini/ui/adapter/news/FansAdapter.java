package com.jsz.peini.ui.adapter.news;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jsz.peini.R;
import com.jsz.peini.model.news.MyFans;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.ui.activity.news.FansActivity;
import com.jsz.peini.ui.adapter.BaseRecyclerViewAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by th on 2017/1/2.
 */
public class FansAdapter extends BaseRecyclerViewAdapter {
    private final FansActivity mFansActivity;
    private List<MyFans.ObjectListBean> mMyFansList;
    private MyFans mMyFansMode;

    public FansAdapter(List list, FansActivity fansActivity) {
        super(list);
        mFansActivity = fansActivity;
    }

    @Override
    public void onBindBodyViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (mMyFansList.size() > 0 && mMyFansMode != null) {
            MyFans.ObjectListBean listBean = mMyFansList.get(position);
            holder.mNickname.setText(listBean.getNickname() + "");
            holder.mDoubleDesc.setVisibility(View.GONE);
            Glide.with(mFansActivity).load(IpConfig.HttpPic + listBean.getImageHead() + "").into(holder.mImageHead);
        }
    }

    @Override
    public RecyclerView.ViewHolder setBodyViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(mFansActivity).inflate(R.layout.news_view_item, viewGroup, false);
        return new ViewHolder(view);
    }

    public void setMyFansList(List<MyFans.ObjectListBean> myFansList) {
        list = myFansList;
        mMyFansList = myFansList;
        notifyDataSetChanged();
    }

    public void setMyFansMode(MyFans myFansMode) {
        mMyFansMode = myFansMode;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.imageHead)
        ImageView mImageHead;
        @InjectView(R.id.nickname)
        TextView mNickname;
        @InjectView(R.id.doubleDesc)
        TextView mDoubleDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}

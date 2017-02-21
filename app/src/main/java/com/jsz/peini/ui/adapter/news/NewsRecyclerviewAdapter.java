package com.jsz.peini.ui.adapter.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jsz.peini.R;
import com.jsz.peini.model.news.NewsList;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.ui.adapter.BaseRecyclerViewAdapter;
import com.jsz.peini.utils.LogUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by kunwe on 2016/11/28.
 */

public class NewsRecyclerviewAdapter extends BaseRecyclerViewAdapter {


    private final Context mActivity;
    private List<NewsList.ObjectListBean> mObjectList;
    private NewsList mBody;

    public NewsRecyclerviewAdapter(Context activity, List<NewsList.ObjectListBean> objectList, NewsList body) {
        super(objectList);
        mActivity = activity;
        mObjectList = objectList;
        mBody = body;
    }

    @Override
    public void onBindBodyViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (mObjectList.size() > 0) {
            final NewsList.ObjectListBean listBean = mObjectList.get(position);

            String DoubleDesc = listBean.getDoubleDesc() + "";
            holder.mDoubleDesc.setText(DoubleDesc.equals("1") ? "互相关注" : "");
            Glide.with(mActivity).load(IpConfig.HttpPic + listBean.getImageHead()).into(holder.mImageHead);
            holder.mNickname.setText(listBean.getNickname());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LogUtil.d(mActivity.getPackageName(), "news关注列表getId" + listBean.getId() + "关注列表getUserId" + listBean.getUserId());
                    mListener.ItemObjectList();
                }
            });
        }
    }

    @Override
    public void onBindHeadViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        super.onBindHeadViewHolder(viewHolder, position);
        HeadViewHolder holder = (HeadViewHolder) viewHolder;
        if (mBody != null) {
            holder.mFansNum.setText(mBody.getFansNum() + "");
            Glide.with(mActivity).load(IpConfig.HttpPic + mBody.getImageHead() + "").into(holder.mImageHead);
            String string = IpConfig.HttpPic + mBody.getMyImageHead() + "";
            LogUtil.d(mActivity.getPackageName(), "图片地址-----" + string);
            Glide.with(mActivity).load(string).into(holder.mMyImageHead);
            holder.mMyNickname.setText(mBody.getMyNickname() + "");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LogUtil.d(mActivity.getPackageName(), "这个是点击了粉丝的列表________>" + " ");
                    mListener.ItemUserClick();
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder setBodyViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.news_view_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder setHeadViewHolder(ViewGroup viewGroup, View headView) {
        return new HeadViewHolder(headView);
    }

    public void setNewsList(List<NewsList.ObjectListBean> newsList) {
        list = newsList;
        mObjectList = newsList;
        notifyDataSetChanged();
    }

    public void setNewsmBody(NewsList newsmBody) {
        mBody = newsmBody;
        notifyDataSetChanged();
    }

    static

    public class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.imageHead)
        CircleImageView mImageHead;
        @InjectView(R.id.nickname)
        TextView mNickname;
        @InjectView(R.id.doubleDesc)
        TextView mDoubleDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.myImageHead)
        ImageView mMyImageHead;
        @InjectView(R.id.myNickname)
        TextView mMyNickname;
        @InjectView(R.id.imageHead)
        CircleImageView mImageHead;
        @InjectView(R.id.fansNum)
        TextView mFansNum;

        HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    OnItemEntryClickListener mListener;

    public void setOnItemEntryClickListener(OnItemEntryClickListener listener) {
        mListener = listener;
    }

    public interface OnItemEntryClickListener {
        void ItemObjectList();

        void ItemUserClick();
    }
}
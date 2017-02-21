package com.jsz.peini.ui.fragment.detail;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.model.square.ScoreHistoryListBean;
import com.jsz.peini.ui.adapter.BaseRecyclerViewAdapter;
import com.jsz.peini.ui.view.RoundAngleImageView;
import com.jsz.peini.utils.LogUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by th on 2017/1/18.
 */
public class DetailAdapter extends BaseRecyclerViewAdapter {

    public final Activity mActivity;
    public final List<ScoreHistoryListBean.DataBean> mList;

    public DetailAdapter(Activity activity, List<ScoreHistoryListBean.DataBean> list) {
        super(list);
        mActivity = activity;
        mList = list;
    }

    @Override
    public void onBindBodyViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        final ScoreHistoryListBean.DataBean dataBean = mList.get(position);
        holder.mCreateTime.setText(dataBean.getCreateTime() + "");
        holder.mName.setText(dataBean.getName() + "");
        holder.mValNum.setText(dataBean.getValNum() + "积分");
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                LogUtil.d("长按了条女" + dataBean.getHisId());
                mListener.onLongItemClick(dataBean.getHisId(),position);
                return true;
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder setBodyViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_detail, viewGroup, false);
        return new ViewHolder(view);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.imgSrc)
        RoundAngleImageView mImgSrc;
        @InjectView(R.id.name)
        TextView mName;
        @InjectView(R.id.type)
        ImageView mType;
        @InjectView(R.id.valNum)
        TextView mValNum;
        @InjectView(R.id.createTime)
        TextView mCreateTime;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
    OnLongItemClickListener mListener;

    public void setOnLongItemClickListener(OnLongItemClickListener listener) {
        mListener = listener;
    }

    public interface OnLongItemClickListener{
        void onLongItemClick(String hisid, int position);
    }
}

package com.jsz.peini.ui.adapter.search;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.model.filter.HotWordBean;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.utils.UiUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 15089 on 2017/2/14.
 */
public class TaskHot9Adapter extends RecyclerView.Adapter {

    private final Activity mActivity;
    private final List<HotWordBean.DataBean> mList;

    public TaskHot9Adapter(Activity activity, List<HotWordBean.DataBean> list) {
        mActivity = activity;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(UiUtils.inflateRecyclerviewAdapter(mActivity, R.layout.item_task_hot, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final HotWordBean.DataBean dataBean = mList.get(position);
        viewHolder.mItemHot.setText(dataBean.getHotName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.ItemHotClick(dataBean.getId(), dataBean.getHotName(), dataBean.getHotNum());
            }
        });
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.item_hot)
        TextView mItemHot;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    private OnItemHotClickListener mListener;

    public void setOnItemHotClickListener(OnItemHotClickListener listener) {
        mListener = listener;
    }

    public interface OnItemHotClickListener {
        void ItemHotClick(int id, String hotName, int hotNum);
    }
}

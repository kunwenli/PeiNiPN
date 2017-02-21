package com.jsz.peini.ui.adapter.square;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.ui.activity.square.MiBeOverdueCouponActivity;
import com.jsz.peini.utils.ToastUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by th on 2017/2/11.
 */
public class MiCouponRecyclerviewAdapter extends RecyclerView.Adapter {
    private int ITEM_TYPE_HEADER = 0;
    private int ITEM_TYPE_HEADER_TWO = 1;
    private int ITEM_TAIL = 2;
    public final Activity mActivity;
    public final List<String> mList;

    public MiCouponRecyclerviewAdapter(Activity activity, List<String> list) {
        mActivity = activity;
        mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else if (position == 2) {
            return ITEM_TAIL;
        } else {
            return ITEM_TYPE_HEADER_TWO;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            return new ViewHolderHead(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_micoupon_head, parent, false));
        } else if (viewType == ITEM_TAIL) {
            return new ViewHolderTail(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tail_micoupon, parent, false));
        } else {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.micoupon_view_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int tion) {
        if (holder instanceof ViewHolderTail) {
            viewHolderTailData(holder, tion);
        }
    }

    /*底部条女的点击事件*/
    private void viewHolderTailData(RecyclerView.ViewHolder holder, int tion) {
        ViewHolderTail holderTail = (ViewHolderTail) holder;
        holderTail.mOneText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(mActivity, "没有更多优惠券了");
            }
        });
        holderTail.mTowText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.startActivity(new Intent(mActivity, MiBeOverdueCouponActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ViewHolderHead extends RecyclerView.ViewHolder {
        public ViewHolderHead(View view) {
            super(view);
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }

    class ViewHolderTail extends RecyclerView.ViewHolder {
        @InjectView(R.id.one_text)
        TextView mOneText;
        @InjectView(R.id.tow_text)
        TextView mTowText;

        public ViewHolderTail(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

}

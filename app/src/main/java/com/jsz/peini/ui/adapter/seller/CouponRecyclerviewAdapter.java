package com.jsz.peini.ui.adapter.seller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.ui.activity.square.IntegraHelpActivity;
import com.jsz.peini.ui.activity.square.MiCouponActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kunwe on 2016/11/28.
 */

public class CouponRecyclerviewAdapter extends RecyclerView.Adapter {
    private int ITEM_TYPE_HEADER = 0;
    private int ITEM_TYPE_HEADER_TWO = 1;
    private int ITEM_TAIL = 2;
    public final Activity mActivity;
    public final List<String> mList;

    public CouponRecyclerviewAdapter(Activity activity, List<String> list) {
        mActivity = activity;
        mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else if (position == 9) {
            return ITEM_TAIL;
        } else {
            return ITEM_TYPE_HEADER_TWO;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            return new ViewHolderHead(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coupon_head, parent, false));
        } else if (viewType == ITEM_TAIL) {
            return new ViewHolderTail(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tail_coupon, parent, false));
        } else {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.coupon_view_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int tion) {
        if (holder instanceof ViewHolderTail) {
            viewHolderTailData(holder, tion);
        }
    }

    private void viewHolderTailData(RecyclerView.ViewHolder holder, int tion) {
        ViewHolderTail holderTail = (ViewHolderTail) holder;
        holderTail.mOneText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.startActivity(new Intent(mActivity, IntegraHelpActivity.class));
            }
        });
        holderTail.mTowText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.startActivity(new Intent(mActivity, MiCouponActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }

    class ViewHolderHead extends RecyclerView.ViewHolder {
        public ViewHolderHead(View view) {
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
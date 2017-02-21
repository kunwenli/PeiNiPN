package com.jsz.peini.ui.adapter.square;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jsz.peini.R;
import com.jsz.peini.ui.activity.square.TaTaskActivity;

import java.util.List;

/**
 * Created by th on 2017/2/8.
 */
public class TaTaskAdapter extends RecyclerView.Adapter {
    public int ITEM_TYPE_HEADER = 0;
    public int ITEM_TYPE_HEADER_TWO = 1;
    public final List<String> mList;
    public final TaTaskActivity mActivity;

    public TaTaskAdapter(TaTaskActivity activity, List<String> list) {
        mList = list;
        mActivity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else {
            return ITEM_TYPE_HEADER_TWO;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            return new ViewHolderHead(LayoutInflater.from(mActivity).inflate(R.layout.item_head_ta_task, parent, false));
        } else {
            return new ViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.item_ta_task, parent, false));
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }

    class ViewHolderHead extends RecyclerView.ViewHolder {
        public ViewHolderHead(View inflate) {
            super(inflate);
        }
    }
}

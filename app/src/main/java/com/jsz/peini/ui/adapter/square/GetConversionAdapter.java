package com.jsz.peini.ui.adapter.square;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.model.pay.ConversionListBean;
import com.jsz.peini.ui.activity.square.RechargeActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 15089 on 2017/2/17.
 */
public class GetConversionAdapter extends RecyclerView.Adapter {

    private final RechargeActivity mActivity;
    private final List<ConversionListBean.ListBean> mData;

    int GoldChoice = -1;

    public GetConversionAdapter(RechargeActivity activity, List<ConversionListBean.ListBean> data) {
        mActivity = activity;
        mData = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderHead(LayoutInflater.from(mActivity).inflate(R.layout.item_getconversion, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolderHead holderHead = (ViewHolderHead) holder;
        final ConversionListBean.ListBean listBean = mData.get(position);
        final int payNum = listBean.getPayNum();
        final int goldNum = listBean.getGoldNum();
        final int id = listBean.getId();
        if (position == GoldChoice) {
            holderHead.mGoldChoice.setBackgroundResource(R.mipmap.selector_money_true);
        } else {
            holderHead.mGoldChoice.setBackgroundResource(R.mipmap.selector_money_fs);
        }
        holderHead.mGoldText.setText(goldNum + "元购买" + payNum + "金币");
        holderHead.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.itemClick(position, payNum, goldNum, id);
            }
        });
    }

    public void setGoldChoice(int Choice) {
        GoldChoice = Choice;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolderHead extends RecyclerView.ViewHolder {
        @InjectView(R.id.imagejinbi)
        ImageView mImagejinbi;
        @InjectView(R.id.gold_text)
        TextView mGoldText;
        @InjectView(R.id.gold_choice)
        ImageView mGoldChoice;

        public ViewHolderHead(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    private OnitemClickListener mListener;

    public void setitemClickListener(OnitemClickListener listener) {
        mListener = listener;
    }

    public interface OnitemClickListener {
        /**
         * goldNum 多少钱
         */
        void itemClick(int position, int payNum, int goldNum, int id);
    }

}

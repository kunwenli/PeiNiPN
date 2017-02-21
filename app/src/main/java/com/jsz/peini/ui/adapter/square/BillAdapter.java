package com.jsz.peini.ui.adapter.square;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.model.square.MiBillBean;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.ui.activity.square.BillActivity;
import com.jsz.peini.ui.adapter.BaseRecyclerViewAdapter;
import com.jsz.peini.utils.Bitmap.GlideImgManager;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BillAdapter extends BaseRecyclerViewAdapter {
    public final BillActivity mActivity;
    public final List<MiBillBean.OrderInfoList> mList;

    public BillAdapter(BillActivity billActivity, List<MiBillBean.OrderInfoList> list) {
        super(list);
        mActivity = billActivity;
        mList = list;
    }

    @Override
    public void onBindBodyViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        MiBillBean.OrderInfoList orderInfoList = mList.get(position);
        //图片
        GlideImgManager.loadImage(mActivity, IpConfig.HttpPic + orderInfoList.getImgSrc(), holder.mImgSrc);
        //订单编号
        holder.mOrderCode.setText("订单号: " + orderInfoList.getOrderCode());
        //时间
        holder.mOrderTime.setText(orderInfoList.getOrderTime());
        //标题
        holder.mTitle.setText(orderInfoList.getTitle());
        //到店买单
        String orderType = orderInfoList.getOrderType();
        switch (orderType) {
            case "1":
                holder.mOrderType.setText("任务买单");
                break;
            case "3":
                holder.mOrderType.setText("到店买单");
                break;
            case "4":
                holder.mOrderType.setText("打赏");
                break;
            case "5":
                holder.mOrderType.setText("金币转增");
                break;
        }

        //1金币；2微信；3支付宝
        String payType = orderInfoList.getPayType();
        switch (payType) {
            case "1":
                holder.mPayType.setBackgroundResource(R.mipmap.jinbi);
                holder.mPayTypeText.setText("金币消费");
                break;
            case "2":
                holder.mPayType.setBackgroundResource(R.mipmap.weixin);
                holder.mPayTypeText.setText("微信消费");
                break;
            case "3":
                holder.mPayType.setBackgroundResource(R.mipmap.zfb);
                holder.mPayTypeText.setText("支付宝消费");
                break;
        }
        holder.mTotalPay.setText(orderInfoList.getTotalPay());
        holder.mPayMoney.setText(orderInfoList.getPayMoney());
        //-1已取消；2已完成；0待付款 1待评价
        String orderStatus = orderInfoList.getOrderStatus();
        switch (orderStatus) {
            case "-1":
                holder.mOrderStatus.setBackgroundResource(R.mipmap.jinbi);
                holder.mOrderStatusText.setText("已取消");
                break;
            case "2":
                holder.mOrderStatus.setBackgroundResource(R.mipmap.weixin);
                holder.mOrderStatusText.setText("已完成");
                break;
            case "0":
                holder.mOrderStatus.setBackgroundResource(R.mipmap.zfb);
                holder.mOrderStatusText.setText("待付款");
                break;
            case "1":
                holder.mOrderStatus.setBackgroundResource(R.mipmap.zfb);
                holder.mOrderStatusText.setText("待评价");
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder setBodyViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.bill_item, viewGroup, false);
        return new ViewHolder(view);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.orderCode)
        TextView mOrderCode;
        @InjectView(R.id.orderTime)
        TextView mOrderTime;
        @InjectView(R.id.imgSrc)
        ImageView mImgSrc;
        @InjectView(R.id.title)
        TextView mTitle;
        @InjectView(R.id.orderType)
        TextView mOrderType;
        @InjectView(R.id.orderStatus)
        ImageView mOrderStatus;
        @InjectView(R.id.orderStatus_text)
        TextView mOrderStatusText;
        @InjectView(R.id.payType)
        ImageView mPayType;
        @InjectView(R.id.totalPay)
        TextView mTotalPay;
        @InjectView(R.id.payType_text)
        TextView mPayTypeText;
        @InjectView(R.id.payMoney)
        TextView mPayMoney;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}

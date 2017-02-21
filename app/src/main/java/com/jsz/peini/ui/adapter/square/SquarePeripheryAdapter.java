package com.jsz.peini.ui.adapter.square;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.jsz.peini.R;
import com.jsz.peini.ui.activity.square.SquarePeriphery;
import com.jsz.peini.ui.adapter.BaseRecyclerViewAdapter;
import com.jsz.peini.utils.LogUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by th on 2016/12/28.
 */
public class SquarePeripheryAdapter extends BaseRecyclerViewAdapter {
    String TAG = "SquarePeripheryAdapter";
    private final SquarePeriphery mContext;
    private final List<PoiInfo> mList;
    private int mSelect = -1;
    private String mPoiInfoSelete;

    public SquarePeripheryAdapter(SquarePeriphery context, List<PoiInfo> list) {
        super(list);
        mContext = context;
        mList = list;
    }

    @Override
    public void onBindBodyViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        final PoiInfo poiInfo = mList.get(position);
        holder.mSquarePeripheryName.setText(poiInfo.name);
        holder.mSquarePeripheryAddress.setText(poiInfo.address);
        holder.mSquarePeripheryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.i(TAG, "这个是地址位置" + poiInfo.name + "----" + position);
                LatLng location = poiInfo.location;
                double X = location.latitude;
                double Y = location.longitude;
                mListener.ItemSeleteAddressListener(poiInfo.name, position, X, Y);
            }
        });
        if (mSelect == position) {
            holder.mSquarePeripheryAddressSelete.setVisibility(View.VISIBLE);
        } else {
            holder.mSquarePeripheryAddressSelete.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBindHeadViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        super.onBindHeadViewHolder(viewHolder, position);
        HeadViewHolder holder = (HeadViewHolder) viewHolder;
        holder.mSquarePeripheryNoName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.i(TAG, "这个是不显示地址位置" + position);
                mListener.ItemSeleteAddressListener("不显示位置", position, 0f, 0f);
            }
        });
        holder.mSquarePeripheryShowName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.i(TAG, "这个是显示石家庄位置" + position);
                mListener.ItemSeleteAddressListener("石家庄", position, 0f, 0f);
            }
        });
        if (!TextUtils.isEmpty(mPoiInfoSelete)) {
            holder.mSquarePeripheryShowSelete.setVisibility(View.VISIBLE);
            holder.mSquarePeripheryShowSeleteName.setText(mPoiInfoSelete);
            holder.mSquarePeripheryShowSeleteName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LogUtil.i(TAG, "这个是显示选择返回的位置" + position);
                    mListener.ItemSeleteAddressListener(mPoiInfoSelete, position, 0f, 0f);
                }
            });
        } else {
            holder.mSquarePeripheryShowSelete.setVisibility(View.GONE);
        }

    }

    @Override
    public RecyclerView.ViewHolder setBodyViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.square_square_periphery, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder setHeadViewHolder(ViewGroup viewGroup, View headView) {
        return new HeadViewHolder(headView);
    }

    public void setPoiInfoSelete(String poiInfoSelete) {
        mPoiInfoSelete = poiInfoSelete;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.square_periphery_name)
        TextView mSquarePeripheryName;
        @InjectView(R.id.square_periphery_address)
        TextView mSquarePeripheryAddress;
        @InjectView(R.id.square_periphery_item)
        LinearLayout mSquarePeripheryItem;
        @InjectView(R.id.square_periphery_address_selete)
        ImageView mSquarePeripheryAddressSelete;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.square_periphery_onname)
        TextView mSquarePeripheryNoName;
        @InjectView(R.id.square_periphery_showname)
        TextView mSquarePeripheryShowName;
        @InjectView(R.id.square_periphery_show_selete_name)
        TextView mSquarePeripheryShowSeleteName;
        @InjectView(R.id.square_periphery_show_selete)
        LinearLayout mSquarePeripheryShowSelete;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public void setSelect(int Select) {
        mSelect = Select;
    }

    public void setPoiInfoList(List list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public OnClickItemListener mListener;

    public void setListener(OnClickItemListener listener) {
        mListener = listener;
    }

    public interface OnClickItemListener {
        void ItemSeleteAddressListener(String Address, int position, double x, double y);
    }
}

package com.jsz.peini.ui.adapter.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.gen.SerachInfo;
import com.jsz.peini.ui.activity.search.IsSearchActivity;
import com.jsz.peini.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunwe on 2016/12/2.
 */
public class IsSearchRecyclerviewAdapter extends RecyclerView.Adapter<IsSearchRecyclerviewAdapter.Holder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private final IsSearchActivity isSearchActivity; //上下文
    /**
     * 保存的历史记录
     */
    private final List<SerachInfo> serachInfoList;
    private ArrayList<SerachInfo> mDatas = new ArrayList<>();
    private View mHeaderView;

    private OnItemClickListener mListener;

    public IsSearchRecyclerviewAdapter(IsSearchActivity isSearchActivity, List<SerachInfo> serachInfoList) {
        this.isSearchActivity = isSearchActivity;
        this.serachInfoList = serachInfoList;
    }

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void addDatas(List<SerachInfo> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) return new Holder(mHeaderView);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.issearch_item, parent, false);
        return new Holder(layout);
    }


    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
        //强制关闭复用
        viewHolder.setIsRecyclable(true);
        if (getItemViewType(position) == TYPE_HEADER) return;
        /**"id": 3,                      private String Ids;
         "sellerName": "大驴健身",       private String Type;
         "sellerAddress": "友谊公园",    private String Name;
         "sellerType": 104,              private String Address;
         "districtName": "东胜",         private String Preferential;//优选 优惠
         "information": "健身第一",      private String Gold; //金
         "labelsName": "健身房",         private String icenSerach;
         "searchType": 1,                private String icenAddress;
         */
        final int pos = getRealPosition(viewHolder);
        final SerachInfo data = mDatas.get(pos);
        if (pos == 0) {
            viewHolder.issearch_item_title.setVisibility(View.VISIBLE);
        } else {
            viewHolder.issearch_item_title.setVisibility(View.GONE);
        }
        if (viewHolder instanceof Holder) {
            ((Holder) viewHolder).text.setText(data.getType());
            String dataName = data.getName();
            LogUtil.i("这个是 搜索历史数据", dataName + "<dataName + dataName>");
            if (!dataName.equals("null")) {
                ((Holder) viewHolder).issearch_history.setVisibility(View.VISIBLE);
                ((Holder) viewHolder).issearch_history.setText(dataName + "");
            } else {
                ((Holder) viewHolder).issearch_history.setVisibility(View.GONE);
            }
            if (mListener == null) return;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(pos, data);
                }
            });
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mDatas.size() : mDatas.size() + 1;
    }


    class Holder extends RecyclerView.ViewHolder {

        TextView issearch_history;
        TextView text;
        TextView issearch_item_title;

        public Holder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) return;
            text = (TextView) itemView.findViewById(R.id.issearch_text);
            issearch_item_title = (TextView) itemView.findViewById(R.id.issearch_item_title);
            issearch_history = (TextView) itemView.findViewById(R.id.issearch_history);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, SerachInfo data);
    }
}


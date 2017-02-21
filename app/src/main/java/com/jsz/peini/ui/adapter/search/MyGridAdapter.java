package com.jsz.peini.ui.adapter.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseViewHolder;
import com.jsz.peini.model.search.SearchBean;

import java.util.List;

/**
 * @author http://blog.csdn.net/finddreams
 * @Description:gridview的Adapter
 */
public class MyGridAdapter extends BaseAdapter {
    private final List<SearchBean.WordListBean> wordList;
    private Context mContext;
    public String[] img_text = {"转账", "余额宝", "手机充值", "信用卡还款", "淘宝电影", "彩票",
            "当面付", "亲密付", "机票",};

    public MyGridAdapter(Context mContext, List<SearchBean.WordListBean> wordList) {
        super();
        this.mContext = mContext;
        this.wordList = wordList;
    }
    @Override
    public int getCount() {
        return wordList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.grid_item, parent, false);
        }
        TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
        tv.setText(wordList.get(position).getHotName());
        return convertView;
    }

}

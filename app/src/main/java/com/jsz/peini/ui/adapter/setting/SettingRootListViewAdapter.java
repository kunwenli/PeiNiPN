package com.jsz.peini.ui.adapter.setting;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.model.CrtyBean;
import com.jsz.peini.utils.UiUtils;

import java.util.List;

/**
 * Created by th on 2016/12/15.
 */

public class SettingRootListViewAdapter extends BaseAdapter {

    private final List<CrtyBean.AreaCityBean> setiingCity;
    public final Activity mActivity;

    public SettingRootListViewAdapter(Activity activity, List<CrtyBean.AreaCityBean> setiingCity) {
        this.setiingCity = setiingCity;
        mActivity = activity;
    }
    @Override
    public int getCount() {
        return setiingCity.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1;
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view1 = UiUtils.inflate(mActivity,R.layout.setting_listview_item_right);
            holder.item_text = (TextView) view1.findViewById(R.id.seting_crty);
            view1.setTag(holder);
        } else {
            view1 = view;
            holder = (ViewHolder) view1.getTag();
        }
        String cityName = setiingCity.get(i).getProvinceName();
        holder.item_text.setText("" + cityName);
        return view1;
    }
    class ViewHolder {
        TextView item_text;
    }
}

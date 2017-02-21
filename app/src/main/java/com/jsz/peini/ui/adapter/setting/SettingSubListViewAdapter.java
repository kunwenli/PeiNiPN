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

public class SettingSubListViewAdapter extends BaseAdapter {

    private final List<CrtyBean.AreaCityBean.ProvinceObjectBean> provinceObject;
    public final Activity mActivity;

    public SettingSubListViewAdapter(Activity activity, List<CrtyBean.AreaCityBean.ProvinceObjectBean> provinceObject, int i) {
        this.provinceObject = provinceObject;
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return provinceObject.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        // 判断convertView的状态，来达到复用效果
        if (null == convertView) {
            // 如果convertView为空，则表示第一次显示该条目，需要创建一个view
            view = UiUtils.inflate(mActivity,R.layout.setting_listview_item_right);
            //新建一个viewholder对象
            holder = new ViewHolder();
            //将findviewbyID的结果赋值给holder对应的成员变量
            holder.item_text = (TextView) view.findViewById(R.id.seting_crty);
            // 将holder与view进行绑定
            view.setTag(holder);
        } else {
            // 否则表示可以复用convertView
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        // 直接操作holder中的成员变量即可，不需要每次都findViewById
        holder.item_text.setText("" + provinceObject.get(position).getCityName());
        return view;
    }

    class ViewHolder {
        TextView item_text;
    }
}
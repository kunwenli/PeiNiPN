package com.jsz.peini.ui.adapter.news;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jsz.peini.R;
import com.jsz.peini.ui.activity.news.SettingNesActivity;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by kunwe on 2016/11/28.
 */

public class SettingNewsRecyclerviewAdapter extends RecyclerView.Adapter<SettingNewsRecyclerviewAdapter.ViewHolder> {

    private final SettingNesActivity settingNesActivity;

    public SettingNewsRecyclerviewAdapter(SettingNesActivity settingNesActivity) {
        this.settingNesActivity = settingNesActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_news_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(settingNesActivity, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);

        }
    }
}
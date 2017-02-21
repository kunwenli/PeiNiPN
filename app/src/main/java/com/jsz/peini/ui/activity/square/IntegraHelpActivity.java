package com.jsz.peini.ui.activity.square;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.ui.adapter.square.HelpAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by th on 2017/1/17.
 */
public class IntegraHelpActivity extends BaseActivity {
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.help_recyclerview)
    RecyclerView mHelpRecyclerview;
    public IntegraHelpActivity mActivity;
    List<String> mList;

    @Override
    public int initLayoutId() {
        return R.layout.activity_integra_help;
    }

    @Override
    public void initView() {
        mActivity = this;
        mTitle.setText("使用帮助");
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mList = new ArrayList<>();
        mList.add("");
        mList.add("");
        mHelpRecyclerview.setAdapter(new HelpAdapter(mActivity, mList));
        mHelpRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));

    }

}

package com.jsz.peini.ui.activity.square;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.ui.adapter.square.TaTaskAdapter;
import com.jsz.peini.widget.RecyclerView.LoadMoreFooterView;
import com.jsz.peini.widget.RecyclerView.RefreshHeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by th on 2017/2/8.
 */
public class TaTaskActivity extends BaseActivity {

    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.right_button)
    TextView mRightButton;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.swipe_refresh_header)
    RefreshHeaderView mSwipeRefreshHeader;
    @InjectView(R.id.swipe_target)
    RecyclerView mSwipeTarget;
    @InjectView(R.id.swipe_load_more_footer)
    LoadMoreFooterView mSwipeLoadMoreFooter;
    @InjectView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;
    public TaTaskActivity mActivity;
    List<String> mList = new ArrayList<>();

    @Override
    public int initLayoutId() {
        return R.layout.activity_tatask;
    }

    @Override
    public void initView() {
        mActivity = this;
        mTitle.setText("他人任务");
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        mSwipeTarget.setLayoutManager(new LinearLayoutManager(mActivity));
        TaTaskAdapter adapter = new TaTaskAdapter(mActivity, mList);
        mSwipeTarget.setAdapter(adapter);
    }
}

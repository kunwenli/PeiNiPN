package com.jsz.peini.ui.activity.square;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.base.BaseFragment;
import com.jsz.peini.ui.adapter.square.MiTaskAdapter;
import com.jsz.peini.ui.fragment.square.MiTaskAllFragMent;
import com.jsz.peini.ui.fragment.square.MiTaskMeetFragMent;
import com.jsz.peini.ui.fragment.square.MiTaskSendFragMent;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by th on 2017/1/24.
 */
public class MiTaskActivity extends BaseActivity {
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.right_button)
    TextView mRightButton;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.tlayout)
    TabLayout mTlayout;
    @InjectView(R.id.vp)
    ViewPager mVp;
    public MiTaskActivity mActivity;
    public List<String> mListTitle;
    public List<BaseFragment> mFragment;
    public MiTaskAdapter mAdapter;

    @Override
    public int initLayoutId() {
        return R.layout.activity_mitaskactivity;
    }

    @Override
    public void initView() {
        super.initView();
        mActivity = this;
        mTitle.setText("我的任务");
        mRightButton.setText("发布");
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mListTitle = new ArrayList<>();
        mFragment = new ArrayList<>();
        /*标题*/
        mListTitle.add("全部");
        mListTitle.add("发任务");
        mListTitle.add("接任务");
        /*初始化布局*/
        mFragment.add(new MiTaskAllFragMent());
        mFragment.add(new MiTaskSendFragMent());
        mFragment.add(new MiTaskMeetFragMent());
        //设置TabLayout的模式
        mTlayout.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        for (int i = 0; i < mListTitle.size(); i++) {
            mTlayout.addTab(mTlayout.newTab().setText(mListTitle.get(i)));
        }
        mAdapter = new MiTaskAdapter(getSupportFragmentManager(), mFragment, mListTitle);
        mVp.setAdapter(mAdapter);
        mTlayout.setupWithViewPager(mVp);
        /*点击Tablyout 设置选择*/
        mTlayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                mVp.setCurrentItem(position, false);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }
}

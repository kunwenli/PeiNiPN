package com.jsz.peini.ui.activity.square;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.base.BaseFragment;
import com.jsz.peini.ui.activity.news.NewsMapAdapter;
import com.jsz.peini.ui.fragment.detail.DetailAllFragMent;
import com.jsz.peini.ui.fragment.detail.DetailIncomeFragment;
import com.jsz.peini.ui.fragment.detail.DetailPayFragment;
import com.jsz.peini.ui.view.NoScrollViewPager;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.utils.UiUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by th on 2017/1/18.
 */
public class IntegralDetailActivity extends BaseActivity {
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.right_button)
    TextView mRightButton;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.detail_tablayout)
    TabLayout mDetailTablayout;
    @InjectView(R.id.myvp)
    NoScrollViewPager mMyvp;

    public List<Fragment> mFragment;
    public List<String> mListTitle;
    public NewsMapAdapter mAdapter;
    public IntegralDetailActivity mActivity;

    @Override
    public int initLayoutId() {
        return R.layout.activity_integraldetail;
    }

    @Override
    public void initView() {

        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mActivity = this;
        mTitle.setText("积分明细");
        mListTitle = new ArrayList<>();
        mListTitle.add("全部");
        mListTitle.add("收入");
        mListTitle.add("支出");
        mFragment = new ArrayList<>();
        /*初始化布局*/
        mFragment.add(new DetailAllFragMent());
        mFragment.add(new DetailIncomeFragment());
        mFragment.add(new DetailPayFragment());
        //设置TabLayout的模式
        mDetailTablayout.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        for (int i = 0; i < mListTitle.size(); i++) {
            mDetailTablayout.addTab(mDetailTablayout.newTab().setText(mListTitle.get(i)));
        }
        mAdapter = new NewsMapAdapter(getSupportFragmentManager(), mFragment, mListTitle);

        //viewpager加载adapter
        mMyvp.setAdapter(mAdapter);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        mMyvp.setOffscreenPageLimit(1);
        mDetailTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                ToastUtils.showToast(mActivity, position + "");
                mMyvp.setCurrentItem(position, false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        try {
            settab();
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }

    private void settab() throws NoSuchFieldException, IllegalAccessException {
        Class<?> tablayout = mDetailTablayout.getClass();
        Field tabStrip = tablayout.getDeclaredField("mTabStrip");
        tabStrip.setAccessible(true);
        LinearLayout ll_tab = (LinearLayout) tabStrip.get(mDetailTablayout);
        for (int i = 0; i < ll_tab.getChildCount(); i++) {
            View child = ll_tab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.setMarginStart(UiUtils.dip2px(mActivity, 25f));
            params.setMarginEnd(UiUtils.dip2px(mActivity, 25f));
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
}

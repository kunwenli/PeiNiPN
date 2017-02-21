package com.jsz.peini.ui.activity.seephoto;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.square.ImageListBean;
import com.jsz.peini.ui.adapter.square.SquarePhotoAdapter;
import com.jsz.peini.utils.LogUtil;

import java.util.ArrayList;

/**
 * Created by th on 2017/1/12.
 */

public class SeePhotoActivity extends BaseActivity {
    private ViewPager mViewPager;
    private LinearLayout mLinearLayout;
    private ImageView mView;
    private int diatance = 0;
    public SeePhotoActivity mActivity;
    public ArrayList<ImageListBean> mMlist;
    public int mPosition;
    boolean b = true;

    @Override
    public int initLayoutId() {
        return R.layout.activity_see_photo;
    }

    @Override
    public void initView() {
        ShowWindows(true);
        mActivity = this;
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_points);
        mView = (ImageView) findViewById(R.id.v_redpoint);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(15, 15);
        mView.setLayoutParams(params);
        Intent intent = getIntent();
        mMlist = (ArrayList<ImageListBean>) intent.getSerializableExtra("mlist");
        mPosition = intent.getIntExtra("position", 0);

    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        initInfo();
        initEvent();
    }

    /**
     * 初始化数据
     */
    private void initInfo() {
        for (int i = 0; i < mMlist.size(); i++) {
            //添加底部灰点
            ImageView v = new ImageView(mActivity);
            v.setBackgroundResource(R.drawable.dot_nomal);
            //指定其大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
            if (i != 0) {
                params.leftMargin = 15;
            }
            v.setLayoutParams(params);
            mLinearLayout.addView(v);
        }
        SquarePhotoAdapter adapter = new SquarePhotoAdapter(mActivity, mMlist);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(mPosition);
        adapter.setPhotoItemClickListener(new SquarePhotoAdapter.OnPhotoItemClickListener() {
            @Override
            public void onItemClick(int position) {
                finish();
            }
        });
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        /**
         * 当底部红色小圆点加载完成时测出两个小灰点的距离，便于计算后面小红点动态移动的距离
         */
        if (mMlist.size() > 1) {
            mView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    diatance = mLinearLayout.getChildAt(1).getLeft() - mLinearLayout.getChildAt(0).getLeft();
                    LogUtil.d("两点间距", diatance + "测出来了");
                    if (b) {
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mView.getLayoutParams();
                        params.leftMargin = mPosition * diatance;
                        mView.setLayoutParams(params);
                        LogUtil.d("红点在这", Math.round(mPosition * diatance) + "");
                        b = false;
                    }
                }
            });
        }
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //测出页面滚动时小红点移动的距离，并通过setLayoutParams(params)不断更新其位置
                int leftMargin = (int) (diatance * positionOffset) + position * diatance;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mView.getLayoutParams();
                params.leftMargin = leftMargin;
                mView.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }
}

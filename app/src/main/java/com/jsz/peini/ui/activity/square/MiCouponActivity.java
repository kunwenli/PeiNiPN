package com.jsz.peini.ui.activity.square;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.ui.adapter.square.MiCouponRecyclerviewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by kunwe on 2016/12/5.
 */
public class MiCouponActivity extends BaseActivity {


    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.right_button)
    TextView mRightButton;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.coupon_recyclerview)
    RecyclerView mCouponRecyclerview;
    public MiCouponActivity mActivity;
    List<String> mList = new ArrayList<>();
    public MiCouponRecyclerviewAdapter mCouponRecyclerviewAdapter;

    @Override
    public int initLayoutId() {
        return R.layout.activity_coupon;
    }

    @Override
    public void initView() {
        mActivity = this;
        mTitle.setText("我的优惠券");
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        mCouponRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        mCouponRecyclerviewAdapter = new MiCouponRecyclerviewAdapter(mActivity, mList);
        mCouponRecyclerview.setAdapter(mCouponRecyclerviewAdapter);
    }
}

package com.jsz.peini.ui.activity.seller;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.presenter.PayService;
import com.jsz.peini.ui.adapter.seller.CouponRecyclerviewAdapter;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kunwe on 2016/12/5.
 */
public class CouponActivity extends BaseActivity {


    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.right_button)
    TextView mRightButton;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.coupon_recyclerview)
    RecyclerView mCouponRecyclerview;
    public CouponActivity mActivity;
    List<String> mList = new ArrayList<>();
    public CouponRecyclerviewAdapter mCouponRecyclerviewAdapter;

    @Override
    public int initLayoutId() {
        return R.layout.activity_coupon;
    }

    @Override
    public void initView() {
        mActivity = this;
        mTitle.setText("选择优惠券");
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
        mCouponRecyclerviewAdapter = new CouponRecyclerviewAdapter(mActivity, mList);
        mCouponRecyclerview.setAdapter(mCouponRecyclerviewAdapter);

        inItNetWork();
    }

    private void inItNetWork() {
        RetrofitUtil.createService(PayService.class)
                .getCouponInfoByPay(mUserToken, "1", "1212", "12", "1")
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            LogUtil.d("获取可用优惠券" + response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
    }
}

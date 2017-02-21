package com.jsz.peini.ui.activity.square;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.square.CouponInfoUnGet;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.presenter.square.SquareService;
import com.jsz.peini.utils.Bitmap.GlideImgManager;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.RetrofitUtil;

import java.util.List;

import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by th on 2017/1/18.
 */
public class IntegralMessageActivity extends BaseActivity {
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.right_button)
    TextView mRightButton;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    public IntegralMessageActivity mActivity;
    public String mId;
    @InjectView(R.id.couponImg)
    ImageView mCouponImg;
    @InjectView(R.id.couponName)
    TextView mCouponName;
    @InjectView(R.id.couponmoney_rulemoney)
    TextView mCouponmoneyRulemoney;
    @InjectView(R.id.couponmoney_rulemoney_big)
    TextView mCouponmoneyRulemoneyBig;
    @InjectView(R.id.couponDesc)
    TextView mCouponDesc;
    @InjectView(R.id.couponRange)
    TextView mCouponRange;
    @InjectView(R.id.lastDate)
    TextView mLastDate;
    @InjectView(R.id.remindText)
    TextView mRemindText;
    @InjectView(R.id.getNum)
    TextView mGetNum;

    @Override
    public int initLayoutId() {
        return R.layout.activity_integralmessage;
    }

    @Override
    public void initView() {
        mActivity = this;
        mTitle.setText("现金券详情");
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        mId = intent.getStringExtra("id");
        initNetWork();
    }

    /**
     * 网络获取
     */
    private void initNetWork() {
        RetrofitUtil.createService(SquareService.class)
                .getCouponInfo_unGet(mUserToken, mId, "2")
                .enqueue(new Callback<CouponInfoUnGet>() {
                    @Override
                    public void onResponse(Call<CouponInfoUnGet> call, Response<CouponInfoUnGet> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                final CouponInfoUnGet body = response.body();
                                LogUtil.d("现金券详情成功" + body.toString());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        initShowView(body);
                                    }
                                });
                            } else {
                                LogUtil.d("现金券详情失败" + response.body().toString());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CouponInfoUnGet> call, Throwable t) {
                        LogUtil.d("现金券详情失败" + t.getMessage());
                    }
                });
    }

    private void initShowView(CouponInfoUnGet body) {
        CouponInfoUnGet.CouponInfoBean couponInfo = body.getCouponInfo();
        mCouponName.setText(couponInfo.getCouponName() + "");
        mCouponDesc.setText(couponInfo.getCouponDesc() + "");
        mCouponmoneyRulemoney.setText(couponInfo.getCouponMoney() + "元抵" + couponInfo.getRuleMoney() + "元");
//        45元购此券结账时可抵50元现金
        mCouponmoneyRulemoneyBig.setText(couponInfo.getCouponMoney() + "元购此券结账时可抵" + couponInfo.getRuleMoney() + "元现金");
        mLastDate.setText("有效期至 " + couponInfo.getLastDate());
        List<CouponInfoUnGet.CouponInfoBean.CouponRangeBean> couponRange = couponInfo.getCouponRange();
        String couponRangeBean = "";
        for (int i = 0; i < couponRange.size(); i++) {
            couponRangeBean += couponRange.get(i).getName() + "\n";
        }
        mCouponRange.setText(couponRangeBean);
        mRemindText.setText(couponInfo.getRemindText());
        GlideImgManager.loadImage(mActivity, IpConfig.HttpPic + couponInfo.getCouponImg(), mCouponImg);
    }
}

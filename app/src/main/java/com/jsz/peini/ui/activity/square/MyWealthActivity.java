package com.jsz.peini.ui.activity.square;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.square.MyWealthABean;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.presenter.square.SquareService;
import com.jsz.peini.utils.Bitmap.GlideImgManager;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jsz.peini.R.id.integral;

/**
 * Created by th on 2017/1/3.
 */
public class MyWealthActivity extends BaseActivity {
    @InjectView(R.id.imageHead)
    CircleImageView mImageHead;
    @InjectView(R.id.gold)
    TextView mGold;
    @InjectView(R.id.wealth_recharge)
    TextView mWealthRecharge;
    @InjectView(R.id.bill)
    LinearLayout mBill;
    @InjectView(R.id.score)
    TextView mScore;
    @InjectView(integral)
    LinearLayout mIntegral;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.right_button)
    TextView mRightButton;
    @InjectView(R.id.kuntoolbar)
    RelativeLayout mKuntoolbar;
    private MyWealthActivity mActivity;

    @Override
    public int initLayoutId() {
        return R.layout.activity_my_wealth;
    }

    @Override
    public void initView() {
        super.initView();
        mActivity = this;
        mTitle.setText("我的财富");
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initNetWork();
    }

    /**
     * 发起网络访问
     */
    private void initNetWork() {
        LogUtil.d("这个是我的财富界面传的 userId" + mId);
        RetrofitUtil.createService(SquareService.class).getUserGoldAndScore(mId).enqueue(new Callback<MyWealthABean>() {
            @Override
            public void onResponse(Call<MyWealthABean> call, final Response<MyWealthABean> response) {
                int resultCode = response.body().getResultCode();
                if (response.isSuccessful() && resultCode == 1) {
                    String toString = response.body().toString();
                    LogUtil.d("我的富访问成功" + toString);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            isShowMyWealth(response.body());
                        }
                    });
                } else if (resultCode == 9) {
                    LogUtil.d("我的财富访问失败 token 过期 = " + response.body().getResultCode());
                }

            }

            @Override
            public void onFailure(Call<MyWealthABean> call, Throwable t) {

            }
        });
    }

    private void isShowMyWealth(MyWealthABean wealthABean) {
        MyWealthABean.UserInfoBean userInfo = wealthABean.getUserInfo();
        /*头像*/
        String imageHead = userInfo.getImageHead();
        if (StringUtils.isNoNull(imageHead)) {
            String url = IpConfig.HttpPic + imageHead;
            LogUtil.d("我的财富头像" + url);
            GlideImgManager.loadImage(mActivity, url, mImageHead, mSex);
        }
        /*金币*/
        String gold = userInfo.getGold();
        if (StringUtils.isNoNull(gold)) {
            mGold.setText(gold);
        }
        //积分
        String score = userInfo.getScore();
        if (StringUtils.isNoNull(score)) {
            mScore.setText(score);
        }

    }

    @OnClick({R.id.wealth_recharge, R.id.bill, R.id.AReward, R.id.integral})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wealth_recharge:
                LogUtil.d(getLocalClassName(), "充值按钮");
                startActivity(new Intent(mActivity, RechargeActivity.class));
                break;
            case R.id.bill:
                LogUtil.d(getLocalClassName(), "账单明细");
                startActivity(new Intent(mActivity, BillActivity.class));
                break;
            case R.id.AReward:
                LogUtil.d(getLocalClassName(), "打赏");
                startActivity(new Intent(mActivity, BillActivity.class));
                break;
            case integral:
                LogUtil.d("积分兑换");
                startActivity(new Intent(mActivity, IntegralActivity.class));
                break;
        }
    }
}

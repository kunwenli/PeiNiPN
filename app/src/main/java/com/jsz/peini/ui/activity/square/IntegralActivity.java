package com.jsz.peini.ui.activity.square;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.square.CouponInfoListAllUnGetByScore;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.presenter.square.SquareService;
import com.jsz.peini.ui.adapter.square.IntegralAdapter;
import com.jsz.peini.ui.view.SpacesItemDecoration;
import com.jsz.peini.utils.Bitmap.GlideImgManager;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by th on 2017/1/18.
 */
public class IntegralActivity extends BaseActivity {
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.goldList)
    ImageView mGoldList;
    @InjectView(R.id.buyList)
    ImageView mBuyList;
    @InjectView(R.id.integrityList)
    ImageView mIntegrityList;
    @InjectView(R.id.score)
    TextView mScore;
    @InjectView(R.id.recycler)
    RecyclerView mRecyclerView;
    public IntegralActivity mActivity;
    List<CouponInfoListAllUnGetByScore.CouponListBean> mList;
    @InjectView(R.id.more)
    TextView mMore;
    public Intent mIntent;
    @InjectView(R.id.right_button)
    TextView mRightButton;
    public IntegralAdapter mAdapter;
    @InjectView(R.id.imageHead)
    CircleImageView mImageHead;
    @InjectView(R.id.nickName)
    TextView mNickName;
    @InjectView(R.id.vp)
    ViewPager mVp;

    @Override
    public int initLayoutId() {
        return R.layout.activity_integral;
    }

    @Override
    public void initView() {
        mActivity = this;
        mTitle.setText("积分兑换");
        mRightButton.setText("明细");
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mList = new ArrayList<>();
    }

    @Override
    public void initData() {
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(3, 2, false));
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 3));
        mAdapter = new IntegralAdapter(mActivity, mList, 3);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new IntegralAdapter.OnItemClickListener() {
            @Override
            public void OnItemListener(int position) {
                ToastUtils.showToast(mActivity, position + "");
                LogUtil.d("积分兑换点击了" + position);
                mIntent = new Intent(mActivity, IntegralMessageActivity.class);
                mIntent.putExtra("id", position + "");
                startActivity(mIntent);
            }
        });
        initNetWork();
    }

    private void initNetWork() {
        RetrofitUtil.createService(SquareService.class)
                .getCouponInfoList_allUnGet_byScore(mUserToken, "1", "6")
                .enqueue(new Callback<CouponInfoListAllUnGetByScore>() {
                    @Override
                    public void onResponse(Call<CouponInfoListAllUnGetByScore> call, final Response<CouponInfoListAllUnGetByScore> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getResultCode() == 1) {
                                //成功
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CouponInfoListAllUnGetByScore body = response.body();
                                        if (body != null) {
                                            setIntegraView(body);
                                        }

                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CouponInfoListAllUnGetByScore> call, Throwable t) {

                    }
                });
    }

    private void setIntegraView(CouponInfoListAllUnGetByScore response) {
        CouponInfoListAllUnGetByScore.UserInfoBean userInfo = response.getUserInfo();
        mNickName.setText(userInfo.getNickName());
        GlideImgManager.loadImage(mActivity, IpConfig.HttpPic + super.mImageHead + "", mImageHead);
        //多少积分
        String score = userInfo.getScore() + "";
        mScore.setText(score);
        mList.clear();
        mList.addAll(response.getCouponList());
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.more, R.id.right_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.more:
                mIntent = new Intent(mActivity, IntegralActivityNext.class);
                startActivity(mIntent);
                break;
            case R.id.right_button:
                mIntent = new Intent(mActivity, IntegralDetailActivity.class);
                startActivity(mIntent);
                break;

        }
    }
}

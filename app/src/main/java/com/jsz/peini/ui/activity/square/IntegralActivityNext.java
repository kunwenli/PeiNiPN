package com.jsz.peini.ui.activity.square;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.square.CouponInfoListAllUnGetByScore;
import com.jsz.peini.presenter.square.SquareService;
import com.jsz.peini.ui.adapter.square.IntegralAdapter;
import com.jsz.peini.ui.view.SpacesItemDecoration;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by th on 2017/1/17.
 */
public class IntegralActivityNext extends BaseActivity {
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.integral)
    RecyclerView mRecyclerView;
    public IntegralActivityNext mActivity;
    List<CouponInfoListAllUnGetByScore.CouponListBean> mList;
    @InjectView(R.id.help)
    Button mHelp;
    @InjectView(R.id.more)
    TextView mMore;
    public IntegralAdapter mAdapter;
    public Intent mIntent;

    @Override
    public int initLayoutId() {
        return R.layout.activity_integral_next;
    }

    @Override
    public void initView() {
        mActivity = this;
        mTitle.setText("积分兑换");
        mList = new ArrayList<>();
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mMore.setVisibility(mList.size() >= 6 ? View.GONE : View.VISIBLE);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(2, 3, true));
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mAdapter = new IntegralAdapter(mActivity, mList, 2);
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

    /**
     * 网络访问
     */
    private void initNetWork() {
        RetrofitUtil.createService(SquareService.class)
                .getCouponInfoList_allUnGet_byScore(mUserToken, "1", "4")
                .enqueue(new Callback<CouponInfoListAllUnGetByScore>() {
                    @Override
                    public void onResponse(Call<CouponInfoListAllUnGetByScore> call, final Response<CouponInfoListAllUnGetByScore> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getResultCode() == 1) {
                                //成功
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setIntegraView(response);
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

    private void setIntegraView(Response<CouponInfoListAllUnGetByScore> response) {
        mList.clear();
        mList.addAll(response.body().getCouponList());
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.help)
    public void onClick() {
        startActivity(new Intent(mActivity, IntegraHelpActivity.class));
    }

}

package com.jsz.peini.ui.activity.square;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.listener.RetrofitCallback;
import com.jsz.peini.model.square.MiBillBean;
import com.jsz.peini.model.square.MiSignDataBean;
import com.jsz.peini.presenter.square.SquareService;
import com.jsz.peini.san.huanxin.HuanxinHeadBean;
import com.jsz.peini.ui.adapter.square.BillAdapter;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.LoginDialogUtils;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.widget.RecyclerView.LoadMoreFooterView;
import com.jsz.peini.widget.RecyclerView.RefreshHeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by th on 2017/1/11.
 */
public class BillActivity extends BaseActivity {
    private static final int SDK_PAY_FLAG = 200;
    List<MiBillBean.OrderInfoList> mList = new ArrayList<>();
    public BillActivity mActivity;
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
    private BillAdapter mBillAdapter;

    @Override
    public int initLayoutId() {
        return R.layout.activity_bill;
    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    MiBillBean Bean = (MiBillBean) msg.obj;
                    mList.clear();
                    List<MiBillBean.OrderInfoList> orderInfoList = Bean.getOrderInfoList();
                    mList.addAll(orderInfoList);
                    mBillAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void initView() {
        mActivity = this;
        mTitle.setText("账单明细");
        mRightButton.setText("筛选");
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSwipeTarget.setLayoutManager(new LinearLayoutManager(mActivity));
        mBillAdapter = new BillAdapter(mActivity, mList);
        mSwipeTarget.setAdapter(mBillAdapter);
        initNetWork();
    }

    /*网络访问*/
    private void initNetWork() {
        RetrofitUtil.createService(SquareService.class)
                .getMyOrderList(
                        mUserToken,
                        "",
                        "",
                        "",
                        "",
                        "1",
                        "10"
                )
                .enqueue(new RetrofitCallback<MiBillBean>() {
                    @Override
                    public void onSuccess(Call<MiBillBean> call, Response<MiBillBean> response) {
                        if (response.isSuccessful()) {
                            MiBillBean body = response.body();
                            ToastUtils.showToast(mActivity, body.getResultDesc());
                            if (body.getResultCode() == 1) {
                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = response.body();
                                mHandler.sendMessage(msg);
                            } else if (body.getResultCode() == 9) {
                                LoginDialogUtils.isNewLogin(mActivity);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MiBillBean> call, Throwable t) {
                        ToastUtils.showToast(mActivity, t.getMessage());
                    }
                });
    }

}

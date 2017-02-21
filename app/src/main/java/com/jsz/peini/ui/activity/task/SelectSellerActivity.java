package com.jsz.peini.ui.activity.task;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.seller.SellerBean;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.presenter.seller.SellerService;
import com.jsz.peini.ui.activity.seller.SellerMessageActivity;
import com.jsz.peini.ui.adapter.seller.SellerAdapter;
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

import static com.jsz.peini.R.id.swipeToLoadLayout;

/**
 * Created by th on 2017/1/13.
 */
public class SelectSellerActivity extends BaseActivity {
    public SelectSellerActivity mActivity;
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.right_button)
    TextView mRightButton;
    @InjectView(R.id.swipe_target)
    RecyclerView mSelectorSeller;

    @InjectView(R.id.item_selector)
    LinearLayout mItemSelector;

    @InjectView(swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;
    /**
     * 商家的初始化数据
     */
    public List<SellerBean.SellerInfoBean> mSellerInfo;
    public SellerAdapter mAdapter;
    public String sellerType;
    public String distance;
    public String districtCode;
    public String labelsId;
    public String sort;
    public String pageNow;
    public String pageSize;
    public String cityCode;
    public LinearLayoutManager mLinearLayoutManager;
    public Intent mIntent;
    @InjectView(R.id.image_1)
    ImageView mImage1;
    @InjectView(R.id.seller_seller)
    LinearLayout mSellerSeller;
    @InjectView(R.id.image_2)
    ImageView mImage2;
    @InjectView(R.id.seller_distance)
    LinearLayout mSellerDistance;
    @InjectView(R.id.image_3)
    ImageView mImage3;
    @InjectView(R.id.seller_typeofoperation)
    LinearLayout mSellerTypeofoperation;
    @InjectView(R.id.image_4)
    ImageView mImage4;
    @InjectView(R.id.seller_sort)
    LinearLayout mSellerSort;

    @Override
    public int initLayoutId() {
        return R.layout.activity_selectseller;
    }

    @Override
    public void initView() {
        mActivity = this;
        mTitle.setText("商家");
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSellerInfo = new ArrayList<>();
        sellerType = "";
        distance = "1000000";
        districtCode = "";
        labelsId = "";
        sort = "304";
        pageNow = "1";
        pageSize = "20";
        cityCode = IpConfig.cityCode;
    }

    @Override
    public void initData() {
        /**上啦加载*/
        mSwipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeToLoadLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeToLoadLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        /**下拉刷新*/
        mSwipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mSwipeToLoadLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeToLoadLayout.setLoadingMore(false);
                    }
                }, 2000);
            }
        });
        /**给Recyclerview设置布局*/
        mLinearLayoutManager = new LinearLayoutManager(mActivity);
        mSelectorSeller.setLayoutManager(mLinearLayoutManager);
        /**设置分割线*/
        mSelectorSeller.addItemDecoration(new SpacesItemDecoration(1, 2, false));
        /**适配器*/
        mAdapter = new SellerAdapter(mActivity, mSellerInfo);
        mSelectorSeller.setAdapter(mAdapter);
        /**访问网络*/
        initNetWork();
        /**适配器的回调*/
        mAdapter.setOnClickListener(new SellerAdapter.OnClickListener() {
            @Override
            public void onClick(int i) {
                smoothMoveToPosition(i);
            }

            @Override
            public void onSellerSelectItemClick(int position) {
                SellerSelectItemClick(position);
            }

            @Override
            public void onSellerItemClick(int id, int position) {
                mIntent = new Intent(mActivity, SellerMessageActivity.class);
                mIntent.putExtra("id", id + "");
                mIntent.putExtra("type", 1);
                startActivity(mIntent);
                finish();
            }
        });
        mSelectorSeller.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mSwipeToLoadLayout.setRefreshing(false);
                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                LogUtil.d("滑动" + firstVisibleItemPosition + "");
                mItemSelector.setVisibility(firstVisibleItemPosition == 0 ? View.GONE : View.VISIBLE);
            }
        });
    }

    /**
     * 点击时间的回调
     */
    private void SellerSelectItemClick(int position) {
        switch (position) {
            case 1:
                SellerItemClick(mSellerSeller, position);
                break;
            case 2:
                SellerItemClick(mSellerDistance, position);
                break;
            case 3:
                SellerItemClick(mSellerTypeofoperation, position);
                break;
            case 4:
                SellerItemClick(mSellerSort, position);
                break;
        }
    }

    /**
     * 点击事件
     */
    private void SellerItemClick(LinearLayout sellerSeller, final int position) {
        ToastUtils.showToast(mActivity, position + "");
        sellerSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(mActivity, position + "");
            }
        });
    }

    //界面可见刷新
    private void autoRefresh() {
        mSwipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeToLoadLayout.setRefreshing(true);
            }
        });
    }


    /**
     * 网络访问
     */
    private void initNetWork() {
        RetrofitUtil.createService(SellerService.class)
                .getSellerInfoBySellerInfo(mXpoint, mYpoint, sellerType, distance, districtCode, labelsId, sort, pageNow, pageSize, cityCode)
                .enqueue(new Callback<SellerBean>() {
                    @Override
                    public void onResponse(Call<SellerBean> call, Response<SellerBean> response) {
                        if (response.isSuccessful()) {
                            SellerBean body = response.body();
                            if (body.getResultCode() == 1) {
                                SellerBean sellerBean = response.body();
                                mSellerInfo.clear();
                                mSellerInfo.addAll(sellerBean.getSellerInfo());
                                mAdapter.notifyDataSetChanged();
                                LogUtil.d("商家选择界面返回的数据" + body.toString());
                            } else if (body.getResultCode() == 9) {
                                ToastUtils.showToast(mActivity, body.getResultDesc());
                            } else {
                                ToastUtils.showToast(mActivity, body.getResultDesc());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SellerBean> call, Throwable t) {
                    }
                });
    }

    private void smoothMoveToPosition(int position) {
        int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastPosition = mLinearLayoutManager.findLastVisibleItemPosition();
        if (position <= lastPosition) {
            int top = mSelectorSeller.getChildAt(position - firstPosition).getTop();
            mSelectorSeller.smoothScrollBy(0, top);
        }
    }

    @OnClick({R.id.seller_seller, R.id.seller_distance, R.id.seller_typeofoperation, R.id.seller_sort})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.seller_seller:
                SellerItemClick(mSellerSeller, 1);
                break;
            case R.id.seller_distance:
                SellerItemClick(mSellerDistance, 2);
                break;
            case R.id.seller_typeofoperation:
                SellerItemClick(mSellerTypeofoperation, 3);
                break;
            case R.id.seller_sort:
                SellerItemClick(mSellerSort, 4);
                break;
        }
    }
}

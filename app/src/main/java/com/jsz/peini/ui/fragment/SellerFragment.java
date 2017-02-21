package com.jsz.peini.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.jsz.peini.R;
import com.jsz.peini.base.BaseFragment;
import com.jsz.peini.model.address.HotBean;
import com.jsz.peini.model.address.SellerAddress;
import com.jsz.peini.model.seller.SellerBean;
import com.jsz.peini.model.seller.SellerCodesBySellerCodesBean;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.presenter.seller.SellerService;
import com.jsz.peini.ui.activity.seller.SellerMessageActivity;
import com.jsz.peini.ui.adapter.seller.OneRecyclerviewAdapter;
import com.jsz.peini.ui.adapter.seller.SellerAdapter;
import com.jsz.peini.ui.adapter.seller.TowRecyclerviewAdapter;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.utils.UiUtils;
import com.jsz.peini.widget.RecyclerView.LoadMoreFooterView;
import com.jsz.peini.widget.RecyclerView.RefreshHeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jsz.peini.R.id.seller_distance;
import static com.jsz.peini.R.id.seller_seller;
import static com.jsz.peini.R.id.seller_sort;
import static com.jsz.peini.R.id.seller_typeofoperation;


/**
 * Created by kunwe on 2016/11/25.
 * 商家界面
 */

public class SellerFragment extends BaseFragment {
    @InjectView(R.id.swipe_refresh_header)
    RefreshHeaderView mSwipeRefreshHeader;
    @InjectView(R.id.swipe_target)
    RecyclerView mSwipeTarget;
    @InjectView(R.id.swipe_load_more_footer)
    LoadMoreFooterView mSwipeLoadMoreFooter;
    @InjectView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;
    @InjectView(R.id.image_1)
    ImageView mImage1;
    @InjectView(seller_seller)
    LinearLayout mSellerSeller;
    @InjectView(R.id.image_2)
    ImageView mImage2;
    @InjectView(seller_distance)
    LinearLayout mSellerDistance;
    @InjectView(R.id.image_3)
    ImageView mImage3;
    @InjectView(seller_typeofoperation)
    LinearLayout mSellerTypeofoperation;
    @InjectView(R.id.image_4)
    ImageView mImage4;
    @InjectView(seller_sort)
    LinearLayout mSellerSort;
    @InjectView(R.id.item_selector)
    LinearLayout mItemSelector;
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
    public SellerAddress mSellerAddress;
    public RecyclerView mOneRecyclerView, mTowRecyclerView;
    public PopupWindow mPop;
    public List<HotBean> mDistrictList = new ArrayList<>();
    public TowRecyclerviewAdapter mTowRecyclerviewAdapter;
    public SellerCodesBySellerCodesBean mSellerCodesBean;

    @Override
    public View initViews() {
        return View.inflate(mActivity, R.layout.fragment_sellers, null);
    }

    @Override
    public void initData() {
        mSellerInfo = new ArrayList<>();
        sellerType = "";
        distance = "10000";
        districtCode = "";
        labelsId = "";
        sort = "";
        pageNow = "1";
        pageSize = "10";
        cityCode = "64";
        /**上啦加载*/
        mSwipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeToLoadLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeToLoadLayout.setRefreshing(false);
                        initNetWork();
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
        mSwipeTarget.setLayoutManager(mLinearLayoutManager);
        /**设置分割线*/
//        mSwipeTarget.addItemDecoration(new SpacesItemDecoration(1, 2, false));
        /**适配器*/
        mAdapter = new SellerAdapter(mActivity, mSellerInfo);
        mSwipeTarget.setAdapter(mAdapter);
        /**访问网络*/
        NetWork();
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
                mIntent.putExtra("type", 2);
                mActivity.startActivity(mIntent);
            }
        });
        mSwipeTarget.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mSwipeToLoadLayout.setRefreshing(false);
                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                LogUtil.d("===================" + firstVisibleItemPosition + "");
                mItemSelector.setVisibility(firstVisibleItemPosition == 0 ? View.GONE : View.VISIBLE);
            }
        });
    }

    private void NetWork() {
        initNetWork();
        /*商圈*/
        initsellerSellerNetWorkData();
        /*业态*/
        sellerTypeofoperationNetWorkData();
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
                            ToastUtils.showToast(mActivity, body.getResultDesc());
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
            int top = mSwipeTarget.getChildAt(position - firstPosition).getTop();
            mSwipeTarget.smoothScrollBy(0, top);
        }
    }

    /**
     * 点击时间的回调
     */
    private void SellerSelectItemClick(int position) {
        switch (position) {
            case 1:
                ToastUtils.showToast(mActivity, "1");
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //execute the task
                        sellerSellerData();
                    }
                }, 200);
                break;
            case 2:
                ToastUtils.showToast(mActivity, "2");
                mDistrictList.clear();
                for (int i = 1; i <= 6; i++) {
                    if (i == 6) {
                        mDistrictList.add(new HotBean(0, "全部"));
                    } else {
                        mDistrictList.add(new HotBean(i, i + "km"));
                    }
                }
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //execute the task
                        sellerTypeofoperationData(1, 1);
                    }
                }, 200);
                break;
            case 3:
                ToastUtils.showToast(mActivity, "3");
                if (mSellerCodesBean == null) {
                    return;
                }
                List<SellerCodesBySellerCodesBean.SellerCodesBean> sellerCodes = mSellerCodesBean.getSellerCodes();
                mDistrictList.clear();
                for (int i = 0; i < sellerCodes.size(); i++) {
                    mDistrictList.add(new HotBean(sellerCodes.get(i).getId(), sellerCodes.get(i).getCodesName()));
                }
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //execute the task
                        sellerTypeofoperationData(1, 3);
                    }
                }, 200);
                break;
            case 4:
                ToastUtils.showToast(mActivity, "4");
                mDistrictList.clear();
                mDistrictList.add(new HotBean(1, "人气最高"));
                mDistrictList.add(new HotBean(1, "评分最高"));
                mDistrictList.add(new HotBean(1, "距离最近"));
                mDistrictList.add(new HotBean(1, "好评最多"));
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //execute the task
                        sellerTypeofoperationData(1, 1);
                    }
                }, 200);
                break;
        }
    }

    @OnClick({R.id.seller_seller, R.id.seller_distance, R.id.seller_typeofoperation, R.id.seller_sort})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.seller_seller:
                ToastUtils.showToast(mActivity, "1");//商家选择
                if (mSellerAddress == null) {
                    return;
                }
                sellerSellerData();
                break;
            case R.id.seller_distance:
                ToastUtils.showToast(mActivity, "2");
                mDistrictList.clear();
                for (int i = 1; i <= 6; i++) {
                    if (i == 6) {
                        mDistrictList.add(new HotBean(0, "全部"));
                    } else {
                        mDistrictList.add(new HotBean(i, i + "km"));
                    }
                }
                sellerTypeofoperationData(1, 1);
                break;
            case R.id.seller_typeofoperation:
                ToastUtils.showToast(mActivity, "3");
                if (mSellerCodesBean == null) {
                    return;
                }
                List<SellerCodesBySellerCodesBean.SellerCodesBean> sellerCodes = mSellerCodesBean.getSellerCodes();
                mDistrictList.clear();
                for (int i = 0; i < sellerCodes.size(); i++) {
                    mDistrictList.add(new HotBean(sellerCodes.get(i).getId(), sellerCodes.get(i).getCodesName()));
                }
                sellerTypeofoperationData(1, 3);
                break;
            case R.id.seller_sort:
                ToastUtils.showToast(mActivity, "4");
                mDistrictList.clear();
                mDistrictList.add(new HotBean(1, "人气最高"));
                mDistrictList.add(new HotBean(1, "评分最高"));
                mDistrictList.add(new HotBean(1, "距离最近"));
                mDistrictList.add(new HotBean(1, "好评最多"));
                sellerTypeofoperationData(1, 1);
                break;
        }
    }


    /**
     * 商家业态选择
     *
     * @param i1
     * @param i
     */
    private void sellerTypeofoperationData(int i1, int i) {
        View view = UiUtils.inflate(mActivity, R.layout.pop_one_recyclerview);
        mPop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPop.setFocusable(true);
//        mPop.setTouchable(true);
//        mPop.setOutsideTouchable(true);
        mPop.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bj_4c4c4c)));
        mPop.setAnimationStyle(R.style.mypopwindow_anim_style);
        mPop.showAsDropDown(mItemSelector);
        mPop.update();
        view.findViewById(R.id.pop_tow_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPop.dismiss();
            }
        });
                   /*右列表*/
        mTowRecyclerView = (RecyclerView) view.findViewById(R.id.one_recyclerview);
        //设置recyclerview高度
        if (i == 3) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) mTowRecyclerView.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = UiUtils.dip2px(mActivity, 230f);// 控件的宽强制设成30
            mTowRecyclerView.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        mTowRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mTowRecyclerviewAdapter = new TowRecyclerviewAdapter(mActivity, mDistrictList, i1);
        mTowRecyclerView.setAdapter(mTowRecyclerviewAdapter);
        mTowRecyclerviewAdapter.setOnItemClickListener(new TowRecyclerviewAdapter.OnItemClickListener() {
            @Override
            public void ItemClickListener(int position, int countyId) {
                ToastUtils.showToast(mActivity, "==" + countyId);
                mPop.dismiss();
            }
        });
    }

    /**
     * 商家位置选择
     */
    private void sellerSellerData() {
        View view = UiUtils.inflate(mActivity, R.layout.pop_tow_address);
        mPop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPop.setFocusable(true);
//        mPop.setTouchable(true);
//        mPop.setOutsideTouchable(true);
        mPop.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bj_4c4c4c)));
        mPop.setAnimationStyle(R.style.mypopwindow_anim_style);
        mPop.showAsDropDown(mItemSelector);
        mPop.update();

        view.findViewById(R.id.pop_tow_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPop.dismiss();
            }
        });
        /*左列表*/
        mOneRecyclerView = (RecyclerView) view.findViewById(R.id.one_recyclerview);
        mOneRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mDistrictList.clear();
        List<SellerAddress.DistrictHotListBean> districtHotList = mSellerAddress.getDistrictHotList();
        for (int i = 0; i < districtHotList.size(); i++) {
            mDistrictList.add(new HotBean(districtHotList.get(i).getPlaceCode(), districtHotList.get(i).getPlaceName()));
        }
        OneRecyclerviewAdapter oneRecyclerviewAdapter = new OneRecyclerviewAdapter(mActivity, mSellerAddress.getDistrictList());
        mOneRecyclerView.setAdapter(oneRecyclerviewAdapter);
        oneRecyclerviewAdapter.setOnItemClickListener(new OneRecyclerviewAdapter.OnItemClickListener() {
            @Override
            public void ItemClickListener(int position, int countyId) {
                ToastUtils.showToast(mActivity, position + "---" + countyId);
                mDistrictList.clear();
                List<SellerAddress.DistrictListBean.DistrictObjectBean> districtObject = mSellerAddress.getDistrictList().get(position).getDistrictObject();
                for (int i = 0; i < districtObject.size(); i++) {
                    mDistrictList.add(new HotBean(districtObject.get(i).getDistrictId(), districtObject.get(i).getDistrictName()));
                }
                mTowRecyclerviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void ItemClicknearbyListener(int position) {
                ToastUtils.showToast(mActivity, position + "");
                mDistrictList.clear();
                List<SellerAddress.DistrictHotListBean> districtHotList = mSellerAddress.getDistrictHotList();
                for (int i = 0; i < districtHotList.size(); i++) {
                    mDistrictList.add(new HotBean(districtHotList.get(i).getPlaceCode(), districtHotList.get(i).getPlaceName()));
                }
                mTowRecyclerviewAdapter.notifyDataSetChanged();
            }
        });
                /*右列表*/
        mTowRecyclerView = (RecyclerView) view.findViewById(R.id.tow_recyclerview);
        mTowRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mTowRecyclerviewAdapter = new TowRecyclerviewAdapter(mActivity, mDistrictList, 0);
        mTowRecyclerView.setAdapter(mTowRecyclerviewAdapter);
        mTowRecyclerviewAdapter.setOnItemClickListener(new TowRecyclerviewAdapter.OnItemClickListener() {
            @Override
            public void ItemClickListener(int position, int countyId) {
                ToastUtils.showToast(mActivity, "==" + countyId);
                mPop.dismiss();
            }
        });
    }

    /**
     * 获取商家位置筛选信息
     */
    private void initsellerSellerNetWorkData() {
        RetrofitUtil.createService(SellerService.class).getDistrictList().enqueue(new Callback<SellerAddress>() {
            @Override
            public void onResponse(Call<SellerAddress> call, Response<SellerAddress> response) {
                if (response.isSuccessful()) {
                    ToastUtils.showToast(mActivity, response.body().getResultDesc());
                    if (response.body().getResultCode() == 1) {
                        SellerAddress body = response.body();
                        if (body != null) {
                            mSellerAddress = body;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SellerAddress> call, Throwable t) {

            }
        });
    }

    /**
     * 业态获取接口
     */
    private void sellerTypeofoperationNetWorkData() {
        RetrofitUtil.createService(SellerService.class)
                .getSellerCodesBySellerCodesBean()
                .enqueue(new Callback<SellerCodesBySellerCodesBean>() {
                    @Override
                    public void onResponse(Call<SellerCodesBySellerCodesBean> call, Response<SellerCodesBySellerCodesBean> response) {
                        if (response.isSuccessful()) {
                            ToastUtils.showToast(mActivity, response.body().getResultDesc());
                            if (response.body().getResultCode() == 1) {
                                SellerCodesBySellerCodesBean body = response.body();
                                if (body != null) {
                                    mSellerCodesBean = response.body();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SellerCodesBySellerCodesBean> call, Throwable t) {

                    }
                });
    }

    /**
     * 获取商家排序数据
     */
    private void initsellerSortNetWorkData() {

    }
}

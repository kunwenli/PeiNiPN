package com.jsz.peini.ui.activity.square;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.ui.adapter.square.SquarePeripheryAdapter;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.SpUtils;
import com.jsz.peini.utils.UiUtils;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by th on 2016/12/27.
 */
public class SquarePeriphery extends BaseActivity {
    private static final int RESULT_LOAD_IMAGE3 = 300;
    private static final String TAG = "SquarePeriphery";
    @InjectView(R.id.release_return)
    Toolbar mReleaseReturn;
    @InjectView(R.id.square_periphery_edittext)
    EditText mSquarePeripheryEdittext;
    @InjectView(R.id.square_periphery)
    PullLoadMoreRecyclerView mSquarePeriphery;
    private SquarePeriphery mContext;
    private PoiSearch mPoiSearch;
    private double mXpoint;
    private double mYpoint;
    private GeoCoder mSearch;
    ArrayList<PoiInfo> mPoiInfos = new ArrayList<>();
    private SquarePeripheryAdapter mPeripheryAdapter;
    private String mEditText;
    private int mIndex = 0;
    private boolean IsFirst = true;
    private int mTotalPageNum;
    private int mPosition;

    @Override
    public int initLayoutId() {
        SDKInitializer.initialize(getApplicationContext());
        return R.layout.activity_square_periphery;
    }

    @Override
    public void initView() {
        super.initView();
        isShowEdittext(false);
        mContext = this;
        // 实例化PoiSearch对象
        mPoiSearch = PoiSearch.newInstance();
        // 设置检索监听器
        mPoiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);
        // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(onGetGeoCoderResultListener);
        mXpoint = Double.parseDouble((String) SpUtils.get(mContext, "xpoint", ""));
        mYpoint = Double.parseDouble((String) SpUtils.get(mContext, "ypoint", ""));
        mReleaseReturn.setTitle("");
        setSupportActionBar(mReleaseReturn);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回????
        mReleaseReturn.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        Intent intent = getIntent();
        String address = intent.getStringExtra("Address");
        mSquarePeriphery.setLinearLayout();
        mSquarePeriphery.setRefreshing(true);//不显示下拉刷新
        mSquarePeriphery.setPullRefreshEnable(false);//不下啦刷新
        mSquarePeriphery.setPushRefreshEnable(false); // 需不需要下拉加载
        mPeripheryAdapter = new SquarePeripheryAdapter(mContext, mPoiInfos);
        mPeripheryAdapter.addHeadView(UiUtils.inflate(mContext,R.layout.noaddress_head));
        if (!TextUtils.isEmpty(address)) {
            mPeripheryAdapter.setPoiInfoSelete(address);
        }
        mSquarePeriphery.setAdapter(mPeripheryAdapter);
        GetmSearch();
        mPeripheryAdapter.setListener(new SquarePeripheryAdapter.OnClickItemListener() {
            @Override
            public void ItemSeleteAddressListener(String Address, int position, double x, double y) {
                isShowEdittext(false);
                Intent intent = new Intent();
                intent.putExtra("Address", "" + Address);
                intent.putExtra("latitude", x);
                intent.putExtra("longitude", y);
                setResult(RESULT_LOAD_IMAGE3, intent);
                mPosition = position;
                finish();
            }
        });
        mSquarePeripheryEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mEditText = mSquarePeripheryEdittext.getText().toString().trim();
                LogUtil.i(TAG, editable + "" + mEditText);
                if (editable.length() > 0) {
                    GetmPoiSearch();
                    mSquarePeriphery.setPushRefreshEnable(true); // 需不需要下拉加载
                } else {
                    mPoiInfos.clear();
                    mSquarePeriphery.setPushRefreshEnable(false); // 需不需要下拉加载
                    GetmSearch();
                }
            }
        });
        mSquarePeriphery.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                GetmPoiSearch();
                mIndex++;
                IsFirst = false;
                isShowEdittext(false);
            }
        });
    }

    public void GetmPoiSearch() {
        IsFirst = true;
        //构造请求参数，其中centerPt是自己的位置坐标
        LatLng latLng = new LatLng(mXpoint, mYpoint);
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
        nearbySearchOption.location(latLng);
        nearbySearchOption.keyword(mEditText);
        nearbySearchOption.radius(10000);// 检索半径，单位是米
        nearbySearchOption.pageNum(mIndex);
        nearbySearchOption.pageCapacity(30);
        mPoiSearch.searchNearby(nearbySearchOption);
    }

    private void GetmSearch() {
        // 反Geo搜索
        LatLng latLng1 = new LatLng(mXpoint, mYpoint);
        ReverseGeoCodeOption geoCodeOption = new ReverseGeoCodeOption();
        geoCodeOption.location(latLng1);
        mSearch.reverseGeoCode(geoCodeOption);
    }

    /**
     * 关键词检索
     */
    OnGetPoiSearchResultListener onGetPoiSearchResultListener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult result) {
            if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
                LogUtil.d("mPoiSearch--------------", "未找到结果");
                mSquarePeriphery.setPullLoadMoreCompleted();
                return;
            }
            //获取POI检索结果
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                List<PoiInfo> allAddr = result.getAllPoi();
                if (IsFirst) {
                    mPoiInfos.clear();
                }
                for (PoiInfo p : allAddr) {
                    LogUtil.d("mPoiSearch-------------", "p.name--->" + p.name + "p.phoneNum" + p.phoneNum + " -->p.address:" + p.address + "p.location" + p.location);
                    mPoiInfos.add(p);
                }
                mTotalPageNum = result.getTotalPageNum();
                LogUtil.d("mPoiSearch-----------", "总共查到" + result.getTotalPoiNum() + "个兴趣点, 分为" + mTotalPageNum + "页");
                mSquarePeriphery.setPullLoadMoreCompleted();
                mPeripheryAdapter.setPoiInfoList(mPoiInfos);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //execute the task
                        isShowEdittext(false);
                    }
                }, 3000);
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
        }
    };
    /**
     * 周边?
     */
    OnGetGeoCoderResultListener onGetGeoCoderResultListener = new OnGetGeoCoderResultListener() {
        @Override
        public void onGetGeoCodeResult(GeoCodeResult result) {
        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                LogUtil.d("mSearch-------------", "抱歉，未能找到结果");
            } else {
                List<PoiInfo> poiList = result.getPoiList();
                if (poiList.size() != 0) {
                    if (mPoiInfos.size() != 0) {
                        mPoiInfos.clear();
                    }
                    for (PoiInfo p : poiList) {
                        mPoiInfos.add(p);
                        LogUtil.d("mSearch----------", "p.name--->" + p.name + "p.phoneNum" + p.phoneNum + " -->p.address:" + p.address + "p.location" + p.location);
                    }
                    mPeripheryAdapter.setPoiInfoList(mPoiInfos);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //execute the task
                            isShowEdittext(false);
                        }
                    }, 0);
                }
            }
        }
    };
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {//手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)

        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
            if (y1 - y2 > 50) {
                isShowEdittext(false);
                Toast.makeText(this, "向上滑", Toast.LENGTH_SHORT).show();
            } else if (y2 - y1 > 50) {
                Toast.makeText(this, "向下滑", Toast.LENGTH_SHORT).show();
            } else if (x1 - x2 > 50) {
                Toast.makeText(this, "向左滑", Toast.LENGTH_SHORT).show();
            } else if (x2 - x1 > 50) {
                Toast.makeText(this, "向右滑", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    public void isShowEdittext(boolean isshow) {
        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString("输入位置");
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(15, true);
        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置hint
        mSquarePeripheryEdittext.setHint(new SpannedString(ss));
        mSquarePeripheryEdittext.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isshow) {
            imm.showSoftInput(mSquarePeripheryEdittext, 0);
        } else {
            imm.hideSoftInputFromWindow(mSquarePeripheryEdittext.getWindowToken(), 0);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        isShowEdittext(false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        isShowEdittext(false);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPoiSearch.destroy();
        mSearch.destroy();
        isShowEdittext(false);
    }
}

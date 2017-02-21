package com.jsz.peini.ui.activity.filter;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.jsz.peini.PeiNiApp;
import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.gen.HistoryHotBean;
import com.jsz.peini.gen.HistoryHotBeanDao;
import com.jsz.peini.gen.SerachInfoDao;
import com.jsz.peini.listener.RetrofitCallback;
import com.jsz.peini.model.filter.HotWordBean;
import com.jsz.peini.model.search.LatLngBean;
import com.jsz.peini.model.setting.SuccessfulBean;
import com.jsz.peini.presenter.task.TaskService;
import com.jsz.peini.ui.adapter.search.TaskSearchAdapter;
import com.jsz.peini.ui.adapter.search.TaskSearchEndAdapter;
import com.jsz.peini.utils.Conversion;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.widget.RecyclerView.LoadMoreFooterView;
import com.jsz.peini.widget.RecyclerView.RefreshHeaderView;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by 15089 on 2017/2/13.
 */
public class TaskSearchActivity extends BaseActivity {
    @InjectView(R.id.task_search_recyclerview)
    RecyclerView mTaskSearchRecyclerview;
    @InjectView(R.id.swipe_refresh_header)
    RefreshHeaderView mSwipeRefreshHeader;
    @InjectView(R.id.swipe_target)
    RecyclerView mSwipeTarget;
    @InjectView(R.id.swipe_load_more_footer)
    LoadMoreFooterView mSwipeLoadMoreFooter;
    @InjectView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;
    @InjectView(R.id.square_periphery_edittext)
    EditText mSquarePeripheryEdittext;
    @InjectView(R.id.close)
    TextView mClose;
    private TaskSearchActivity mActivity;

    List<HotWordBean.DataBean> mList = new ArrayList<>();

    ArrayList<PoiInfo> mPoiInfos = new ArrayList<>();

    private String mEditText;

    private int mIndex = 0;

    private PoiSearch mPoiSearch;

    private boolean IsFirst = true;

    private int mTotalPageNum;
    private TaskSearchEndAdapter mSearchEndAdapter;
    private TaskSearchAdapter mSearchAdapter;
    private HistoryHotBeanDao mHotBeanDao;
    /*获取的历史记录*/
    private List<HistoryHotBean> mHistoryHotBeen;

    @Override
    public int initLayoutId() {
        return R.layout.activity_tasksearch;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Conversion.DATASUCCESS:
                    HotWordBean wordBean = (HotWordBean) msg.obj;
                    LogUtil.d("这个是返回的热门" + wordBean.toString());
                    mList.clear();
                    List<HotWordBean.DataBean> data = wordBean.getData();
                    mList.addAll(data);
                    mSearchAdapter.notifyDataSetChanged();
                    mDialog.dismiss();
                    break;
                case Conversion.DATA_SUCCESS:
                    SuccessfulBean successfulBean = (SuccessfulBean) msg.obj;
                    ToastUtils.showToast(mActivity, successfulBean.getResultDesc());
                    break;
                default:
                    break;
            }
        }

        ;
    };

    @Override
    public void initView() {
        mActivity = this;
        // 实例化PoiSearch对象
        mHotBeanDao = PeiNiApp.HistoryHotBeanDao;
        /*获取mHotBeanDao*/
        mHistoryHotBeen = mHotBeanDao.queryBuilder().orderDesc(HistoryHotBeanDao.Properties.Id).limit(10).list();
        LogUtil.d("historyHotBeen" + mHistoryHotBeen.size());

        mPoiSearch = PoiSearch.newInstance();
        // 设置检索监听器
        mPoiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);
        // 初始化搜索模块，注册事件监听
        mSquarePeripheryEdittext.addTextChangedListener(getWatcher());
        mSwipeToLoadLayout.setVisibility(View.GONE);
    }


    @NonNull
    private TextWatcher getWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mEditText = mSquarePeripheryEdittext.getText().toString().trim();
                if (mEditText.length() > 0) {
                    mSwipeToLoadLayout.setVisibility(View.VISIBLE);
                }
                GetmPoiSearch();
            }
        };
    }


    @Override
    public void initData() {
        /*基本数据*/
        mTaskSearchRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        mSearchAdapter = new TaskSearchAdapter(mActivity, mList, mHistoryHotBeen);
        mTaskSearchRecyclerview.setAdapter(mSearchAdapter);
        /*搜索数据*/
        mSwipeTarget.setLayoutManager(new LinearLayoutManager(mActivity));
        mSearchEndAdapter = new TaskSearchEndAdapter(mActivity, mPoiInfos, Double.parseDouble(mXpoint), Double.parseDouble(mYpoint));
        mSwipeTarget.setAdapter(mSearchEndAdapter);
        initHotWordNetWork();
    }

    /*获取搜索词*/
    private void initHotWordNetWork() {
        mDialog.show();
        RetrofitUtil.createService(TaskService.class)
                .getHotWord("2")
                .enqueue(new RetrofitCallback<HotWordBean>() {
                    @Override
                    public void onSuccess(Call<HotWordBean> call, Response<HotWordBean> response) {
                        if (response.isSuccessful()) {
                            HotWordBean body = response.body();
                            if (body.getResultCode() == 1) {
                                Message msg = new Message();
                                msg.what = Conversion.DATASUCCESS;
                                msg.obj = body;
                                mHandler.sendMessage(msg);
                            } else {
                                ToastUtils.showToast(mActivity, body.getResultDesc());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<HotWordBean> call, Throwable t) {
                        ToastUtils.showToast(mActivity, t.getMessage());
                    }
                });
    }

    @Override
    protected void initListener() {
        mSearchAdapter.setOnClickListener(new TaskSearchAdapter.OnClickListener() {
            @Override
            public void ItemHotClick(int id, String hotName, int hotNum) {
                LogUtil.d(id + hotName + hotNum);
                mSquarePeripheryEdittext.setText(hotName);
                initSearchByHotWordNetWork(hotName);
            }

            @Override
            public void itemClick(String name, String distance, String address, double latitude, double longitude) {
                LatLngBean latLng = new LatLngBean(latitude, longitude);
                EventBus.getDefault().post(latLng);
                mActivity.finish();
            }

            @Override
            public void onDetele() {
                mHotBeanDao.deleteAll();
                mSearchAdapter.setBottomCount(0);
            }
        });
        mSearchEndAdapter.setOnitemClickListener(new TaskSearchEndAdapter.OnitemClickListener() {
            @Override
            public void itemClick(String name, String distance, String address, double latitude, double longitude) {
                HistoryHotBean hotBean = new HistoryHotBean(null, "", name, distance, address, latitude, longitude);
                mHotBeanDao.insert(hotBean);
//                mLists.add(new HistoryHotBean(name, distance, address, latitude, longitude));
                LatLngBean latLng = new LatLngBean(latitude, longitude);
                EventBus.getDefault().post(latLng);
                mActivity.finish();
            }
        });
    }

    private void initSearchByHotWordNetWork(String hotName) {
        RetrofitUtil.createService(TaskService.class)
                .searchByHotWord(hotName)
                .enqueue(new RetrofitCallback<SuccessfulBean>() {
                    @Override
                    public void onSuccess(Call<SuccessfulBean> call, Response<SuccessfulBean> response) {
                        if (response.isSuccessful()) {
                            SuccessfulBean body = response.body();
                            if (body.getResultCode() == 1) {
                                Message msg = new Message();
                                msg.what = Conversion.DATA_SUCCESS;
                                msg.obj = body;
                                mHandler.sendMessage(msg);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessfulBean> call, Throwable t) {

                    }
                });
    }

    private void GetmPoiSearch() {
        //构造请求参数，其中centerPt是自己的位置坐标
        LatLng latLng = new LatLng(
                Double.parseDouble(mXpoint),
                Double.parseDouble(mYpoint));
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
        nearbySearchOption.location(latLng);
        nearbySearchOption.keyword(mEditText);
        nearbySearchOption.radius(1000);// 检索半径，单位是米
        nearbySearchOption.pageNum(mIndex);
        nearbySearchOption.pageCapacity(20);
        mPoiSearch.searchNearby(nearbySearchOption);
    }

    /**
     * 关键词检索
     */
    OnGetPoiSearchResultListener onGetPoiSearchResultListener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult result) {
            if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
                return;
            }
            //获取POI检索结果
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                List<PoiInfo> allAddr = result.getAllPoi();
                if (allAddr.size() > 0) {
                    mSwipeToLoadLayout.setVisibility(View.VISIBLE);
                    mPoiInfos.clear();
                    mPoiInfos.addAll(allAddr);
                    mSearchEndAdapter.notifyDataSetChanged();
                }
                mTotalPageNum = result.getTotalPageNum();
                LogUtil.d("mPoiSearch-----------", "总共查到" + result.getTotalPoiNum() + "个兴趣点, 分为" + mTotalPageNum + "页");
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
        }
    };


    @OnClick({R.id.close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                finish();
                break;
        }
    }


    //将list集合转换成字符串

    public static String SceneList2String(List SceneList) throws IOException {
// 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
// 然后将得到的字符数据装载到ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
// writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
        objectOutputStream.writeObject(SceneList);
// 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
        String SceneListString = new String(Base64.encode(
                byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
// 关闭objectOutputStream
        objectOutputStream.close();
        return SceneListString;
    }

//将字符串转换成list集合

    @SuppressWarnings("unchecked")
    public static List String2SceneList(String SceneListString)
            throws StreamCorruptedException, IOException,
            ClassNotFoundException {
        byte[] mobileBytes = Base64.decode(SceneListString.getBytes(),
                Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                mobileBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        List SceneList = (List) objectInputStream.readObject();
        objectInputStream.close();
        return SceneList;
    }
}

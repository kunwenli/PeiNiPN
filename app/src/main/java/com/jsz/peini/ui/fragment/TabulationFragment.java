package com.jsz.peini.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseFragment;
import com.jsz.peini.model.eventbus.FilterReturnBean;
import com.jsz.peini.model.tabulation.TabulationBean;
import com.jsz.peini.presenter.task.TaskService;
import com.jsz.peini.ui.activity.news.MapNewsActivity;
import com.jsz.peini.ui.activity.square.MiTaskActivity;
import com.jsz.peini.ui.activity.task.TaskActivity;
import com.jsz.peini.ui.activity.task.TaskDtailsActivity;
import com.jsz.peini.ui.adapter.tabulation.TabulationAdapter;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.LoginDialogUtils;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.SpUtils;
import com.jsz.peini.utils.UiUtils;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kunwe on 2016/11/29.
 * 列表的fragment
 */

public class TabulationFragment extends BaseFragment {
    @InjectView(R.id.tabuiation_viewpager)
    public ViewPager tabuiation_viewpager;
    @InjectView(R.id.task_setcurrentitem)
    LinearLayout mTaskSetcurrentitem;
    @InjectView(R.id.task_setcurrentitem2)
    LinearLayout mTaskSetcurrentitem2;
    @InjectView(R.id.map_button)
    Button mMapButton;
    private TabulationAdapter mAdapter;
    private List<TabulationBean.TaskAllListBean> mTaskAllList;
    private String mXpoint;
    private String mYpoint;
    private String mTaskCity;
    private String mSort;
    private String mOtherSex;
    private String mOtherLowAge;
    private String mOtherHignAge;
    private String mOtherLowheight;
    private String mOtherHignheight;
    private String mIsVideo;
    private String mIsIdcard;
    private String mSellerType;
    public Intent mIntent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册事件
        EventBus.getDefault().register(this);
    }

    @Override
    public View initViews() {
        return UiUtils.inflate(mActivity, R.layout.fragment_tabulation);
    }

    @Override
    public void initData() {
        tabuiation_viewpager = (ViewPager) mRootView.findViewById(R.id.tabuiation_viewpager);
        mXpoint = (String) SpUtils.get(mActivity, "xpoint", "");
        mYpoint = (String) SpUtils.get(mActivity, "ypoint", "");
        mTaskCity = "130100";
        initNetWork(mXpoint, mYpoint, "1", "", "", "", "", "", "", "", "", mTaskCity);
    }

    /**
     * 网络访问借口
     *
     * @param xpoint
     * @param ypoint
     * @param sort
     * @param otherLowAge
     * @param otherHignAge
     * @param otherLowheight
     * @param otherHignheight
     * @param isVideo
     * @param isIdcard
     * @param sellerType
     * @param city
     */
    private void initNetWork(String xpoint, String ypoint, String sort, String otherSex, String otherLowAge, String otherHignAge, String otherLowheight, String otherHignheight, String isVideo, String isIdcard, String sellerType, String city) {
        RetrofitUtil.createService(TaskService.class).selectTaskInfoBy(
                mUserToken,
                "" + xpoint,
                "" + ypoint,
                "" + sort,
                "" + otherSex,
                "" + otherLowAge,
                "" + otherHignAge,
                "" + otherLowheight,
                "" + otherHignheight,
                "" + isVideo,
                "" + isIdcard,
                "" + sellerType,
                "" + city)
                .enqueue(new Callback<TabulationBean>() {
                    @Override
                    public void onResponse(Call<TabulationBean> call, Response<TabulationBean> response) {
                        if (response.isSuccessful()) {
                            TabulationBean body = response.body();
                            int resultCode = body.getResultCode();
                            if (resultCode == 1) {
                                mTaskAllList = body.getTaskAllList();
                                isShowTabulation(mTaskAllList);
                                LogUtil.d("列表访问成功" + response.body().toString());
                            } else if (resultCode == 9) {
                                LoginDialogUtils.isNewLogin(mActivity);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<TabulationBean> call, Throwable t) {
                        LogUtil.d("首页列表访问失败" + t.getMessage());
                    }
                });
    }

    private void isShowTabulation(final List<TabulationBean.TaskAllListBean> taskAllList) {
        if (taskAllList.size() > 0) {
            SystemClock.sleep(1000);
            mAdapter = new TabulationAdapter(mActivity, taskAllList);
            tabuiation_viewpager.setAdapter(mAdapter);//写法不变
            tabuiation_viewpager.setOffscreenPageLimit(4);//>=3
            tabuiation_viewpager.setPageMargin(20);//设置page间间距，自行根据需求设置
            //setPageTransformer 决定动画效果
            tabuiation_viewpager.setPageTransformer(true, new ScaleInTransformer());
            mAdapter.setOnItemClickListenter(new TabulationAdapter.OnItemClickListenter() {
                @Override
                public void onItemClick(int position, int id) {
                    mIntent = new Intent(mActivity, TaskDtailsActivity.class);
                    int value = taskAllList.get(position).getId();
                    mIntent.putExtra("id", value + "");
                    startActivity(mIntent);
                    LogUtil.d("列表点击的界面" + "第几个 -->  " + position + "\n id---" + value);
                }
            });
        }
    }

    @OnClick({R.id.task_setcurrentitem, R.id.task_setcurrentitem2, R.id.map_button, R.id.map_news, R.id.mi_task})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.task_setcurrentitem:
                if (mTaskAllList.size() > 0) {
                    tabuiation_viewpager.setCurrentItem(0); //显示到第一个
                }
                break;
            case R.id.task_setcurrentitem2:
                if (mTaskAllList.size() > 0) {
                    tabuiation_viewpager.setCurrentItem(mTaskAllList.size()); //显示到最后一个
                }
                break;
            case R.id.map_button:
                startActivity(new Intent(getActivity(), TaskActivity.class));
                break;
            case R.id.map_news:
                startActivity(new Intent(getActivity(), MapNewsActivity.class));
                break;
            case R.id.mi_task:
                startActivity(new Intent(getActivity(), MiTaskActivity.class));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowMessageEvent(FilterReturnBean messageEvent) {
        mSort = messageEvent.getSort();
        mOtherSex = messageEvent.getOtherSex();
        mOtherLowAge = messageEvent.getOtherLowAge();
        mOtherHignAge = messageEvent.getOtherHignAge();
        mOtherLowheight = messageEvent.getOtherLowheight();
        mOtherHignheight = messageEvent.getOtherHignheight();
        mIsVideo = messageEvent.getIsVideo();
        mIsIdcard = messageEvent.getIsIdcard();
        mSellerType = messageEvent.getSellerType();
        mTaskCity = messageEvent.getTaskCity();
        LogUtil.d(mActivity.getPackageName(), messageEvent.toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        initNetWork(mXpoint, mYpoint, mSort, mOtherSex, mOtherLowAge, mOtherHignAge, mOtherLowheight, mOtherHignheight, mIsVideo, mIsIdcard, mSellerType, mTaskCity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消事件注册
        EventBus.getDefault().unregister(this);
    }
}

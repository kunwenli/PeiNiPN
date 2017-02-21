package com.jsz.peini.ui.fragment.square;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.jsz.peini.R;
import com.jsz.peini.base.BaseFragment;
import com.jsz.peini.model.square.MyTaskAllBean;
import com.jsz.peini.presenter.square.SquareService;
import com.jsz.peini.ui.adapter.seller.ReportAdapter;
import com.jsz.peini.ui.adapter.square.ScreenAdapter;
import com.jsz.peini.ui.view.Popwindou;
import com.jsz.peini.utils.LoginDialogUtils;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.utils.UiUtils;
import com.jsz.peini.widget.RecyclerView.LoadMoreFooterView;
import com.jsz.peini.widget.RecyclerView.RefreshHeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jsz.peini.R.id.swipeToLoadLayout;

/**
 * Created by th on 2017/1/24.
 */
public class MiTaskSendFragMent extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {
    @InjectView(R.id.swipe_refresh_header)
    RefreshHeaderView mSwipeRefreshHeader;
    @InjectView(R.id.swipe_target)
    RecyclerView mSwipeTarget;
    @InjectView(R.id.swipe_load_more_footer)
    LoadMoreFooterView mSwipeLoadMoreFooter;
    @InjectView(swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;
    private List<MyTaskAllBean.TaskInfoByUserIdListBean> mList;
    public MiTaskRecycleerViewAdapter mAdapter;
    public Popwindou mPopwindou;
    private List<String> mListBeen;

    @Override
    public View initViews() {
        return UiUtils.inflate(mActivity, R.layout.fragment_mitaskall);
    }

    @Override
    public void initData() {
        mList = new ArrayList<>();
        mListBeen = new ArrayList<>();
        mListBeen.add("全部");
        mListBeen.add("发布中");
        mListBeen.add("进行中");
        mListBeen.add("已完成");
        mListBeen.add("已取消");
        mSwipeTarget.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new MiTaskRecycleerViewAdapter(mActivity, mList);
        mSwipeTarget.setAdapter(mAdapter);
        mSwipeToLoadLayout.setOnRefreshListener(this);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);
        mAdapter.setOnLongClickListener(new MiTaskRecycleerViewAdapter.OnLongClickListener() {
            @Override
            public void onLongClickItem(int position) {
                ToastUtils.showToast(mActivity, "要删除的" + position);
            }

            @Override
            public void onScreenClickItem() {
                initPopWindows();
            }

            @Override
            public void onClickItem(int position) {

            }

            @Override
            public void onChatClickItem(int position) {

            }

            @Override
            public void onTaskStatusClickItem(int position, int taskStatus) {

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        initNetWork();
    }
    private void initPopWindows() {
        mPopwindou = new Popwindou(mActivity, UiUtils.inflate(mActivity, R.layout.fragment_mitaskall));
        View view = UiUtils.inflate(mActivity, R.layout.pop_recyclerview);
        mPopwindou.init(view, Gravity.BOTTOM, true);
        RecyclerView report_recyclerview = (RecyclerView) view.findViewById(R.id.report_recyclerview);
        report_recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        ScreenAdapter adapter = new ScreenAdapter(mActivity, mListBeen);
        report_recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(new ReportAdapter.OnItemClickListener() {
            @Override
            public void ItemClic(int position) {
                mPopwindou.dismiss();
            }
        });
        view.findViewById(R.id.item_popupwindows_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopwindou.dismiss();
            }
        });
    }

    /**
     * 我的任务访问获取
     */
    private void initNetWork() {
        RetrofitUtil.createService(SquareService.class)
                .getTaskInfoByUserId(mUserToken)
                .enqueue(new Callback<MyTaskAllBean>() {
                    @Override
                    public void onResponse(Call<MyTaskAllBean> call, Response<MyTaskAllBean> response) {
                        if (response.isSuccessful()) {
                            int resultCode = response.body().getResultCode();
                            if (resultCode == 1) {
                                List<MyTaskAllBean.TaskInfoByUserIdListBean> taskInfoByUserIdList = response.body().getTaskInfoByUserIdList();
                                mList.clear();
                                mList.addAll(taskInfoByUserIdList);
                                mAdapter.notifyDataSetChanged();
                            } else if (resultCode == 9) {
                                LoginDialogUtils.isNewLogin(mActivity);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MyTaskAllBean> call, Throwable t) {

                    }
                });
    }

    @Override
    public void onRefresh() {
        mSwipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeToLoadLayout.setRefreshing(false);
                mAdapter.notifyDataSetChanged();
                initNetWork();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        mSwipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeToLoadLayout.setLoadingMore(false);
                mAdapter.notifyDataSetChanged();
            }
        }, 2000);
    }

}

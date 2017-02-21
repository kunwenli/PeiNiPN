package com.jsz.peini.ui.activity.task;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.tabulation.TaskList;
import com.jsz.peini.presenter.task.TaskService;
import com.jsz.peini.ui.adapter.tabulation.TabulationAdapter;
import com.jsz.peini.ui.adapter.task.TaskTabulationAdapter;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.LoginDialogUtils;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.ToastUtils;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by th on 2017/1/20.
 */
public class MoreTaskActivity extends BaseActivity {
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.right_button)
    TextView mRightButton;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.tabuiation_viewpager)
    ViewPager mTabuiationViewpager;
    public TaskTabulationAdapter mAdapter;
    public Activity mActivity;
    public List<View> mViews;
    public Intent mIntent;
    String idStr;

    @Override
    public int initLayoutId() {
        return R.layout.activity_moretask;
    }

    @Override
    public void initView() {
        mTitle.setText("多任务列表");
        mActivity = this;
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViews = new ArrayList<>();
        idStr = getIntent().getStringExtra("idStr");
    }

    @Override
    public void initData() {
        initNetWork();
    }

    private void initNetWork() {
        RetrofitUtil.createService(TaskService.class).selectTaskInfoBySort(
                "" + mXpoint,
                "" + mYpoint,
                "" + "1",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "" + "130100",
                "" + idStr)
                .enqueue(new Callback<TaskList>() {
                    @Override
                    public void onResponse(Call<TaskList> call, Response<TaskList> response) {
                        if (response.isSuccessful()) {
                            TaskList body = response.body();
                            ToastUtils.showToast(mActivity, body.getResultDesc());
                            if (body.getResultCode() == 1) {
                                List<TaskList.TaskAllListBean> taskAllList = body.getTaskAllList();
                                isShowTabulation(mActivity, taskAllList);
                            } else if (body.getResultCode() == 9) {
                                LoginDialogUtils.isNewLogin(mActivity);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<TaskList> call, Throwable t) {
                        ToastUtils.showToast(mActivity, t.getMessage());
                    }
                });
    }

    private void isShowTabulation(Activity activity, final List<TaskList.TaskAllListBean> taskAllList) {
        mAdapter = new TaskTabulationAdapter(mActivity, taskAllList);
        mTabuiationViewpager.setAdapter(mAdapter);//写法不变
        mTabuiationViewpager.setOffscreenPageLimit(4);//>=3
        mTabuiationViewpager.setPageMargin(20);//设置page间间距，自行根据需求设置
        //setPageTransformer 决定动画效果
        mTabuiationViewpager.setPageTransformer(true, new ScaleInTransformer());
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

package com.jsz.peini.ui.activity.task;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.setting.SuccessfulBean;
import com.jsz.peini.presenter.task.TaskService;
import com.jsz.peini.ui.view.pay.MyRadioButton;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.StringUtils;
import com.jsz.peini.utils.ToastUtils;

import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kunwe on 2016/12/5.
 */
public class TaskCancelActivity extends BaseActivity {

    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.right_button)
    Button mRightButton;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.task_radiobutton1)
    MyRadioButton mTaskRadiobutton1;
    @InjectView(R.id.task_radiobutton2)
    MyRadioButton mTaskRadiobutton2;
    @InjectView(R.id.task_radiogroup)
    RadioGroup mTaskRadiogroup;
    public String mCancleType = "";
    public TaskCancelActivity mActivity;
    public String mId;

    @Override
    public int initLayoutId() {
        return R.layout.activity_taskcancel;
    }

    @Override
    public void initView() {
        super.initView();
        mActivity = this;
        Intent intent = getIntent();
        mId = intent.getStringExtra("id");
        mTitle.setText("取消任务");
        mRightButton.setText("提交");
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initListener() {
        mTaskRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                switch (checkedRadioButtonId) {
                    case R.id.task_radiobutton1:
                        LogUtil.d("任务取消方式1");
                        mCancleType = "1";
                        break;
                    case R.id.task_radiobutton2:
                        LogUtil.d("任务取消方式2");
                        mCancleType = "2";
                        break;
                }
            }
        });

        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!StringUtils.isNoNull(mCancleType)) {
                    ToastUtils.showToast(mActivity, "输入选择取消的类型");
                    return;
                }
                if (!StringUtils.isNoNull(mId)) {
                    ToastUtils.showToast(mActivity, "mId为空");
                    finish();
                    return;
                }

                initNetWork();
            }
        });

    }

    private void initNetWork() {
        LogUtil.d("取消任务的请求参数" + mUserToken + mId + mCancleType);
        RetrofitUtil.createService(TaskService.class)
                .cancelTaskInfo(mUserToken, mId, mCancleType)
                .enqueue(new Callback<SuccessfulBean>() {
                    @Override
                    public void onResponse(Call<SuccessfulBean> call, Response<SuccessfulBean> response) {
                        if (response.isSuccessful()) {
                            String toString = response.body().toString();
                            SuccessfulBean body = response.body();
                            LogUtil.d("这个是取消的按钮" + toString);
                            if (body.getResultCode() == 1) {
                                finish();
                            } else if (body.getResultCode() == 9) {

                            } else {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessfulBean> call, Throwable t) {

                    }
                });
    }

}

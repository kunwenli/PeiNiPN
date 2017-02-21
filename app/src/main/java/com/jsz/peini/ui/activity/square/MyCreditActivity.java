package com.jsz.peini.ui.activity.square;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.square.MyCreditBean;
import com.jsz.peini.presenter.square.SquareService;
import com.jsz.peini.ui.view.TextProgressBar;
import com.jsz.peini.ui.view.square.MyColorProgressBar;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.RetrofitUtil;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by th on 2017/1/16.
 */
public class MyCreditActivity extends BaseActivity {
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.creditNum)
    TextProgressBar mCreditNum;
    @InjectView(R.id.selfNum)
    TextProgressBar mSelfNum;
    @InjectView(R.id.taskNum)
    TextProgressBar mTaskNum;
    @InjectView(R.id.idcardNum)
    MyColorProgressBar mIdcardNum;
    public int mCredit;
    public int mIdcard;
    public int mSelf;
    public int mTask;

    @Override
    public int initLayoutId() {
        return R.layout.activity_my_credit;
    }

    @Override
    public void initView() {
        mTitle.setText("我的信用");
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initNetWork();
    }

    public void initUpdataView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mIdcardNum.setCurrentValues(new Random().nextInt(100));
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

        /*设置更新时间*/
        mIdcardNum.setUnit("评估于2017-15-16");
        mCreditNum.setProgress(mCredit);
        mTaskNum.setProgress(mTask);
        mSelfNum.setProgress(mSelf);
    }

    /**
     * 网络访问
     */
    private void initNetWork() {
        RetrofitUtil.createService(SquareService.class).getCredit(mUserToken).enqueue(new Callback<MyCreditBean>() {
            @Override
            public void onResponse(Call<MyCreditBean> call, Response<MyCreditBean> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResultCode() == 1) {
                        LogUtil.d(response.body().toString());
                        MyCreditBean.isMyCreditBean myCredit = response.body().getMyCredit();
                        mIdcard = myCredit.getIdcardNum();
                        mCredit = myCredit.getCreditNum();
                        mSelf = myCredit.getSelfNum();
                        mTask = myCredit.getTaskNum();
                        initUpdataView();
                    } else {
                        LogUtil.d(response.body().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<MyCreditBean> call, Throwable t) {
                LogUtil.d("我的信用界面" + t.getMessage());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}

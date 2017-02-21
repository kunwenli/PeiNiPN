package com.jsz.peini.ui.activity.news;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.news.MyFans;
import com.jsz.peini.presenter.news.NewRetrofitHttp;
import com.jsz.peini.ui.adapter.news.FansAdapter;
import com.jsz.peini.ui.view.RecyclerViewDivider;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by th on 2017/1/2.
 */
public class FansActivity extends BaseActivity {
    @InjectView(R.id.fans)
    RecyclerView mFans;
    ArrayList mList = new ArrayList();
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.right_button)
    TextView mRightButton;
    @InjectView(R.id.kuntoolbar)
    RelativeLayout mKuntoolbar;
    private FansAdapter mAdapter;
    private MyFans mBody;
    private FansActivity mFansActivity;

    @Override
    public int initLayoutId() {
        return R.layout.activity_fans;
    }

    @Override
    public void initView() {
        super.initView();
        mFansActivity = this;
        mTitle.setText("粉丝");
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        mFans.setLayoutManager(new LinearLayoutManager(mFansActivity));
        mAdapter = new FansAdapter(mList, mFansActivity);
        mFans.setAdapter(mAdapter);
        mFans.addItemDecoration(new RecyclerViewDivider(mFansActivity, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(mFansActivity, R.color.backgroundf1f1f1)));
        initNetWork();
    }

    private void initNetWork() {
        NewRetrofitHttp.getNetWork().myFans(mUserToken).enqueue(new Callback<MyFans>() {
            @Override
            public void onResponse(Call<MyFans> call, Response<MyFans> response) {
                if (response.isSuccessful() && response.body().getResultCode() == 1) {
                    mBody = response.body();
                    mAdapter.setMyFansMode(mBody);
                    mAdapter.setMyFansList(mBody.getObjectList());
                }
            }

            @Override
            public void onFailure(Call<MyFans> call, Throwable t) {

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

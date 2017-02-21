package com.jsz.peini.ui.fragment.news;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseFragment;
import com.jsz.peini.model.news.NewsList;
import com.jsz.peini.presenter.news.NewRetrofitHttp;
import com.jsz.peini.ui.activity.news.FansActivity;
import com.jsz.peini.ui.adapter.news.NewsRecyclerviewAdapter;
import com.jsz.peini.ui.view.RecyclerViewDivider;
import com.jsz.peini.utils.LoginDialogUtils;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jsz.peini.R.id.new_recyclerview;

/**
 * Created by kunwe on 2016/12/2.
 */
public class NewsFragment extends BaseFragment {
    private static final String TAG = "NewsFragment";
    @InjectView(new_recyclerview)
    RecyclerView mNewrecyclerview;
    private NewsRecyclerviewAdapter mAdapter;
    private NewsList mBody = null;
    private List<NewsList.ObjectListBean> mObjectList = new ArrayList<>();

    @Override
    public View initViews() {
        return UiUtils.inflate(mActivity, R.layout.fragment_news);
    }

    @Override
    public void initData() {
        mNewrecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new NewsRecyclerviewAdapter(mActivity, mObjectList, mBody);
        mAdapter.addHeadView(UiUtils.inflate(mActivity, R.layout.news_head));
        mNewrecyclerview.setAdapter(mAdapter);
        mNewrecyclerview.addItemDecoration(new RecyclerViewDivider(mActivity, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(mActivity, R.color.backgroundf1f1f1)));

        initNetWork();

    }

    @Override
    public void initListentr() {
        mAdapter.setOnItemEntryClickListener(new NewsRecyclerviewAdapter.OnItemEntryClickListener() {
            @Override
            public void ItemObjectList() {

            }

            @Override
            public void ItemUserClick() {
                startActivity(new Intent(mActivity, FansActivity.class));
            }
        });
    }

    /**
     * 访问网络
     */
    private void initNetWork() {
        NewRetrofitHttp.getNetWork().myConcern(mUserToken).enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(Call<NewsList> call, Response<NewsList> response) {
                if (response.isSuccessful()) {
                    NewsList body = response.body();
                    ToastUtils.showToast(mActivity, body.getResultDesc());
                    if (body.getResultCode() == 1) {
                        mBody = response.body();
                        mObjectList = mBody.getObjectList();
                        mAdapter.setNewsList(mObjectList);
                        mAdapter.setNewsmBody(mBody);
                    } else if (body.getResultCode() == 9) {
                        LoginDialogUtils.isNewLogin(mActivity);
                    }

                }
            }

            @Override
            public void onFailure(Call<NewsList> call, Throwable t) {
                ToastUtils.showToast(mActivity, t.getMessage());
            }
        });
    }
}

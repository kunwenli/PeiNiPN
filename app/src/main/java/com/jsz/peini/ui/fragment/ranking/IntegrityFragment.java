package com.jsz.peini.ui.fragment.ranking;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseFragment;
import com.jsz.peini.model.ranking.RanKingBean;
import com.jsz.peini.presenter.ranking.RanKingService;
import com.jsz.peini.ui.adapter.ranking.RanKingAdapter;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.UiUtils;

import java.util.List;

import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 财富榜
 */
public class IntegrityFragment extends BaseFragment {
    @InjectView(R.id.ranking_rv)
    RecyclerView mRankingRv;
    public RanKingAdapter mAdapter;
    /*rType	Int		1:金币榜2:土豪榜3:诚信榜*/
    public int mRType = 3;
    public List<RanKingBean.RankListBean> mListTitle;
    String dType;
    public RanKingBean mBody;
    int type = 1;

    @Override
    public View initViews() {
        return UiUtils.inflate(mActivity, R.layout.fragment_integrity);
    }


    @Override
    public void initData() {
        LinearLayoutManager layout = new LinearLayoutManager(mActivity);
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        mRankingRv.setLayoutManager(layout);
        dType = "1";
        initNetWork(type);
    }


    /**
     * 访问网络获取数据
     *
     * @param type
     */
    private void initNetWork(final int type) {
        RetrofitUtil.createService(RanKingService.class).getRank(dType, mRType + "", mUserToken).enqueue(new Callback<RanKingBean>() {
            @Override
            public void onResponse(Call<RanKingBean> call, final Response<RanKingBean> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResultCode() == 1) {
                        setRanKingView(response, type);
                    }
                }
            }

            @Override
            public void onFailure(Call<RanKingBean> call, Throwable t) {

            }
        });
    }

    private void setRanKingView(Response<RanKingBean> response, int type) {
        mBody = response.body();
        LogUtil.d("财富榜" + mBody.toString());
        if (type != 1) {
            mAdapter.notifyItemRangeChanged(1, mBody.getRankList().size(), null);
        } else {
            mAdapter = new RanKingAdapter(mActivity, mBody, mRType);
            mRankingRv.setAdapter(mAdapter);
        }
        mAdapter.setOnTabSelectedListener(new RanKingAdapter.OnTabSelectedListener() {
            @Override
            public void OnTabItemSelectedListener(int tabPosition) {
                switch (tabPosition) {
                    case 0:
                        dType = "1";
                        break;
                    case 1:
                        dType = "2";
                        break;
                    case 2:
                        dType = "3";
                        break;
                    case 3:
                        dType = "0";
                        break;
                }
                initNetWork(5);
            }
        });
    }
}

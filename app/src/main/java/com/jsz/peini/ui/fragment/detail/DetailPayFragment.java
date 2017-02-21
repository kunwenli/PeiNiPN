package com.jsz.peini.ui.fragment.detail;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseFragment;
import com.jsz.peini.model.setting.SuccessfulBean;
import com.jsz.peini.model.square.ScoreHistoryListBean;
import com.jsz.peini.presenter.square.SquareService;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by th on 2017/1/18.
 */

public class DetailPayFragment extends BaseFragment {
    /**
     * 支出
     */
    String type = "0";
    @InjectView(R.id.detail_pay)
    RecyclerView mDetailPay;
    public List<ScoreHistoryListBean.DataBean> mList;
    public DetailAdapter mAdapter;

    @Override
    public View initViews() {
        return UiUtils.inflate(mActivity, R.layout.fragment_detailpay);
    }

    @Override
    public void initData() {
        mList = new ArrayList<>();
        mDetailPay.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new DetailAdapter(mActivity, mList);
        mDetailPay.setAdapter(mAdapter);
        mAdapter.setOnLongItemClickListener(new DetailAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(String hisid, int position) {
                dialog(hisid, position);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        initNetWork();
    }

    protected void dialog(final String hisid, final int position) {
        new AlertDialog.Builder(mActivity)
                .setTitle("清除积分明细")
                .setMessage("确定清除吗?")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        initDelete(hisid, position);
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void initDelete(String hisid, final int position) {
        RetrofitUtil.createService(SquareService.class)
                .delScoreHistory(mUserToken, hisid)
                .enqueue(new Callback<SuccessfulBean>() {
                    @Override
                    public void onResponse(Call<SuccessfulBean> call, Response<SuccessfulBean> response) {
                        if (response.isSuccessful()) {
                            SuccessfulBean body = response.body();
                            if (body.getResultCode() == 1) {
                                mList.remove(position);
                                mAdapter.notifyItemRemoved(position);
                            } else {
                                if (body.getResultCode() == 9) {
                                    LogUtil.d("token失效");
                                } else {
                                    LogUtil.d("访问失败");

                                }
                            }
                        } else {
                            LogUtil.d("访问失败");
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessfulBean> call, Throwable t) {
                        LogUtil.d("访问失败" + t.getMessage());
                    }
                });
    }

    private void initNetWork() {
        RetrofitUtil.createService(SquareService.class)
                .getScoreHistoryList("1", "10", mUserToken, type)
                .enqueue(new Callback<ScoreHistoryListBean>() {
                    @Override
                    public void onResponse(Call<ScoreHistoryListBean> call, Response<ScoreHistoryListBean> response) {
                        if (response.isSuccessful()) {
                            ScoreHistoryListBean body = response.body();
                            if (body.getResultCode() == 1) {
                                mList.clear();
                                mList.addAll(body.getData());
                                mAdapter.notifyDataSetChanged();
                                LogUtil.d("界面可见访问网络");
                            } else {
                                if (body.getResultCode() == 9) {
                                    LogUtil.d("token失效");
                                } else {
                                    LogUtil.d("访问失败");

                                }
                            }
                        } else {
                            LogUtil.d("访问失败");
                        }
                    }

                    @Override
                    public void onFailure(Call<ScoreHistoryListBean> call, Throwable t) {
                        LogUtil.d("访问失败" + t.getMessage());
                    }
                });
    }
}

package com.jsz.peini.ui.activity.task;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.xlhratingbar_lib.XLHRatingBar;
import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.setting.SuccessfulBean;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.presenter.assess.AssessService;
import com.jsz.peini.utils.LogUtil;

import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by th on 2016/12/7.
 */
public class SellerSuccessPingjia extends BaseActivity {
    @InjectView(R.id.seller_success_toolbar)
    Toolbar seller_success_toolbar;
    @InjectView(R.id.ratingBar1)
    XLHRatingBar ratingBar1;
    @InjectView(R.id.ratingBar2)
    XLHRatingBar ratingBar2;
    @InjectView(R.id.ratingBar3)
    XLHRatingBar ratingBar3;
    @InjectView(R.id.ratingBar4)
    XLHRatingBar ratingBar4;
    @InjectView(R.id.ratingBar5)
    XLHRatingBar ratingBar5;
    @InjectView(R.id.ratingBar6)
    XLHRatingBar ratingBar6;
    @InjectView(R.id.seller_success)
    Button sellerSuccess;
    private Retrofit retrofit;
    private int countSelected1;
    private int countSelected2;
    private int countSelected3;
    private int countSelected4;
    private int countSelected5;
    private int countSelected6;

    @Override
    public int initLayoutId() {
        return R.layout.activity_seller_success;
    }

    @Override
    public void initView() {
        seller_success_toolbar.setTitle("");
        setSupportActionBar(seller_success_toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        seller_success_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        retrofit = new Retrofit.Builder()
                .baseUrl(IpConfig.HttpPeiniIp)
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void initData() {
        /**得到星星的个数*/
        ratingBar1.setOnRatingChangeListener(new XLHRatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(int countSelected) {
                countSelected1 = countSelected;
            }
        });
        ratingBar2.setOnRatingChangeListener(new XLHRatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(int countSelected) {
                countSelected2 = countSelected;
            }
        });
        ratingBar3.setOnRatingChangeListener(new XLHRatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(int countSelected) {
                countSelected3 = countSelected;
            }
        });
        ratingBar4.setOnRatingChangeListener(new XLHRatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(int countSelected) {
                countSelected4 = countSelected;
            }
        });
        ratingBar5.setOnRatingChangeListener(new XLHRatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(int countSelected) {
                countSelected5 = countSelected;
            }
        });
        ratingBar6.setOnRatingChangeListener(new XLHRatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(int countSelected) {
                countSelected6 = countSelected;
            }
        });

        sellerSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.i("请假的星星个数", "" + countSelected1 + "  " + countSelected2 + "  " + countSelected3);
                AssessService assessService = retrofit.create(AssessService.class);
                Call<SuccessfulBean> enterCall =
                        assessService.sellerAndUserJudge(
                                "1",//店铺id
                                "1",//任务id
                                "" + countSelected1,
                                "" + countSelected2,
                                "" + countSelected3,
                                "1",//评分用户id
                                "1",//被评分任务id
                                "" + countSelected4,
                                "" + countSelected5,
                                "" + countSelected6,
                                "0",//打赏金额 默认0
                                "1");//订单id
                enterCall.enqueue(new Callback<SuccessfulBean>() {
                    @Override
                    public void onResponse(Call<SuccessfulBean> call, Response<SuccessfulBean> response) {
                        if (response.isSuccessful()) {
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessfulBean> call, Throwable t) {

                    }
                });
            }
        });
    }
}

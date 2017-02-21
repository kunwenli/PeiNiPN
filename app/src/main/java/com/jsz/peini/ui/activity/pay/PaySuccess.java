package com.jsz.peini.ui.activity.pay;

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
 * Created by kunwe on 2016/12/1.
 */
public class PaySuccess extends BaseActivity {
    @InjectView(R.id.pay_success)
    Button pay_success;

    @InjectView(R.id.pay_success_toolbar)
    Toolbar pay_success_toolbar;
    @InjectView(R.id.ratingBar)
    XLHRatingBar ratingBar;
    @InjectView(R.id.ratingBar2)
    XLHRatingBar ratingBar2;
    @InjectView(R.id.ratingBar3)
    XLHRatingBar ratingBar3;
    private Retrofit retrofit;
    private int countSelected1;
    private int countSelected3;
    private int countSelected2;

    @Override
    public int initLayoutId() {
        return R.layout.activity_pay_success;
    }

    @Override
    public void initView() {
        pay_success_toolbar.setTitle("");
        setSupportActionBar(pay_success_toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回????
        pay_success_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
        /**操作*/
    }

    @Override
    public void initData() {
        /**得到星星的个数*/
        ratingBar.setOnRatingChangeListener(new XLHRatingBar.OnRatingChangeListener() {
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
        pay_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.i("请假的星星个数", "" + countSelected1 + "  " + countSelected2 + "  " + countSelected3);
                AssessService assessService = retrofit.create(AssessService.class);
                Call<SuccessfulBean> enterCall = assessService.sellerJudge("1", "" + (countSelected1 * 20), "" + (countSelected2 * 20), "" + (countSelected3 * 20), "1");
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

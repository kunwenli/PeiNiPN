package com.jsz.peini.ui.activity.search;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.search.SearchHot;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.presenter.search.SearchService;
import com.jsz.peini.ui.adapter.search.SellerTabulationAdapter;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.SpUtils;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by th on 2016/12/20.
 */
public class SellerTabulation extends BaseActivity {
    @InjectView(R.id.seller_tabulation_1)
    LinearLayout sellerTabulation1;
    @InjectView(R.id.seller_tabulation_2)
    LinearLayout sellerTabulation2;
    @InjectView(R.id.seller_tabulation_3)
    LinearLayout sellerTabulation3;
    @InjectView(R.id.seller_tabulation_4)
    LinearLayout sellerTabulation4;
    @InjectView(R.id.seller_item)
    PullLoadMoreRecyclerView sellerItem;
    @InjectView(R.id.seller_tabulation_toolbar)
    Toolbar sellerTabulationToolbar;

    private Retrofit retrofit;

    @Override
    public int initLayoutId() {
        return R.layout.seller_tabulation;
    }

    @Override
    public void initView() {
        super.initView();
        sellerTabulationToolbar.setTitle("");
        setSupportActionBar(sellerTabulationToolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回????
        sellerTabulationToolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
        Intent intent = getIntent();
        String searchWord = intent.getStringExtra("searchWord");
        //增加返回值为String的支持
//增加返回值为Gson的支持(以实体类返回)

        if (!TextUtils.isEmpty(searchWord)) {
            initNetworkSeller(searchWord, (String) SpUtils.get(this, "xpoint", ""), (String) SpUtils.get(this, "ypoint", ""));
        }

    }

    private void IsShowRecyclerView(List<SearchHot.SellerListBean> sellerInfo) {
        /**这个是底部的滑动列表*/
        sellerItem.setLinearLayout();
        SellerTabulationAdapter adapter = new SellerTabulationAdapter(this, sellerInfo);
        sellerItem.setAdapter(adapter);
        /**为每个item之间添加分割线*/
        sellerItem.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        sellerItem.setPullRefreshEnable(false);
        /**不需要下拉刷新*/
        sellerItem.setPushRefreshEnable(false);
    }

    @Override
    public void initData() {
        super.initData();
    }


    @OnClick({R.id.seller_tabulation_1, R.id.seller_tabulation_2, R.id.seller_tabulation_3, R.id.seller_tabulation_4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.seller_tabulation_1:
                break;
            case R.id.seller_tabulation_2:
                break;
            case R.id.seller_tabulation_3:
                break;
            case R.id.seller_tabulation_4:
                break;
        }
    }

    private void initNetworkSeller(String o, String xpoint, String ypoint) {
        SearchService sellerService = retrofit.create(SearchService.class);//这里采用的是Java的动态代理模式
        Call<SearchHot> sellerBeanCall = sellerService.searchAllSeller(o, xpoint, ypoint, "1", "20");
        sellerBeanCall.enqueue(new Callback<SearchHot>() {
            @Override
            public void onResponse(Call<SearchHot> call, Response<SearchHot> response) {
                String s = response.body().toString();
                LogUtil.i("这个是商家界面", "数据" + s);
                SearchHot sellerBean = response.body();
                if (sellerBean.getResultCode() == 1) {
                    List<SearchHot.SellerListBean> sellerInfo = response.body().getSellerList();
                    if (sellerInfo.size() > 0) {
                        IsShowRecyclerView(sellerInfo);
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchHot> call, Throwable t) {

            }
        });
    }

}

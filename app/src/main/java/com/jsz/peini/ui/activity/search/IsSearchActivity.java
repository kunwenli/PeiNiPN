package com.jsz.peini.ui.activity.search;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jsz.peini.PeiNiApp;
import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.gen.SerachInfo;
import com.jsz.peini.gen.SerachInfoDao;
import com.jsz.peini.model.search.SearchBean;
import com.jsz.peini.model.search.SearchHot;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.presenter.serach.SerachService;
import com.jsz.peini.ui.activity.seller.SellerMessageActivity;
import com.jsz.peini.ui.adapter.search.IsSearchRecyclerviewAdapter;
import com.jsz.peini.ui.adapter.search.MyGridAdapter;
import com.jsz.peini.ui.adapter.search.SearchHotAdapter;
import com.jsz.peini.ui.view.MyGridView;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.SpUtils;
import com.jsz.peini.utils.UiUtils;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kunwe on 2016/12/2.
 */
public class IsSearchActivity extends BaseActivity {
    /**
     * 这个是搜索的列表
     */
    @InjectView(R.id.issearch_recyclerview)
    RecyclerView issearch_recyclerview;
    /**
     * 清楚聊天记录
     */
    @InjectView(R.id.search_delete_sq)
    Button searchDeleteSq;
    private IsSearchRecyclerviewAdapter searchRecyclerviewAdapter;
    /**
     * 列表
     */
    @InjectView(R.id.search_toolbar)
    Toolbar search_toolbar;
    @InjectView(R.id.search_recyclerview)
    FrameLayout fsearchRecyclerview;
    @InjectView(R.id.issearch_listview)
    ListView issearchListview;
    @InjectView(R.id.search_listview)
    FrameLayout fsearchListview;
    /**
     * 搜索布局
     */
    @InjectView(R.id.search_issearch)
    LinearLayout searchIssearch;
    /**
     * 输入的文本框
     */
    @InjectView(R.id.phone_edt)
    EditText phone_edt;
    /**
     * 取消的按钮
     */
    @InjectView(R.id.issearch_button)
    Button issearchButton;
    /**
     * 这个是九宫格头部局
     */
    private View issearch_header;
    /**
     * 头部局的适配器
     */
    private MyGridAdapter myGridAdapter;
    /**
     * 网络请求的数据
     */
    private List<SearchBean.WordListBean> wordList;
    /**
     * 本地缓存的搜索记录
     */
    private List<SerachInfo> serachInfoList;
    private SerachInfoDao serachInfoDao;
    private SearchHotAdapter isListViewAdapter;
    private IsSearchActivity mActivity;
    public Intent mIntent;

    @Override
    public int initLayoutId() {
        return R.layout.activity_issearchactivty;
    }

    @Override
    public void initView() {
        search_toolbar.setTitle("");
        setSupportActionBar(search_toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回????
        search_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        mActivity = IsSearchActivity.this;
        serachInfoDao = PeiNiApp.SerachInfoDao;
        /**历史数据的集合*/
        serachInfoList = serachInfoDao.queryBuilder().orderDesc(SerachInfoDao.Properties.Id).limit(10).list();
        LogUtil.i("这个是存储的历史记录", "" + serachInfoList.size());
        if (serachInfoList.size() > 0) {
            LogUtil.i("这个是存储的历史记录", "" + serachInfoList.toString());
        }
        initNetWork();
        initSerach();
    }

    /**
     * 显示数据的
     *
     * @param wordList
     */
    private void initShowRecyclerview(List<SearchBean.WordListBean> wordList) {
        searchRecyclerviewAdapter = new IsSearchRecyclerviewAdapter(mActivity, serachInfoList);
        issearch_recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        issearch_recyclerview.setAdapter(searchRecyclerviewAdapter);
        //为每个item之间添加分割线
        issearch_recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        searchRecyclerviewAdapter.setOnItemClickListener(new IsSearchRecyclerviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, SerachInfo data) {
                LogUtil.i("搜索历史的点击事件", data.getType() + "-->Name" + data.getAddress());
                String address = data.getIcenAddress();
                int integer = Integer.parseInt(address);
                if (integer == 2) {
                    mIntent = new Intent(mActivity, SellerTabulation.class);
                    mIntent.putExtra("searchWord", data.getType());
                    mActivity.startActivity(mIntent);
                    mActivity.finish();
                } else {
                    mIntent = new Intent(mActivity, SellerMessageActivity.class);
                    mIntent.putExtra("id", data.getIds() + "");
                    mIntent.putExtra("type", 1);
                    mActivity.startActivity(mIntent);
                    mActivity.finish();
                }
            }
        });
        addRecycleerviewHead(wordList);
    }

    /**
     * 添加的头部局
     */
    private void addRecycleerviewHead(List<SearchBean.WordListBean> wordList) {
        issearch_header = UiUtils.inflate(mActivity, R.layout.issearch_header);
        searchRecyclerviewAdapter.setHeaderView(issearch_header);
        MyGridView myGridView = (MyGridView) issearch_header.findViewById(R.id.search_gridview);
        if (wordList != null && wordList.size() > 0) {
            myGridAdapter = new MyGridAdapter(this, wordList);
            myGridView.setAdapter(myGridAdapter);
            myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    LogUtil.i("这个是搜索界面", "点击的九宫格界面" + i + l);
                }
            });
        }
    }

    /**
     * 输入框搜索弹出的东西
     */
    private void initSerach() {
        final String xpoint = (String) SpUtils.get(this, "xpoint", "");
        final String ypoint = (String) SpUtils.get(this, "ypoint", "");
        phone_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String trim = phone_edt.getText().toString().trim();
                LogUtil.i("这个是搜索界面", "输入的汉子 -->" + trim);
                if (trim.length() > 0) {
                    initSearchNetWork(xpoint, ypoint, trim);
                } else {
                    fsearchListview.setVisibility(View.GONE);
                    fsearchRecyclerview.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 发起网络请求
     *
     * @param s
     * @param xpoint
     * @param ypoint
     */
    private void initSearchNetWork(String xpoint, String ypoint, String s) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IpConfig.HttpPeiniIp)
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SerachService serachService = retrofit.create(SerachService.class);//这里采用的是Java的动态代理模式
        Call<SearchHot> searchHotCall = serachService.searchFastSeller(xpoint, ypoint, s);
        searchHotCall.enqueue(new Callback<SearchHot>() {
            @Override
            public void onResponse(Call<SearchHot> call, Response<SearchHot> response) {
                SearchHot body = response.body();
                if (body.getResultCode() == 1 && body.getSellerList().size() > 0) {
                    List<SearchHot.SellerListBean> sellerList = body.getSellerList();
                    LogUtil.i("搜索界面热搜", "返回的数据" + body.getSellerList().toString());
                    isShowSearchHot(sellerList);
                    fsearchListview.setVisibility(View.VISIBLE);
                    fsearchRecyclerview.setVisibility(View.GONE);
                } else {
                    fsearchListview.setVisibility(View.GONE);
                    fsearchRecyclerview.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<SearchHot> call, Throwable t) {

            }
        });
    }

    private void isShowSearchHot(final List<SearchHot.SellerListBean> sellerList) {
        isListViewAdapter = new SearchHotAdapter(this, sellerList);
        issearchListview.setAdapter(isListViewAdapter);
        issearchListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                fsearchListview.setVisibility(View.GONE);
                fsearchRecyclerview.setVisibility(View.VISIBLE);
                SearchHot.SellerListBean sellerListBean = sellerList.get(i);
                /**"id": 3,
                 "sellerName": "大驴健身",
                 "sellerAddress": "友谊公园",
                 "sellerType": 104,
                 "districtName": "东胜",
                 "information": "健身第一",
                 "labelsName": "健身房",
                 "searchType": 1,
                 */
                SerachInfo serachInfo = new SerachInfo(null,
                        "" + sellerListBean.getId(), //id
                        "" + sellerListBean.getSellerName(),//地址
                        "" + sellerListBean.getSellerAddress(),//地址
                        "" + sellerListBean.getSellerType(),//这个是商家type
                        "" + sellerListBean.getDistrictName(),//这地址
                        "" + sellerListBean.getInformation(),//这地址
                        "" + sellerListBean.getLabelsName(),//这地址
                        "" + sellerListBean.getSearchType());//标记
                if (serachInfoList.contains(serachInfo)) {
                    LogUtil.i("搜索界面", "已经有这条数据了");
                } else {
                    serachInfoDao.insert(serachInfo);
                    LogUtil.i("搜索界面", "pop弹出那个==>" + i + "存取成功-->" + sellerListBean.getSellerName());
                }
                if (sellerListBean.getSearchType() == 2) {
                    Intent intent = new Intent(mActivity, SellerTabulation.class);
                    intent.putExtra("searchWord", sellerListBean.getSellerName());
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(mActivity, SellerMessageActivity.class);
                    mActivity.startActivity(intent);
                    finish();
                }

            }
        });
    }

    /**
     * 搜索热词的搜索功能
     */
    private void initNetWork() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IpConfig.HttpPeiniIp)
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SerachService serachService = retrofit.create(SerachService.class);//这里采用的是Java的动态代理模式
        Call<SearchBean> searchBeanCall = serachService.getCityList();
        searchBeanCall.enqueue(new Callback<SearchBean>() {
            @Override
            public void onResponse(Call<SearchBean> call, Response<SearchBean> response) {
                SearchBean body = response.body();
                if (body.getResultCode() == 1) {
                    wordList = body.getWordList();
                    initShowRecyclerview(wordList);
                }
            }

            @Override
            public void onFailure(Call<SearchBean> call, Throwable t) {

            }
        });
    }

    @OnClick({R.id.search_issearch, R.id.issearch_button, R.id.search_delete_sq})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_issearch:
                break;
            case R.id.issearch_button:
                finish();
                break;
            case R.id.search_delete_sq:
                serachInfoList.clear();
                searchRecyclerviewAdapter.notifyItemRangeChanged(1, serachInfoList.size());
                serachInfoDao.deleteAll();
                break;
        }
    }

}

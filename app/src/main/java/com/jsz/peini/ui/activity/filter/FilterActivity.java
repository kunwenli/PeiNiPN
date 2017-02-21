package com.jsz.peini.ui.activity.filter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.CrtyBean;
import com.jsz.peini.model.eventbus.FilterReturnBean;
import com.jsz.peini.model.filter.FilterRecycleviewBean;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.presenter.setting.SettingService;
import com.jsz.peini.ui.view.Popwindou;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.LoginDialogUtils;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.utils.UiUtils;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FilterActivity extends BaseActivity {

    @InjectView(R.id.filter_sort_distance)
    RadioButton mFilterSortDistance;
    @InjectView(R.id.filter_sort_time)
    RadioButton mFilterSortTime;
    @InjectView(R.id.filter_sort)
    RadioGroup mFilterSort;
    @InjectView(R.id.task_sex_no)
    RadioButton mTaskSexNo;
    @InjectView(R.id.task_sex_man)
    RadioButton mTaskSexMan;
    @InjectView(R.id.task_sex_woman)
    RadioButton mTaskSexWoman;
    @InjectView(R.id.task_sex)
    RadioGroup mTaskSex;
    @InjectView(R.id.filter_age)
    TextView mFilterAge;
    @InjectView(R.id.filter_age_ll)
    LinearLayout mFilterAgeLl;
    @InjectView(R.id.filter_height)
    TextView mFilterHeight;
    @InjectView(R.id.filter_height_ll)
    LinearLayout mFilterHeightLl;
    @InjectView(R.id.filter_address)
    TextView mFilterAddress;
    @InjectView(R.id.filter_address_ll)
    LinearLayout mFilterAddressLl;
    @InjectView(R.id.filter_ideoauthentication)
    CheckBox mFilterIdeoauthentication;
    @InjectView(R.id.filter_identityauthentication)
    CheckBox mFilterIdentityauthentication;
    @InjectView(R.id.activity_filter)
    LinearLayout mActivityFilter;
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.right_button)
    TextView mRightButton;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.filter_recycleview)
    RecyclerView mFilterRecycleview;
    private TagAdapter<String> mAdapter;//标签的适配器
    private String[] mStr;
    ArrayList<String> mList;

    /**
     * sortsort	Int		排序方式
     * （1时间（默认1）2距离）
     * otherSex	Int		性别（不限不传1男2女）
     * otherLowAge	Int		年龄最低限（没有不传）
     * otherHignAge	Int		年龄最高限（没有不传）
     * otherLowheight	Int		身高最低限（没有不传）
     * otherHignheight	Int		身高最高限（没有不传）
     * isVideo	Int		是否视频验证（1或不传）
     * isIdcard	Int		是否身份认证（1或不传）
     * sellerType	Int		商家类别（全部为不传）
     */
    String sort = "1";
    String otherSex = "";
    String otherLowAge = "";
    String otherHignAge = "";
    String otherLowheight = "";
    String otherHignheight = "";
    String isVideo = "";
    String isIdcard = "";
    String sellerType = "";
    public CrtyBean mCrtyBean;
    public FilterActivity mActivity;
    public int mMinimumAge;
    public int mMaximumAge;
    public int mPosition = 0;
    public int mPosition1 = 0;

    @Override
    public int initLayoutId() {
        return R.layout.activity_filter;
    }

    @Override
    public void initView() {
        mActivity = this;
        mTitle.setText("筛选条件");
        mRightButton.setText("确定");
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initIalise();
    }

    @Override
    public void initData() {
        initNetWork();
    }

    private void initNetWork() {
        initCity();
    }

    /**
     * 访问城市
     */
    private void initCity() {
        RetrofitUtil.createService(SettingService.class)
                .getCityList(mUserToken)
                .enqueue(new Callback<CrtyBean>() {
                    @Override
                    public void onResponse(Call<CrtyBean> call, Response<CrtyBean> response) {
                        if (response.isSuccessful()) {
                            CrtyBean body = response.body();
                            if (body.getResultCode() == 1) {
                                LogUtil.d("地址列表请求成功" + response.body().toString());
                                mCrtyBean = response.body();
                            } else if (body.getResultCode() == 9) {
                                LoginDialogUtils.isNewLogin(mActivity);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CrtyBean> call, Throwable t) {
                        LogUtil.d("地址列表请求失败" + t.getMessage());
                    }
                });
    }

    private void initIalise() {
        mStr = new String[5];
        mFilterSort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.filter_sort_distance:
                        LogUtil.i("筛选的距离", "这个是距离");
                        sort = "1";
                        break;
                    case R.id.filter_sort_time:
                        LogUtil.i("筛选的时间", "这个是时间");
                        sort = "1";
                        break;
                }
            }
        });
        mTaskSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.task_sex_no:
                        LogUtil.i("筛选的不限", "这个是不限");
                        otherSex = "";
                        break;
                    case R.id.task_sex_man:
                        LogUtil.i("筛选的性别", "这个是男");
                        otherSex = "1";
                        break;
                    case R.id.task_sex_woman:
                        LogUtil.i("筛选的性别", "这个是女");
                        otherSex = "2";
                        break;
                }
            }
        });
        mFilterIdeoauthentication.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                LogUtil.i("这个是视频认证", "选择了还是没有" + b);
                if (b) {
                    isVideo = "1";
                } else {
                    isVideo = "";
                }
            }
        });
        mFilterIdentityauthentication.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                LogUtil.i("这个是身份认证认证", "选择了还是没有" + b);
                if (b) {
                    isIdcard = "1";
                } else {
                    isIdcard = "";
                }

            }
        });
        initLabel();
    }

    List<FilterRecycleviewBean> mBeanList;

    private void initLabel() {
        mBeanList = new ArrayList<>();
        final String[] mVals = new String[]{"全部", "吃饭", "唱歌", "电影", "运动", "休闲", "足疗", "旅行", "丽人", "其他"};
        for (int i = 0; i < mVals.length; i++) {
            mBeanList.add(new FilterRecycleviewBean(0, mVals[i]));
        }
        mFilterRecycleview.setLayoutManager(new GridLayoutManager(mActivity, 4));
        mFilterRecycleview.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ViewHolder(UiUtils.inflateRecyclerviewAdapter(mActivity, R.layout.tv, parent));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                holder.setIsRecyclable(false);
                final ViewHolder viewHolder = (ViewHolder) holder;
                final FilterRecycleviewBean filterRecycleviewBean = mBeanList.get(position);
                viewHolder.mFilter_name.setText(filterRecycleviewBean.getString());
                if (filterRecycleviewBean.getInt() == 0) {
                    viewHolder.mFilter_name.setChecked(false);
                } else if (filterRecycleviewBean.getInt() == 1) {
                    viewHolder.mFilter_name.setChecked(true);
                }
                viewHolder.mFilter_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (position == 0) {
                            if (isChecked) {
                                mBeanList.set(position, new FilterRecycleviewBean(1, filterRecycleviewBean.getString()));
                            } else {
                                mBeanList.set(position, new FilterRecycleviewBean(0, filterRecycleviewBean.getString()));
                            }
                        } else {
                            mBeanList.set(0, new FilterRecycleviewBean(0, mBeanList.get(0).getString()));
                            if (isChecked) {
                                mBeanList.set(position, new FilterRecycleviewBean(1, filterRecycleviewBean.getString()));
                            } else {
                                mBeanList.set(position, new FilterRecycleviewBean(0, filterRecycleviewBean.getString()));
                            }
                        }
                        if (mBeanList.get(0).getInt() == 1) {
                            for (int i = 1; i < mBeanList.size(); i++) {
                                mBeanList.set(i, new FilterRecycleviewBean(0, mBeanList.get(i).getString()));
                            }

                        } else {
                            if (isChecked) {
                                mBeanList.set(position, new FilterRecycleviewBean(1, filterRecycleviewBean.getString()));
                            } else {
                                mBeanList.set(position, new FilterRecycleviewBean(0, filterRecycleviewBean.getString()));
                            }
                        }

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                notifyDataSetChanged();
                            }
                        }, 100);
                    }
                });

            }

            @Override
            public int getItemCount() {
                return mBeanList.size();
            }

            class ViewHolder extends RecyclerView.ViewHolder {

                private CheckBox mFilter_name;

                public ViewHolder(View view) {
                    super(view);
                    mFilter_name = (CheckBox) view.findViewById(R.id.filter_name);
                }
            }
        });
    }

    /**
     * 年龄选择
     */
    private void PopupWindowAge(List<String> mainAge, final TextView filterAge, final int i) {
        final Popwindou popwindou = new Popwindou(mActivity, UiUtils.inflate(mActivity, R.layout.activity_filter));
        View view = UiUtils.inflate(mActivity, R.layout.pop_tow_selector);
        popwindou.init(view, Gravity.BOTTOM, true);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.holoBorderColor = getResources().getColor(R.color.backgroundf1f1f1);
        style.selectedTextSize = 20;
        style.textSize = 18;
        style.textAlpha = 0.5f;
        style.selectedTextZoom = 5;

        final WheelView mainWheelView = (WheelView) view.findViewById(R.id.main_wheelview);
        mainWheelView.setWheelAdapter(new ArrayWheelAdapter(this));
        mainWheelView.setSkin(WheelView.Skin.Holo);
        mainWheelView.setWheelSize(5);
        mainWheelView.setStyle(style);

        mainWheelView.setWheelData(mainAge);

        final WheelView subWheelView = (WheelView) view.findViewById(R.id.sub_wheelview);
        subWheelView.setWheelAdapter(new ArrayWheelAdapter(this));
        subWheelView.setSkin(WheelView.Skin.Holo);
        subWheelView.setWheelSize(5);
        subWheelView.setStyle(style);
        subWheelView.setWheelData(mainAge);
        mainWheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                switch (i) {
                    case 1:
                        mMinimumAge = position + 18;
                        break;
                    case 2:
                        mMinimumAge = position + 100;
                        break;
                }

                LogUtil.d("这个是前面选择的监听--->" + mMinimumAge);
            }
        });
        subWheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                switch (i) {
                    case 1:
                        mMaximumAge = position + 18;
                        break;
                    case 2:
                        mMaximumAge = position + 100;
                        break;
                }
                LogUtil.d("这个是后面区选择的监听--->" + mMaximumAge);
            }
        });
        /*  android:id="@+id/cancel_selector"
        <TextView
            android:id="@+id/ok_selector"*/
        view.findViewById(R.id.cancel_selector).setOnClickListener(new View.OnClickListener() {//确定
            @Override
            public void onClick(View view) {
                popwindou.dismiss();
            }
        });
        view.findViewById(R.id.ok_selector).setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View view) {
                switch (i) {
                    case 1:
                        if (mMaximumAge >= mMinimumAge) {
                            otherLowAge = mMinimumAge + "";
                            otherHignAge = mMaximumAge + "";
                            filterAge.setText(mMinimumAge + "岁 - " + mMaximumAge + "岁");
                            popwindou.dismiss();
                        } else {
                            ToastUtils.showToast(mActivity, "请选择正确的年龄阶段");
                        }
                        break;
                    case 2:
                        if (mMaximumAge >= mMinimumAge) {
                            otherLowheight = mMinimumAge + "";
                            otherHignheight = mMaximumAge + "";
                            filterAge.setText(mMinimumAge + "cm - " + mMaximumAge + "cm");
                            popwindou.dismiss();
                        } else {
                            ToastUtils.showToast(mActivity, "请选择正确的身高阶段");
                        }
                        break;
                }

            }
        });

    }

    /**
     * 按钮的点击事件
     */
    @OnClick({R.id.filter_age_ll, R.id.filter_height_ll, R.id.filter_address_ll, R.id.right_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.filter_age_ll:
                PopupWindowAge(createMainAge(), mFilterAge, 1);
                break;
            case R.id.filter_height_ll:
                PopupWindowAge(createMainHeight(), mFilterHeight, 2);
                break;
            case R.id.filter_address_ll: //选择城市列表
//                if (mCrtyBean != null) {
//                    if (mCrtyBean.getAreaCity().size() > 1) {
//                        popsselector(mCrtyBean);
//                    } else {
//                        ToastUtils.ToastAddress(mActivity);
//                    }
//                }
                ToastUtils.ToastAddress(mActivity);
                break;
            case R.id.right_button:
                FilterReturnBean returnBean = new FilterReturnBean(sort, otherSex, otherLowAge, otherHignAge, otherLowheight, otherHignheight, isVideo, isIdcard, sellerType, IpConfig.cityCode);
                EventBus.getDefault().post(returnBean);
                finish();
                break;
        }
    }

    private void popsselector(CrtyBean crtyBean) {
        Popwindou popwindou = new Popwindou(mActivity, UiUtils.inflate(mActivity, R.layout.activity_filter));
        View view = UiUtils.inflate(mActivity, R.layout.pop_tow_selector);
        popwindou.init(view, Gravity.BOTTOM, true);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.holoBorderColor = getResources().getColor(R.color.backgroundf1f1f1);
        style.selectedTextSize = 20;
        style.textSize = 18;
        style.textAlpha = 0.5f;
        style.selectedTextZoom = 5;

        WheelView mainWheelView = (WheelView) view.findViewById(R.id.main_wheelview);
        mainWheelView.setWheelAdapter(new ArrayWheelAdapter(this));
        mainWheelView.setSkin(WheelView.Skin.Holo);
        mainWheelView.setWheelSize(5);
        mainWheelView.setWheelData(createMainDatas());
        mainWheelView.setStyle(style);

        WheelView subWheelView = (WheelView) view.findViewById(R.id.sub_wheelview);
        subWheelView.setWheelAdapter(new ArrayWheelAdapter(this));
        subWheelView.setSkin(WheelView.Skin.Holo);
        subWheelView.setWheelSize(5);
        subWheelView.setWheelData(createSubDatas().get(createMainDatas().get(mainWheelView.getSelection())));
        subWheelView.setStyle(style);
        mainWheelView.join(subWheelView);
        mainWheelView.joinDatas(createSubDatas());

        mainWheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                LogUtil.d("这个是城市选择的监听" + position);
            }
        });
        subWheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                LogUtil.d("这个是市区选择的监听" + position);
            }
        });

    }

    List<String> ProvinceName; //城市

    List<String> cityName;//市区

    private List<String> createMainDatas() {
        ProvinceName = new ArrayList<>();
        List<CrtyBean.AreaCityBean> areaCity = mCrtyBean.getAreaCity();
        for (int i = 0; i < areaCity.size(); i++) {
            ProvinceName.add(areaCity.get(i).getProvinceName());
        }
        return ProvinceName;
    }

    private HashMap<String, List<String>> createSubDatas() {
        HashMap<String, List<String>> map = new HashMap<>();
        cityName = new ArrayList<>();
        List<CrtyBean.AreaCityBean> areaCity = mCrtyBean.getAreaCity();
        for (int i = 0; i < areaCity.size(); i++) {
            List<CrtyBean.AreaCityBean.ProvinceObjectBean> provinceObject = areaCity.get(i).getProvinceObject();
            for (int j = 0; j < provinceObject.size(); j++) {
                cityName.add(provinceObject.get(j).getCityName());
            }
        }
        for (int i = 0; i < ProvinceName.size(); i++) {
            map.put(ProvinceName.get(i), cityName);
        }
        return map;
    }

    private List<String> createMainAge() {
        ProvinceName = new ArrayList<>();
        for (int i = 18; i < 100; i++) {
            ProvinceName.add(i + " 岁");
        }
        return ProvinceName;
    }


    private List<String> createMainHeight() {
        ProvinceName = new ArrayList<>();
        for (int i = 100; i < 300; i++) {
            ProvinceName.add(i + " cm");
        }
        return ProvinceName;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}

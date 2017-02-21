package com.jsz.peini.ui.activity.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.CrtyBean;
import com.jsz.peini.model.eventbus.SelectorSellerBean;
import com.jsz.peini.model.setting.SuccessfulBean;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.presenter.setting.SettingService;
import com.jsz.peini.presenter.task.TaskService;
import com.jsz.peini.ui.view.Popwindou;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.LoginDialogUtils;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.utils.UiUtils;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by th on 2016/12/7.
 */
public class TaskActivity extends BaseActivity {
    @InjectView(R.id.task_sex_no)
    RadioButton taskSexNo;
    @InjectView(R.id.task_sex_man)
    RadioButton taskSexMan;
    @InjectView(R.id.task_sex_woman)
    RadioButton taskSexWoman;
    @InjectView(R.id.task_sex)
    RadioGroup taskSex;
    @InjectView(R.id.task_pay_mi)
    RadioButton taskPayMi;
    @InjectView(R.id.task_pay_you)
    RadioButton taskPayYou;
    @InjectView(R.id.task_pay_aa)
    RadioButton taskPayAa;
    @InjectView(R.id.task_pay)
    RadioGroup taskPay;
    @InjectView(R.id.task_goout_mi)
    RadioButton taskGooutMi;
    @InjectView(R.id.task_goout_you)
    RadioButton taskGooutYou;
    @InjectView(R.id.task_goout_like)
    RadioButton taskGooutLike;
    @InjectView(R.id.task_goout)
    RadioGroup taskGoout;
    @InjectView(R.id.task_describe)
    EditText task_describe; //输入的文本
    @InjectView(R.id.task_change)
    TextView task_change; //文本的监听
    @InjectView(R.id.task_address)
    TextView taskAddress;
    @InjectView(R.id.task_regist_address)
    LinearLayout taskRegistAddress;
    @InjectView(R.id.task_time)
    TextView taskTime;
    @InjectView(R.id.task_regist_time)
    LinearLayout taskRegistTime;
    @InjectView(R.id.task_age)
    TextView taskAge;
    @InjectView(R.id.task_regist_age)
    LinearLayout taskRegistAge;
    /**
     * 约会地址的类型
     */
    @InjectView(R.id.task_type)
    TextView mTaskType;
    /**
     * 约会的具体位置
     */
    @InjectView(R.id.task_location)
    TextView mTaskLocation;
    /**
     * 约会的点击事件
     */
    @InjectView(R.id.task_rendezvous)
    LinearLayout mTaskRendezvous;
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.right_button)
    TextView mRightButton;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    private List<CrtyBean.AreaCityBean> setiingCity;
    public TaskActivity mActivity;
    /**
     * 约会信息
     */
    public String mTaskDesc;//约会的信息
    public String mOtherSex;//性别
    public String mOtherGo;
    public String mOtherBuy;
    public WheelView mWheelViewThree, mWheelViewOne, mWheelViewTow;
    public ArrayList<String> mArrayList;

    @Override
    public int initLayoutId() {
        return R.layout.activity_task;
    }

    @Override
    public void initView() {
        mActivity = TaskActivity.this;//初始化上下文
        EventBus.getDefault().register(mActivity);
        mTitle.setText("任务发布");
        mRightButton.setText("发布");
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        initEditText();
        SelectButton();
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
                            ToastUtils.showToast(mActivity, body.getResultDesc());
                            if (body.getResultCode() == 1) {
                                setiingCity = body.getAreaCity();
                            } else if (body.getResultCode() == 9) {
                                LoginDialogUtils.isNewLogin(mActivity);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CrtyBean> call, Throwable t) {
                        ToastUtils.showToast(mActivity, t.getMessage());
                    }
                });
    }

    /**
     * 输入文本监听框
     */
    private void initEditText() {
        task_describe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                task_change.setText(charSequence.length() + "/200");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mTaskDesc = task_describe.getText().toString().trim();
                LogUtil.i("这个是输入的信息", mTaskDesc);
            }
        });
    }

    /**
     * 选择框
     */
    private void SelectButton() {
        taskSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.task_sex_no:
                        LogUtil.i("任务自由行选择", "不限");
                        break;
                    case R.id.task_sex_man:
                        mOtherSex = "1";
                        LogUtil.i("任务自由行选择", "男");
                        break;
                    case R.id.task_sex_woman:
                        mOtherSex = "2";
                        LogUtil.i("任务自由行选择", "女");
                        break;
                }
            }
        });
        taskGoout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.task_goout_like:
                        mOtherGo = "3";
                        LogUtil.i("任务自由行选择", "自由行");
                        break;
                    case R.id.task_goout_mi:
                        mOtherGo = "1";
                        LogUtil.i("任务自由行选择", "我接她");
                        break;
                    case R.id.task_goout_you:
                        mOtherGo = "2";
                        LogUtil.i("任务自由行选择", "你接我");
                        break;
                }
            }
        });
        taskPay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.task_pay_aa:
                        mOtherBuy = "3";
                        LogUtil.i("任务买单选择", "AA制");
                        break;
                    case R.id.task_pay_you:
                        mOtherBuy = "2";
                        LogUtil.i("任务买单选择", "你买单");
                        break;
                    case R.id.task_pay_mi:
                        mOtherBuy = "1";
                        LogUtil.i("任务买单选择", "我买单");
                        break;
                }
            }
        });
    }

    private void initNetWork() {
        RetrofitUtil.createService(TaskService.class).setTaskInfo(
                "" + mUserToken,
                "" + IpConfig.cityCode,
                "" + IpConfig.cityCode,
                "" + 2,
                "" + "2017-02-21",
                "" + mTaskDesc,
                "" + mOtherSex,
                "" + 18,
                "" + 100,
                "" + mOtherBuy,
                "" + mOtherGo,
                "" + mXpoint,
                "" + mYpoint)
                .enqueue(new Callback<SuccessfulBean>() {
                    @Override
                    public void onResponse(Call<SuccessfulBean> call, Response<SuccessfulBean> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getResultCode() == 1) {
                                ToastUtils.showToast(mActivity, "任务发布成功");
                                finish();
                            } else if (response.body().getResultCode() == 9) {
                                LoginDialogUtils.isNewLogin(mActivity);
                                LogUtil.d("发布任务失败" + response.body().toString());
                            } else {
                                LogUtil.d("发布任务失败" + response.body().toString());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessfulBean> call, Throwable t) {
                        LogUtil.d("发布任务失败" + t.getMessage());
                    }
                });
    }

    @OnClick({R.id.right_button, R.id.task_regist_address, R.id.task_regist_time, R.id.task_regist_age, R.id.task_rendezvous})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_button://发布按钮
                initNetWork();
                break;
            case R.id.task_regist_address:
                PopupWindowAddress();
                break;
            case R.id.task_regist_time:
                PoWindowTime(taskTime);
                break;
            case R.id.task_regist_age:
                PopupWindowAge();
                break;
            /*约会商家选择的点击事件的点击事件*/
            case R.id.task_rendezvous:
                startActivity(new Intent(mActivity, SelectSellerActivity.class));
                break;
        }
    }

    /**
     * 年龄选择
     */
    private void PopupWindowAge() {
    }

    /**
     * 时间选择
     *
     * @param taskTime
     */
    private void PoWindowTime(TextView taskTime) {
        ToastUtils.showToast(mActivity, "时间选择三级联动");
        Popwindou pop = new Popwindou(mActivity, UiUtils.inflate(mActivity, R.layout.activity_task));
        View view = UiUtils.inflate(mActivity, R.layout.pop_three_selector);
        pop.init(view, Gravity.BOTTOM, true);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.holoBorderColor = getResources().getColor(R.color.backgroundf1f1f1);
        style.selectedTextSize = 20;
        style.textSize = 18;
        style.textAlpha = 0.5f;
        style.selectedTextZoom = 5;

        mWheelViewOne = (WheelView) view.findViewById(R.id.one_wheelview);
        mWheelViewOne.setWheelAdapter(new ArrayWheelAdapter(this));
        mWheelViewOne.setSkin(WheelView.Skin.Holo);
        mWheelViewOne.setWheelSize(5);
        mWheelViewOne.setWheelData(OneData());
        mWheelViewOne.setStyle(style);

        mWheelViewTow = (WheelView) view.findViewById(R.id.tow_wheelview);
        mWheelViewTow.setWheelAdapter(new ArrayWheelAdapter(this));
        mWheelViewTow.setSkin(WheelView.Skin.Holo);
        mWheelViewTow.setWheelSize(5);
        mWheelViewTow.setWheelData(TowData());
        mWheelViewTow.setStyle(style);

        mWheelViewThree = (WheelView) view.findViewById(R.id.three_wheelview);
        mWheelViewThree.setWheelAdapter(new ArrayWheelAdapter(this));
        mWheelViewThree.setSkin(WheelView.Skin.Holo);
        mWheelViewThree.setWheelSize(5);
        mWheelViewThree.setWheelData(ThreeData());
        mWheelViewThree.setStyle(style);
    }

    SimpleDateFormat format = new SimpleDateFormat("MM-dd");
    Calendar c = Calendar.getInstance();
    SimpleDateFormat mHH = new SimpleDateFormat("HH");
    SimpleDateFormat mMm = new SimpleDateFormat("mm");

    private List OneData() {
        mArrayList = new ArrayList<>();
        String str = format.format(new Date());
        mArrayList.add("今天" + str);
        c.add(Calendar.DAY_OF_MONTH, 1);
        String format = this.format.format(c.getTime());
        System.out.println("增加一天后日期 ：  " + format);
        mArrayList.add("明天" + format);
        c.add(Calendar.DAY_OF_MONTH, 1);
        String format1 = this.format.format(c.getTime());
        System.out.println("增加两天后日期 ：  " + format1);
        mArrayList.add("后天" + format1);
        return mArrayList;
    }

    private List TowData() {
        mArrayList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            mArrayList.add(i + "点");
        }
        return mArrayList;
    }

    private List ThreeData() {
        mArrayList = new ArrayList<>();
        for (int i = 0; i < 60; i += 10) {
            mArrayList.add(i + "分");
        }
        return mArrayList;
    }

    /**
     * 地址选择
     */
    private void PopupWindowAddress() {
        ToastUtils.ToastAddress(mActivity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(mActivity);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SelectorSellerBean event) {
        if (event == null) {
            return;
        }
        String tyge = event.getTyge();
        String name = event.getName();
        mTaskType.setText(tyge);
        mTaskLocation.setText(name);
        LogUtil.d("界面穿透式回调" + tyge + "            " + name);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}

package com.jsz.peini.ui.activity.task;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.setting.SuccessfulBean;
import com.jsz.peini.model.tabulation.TabulationMessageBean;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.presenter.task.TaskService;
import com.jsz.peini.ui.activity.pay.PaythebillActivity;
import com.jsz.peini.ui.activity.report.ReportActivity;
import com.jsz.peini.ui.adapter.task.TaskAdapter;
import com.jsz.peini.ui.view.Popwindou;
import com.jsz.peini.utils.Bitmap.GlideImgManager;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.LoginDialogUtils;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.utils.UiUtils;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TaskDtailsActivity extends BaseActivity {
    @InjectView(R.id.taskdtails_toolbar)
    Toolbar taskdtails_toolbar;
    @InjectView(R.id.task_vp)
    RollPagerView task_vp;
    @InjectView(R.id.task_more)
    ImageView task_more;
    @InjectView(R.id.seller_success_pingjia)
    Button seller_success_pingjia;
    @InjectView(R.id.filter_flowlayout)
    TagFlowLayout filter_flowlayout;
    /**
     * 需要填充的
     */
    @InjectView(R.id.name)
    TextView name; //名字
    @InjectView(R.id.task_distance)
    TextView taskDistance; //距离
    @InjectView(R.id.task_sex)
    ImageView taskSex;
    @InjectView(R.id.task_age)
    TextView taskAge;
    @InjectView(R.id.task_reputation)
    TextView taskReputation;
    @InjectView(R.id.task_labelsname)
    TextView taskLabelsname;
    @InjectView(R.id.task_map)
    TextView taskMap;
    @InjectView(R.id.task_time)
    TextView taskTime;
    @InjectView(R.id.task_laijiwwo)
    TextView taskLaijiwwo;
    @InjectView(R.id.task_text)
    TextView taskText;
    @InjectView(R.id.task_mejoin)
    Button mTaskMejoin;
    @InjectView(R.id.task_youjoin)
    Button taskYoujoin;
    @InjectView(R.id.task_telephone)
    Button taskTelephone;
    @InjectView(R.id.task_linear1)
    LinearLayout taskLinear1;
    @InjectView(R.id.task_linear2)
    LinearLayout taskLinear2;
    @InjectView(R.id.task_isshow)
    FrameLayout taskIsshow;
    @InjectView(R.id.task_pay)
    TextView taskPay;
    @InjectView(R.id.task_icen)
    CircleImageView taskIcen;
    @InjectView(R.id.task_linear3)
    LinearLayout mTaskLinear3;
    private TaskAdapter taskadapter;
    private Retrofit retrofit;
    private TagAdapter<String> mAdapter;
    private TaskService mTaskService;
    public Intent mIntent;
    public String mId;
    /*this*/
    private TaskDtailsActivity mActvivty;
    public List<TabulationMessageBean.TaskInfoListBean> mTaskInfoList;
    public int beanId;
    public int mSellerInfoId;

    @Override
    public int initLayoutId() {
        return R.layout.activity_taskdtails;
    }

    @Override
    public void initView() {
        mActvivty = this;
        mIntent = getIntent();
        mId = mIntent.getStringExtra("id");
        taskIsshow.setVisibility(View.GONE);
        taskdtails_toolbar.setTitle("");
        setSupportActionBar(taskdtails_toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        taskdtails_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //初始化底部数据
        isBottomColumn(0);
    }

    @Override
    public void initData() {
        initNetWork();

    }

    private void initNetWork() {
        LogUtil.d("这个是访问详情的接口" + mId + "--" + mUserToken + "--" + mXpoint + "--" + mYpoint);
        RetrofitUtil.createService(TaskService.class)
                .getTaskInfo(mId, mUserToken, mXpoint, mYpoint)
                .enqueue(new Callback<TabulationMessageBean>() {
                    @Override
                    public void onResponse(Call<TabulationMessageBean> call, final Response<TabulationMessageBean> response) {
                        if (response.isSuccessful()) {
                            int resultCode = response.body().getResultCode();
                            if (resultCode == 1) {
                                mTaskInfoList = response.body().getTaskInfoList();
                                mActvivty.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        initShowVp(mTaskInfoList);
                                        initLabel(mTaskInfoList);
                                        initDataView(mTaskInfoList);
                                        LogUtil.d("任务的详细信息界面成功" + response.body().toString());
                                    }
                                });
                            } else if (resultCode == 9) {
                                LoginDialogUtils.isNewLogin(mActvivty);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<TabulationMessageBean> call, Throwable t) {
                        LogUtil.d("任务的详细信息界面失败" + t.getMessage());
                    }
                });
    }

    /**
     * 轮播图
     */
    private void initShowVp(List<TabulationMessageBean.TaskInfoListBean> taskInfoList) {
        //设置播放时间间隔
        task_vp.setPlayDelay(3000);
        //设置透明度
        task_vp.setAnimationDurtion(500);
        taskadapter = new TaskAdapter();
        task_vp.setAdapter(taskadapter);
        task_vp.setHintView(new ColorPointHintView(this, Color.YELLOW, Color.WHITE));
    }

    /**
     * 文本填充
     */
    private void initLabel(List<TabulationMessageBean.TaskInfoListBean> taskInfoList) {
        final String[] mVals = new String[]{"为了真爱", "爱谁谁", "来接我"};
        final LayoutInflater mInflater = LayoutInflater.from(this);
        filter_flowlayout = (TagFlowLayout) findViewById(R.id.filter_flowlayout);
        filter_flowlayout.setAdapter(mAdapter = new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv2,
                        filter_flowlayout, false);
                tv.setText(s);
                return tv;
            }
        });
    }

    /**
     * 填充布局信息
     *
     * @param taskInfoList
     */
    private void initDataView(List<TabulationMessageBean.TaskInfoListBean> taskInfoList) {
        taskIsshow.setVisibility(View.GONE);
        boolean empty = taskInfoList.isEmpty();
        LogUtil.i("这个是判断集合有没有元素的", "又或者么有" + empty);
        TabulationMessageBean.TaskInfoListBean bean = taskInfoList.get(0);
        beanId = bean.getId();
        mSellerInfoId = bean.getSellerInfoId();
        GlideImgManager.loadImage(mActvivty, IpConfig.HttpPic + mImageHead, taskIcen);
        name.setText(bean.getNickName()); //名字
        int sex = bean.getSex(); //男女
        if (sex == 1) {
            taskSex.setBackgroundResource(R.mipmap.nan);
        } else if (sex == 2) {
            taskSex.setBackgroundResource(R.mipmap.nv);
        } else {
            taskSex.setVisibility(View.GONE);
        }
        int distance = bean.getDistance(); //距离
        if (!TextUtils.isEmpty(distance + "")) {
            taskDistance.setText(distance / 1000 + " km");
        } else {
            taskDistance.setVisibility(View.GONE);
        }
        int reputation = bean.getReputation();//信誉
        if (!TextUtils.isEmpty(reputation + "")) {
            taskReputation.setText(reputation + "");
        } else {
            taskReputation.setVisibility(View.GONE);
        }
        taskLabelsname.setText(bean.getSellerSmallName() + ""); //美食
        taskMap.setText(bean.getSellerInfoName() + "");//地点
        taskTime.setText(bean.getTaskAppointedTime() + "");//时间
        String MinAge = bean.getOtherLowAge() + ""; //希望
        String MxnAge = bean.getOtherHignAge() + "岁";
        String Sex = null;
        String Hope = null;
        if (bean.getOtherSex() == 1) {
            Sex = "帅哥";
        } else if (bean.getOtherSex() == 2) {
            Sex = "妹子";
        }
        String TaskDesc = bean.getTaskDesc() + "";
        taskText.setText(TaskDesc);
        /**判断最底层的买单界面布局*/
        //买单（1我买单2他买单3AA制）
        int otherBuy = bean.getOtherBuy();
        //任务状态（全部为空1发布中2进行中3待评价4已完成5已关闭）
        final int taskStatus = bean.getTaskStatus();
        //1我发布的 2ta发
        int publishType = bean.getPublishType();
        //
        int choice = 0;
        switch (publishType) {
            case 0://Ta发布的
                switch (otherBuy) {
                    case 1:
                        taskPay.setText("Ta来买单");
                        choice = 2;
                        break;
                    case 2:
                        taskPay.setText("我来买单");
                        choice = 1;
                        break;
                    case 3:
                        taskPay.setText("AA制");
                        choice = 1;
                        break;
                    default:
                        taskPay.setVisibility(View.GONE);
                        break;
                }
                if (bean.getOtherGo() == 1) {
                    Hope = "Ta接我";
                } else if (bean.getOtherGo() == 2) {
                    Hope = "我接Ta";
                }
                break;
            case 1://我发布的
                switch (otherBuy) {
                    case 1:
                        taskPay.setText("我来买单");
                        choice = 1;
                        break;
                    case 2:
                        taskPay.setText("Ta来买单");
                        choice = 2;
                        break;
                    case 3:
                        taskPay.setText("AA制");
                        choice = 1;
                        break;
                    default:
                        taskPay.setVisibility(View.GONE);
                        break;
                }
                if (bean.getOtherGo() == 1) {
                    Hope = "我接Ta";
                } else if (bean.getOtherGo() == 2) {
                    Hope = "Ta接我";
                }
                break;
            default:
                break;
        }
        taskLaijiwwo.setText("" + MinAge + "-" + MxnAge + "  " + Sex + "  " + Hope);
        switch (taskStatus) {
            case 1://发布中
                isBottomColumn(0);
                break;
            case 2://进行中
                isBottomColumn(choice);
                break;
            case 3://待评价
                break;
            case 4://已完成
                isBottomColumn(3);
                break;
            case 5://已取消
                isBottomColumn(5);
                break;
        }

    }

    /**
     * 点击事件
     */
    @OnClick({R.id.task_more, R.id.seller_success_pingjia, R.id.task_mejoin, R.id.task_mi_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.task_more:
                showPopwindows();
                break;
            case R.id.seller_success_pingjia:
                mIntent = new Intent(this, SellerSuccessPingjia.class);
                startActivity(mIntent);
                break;
            case R.id.task_mejoin:
                if (mTaskInfoList != null) {
                    isJoin();
                }
                break;
            case R.id.task_mi_pay:
                mIntent = new Intent(this, PaythebillActivity.class);
                mIntent.putExtra("taskid", beanId + "");
                mIntent.putExtra("sellerInfoId", mSellerInfoId + "");
                startActivity(mIntent);
                break;
        }
    }

    /**
     * 这个是我要参加的接口
     */
    private void isJoin() {
        RetrofitUtil.createService(TaskService.class)
                .updateTaskInfo(mUserToken, mTaskInfoList.get(0).getId() + "", "1")
                .enqueue(new Callback<SuccessfulBean>() {
                    @Override
                    public void onResponse(Call<SuccessfulBean> call, Response<SuccessfulBean> response) {
                        if (response.isSuccessful()) {
                            SuccessfulBean body = response.body();
                            int resultCode = body.getResultCode();
                            ToastUtils.showToast(mActvivty, body.getResultDesc());
                            if (resultCode == 1) {
                                isBottomColumn(1);
                            } else if (resultCode == 9) {
                                LoginDialogUtils.isNewLogin(mActvivty);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessfulBean> call, Throwable t) {
                        ToastUtils.showToast(mActvivty, t.getMessage());
                    }
                });
    }

    /**
     * 这个是任务详情底部显示的布局的调节器
     */
    public void isBottomColumn(int choice) {
        switch (choice) {
            case 0: //我要参加
                mTaskMejoin.setVisibility(View.VISIBLE);
                taskLinear1.setVisibility(View.GONE);
                taskLinear2.setVisibility(View.GONE);
                mTaskLinear3.setVisibility(View.GONE);
                break;
            case 2://对方买单
                mTaskMejoin.setVisibility(View.GONE);
                taskLinear1.setVisibility(View.VISIBLE);
                taskLinear2.setVisibility(View.GONE);
                mTaskLinear3.setVisibility(View.GONE);
                break;
            case 3://再来一单
                mTaskMejoin.setVisibility(View.GONE);
                taskLinear1.setVisibility(View.GONE);
                taskLinear2.setVisibility(View.VISIBLE);
                mTaskLinear3.setVisibility(View.GONE);
                break;
            case 1://我来买单
                mTaskMejoin.setVisibility(View.GONE);
                taskLinear1.setVisibility(View.GONE);
                taskLinear2.setVisibility(View.GONE);
                mTaskLinear3.setVisibility(View.VISIBLE);
                break;
            case 5://我来买单
                mTaskMejoin.setVisibility(View.GONE);
                taskLinear1.setVisibility(View.GONE);
                taskLinear2.setVisibility(View.GONE);
                mTaskLinear3.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 显示投诉框
     */
    private void showPopwindows() {
        final Popwindou pop = new Popwindou(this, UiUtils.inflate(mActvivty, R.layout.activity_taskdtails));
        View view = UiUtils.inflate(mActvivty, R.layout.popwindows_more);
        pop.init(view, Gravity.BOTTOM, true);
        view.findViewById(R.id.more_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.dismiss();
            }
        });
        view.findViewById(R.id.more_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.dismiss();
                mIntent = new Intent(mActvivty, ReportActivity.class);
                mIntent.putExtra("type", "4");
                startActivity(mIntent);
            }
        });
        view.findViewById(R.id.more_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent = new Intent(mActvivty, TaskCancelActivity.class);
                mIntent.putExtra("id", mId);
                startActivity(mIntent);
                pop.dismiss();
            }
        });
        view.findViewById(R.id.more_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.dismiss();
            }
        });
    }
}

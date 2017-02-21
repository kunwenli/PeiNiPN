package com.jsz.peini.ui.activity.square;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.listener.RetrofitCallback;
import com.jsz.peini.model.square.MiSignBean;
import com.jsz.peini.model.square.MiSignDataBean;
import com.jsz.peini.presenter.square.SquareService;
import com.jsz.peini.ui.adapter.square.getDayNumAdapter;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.LoginDialogUtils;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by th on 2017/2/9.
 */
public class MiSignActivity extends BaseActivity {
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.right_button)
    TextView mRightButton;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.calendar)
    RecyclerView mCalendar;
    @InjectView(R.id.time)
    TextView mTime;
    String[] mStrings = new String[]{"日", "一", "二", "三", "四", "五", "六"};
    Time t = new Time();
    //未签到
    @InjectView(R.id.misign)
    TextView mMisign;
    //积分
    @InjectView(R.id.score)
    TextView mScore;
    //已签到文本连续签到
    @InjectView(R.id.continuitySign_text)
    TextView mContinuitySignText;
    //已签到
    @InjectView(R.id.continuitySign)
    LinearLayout mContinuitySign;
    private int year;
    private int month;
    private int day;
    private int week;
    private List<String> list = new ArrayList<String>();
    private ArrayList<HashMap<String, Object>> sinalist, alisttmp;

    private int dayMaxNum;
    private MiSignActivity mActivity;
    private int mMonthDate;
    private int mMonthWeeks;
    private getDayNumAdapter mDayNumAdapter;
    private MiSignDataBean mBody;
    private List<String> mSignList;

    @Override
    public int initLayoutId() {
        return R.layout.activity_misign;
    }

    @Override
    public void initView() {
        mActivity = this;
        mTitle.setText("本月签到");
        //取本地时间（时间应该从服务器获取）
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        t.setToNow();
        year = t.year;
        month = t.month + 1;
        day = t.monthDay;
        week = t.weekDay;
        final String date = year + "-" + month + "-" + day;
        mTime.setText(month + "月" + day + "日");
        LogUtil.d(year + "-" + month + "-" + day + "-" + week);

        Date now = new Date();
        SimpleDateFormat myMonth = new SimpleDateFormat("MM");
        SimpleDateFormat myYear = new SimpleDateFormat("yyyy");
        int month = Integer.parseInt(myMonth.format(now));
        int year = Integer.parseInt(myYear.format(now));
        mMonthDate = monthDate(year, month);
        mMonthWeeks = monthWeeks();
        System.out.println("本月有多少天" + mMonthDate);//本月有多少天
        System.out.println("本月第一天是星期几" + mMonthWeeks);//本月第一天是星期几
    }

    @Override
    public void initData() {
        list.clear();
        for (int i = 0; i < mStrings.length; i++) {
            list.add(mStrings[i]);
        }
        //此处是用处：每个月1号如果不是周一的话，若其假设其为周三，就用2个元素占下集合中的前两位，让1号对应到相应周数。
        for (int i = 0; i < mMonthWeeks; i++) {
            list.add("");
        }
        for (int i = 1; i <= mMonthDate; i++) {
            list.add("0");
        }
        mCalendar.setLayoutManager(new GridLayoutManager(mActivity, 7));
        mDayNumAdapter = new getDayNumAdapter(mActivity, list, mStrings.length, mMonthWeeks, sinalist, day);
        mCalendar.setAdapter(mDayNumAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initNetWork();
    }

    private void initNetWork() {
        RetrofitUtil.createService(SquareService.class)
                .getUserSignInfo(mUserToken, year + "-" + month + "-" + "1", year + "-" + month + "-" + mMonthDate)
                .enqueue(new RetrofitCallback<MiSignDataBean>() {
                    @Override
                    public void onSuccess(Call<MiSignDataBean> call, Response<MiSignDataBean> response) {
                        if (response.isSuccessful()) {
                            mBody = response.body();
                            ToastUtils.showToast(mActivity, mBody.getResultDesc());
                            if (mBody.getResultCode() == 1) {

                                mSignList = mBody.getSignList();
                                list.clear();
                                for (int i = 0; i < mStrings.length; i++) {
                                    list.add(mStrings[i]);
                                }
                                //此处是用处：每个月1号如果不是周一的话，若其假设其为周三，就用2个元素占下集合中的前两位，让1号对应到相应周数。
                                for (int i = 0; i < mMonthWeeks; i++) {
                                    list.add("");
                                }
                                for (int i = 0; i < mSignList.size(); i++) {
                                    list.add(mSignList.get(i));
                                }
                                mDayNumAdapter.notifyDataSetChanged();
                                if (mBody != null) {
                                    initShowView();
                                }
                            } else if (mBody.getResultCode() == 9) {
                                LoginDialogUtils.isNewLogin(mActivity);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MiSignDataBean> call, Throwable t) {
                        ToastUtils.showToast(mActivity, t.getMessage());
                    }
                });
    }

    /*填充数据*/
    private void initShowView() {
        //是否显示签到
        String s = mSignList.get(day-1);
        if (s.equals("1")) {
            mMisign.setVisibility(View.GONE);
            mContinuitySign.setVisibility(View.VISIBLE);
        } else {
            mMisign.setVisibility(View.VISIBLE);
            mContinuitySign.setVisibility(View.GONE);
        }
        //积分
        String Score = mBody.getScore() + "积分";
        mScore.setText(Score);

        //连续签到
        mContinuitySignText.setText("连续"+mBody.getSignDays()+"天");
    }

    /**
     * 访问网络
     */
    private void initNetSignWork() {
        RetrofitUtil.createService(SquareService.class)
                .insertSignInfo(mUserToken, year + "-" + month + "-" + day)
                .enqueue(new RetrofitCallback<MiSignBean>() {
                    @Override
                    public void onSuccess(Call<MiSignBean> call, Response<MiSignBean> response) {
                        if (response.isSuccessful()) {
                            MiSignBean body = response.body();
                            ToastUtils.showToast(mActivity, body.getResultDesc());
                            if (body.getResultCode() == 1) {

                            } else if (body.getResultCode() == 9) {
                                LoginDialogUtils.isNewLogin(mActivity);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MiSignBean> call, Throwable t) {
                        ToastUtils.showToast(mActivity, t.getMessage());
                    }
                });
    }

    /*获取周几*/
    public int monthWeeks() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat format = new SimpleDateFormat("E");
        int week = 0;
        switch (format.format(calendar1.getTime())) {
            case "周一":
                week = 1;
                break;
            case "周二":
                week = 2;
                break;
            case "周三":
                week = 3;
                break;
            case "周四":
                week = 4;
                break;
            case "周五":
                week = 5;
                break;
            case "周六":
                week = 6;
                break;
            case "周日":
                week = 0;
                break;
        }
        return week;
    }

    /*获取月数的多少天*/
    public int monthDate(int year, int month) {
        int days = 0;
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return days;
    }

    @OnClick({R.id.misign})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.misign:
                initNetSignWork();
                initNetWork();
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}

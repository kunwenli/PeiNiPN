package com.jsz.peini.ui.adapter.task;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jsz.peini.PeiNiApp;
import com.jsz.peini.R;
import com.jsz.peini.model.tabulation.TaskList;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.UiUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by th on 2017/1/21.
 */
public class TaskTabulationAdapter extends PagerAdapter {

    private final Activity TabulationAdaptermActivity;
    private final List<TaskList.TaskAllListBean> taskAllList;
    @InjectView(R.id.seller_map)
    TextView sellerMap;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.task_distance)
    TextView taskDistance;
    @InjectView(R.id.tabuiation_credit)
    TextView tabuiationCredit;
    @InjectView(R.id.task_pay)
    TextView taskPay;
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
    @InjectView(R.id.headIV)
    ImageView angleImageView;//图片的
    @InjectView(R.id.task_sex)
    ImageView taskSex;
    @InjectView(R.id.task_age)
    TextView taskAge;
    @InjectView(R.id.filter_flowlayout)
    TagFlowLayout mTagFlowLayout;
    @InjectView(R.id.task_Line)
    LinearLayout mTaskLine;


    private com.jsz.peini.ui.adapter.tabulation.TabulationAdapter.OnItemClickListenter listenter;
    private TagAdapter<TaskList.TaskAllListBean.StringList> mAdapter;

    public void setOnItemClickListenter(com.jsz.peini.ui.adapter.tabulation.TabulationAdapter.OnItemClickListenter listenter) {
        this.listenter = listenter;
    }

    public TaskTabulationAdapter(Activity mActivity, List<TaskList.TaskAllListBean> taskAllList) {
        TabulationAdaptermActivity = mActivity;
        this.taskAllList = taskAllList;
    }

    @Override
    public int getCount() {
        if (taskAllList.size() != 0) {
            return taskAllList.size();
        }
        return 1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        final TaskList.TaskAllListBean taskAllListBean = taskAllList.get(position);
        View inflate = UiUtils.inflate(TabulationAdaptermActivity, R.layout.tabuiation_viewpager_message);
        ButterKnife.inject(this, inflate);
        Glide.with(PeiNiApp.context)
                .load("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg")
                .into(angleImageView);
        name.setText(taskAllListBean.getSellerInfoName()); //名字
        int sex = taskAllListBean.getOtherSex(); //男女
        if (sex == 1) {
            taskSex.setBackgroundResource(R.mipmap.nan);
        } else if (sex == 2) {
            taskSex.setBackgroundResource(R.mipmap.nv);
        } else {
            taskSex.setVisibility(View.GONE);
        }
        int distance = taskAllListBean.getDistanceNum(); //距离
        if (!TextUtils.isEmpty(distance + "")) {
            taskDistance.setText(distance / 1000 + " km");
        } else {
            taskDistance.setVisibility(View.GONE);
        }
        int reputation = taskAllListBean.getReputation();//信誉
        if (!TextUtils.isEmpty(reputation + "")) {
            taskReputation.setText(reputation + "");
        } else {
            taskReputation.setVisibility(View.GONE);
        }
        if (taskAllListBean.getOtherBuy() == 1) { //你买单
            taskPay.setText("我来买单");
        } else if (taskAllListBean.getOtherBuy() == 2) {
            taskPay.setText("Ta来买单");
        } else if (taskAllListBean.getOtherBuy() == 3) {
            taskPay.setText("AA制");
        } else {
            taskPay.setVisibility(View.GONE);
        }
        taskLabelsname.setText(taskAllListBean.getSellerSmallName() + ""); //美食
        taskMap.setText(taskAllListBean.getSellerInfoName() + "");//地点
        taskTime.setText(taskAllListBean.getTaskAppointedTime() + "");//时间
        String MinAge = taskAllListBean.getOtherLowAge() + ""; //希望
        String MxnAge = taskAllListBean.getOtherHignAge() + "岁";
        String Sex = null;
        String Hope = null;
        if (taskAllListBean.getOtherSex() == 1) {
            Sex = "妹子";
        } else if (taskAllListBean.getOtherSex() == 2) {
            Sex = "帅哥";
        }
        if (taskAllListBean.getOtherGo() == 1) {
            Hope = "我接Ta";
        } else if (taskAllListBean.getOtherGo() == 2) {
            Hope = "Ta接我";
        }
        taskLaijiwwo.setText("" + MinAge + "-" + MxnAge + "  " + Sex + "  " + Hope);
        List<TaskList.TaskAllListBean.StringList> userLabel = taskAllListBean.getUserLabel();
        LogUtil.i("这个是返回的item", "大小是" + userLabel.size());
        if (userLabel.size() > 0) {
            mTaskLine.setVisibility(View.VISIBLE);
        } else {
            mTaskLine.setVisibility(View.GONE);
        }
        final LayoutInflater mInflater = LayoutInflater.from(TabulationAdaptermActivity);
        mTagFlowLayout.setAdapter(mAdapter = new TagAdapter<TaskList.TaskAllListBean.StringList>(userLabel) {
            @Override
            public View getView(FlowLayout parent, int position, TaskList.TaskAllListBean.StringList userLabelBean) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv2,
                        mTagFlowLayout, false);
                tv.setText(userLabelBean.getLabelName());
                return tv;
            }
        });
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listenter != null) {
                    int id = taskAllListBean.getId();
                    if (!TextUtils.isEmpty(id + ""))
                        listenter.onItemClick(position, id);
                }
            }
        });
        container.addView(inflate);//添加进viewpager轮播容器
        return inflate;//返回当前图片
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public interface OnItemClickListenter {
        void onItemClick(int position, int id);
    }


}

package com.jsz.peini.ui.fragment.square;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.model.square.MyTaskAllBean;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.utils.Bitmap.GlideImgManager;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.jsz.peini.R.id.taskStatus;

/**
 * Created by th on 2017/1/24.
 */
public class MiTaskRecycleerViewAdapter extends RecyclerView.Adapter {

    private static final int RECYCLERVIEW_ITEM_1 = 0;
    private static final int RECYCLERVIEW_ITEM_2 = 1;
    public final Activity mActivity;
    public final List<MyTaskAllBean.TaskInfoByUserIdListBean> mList;
    public final int mHeader = 1;

    public MiTaskRecycleerViewAdapter(Activity activity, List<MyTaskAllBean.TaskInfoByUserIdListBean> list) {
        mActivity = activity;
        mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return RECYCLERVIEW_ITEM_1;
        } else {
            return RECYCLERVIEW_ITEM_2;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == RECYCLERVIEW_ITEM_1) {
            return new ViewHolderOne(LayoutInflater.from(mActivity).inflate(R.layout.item_1_mitask, parent, false));
        } else {
            return new ViewHolderTow(LayoutInflater.from(mActivity).inflate(R.layout.item_2_mitask, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderOne) {
            initOneView((ViewHolderOne) holder, position - mHeader);
        } else {
            initTowView((ViewHolderTow) holder, position - mHeader);
        }
    }

    private void initTowView(ViewHolderTow holder, final int position) {
        MyTaskAllBean.TaskInfoByUserIdListBean bean = mList.get(position);
        holder.mSellerInfoName.setText(bean.getSellerInfoName());
        holder.mTaskAppointedTime.setText(bean.getTaskAppointedTime());
        String age = bean.getAge() + "";
        holder.mAge.setText(age);
        holder.mIndustry.setText(bean.getIndustry());
        holder.mNickName.setText(bean.getNickName());
        int sex = bean.getSex();
        if (sex == 1) {
            holder.mSex.setImageResource(R.mipmap.nan);
        } else {
            holder.mSex.setImageResource(R.mipmap.nv);
        }
        //买单（1我买单2他买单3AA制）
        int otherBuy = bean.getOtherBuy();
        //任务状态（全部为空1发布中2进行中3待评价4已完成5已关闭）
        final int taskStatus = bean.getTaskStatus();
        //1我发布的 2ta发布的
        int publishType = bean.getPublishType();
        switch (otherBuy) {
            case 1:
                if (publishType == 1) {
                    holder.mOtherBuy.setText("我来买单");
                } else {
                    holder.mOtherBuy.setText("Ta来买单");
                }
                break;
            case 2:
                if (publishType == 1) {
                    holder.mOtherBuy.setText("Ta来买单");
                } else {
                    holder.mOtherBuy.setText("我来买单");
                }
                break;
            case 3:
                holder.mOtherBuy.setText("AA制");
                break;
        }
        switch (taskStatus) {
            case 1:
                holder.mTaskStatus.setText("发布中");
                holder.mOtherPhone.setVisibility(View.GONE);
                break;
            case 2:
                holder.mTaskStatus.setText("进行中");
                holder.mOtherPhone.setVisibility(View.VISIBLE);
                break;
            case 3:
                holder.mTaskStatus.setText("待评价");
                holder.mOtherPhone.setVisibility(View.GONE);
                holder.mOtherBuy.setText("去评价");
                break;
            case 4:
                holder.mTaskStatus.setText("已完成");
                holder.mOtherPhone.setVisibility(View.GONE);
                holder.mOtherBuy.setText("还去这家");
                break;
            case 5:
                holder.mTaskStatus.setText("已取消");
                holder.mOtherPhone.setVisibility(View.GONE);
                holder.mOtherBuy.setText("再来一单");
                break;
        }
        switch (publishType) {
            case 0:
                holder.mPublishType.setText("Ta发布的");
                break;
            case 1:
                holder.mPublishType.setText("我发布的");
                break;
        }
        String text = bean.getOtherLowAge() + "岁 - " + bean.getOtherHighAge() + "岁" + "  " + (bean.getOtherSex() == 1 ? "帅哥" : "美女") + "  谁接谁";
        GlideImgManager.loadImage(mActivity, IpConfig.HttpPic + bean.getImageHead(), holder.mImageHead);

        holder.mOtherLowAgeotherHighAgeotherSexotherGootherBuy.setText(text);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mListener.onLongClickItem(position);
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickItem(position);
            }
        });
        holder.mOtherPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onChatClickItem(position);
            }
        });
        holder.mOtherBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onTaskStatusClickItem(position, taskStatus);
            }
        });
    }

    private void initOneView(ViewHolderOne holder, final int position) {
        holder.mTaskScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onScreenClickItem();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size() + mHeader;
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        @InjectView(R.id.task_screen)
        TextView mTaskScreen;

        public ViewHolderOne(View inflate) {
            super(inflate);
            ButterKnife.inject(this, inflate);
        }
    }

    class ViewHolderTow extends RecyclerView.ViewHolder {
        @InjectView(R.id.imageHead)
        CircleImageView mImageHead;
        @InjectView(R.id.nickName)
        TextView mNickName;
        @InjectView(R.id.sex)
        ImageView mSex;
        @InjectView(R.id.age)
        TextView mAge;
        @InjectView(R.id.industry)
        TextView mIndustry;
        @InjectView(R.id.publishType)
        TextView mPublishType;
        @InjectView(R.id.sellerInfoName)
        TextView mSellerInfoName;
        @InjectView(R.id.taskAppointedTime)
        TextView mTaskAppointedTime;
        @InjectView(R.id.otherLowAgeotherHighAgeotherSexotherGootherBuy)
        TextView mOtherLowAgeotherHighAgeotherSexotherGootherBuy;
        @InjectView(taskStatus)
        TextView mTaskStatus;
        @InjectView(R.id.otherPhone)
        TextView mOtherPhone;
        @InjectView(R.id.otherBuy)
        TextView mOtherBuy;

        public ViewHolderTow(View inflate) {
            super(inflate);
            ButterKnife.inject(this, inflate);
        }
    }

    private OnLongClickListener mListener;

    public void setOnLongClickListener(OnLongClickListener listener) {
        mListener = listener;
    }

    public interface OnLongClickListener {
        /**
         * 长按
         */
        void onLongClickItem(int position);

        /*筛选*/
        void onScreenClickItem();

        /*点击*/
        void onClickItem(int position);

        /*聊天*/
        void onChatClickItem(int position);

        /*买单的点击事件*/
        void onTaskStatusClickItem(int position, int taskStatus);
    }
}

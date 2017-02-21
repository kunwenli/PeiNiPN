package com.jsz.peini.ui.adapter.square;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.model.square.ImageListBean;
import com.jsz.peini.model.square.UserImageAllByUserId;
import com.jsz.peini.ui.adapter.BaseRecyclerViewAdapter;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.StringUtils;
import com.jsz.peini.utils.time.TimeUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by th on 2017/1/5.
 */
public class MIPhotoAdapter extends BaseRecyclerViewAdapter {

    private final Activity mActivity;
    private UserImageAllByUserId mImageBean;
    private List<UserImageAllByUserId.UserImageListBean> mUserImageList;
    private boolean mImageManage;
    private MIPhotoManageAdapter mMIPhotoManageAdapter;
    private boolean mCheck = false;
    private int mPositio;

    public MIPhotoAdapter(Activity activity, List<UserImageAllByUserId.UserImageListBean> mUserImageList) {
        super(mUserImageList);
        mActivity = activity;
        this.mUserImageList = mUserImageList;
    }

    @Override
    public void onBindBodyViewHolder(RecyclerView.ViewHolder viewHolder, final int positio) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        UserImageAllByUserId.UserImageListBean userImageListBean = mUserImageList.get(positio);
        final String imageTime = userImageListBean.getImageTime();
        if (StringUtils.isNoNull(imageTime)) {
            holder.mImagetime.setText(imageTime);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                holder.mMiPhotoManageTime.setChecked(mCheck);
            }
        }, 50);

        holder.mMiPhotoManageTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundBuon, boolean check) {
                mPositio = positio;
                mCheck = check;
                if (check) {
                    notifyItemChanged(positio);
                } else {
                }
                LogUtil.d("这个是相册的根据时间全部选择的Id--" + positio, "这个是是否选择了" + check + TimeUtils.getCurrentTime());
            }
        });

        List<ImageListBean> userImageAll = userImageListBean.getUserImageAll();
        if (userImageListBean.getUserImageAll().size() != 0) {
            holder.mMiPhotoManageAdapter.setLayoutManager(new GridLayoutManager(mActivity, 4));
            mMIPhotoManageAdapter = new MIPhotoManageAdapter(mActivity, userImageAll, mImageManage, mCheck);
            holder.mMiPhotoManageAdapter.setAdapter(mMIPhotoManageAdapter);
            /**是否管理照片*/
            holder.mMiPhotoManageTime.setVisibility(mImageManage ? View.VISIBLE : View.GONE);
            /**是否全选的监听
             * */
            mMIPhotoManageAdapter.setOnCheckedChangedlistenr(new MIPhotoManageAdapter.onCheckedAllChangedlistenr() {
                @Override
                public void setCheckAll(boolean checkall) {
                    holder.mMiPhotoManageTime.setChecked(checkall);
                }

                @Override
                public void setCheckItem(int position, String imageView) {
                    LogUtil.d("这个是相册图片的第几条数据--" + positio, position + "图片的地址" + imageView);
                }

                @Override
                public void setCheckPhoto(int position, String imageView) {
                    mOnCheckedChangedlistenr.setIsCheckPhoto(positio, position, imageView);
                }
            });

        }

    }

    @Override
    public RecyclerView.ViewHolder setBodyViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.mi_photo_manage, viewGroup, false);
        return new ViewHolder(view);
    }

    public void setImageBean(UserImageAllByUserId imageBean) {
        mImageBean = imageBean;
        mUserImageList = imageBean.getUserImageList();
        list = mUserImageList;
        notifyDataSetChanged();
    }

    public void setImageManage(boolean imageManage) {
        mImageManage = imageManage;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mi_photo_manage_adapter)
        RecyclerView mMiPhotoManageAdapter;
        @InjectView(R.id.imagetime)
        TextView mImagetime;
        @InjectView(R.id.mi_image_manage_time)
        CheckBox mMiPhotoManageTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    private onCheckedChangedlistenr mOnCheckedChangedlistenr;

    public void setOnCheckedChangedlistenr(onCheckedChangedlistenr onCheckedChangedlistenr) {
        mOnCheckedChangedlistenr = onCheckedChangedlistenr;
    }

    public interface onCheckedChangedlistenr {
        /**
         * 点击的图片索引
         */
        void setIsCheckPhoto(int positio, int position, String imageView);
    }
}

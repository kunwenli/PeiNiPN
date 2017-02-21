package com.jsz.peini.ui.adapter.square;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jsz.peini.R;
import com.jsz.peini.model.square.ImageListBean;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.ui.adapter.BaseRecyclerViewAdapter;
import com.jsz.peini.utils.Bitmap.GlideImgManager;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.StringUtils;
import com.jsz.peini.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by th on 2017/1/5.
 */
public class MIPhotoManageAdapter extends BaseRecyclerViewAdapter {

    private final Activity mActivity;
    private final List<ImageListBean> mUserImageAll;
    /**
     * 显示
     */
    private final boolean mNextManage;
    private final boolean mIsCheck;

    /**
     * 选择的条数
     */
    List<String> mStringImage = new ArrayList<>();

    public MIPhotoManageAdapter(Activity activity, List<ImageListBean> userImageAll, boolean imageManage, boolean isCheck) {
        super(userImageAll);
        mActivity = activity;
        mUserImageAll = userImageAll;
        mNextManage = imageManage;
        mIsCheck = isCheck;
    }

    @Override
    public void onBindBodyViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        ViewHolder holder = (ViewHolder) viewHolder;

        final ImageListBean imageListBean = mUserImageAll.get(position);
        final String imageView = IpConfig.HttpPic + imageListBean.getImageSrc() + "";

        holder.mMiImageviewManage.setVisibility(mNextManage ? View.VISIBLE : View.GONE);
        holder.mMiImageviewManage.setChecked(mIsCheck);
        if (mIsCheck) {
            List<ImageListBean> userImageAll = mUserImageAll;
            for (int i = 0; i < userImageAll.size(); i++) {
                mStringImage.add(userImageAll.get(i).getId() + "");
            }
        } else {
            mStringImage.remove(imageListBean.getId() + "");
        }
        if (StringUtils.isNoNull(imageView)) {
            GlideImgManager.loadImage(mActivity, imageView, holder.mImageview);
        }
        holder.mImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnCheckedChangedlistenr.setCheckPhoto(position, imageView);
            }
        });
        holder.mMiImageviewManage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean select) {
                if (select) {
                    LogUtil.d(select + "这个是选择了多少个--" + mStringImage.size() + "具体的第几个---", position + "附上连接的地址" + imageView);
                    mOnCheckedChangedlistenr.setCheckItem(position, imageView);
                    mStringImage.add(imageListBean.getId() + "");
                } else {
                    mStringImage.remove(imageListBean.getId() + "");
                }
                mOnCheckedChangedlistenr.setCheckAll(mStringImage.size() == mUserImageAll.size());
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder setBodyViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_photo_manage, viewGroup, false);
        int screenWidth = UiUtils.getScreenWidth(mActivity) / 4;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(screenWidth - 30, screenWidth - 30);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.imageview)
        ImageView mImageview;
        @InjectView(R.id.mi_imageview_manage)
        CheckBox mMiImageviewManage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    private onCheckedAllChangedlistenr mOnCheckedChangedlistenr;

    public void setOnCheckedChangedlistenr(onCheckedAllChangedlistenr onCheckedChangedlistenr) {
        mOnCheckedChangedlistenr = onCheckedChangedlistenr;
    }

    public interface onCheckedAllChangedlistenr {
        /**
         * 全选
         *
         * @param checkall
         */
        void setCheckAll(boolean checkall);

        /**
         * 选择了哪个条目 和连接的地址
         */
        void setCheckItem(int position, String imageView);

        void setCheckPhoto(int position, String imageView);
    }

}

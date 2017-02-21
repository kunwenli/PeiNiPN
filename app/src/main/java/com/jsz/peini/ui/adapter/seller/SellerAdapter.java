package com.jsz.peini.ui.adapter.seller;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xlhratingbar_lib.XLHRatingBar;
import com.jsz.peini.R;
import com.jsz.peini.model.seller.SellerBean;
import com.jsz.peini.ui.view.RoundAngleImageView;
import com.jsz.peini.utils.Bitmap.GlideImgManager;
import com.jsz.peini.utils.StringUtils;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kunwe on 2016/11/26.
 */
public class SellerAdapter extends RecyclerView.Adapter {

    private static final int ITEM_TYPE_HEADER = 1;
    private static final int ITEM_TYPE_HEADER_TWO = 2;
    private static final int ITEM_TYPE_CONTENT = 3;
    public List<SellerBean.SellerInfoBean> mList;
    public final Activity mMainActivity;
    private int mHeaderCount = 2;//头部View个数
    public int mVisibility;
    public static final int ONE_SCREEN_COUNT = 8; // 一屏能显示的个数，这个根据屏幕高度和各自的需求定

    public SellerAdapter(Activity mainActivity, List<SellerBean.SellerInfoBean> list) {
        mList = list;
        mMainActivity = mainActivity;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else if (position == 1) {
            return ITEM_TYPE_HEADER_TWO;
        } else {
            return ITEM_TYPE_CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            return new ViewHolder(LayoutInflater.from(mMainActivity).inflate(R.layout.item_1, parent, false));
        } else if (viewType == ITEM_TYPE_HEADER_TWO) {
            return new ViewHolderTow(LayoutInflater.from(mMainActivity).inflate(R.layout.item_2, parent, false));
        } else if (viewType == ITEM_TYPE_CONTENT) {
            return new ViewHolderThere(LayoutInflater.from(mMainActivity).inflate(R.layout.seller_item_shopping, parent, false));
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            setHeaderView((ViewHolder) holder, position);
        } else if (holder instanceof ViewHolderTow) {
            setMyRankView((ViewHolderTow) holder, position);
        } else if (holder instanceof ViewHolderThere) {
            setViewHolderOneView((ViewHolderThere) holder, position);
        }
    }

    /**
     * 条目布局
     */
    private void setViewHolderOneView(ViewHolderThere holder, final int position) {
        if (mList.size() > 0) {
            final SellerBean.SellerInfoBean sellerInfoBean = mList.get(position - mHeaderCount);

            //标题name
            String sellerName = sellerInfoBean.getSellerName();
            holder.mSellerName.setText(sellerName);

            //是否可以预定
            holder.mCountMJTitle.setVisibility(View.GONE);

            //位置,吃饭类型
            String DistrictNamelabelsName = sellerInfoBean.getDistrictName() + " | " + sellerInfoBean.getLabelsName();
            holder.mDistrictNamelabelsName.setText(DistrictNamelabelsName);

            /*距离*/
            double size = (double) sellerInfoBean.getDistance() / 1000;
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
            String Distance = df.format(size) + "km";//返回的是String类型的
            holder.mDistance.setText(Distance);

            /**商家图片*/
            String imageSrc = sellerInfoBean.getImageSrc();
            GlideImgManager.loadImage(mMainActivity, "http://i1.s1.dpfile.com/pc/f59ce7b879eea202f36692aa9ead9dac(249x249)/thumb.jpg", holder.mImageSrc);

            //评分
            int countSelected = sellerInfoBean.getSellerScore() / 20;
            holder.mSellerScore.setCountSelected(countSelected);

            //价格
            String Price = "￥" + sellerInfoBean.getPrice() + "/人";
            holder.mPrice.setText(Price);

            //优惠
            String countMJ = sellerInfoBean.getCountMJ();
            if (StringUtils.isNoNull(countMJ)) {
                holder.mCountMJName.setText(countMJ);
                holder.mCountMJ.setVisibility(View.VISIBLE);
            } else {
                holder.mCountMJ.setVisibility(View.GONE);
            }

            //金币优惠
            String countJB = sellerInfoBean.getCountJB();
            if (StringUtils.isNoNull(countJB)) {
                holder.mCountJBName.setText(countJB);
                holder.mCountJB.setVisibility(View.VISIBLE);
            } else {
                holder.mCountJB.setVisibility(View.GONE);
            }
            //如果没有优惠
            boolean b = StringUtils.isNoNull(countJB) && StringUtils.isNoNull(countMJ);
            if (b) {
                holder.mSellerActivity.setVisibility(View.GONE);
            }
            //点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = sellerInfoBean.getId();
                    mListener.onSellerItemClick(id, position);
                }
            });
        }
    }

    /**
     * 悬停布局
     */
    private void setMyRankView(ViewHolderTow holder, final int position) {
        holder.mSellerSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(position);
                mListener.onSellerSelectItemClick(1);
            }
        });

        holder.mSellerDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(position);
                mListener.onSellerSelectItemClick(2);
            }
        });
        holder.mSellerTypeofoperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(position);
                mListener.onSellerSelectItemClick(3);
            }
        });
        holder.mSellerSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(position);
                mListener.onSellerSelectItemClick(4);
            }
        });

    }

    /**
     * 头布局
     */
    private void setHeaderView(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return getCount();
    }

    private int getCount() {
        return mList.size() + mHeaderCount > ONE_SCREEN_COUNT
                ? mList.size() + mHeaderCount
                : getList();
    }

    private int getList() {
        return (mList.size() + mHeaderCount) + (ONE_SCREEN_COUNT - (mList.size() + mHeaderCount));
    }

    /**
     * 轮播条的Viewholder
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 筛选的布局的viewholder
     */
    class ViewHolderTow extends RecyclerView.ViewHolder {
        @InjectView(R.id.image_1)
        ImageView mImage1;
        @InjectView(R.id.seller_seller)
        LinearLayout mSellerSeller;
        @InjectView(R.id.image_2)
        ImageView mImage2;
        @InjectView(R.id.seller_distance)
        LinearLayout mSellerDistance;
        @InjectView(R.id.image_3)
        ImageView mImage3;
        @InjectView(R.id.seller_typeofoperation)
        LinearLayout mSellerTypeofoperation;
        @InjectView(R.id.image_4)
        ImageView mImage4;
        @InjectView(R.id.seller_sort)
        LinearLayout mSellerSort;

        public ViewHolderTow(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    /**
     * 条目的ViewHolder
     */
    class ViewHolderThere extends RecyclerView.ViewHolder {
        @InjectView(R.id.imageSrc)
        RoundAngleImageView mImageSrc;
        @InjectView(R.id.sellerName)
        TextView mSellerName;
        @InjectView(R.id.countMJ_title)
        ImageView mCountMJTitle;
        @InjectView(R.id.sellerScore)
        XLHRatingBar mSellerScore;
        @InjectView(R.id.price)
        TextView mPrice;
        @InjectView(R.id.districtNamelabelsName)
        TextView mDistrictNamelabelsName;
        @InjectView(R.id.distance)
        TextView mDistance;
        @InjectView(R.id.countJB_image)
        ImageView mCountJBImage;
        @InjectView(R.id.countJB_name)
        TextView mCountJBName;
        @InjectView(R.id.countJB)
        LinearLayout mCountJB;
        @InjectView(R.id.countMJ_image)
        ImageView mCountMJImage;
        @InjectView(R.id.countMJ_name)
        TextView mCountMJName;
        @InjectView(R.id.countMJ)
        LinearLayout mCountMJ;
        @InjectView(R.id.seller_activity)
        LinearLayout mSellerActivity;
        @InjectView(R.id.item_selector)
        LinearLayout mItemSelector;

        public ViewHolderThere(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    private OnClickListener mListener;

    public void setOnClickListener(OnClickListener listener) {
        mListener = listener;
    }

    public interface OnClickListener {
        /**
         * 第一条条目的点击事件  负责悬停
         */
        void onClick(int position);

        /**
         * 筛选点击事件的回调
         *
         * @param position 点击筛选的位置
         */
        void onSellerSelectItemClick(int position);

        /**
         * 商家的点击事件
         *
         * @param id       这个是商家的id
         * @param position 这个是条目的position
         */
        void onSellerItemClick(int id, int position);
    }

}

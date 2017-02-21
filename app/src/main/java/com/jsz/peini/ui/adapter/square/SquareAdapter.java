package com.jsz.peini.ui.adapter.square;


import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.model.ad.AdModel;
import com.jsz.peini.model.square.ImageListBean;
import com.jsz.peini.model.square.LikeListBean;
import com.jsz.peini.model.square.SquareBean;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.ui.adapter.BaseRecyclerViewAdapter;
import com.jsz.peini.ui.adapter.TestNormalAdapter;
import com.jsz.peini.ui.view.square.SquareTextView;
import com.jsz.peini.utils.Bitmap.GlideImgManager;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.StringUtils;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by th on 2016/12/23.
 */
public class SquareAdapter extends BaseRecyclerViewAdapter {

    private static final String TAG = "SquareAdapter 广场适配器";
    private final Activity mActivity;
    public List<AdModel.AdvertiseListBean> mAdModels;//广告的信息
    private List<SquareBean.SquareListBean> mList;//空间返回的大数据
    private SquareBean.SquareListBean mSquareListBean; // 基本的业务模型
    public SquareCommentlistAdapter mSquareCommentlistAdapter;
    private int mSpId;
    public SquareLikeAdapter mSquareLikeAdapter;
    private List<LikeListBean> mLikeList;

    public TestNormalAdapter mSelleradapter;

    public SquareAdapter(Activity activity, List<SquareBean.SquareListBean> list, List<AdModel.AdvertiseListBean> adModels) {
        super(list);
        mActivity = activity;
        mList = list;
        mAdModels = adModels;
    }

    @Override
    public void onBindBodyViewHolder(RecyclerView.ViewHolder viewHolder, final int index) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        if (mList.size() != 0 && mList.size() > 0) {
            mSquareListBean = mList.get(index);

            //地址
            String address = mSquareListBean.getAddress() + "";
            if (StringUtils.isNoNull(address)) {
                holder.mSquareAddress.setText(address);
            } else {
                holder.mSquareAddress.setVisibility(View.GONE);
            }
            /**是否有评论和点赞*/
            if (mSquareListBean.getLikeList().size() == 0 && mSquareListBean.getCommentList().size() == 0) {
                holder.mSquareBj.setVisibility(View.GONE);
            } else {
                holder.mSquareBj.setVisibility(View.VISIBLE);
            }

            //名字
            holder.mSquareNickname.setText(mSquareListBean.getNickname());

            //删除按钮
            holder.mSquareDeleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LogUtil.e(TAG, "点击了删除的按钮");
                    mListener.OnDelete(index);
                }
            });

            //名字
            holder.mSquareNickname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onID(index, mSquareListBean.getId() + "", mSquareListBean.getUserId() + "");
                }
            });

            //头像  性别
            String PIC = IpConfig.HttpPic + mSquareListBean.getImageHead();
            GlideImgManager.loadImage(mActivity, PIC, holder.mSquareImageead, "2");

            //头像的点击事件
            holder.mSquareImageead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onID(index, mSquareListBean.getId() + "", mSquareListBean.getUserId() + "");
                    LogUtil.d(mActivity.getPackageName(), "==========" + mSquareListBean.getUserId());
                }
            });

            /*广场的信息*/
            holder.mSquareContent.setToFalse(mSquareListBean.getContent() + "", 3);

            /*图片的列表*/
            if (mSquareListBean.getImageList() != null && mSquareListBean.getImageList().size() > 0) {
                SquareImageAdapter imageAdapter = new SquareImageAdapter(mActivity, (ArrayList<ImageListBean>) mSquareListBean.getImageList());
                holder.mSquareIpc.setLayoutManager(new GridLayoutManager(mActivity, 3));
                holder.mSquareIpc.setAdapter(imageAdapter);
                holder.mSquareIpc.setVisibility(View.VISIBLE);
            } else {
                holder.mSquareIpc.setVisibility(View.GONE);
            }

            /*点赞的列表*/
            if (mSquareListBean.getLikeList() != null && mSquareListBean.getLikeList().size() > 0) {
                holder.mPeopleLike.setVisibility(View.VISIBLE);
                int size = mSquareListBean.getLikeList().size();
                if (size > 8) {
                    holder.mPeopleLike.setText("等" + size + "人赞过");
                } else {
                    holder.mPeopleLike.setVisibility(View.GONE);
                }
                holder.mPeopleLike.setText(mSquareListBean.getLikeList().size() + "人点赞");
                mSquareLikeAdapter = new SquareLikeAdapter(mActivity, mSquareListBean.getLikeList());
                //设置布局管理器
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                holder.mSquareLike.setLayoutManager(linearLayoutManager);
                mSquareLikeAdapter.addHeadView(View.inflate(mActivity, R.layout.square_like_head, null));
                holder.mSquareLike.setAdapter(mSquareLikeAdapter);
                holder.mSquareLike.setVisibility(View.VISIBLE);
            } else {
                holder.mPeopleLike.setVisibility(View.GONE);
                holder.mSquareLike.setVisibility(View.GONE);
            }
            if (mSquareListBean.getCommentList() != null && mSquareListBean.getCommentList().size() > 0) {
                mSpId = mSquareListBean.getId();
                mSquareCommentlistAdapter = new SquareCommentlistAdapter(mActivity, mSquareListBean.getCommentList());
                LinearLayoutManager layout = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
                holder.mSquareCommentlist.setLayoutManager(layout);
                holder.mSquareCommentlist.setAdapter(mSquareCommentlistAdapter);
                holder.mSquareCommentlist.setVisibility(View.VISIBLE);
                mSquareCommentlistAdapter.setOnCommentClickListener(new SquareCommentlistAdapter.OnCommentClickListener() {
                    @Override
                    public void OnUserId(String id, String userId, int position) {
                        mListener.OnUserId(id, userId, position, mSpId);
                    }

                    @Override
                    public void OnToUserId(String id, String toUserId, int position) {
                        mListener.OnToUserId(id, toUserId, position, mSpId);
                    }

                    @Override
                    public void OnContent(String id, String UserId, String UserNickname, String toUserId, String ToUserNickname, String Content, int position) {
                        mListener.OnContent(id, UserId, UserNickname, toUserId, ToUserNickname, Content, position, mSpId, index);
                        LogUtil.i("id", "" + mSpId);
                    }

                    @Override
                    public void OnItemClick(String id, String Content, int position) {
                        mListener.OnItemClick(id, Content, position, mSpId);
                    }
                });
            } else {
                holder.mSquareCommentlist.setVisibility(View.GONE);
            }

            //点击的评论的按钮
            holder.mSquareButtonNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { //评论的按钮
                    LogUtil.e(TAG, "点击了评论的按钮");
                    mListener.OnContentId(index);
                }
            });

            //这个点赞的按钮
            holder.mSquareButtonZan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { //点赞
                    int id = mSquareListBean.getId();
                    LogUtil.e(TAG, "点击了点赞的按钮");
                    mListener.OnLike(index);
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { //条目的按钮
                    mListener.OnOneItemClick(index, mSquareListBean.getId());
                }
            });
        }
    }

    @Override
    public void onBindHeadViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        HeadViewHolder holder = (HeadViewHolder) viewHolder;
//        //设置播放时间间隔
//        holder.mSliderVp.setPlayDelay(3000);
        //设置透明度
        holder.mSliderVp.setAnimationDurtion(500);
        mSelleradapter = new TestNormalAdapter(mActivity, mAdModels);
        holder.mSliderVp.setAdapter(mSelleradapter);
        holder.mSliderVp.setHintView(new ColorPointHintView(mActivity, Color.BLACK, Color.WHITE));
    }

    @Override
    public RecyclerView.ViewHolder setBodyViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_square, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder setHeadViewHolder(ViewGroup viewGroup, View headView) {
        return new HeadViewHolder(headView);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.square_imageead)
        CircleImageView mSquareImageead;
        @InjectView(R.id.square_nickname)
        TextView mSquareNickname;
        @InjectView(R.id.square_content)
        SquareTextView mSquareContent;
        @InjectView(R.id.square_address)
        TextView mSquareAddress;
        @InjectView(R.id.square_squareTime)
        TextView mSquareTime;
        @InjectView(R.id.square_imageview)
        RecyclerView mSquareIpc;
        @InjectView(R.id.square_like)
        RecyclerView mSquareLike;
        @InjectView(R.id.square_commentlist)
        RecyclerView mSquareCommentlist;
        /**
         * 删除按钮
         */
        @InjectView(R.id.square_delete_item)
        TextView mSquareDeleteItem;
        /**
         * 评论按钮
         */
        @InjectView(R.id.square_button_news)
        LinearLayout mSquareButtonNews;
        /**
         * 点赞
         */
        @InjectView(R.id.square_button_zan)
        LinearLayout mSquareButtonZan;
        /**
         * 背景
         */
        @InjectView(R.id.sq_bj)
        LinearLayout mSquareBj;
        /**
         * 背景
         */
        @InjectView(R.id.people_like)
        TextView mPeopleLike;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.slider_vp)
        RollPagerView mSliderVp;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    private OnCommentClickListener mListener;

    public void setOnCommentClickListener(OnCommentClickListener listener) {
        mListener = listener;
    }

    public interface OnCommentClickListener {
        /**
         * 这个是前面的id--和索引
         */
        void OnUserId(String id, String userId, int position, int SpId);

        /**
         * 后面的 id --- 索引
         */

        void OnToUserId(String id, String toUserId, int position, int SpId);

        /**
         * 这个是内容
         */
        void OnContent(String id, String UserId, String UserNickname, String toUserId, String ToUserNickname, String Content, int position, int SpId, int index);

        /**
         * 这个是整个条目的点击事件
         */
        void OnItemClick(String id, String Content, int position, int SpId);

        /**
         * 这个是大列表
         */
        void OnOneItemClick(int position, int SpId);

        /**
         * 这个是删除的按钮
         */
        void OnDelete(int index);

        /**
         * 这个是评论的接口
         */
        void OnContentId(int index);

        /**
         * 点赞的按钮
         */
        void OnLike(int index);

        void onID(int index, String id, String userId);
    }

}

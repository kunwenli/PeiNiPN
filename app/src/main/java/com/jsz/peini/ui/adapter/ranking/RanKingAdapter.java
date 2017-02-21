package com.jsz.peini.ui.adapter.ranking;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.model.ranking.RanKingBean;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.utils.Bitmap.GlideImgManager;
import com.jsz.peini.utils.LogUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.jsz.peini.utils.UiUtils.getResources;

/**
 * Created by th on 2017/1/19.
 */
public class RanKingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //item类型
    public int ITEM_TYPE_HEADER = 0;
    public int ITEM_TYPE_HEADER_TWO = 3;
    public int ITEM_TYPE_CONTENT = 1;
    public int ITEM_TYPE_BOTTOM = 2;
    public final RanKingBean mBody;
    public final List<RanKingBean.RankListBean> mRankList;

    String[] s = new String[]{"月榜", "季榜", "年榜", "总榜"};
    private int mHeaderCount = 2;//头部View个数
    public final LayoutInflater mLayoutInflater;
    public final Activity mActivity;
    public boolean isShowTab = true;
    /*1:金币榜2:土豪榜3:诚信榜*/
    public final int mRType;

    public RanKingAdapter(Activity activity, RanKingBean body, int RType) {
        mLayoutInflater = LayoutInflater.from(activity);
        mActivity = activity;
        mRType = RType;
        mBody = body;
        mRankList = mBody.getRankList();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else if (position == 1) {
            return ITEM_TYPE_HEADER_TWO;
        } else if (position >= 3 + mHeaderCount) {
            return ITEM_TYPE_CONTENT;
        } else {
            return ITEM_TYPE_BOTTOM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            return new ViewHolderHead(mLayoutInflater.inflate(R.layout.item_head_two, parent, false));
        } else if (viewType == ITEM_TYPE_HEADER_TWO) {
            return new ViewHolderHeadTow(mLayoutInflater.inflate(R.layout.item_ranking_head, parent, false));
        } else if (viewType == ITEM_TYPE_CONTENT) {
            return new ViewHolderTwo(mLayoutInflater.inflate(R.layout.item_ranking_small, parent, false));
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            return new ViewHolderOne(mLayoutInflater.inflate(R.layout.item_ranking, parent, false));
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderHead) {
            setHeaderView((ViewHolderHead) holder, position);
        } else if (holder instanceof ViewHolderHeadTow) {
            setMyRankView((ViewHolderHeadTow) holder, position);
        } else if (holder instanceof ViewHolderOne) {
            setViewHolderOneView((ViewHolderOne) holder, position);
        } else if (holder instanceof ViewHolderTwo) {
            setViewHolderTwoView((ViewHolderTwo) holder, position);
        }
    }

    private void setViewHolderTwoView(ViewHolderTwo holder, final int position) {
        int i = position - 2;
        holder.mNickname.setText(mRankList.get(i).getNickname() + "");
        holder.mAge.setText(mRankList.get(i).getAge() + "");
        holder.mNum.setText(mRankList.get(i).getNum() + "");
        holder.mIndustry.setText(mRankList.get(i).getIndustry() + "");
        holder.mText4.setText("NO." + (position - 1));
        int sex = mRankList.get(i).getSex();
        GlideImgManager.loadImage(mActivity, IpConfig.HttpPic + mRankList.get(i).getImageHead(), holder.mImageHead, sex + "");
        if (sex == 1) { //男
            holder.mIndustry.setBackgroundResource(R.color.nan);
            holder.mSex.setImageResource(R.mipmap.nan);
            holder.mAge.setTextColor(getResources().getColor(R.color.nan));
        } else {
            holder.mIndustry.setBackgroundResource(R.color.nv);
            holder.mSex.setImageResource(R.mipmap.nv);
            holder.mAge.setTextColor(getResources().getColor(R.color.nv));
        }
    }

    private void setViewHolderOneView(ViewHolderOne holder, final int position) {
        int i = position - 2;
        switch (mRType) {
            case 1://金币榜
                if (i == 0) {
                    holder.mNumbar.setImageResource(R.mipmap.item1);
                    holder.mType.setImageResource(R.mipmap.gold1);
                    holder.mImageHead.setBorderColor(getResources().getColor(R.color.yi));
                } else if (i == 1) {
                    holder.mNumbar.setImageResource(R.mipmap.item2);
                    holder.mType.setImageResource(R.mipmap.gold2);
                    holder.mImageHead.setBorderColor(getResources().getColor(R.color.er));
                } else if (i == 2) {
                    holder.mNumbar.setImageResource(R.mipmap.item3);
                    holder.mType.setImageResource(R.mipmap.gold3);
                    holder.mImageHead.setBorderColor(getResources().getColor(R.color.san));
                }
                break;
            case 2://土豪榜
                if (i == 0) {
                    holder.mNumbar.setImageResource(R.mipmap.item1);
                    holder.mType.setImageResource(R.mipmap.buy3);
                    holder.mImageHead.setBorderColor(getResources().getColor(R.color.yi));
                } else if (i == 1) {
                    holder.mNumbar.setImageResource(R.mipmap.item2);
                    holder.mType.setImageResource(R.mipmap.buy2);
                    holder.mImageHead.setBorderColor(getResources().getColor(R.color.er));
                } else if (i == 2) {
                    holder.mNumbar.setImageResource(R.mipmap.item3);
                    holder.mType.setImageResource(R.mipmap.buy1);
                    holder.mImageHead.setBorderColor(getResources().getColor(R.color.san));
                }
                break;
            case 3://信誉榜
                if (i == 0) {
                    holder.mNumbar.setImageResource(R.mipmap.item1);
                    holder.mType.setImageResource(R.mipmap.integrity1);
                    holder.mImageHead.setBorderColor(getResources().getColor(R.color.yi));
                } else if (i == 1) {
                    holder.mNumbar.setImageResource(R.mipmap.item2);
                    holder.mType.setImageResource(R.mipmap.integrity2);
                    holder.mImageHead.setBorderColor(getResources().getColor(R.color.er));
                } else if (i == 2) {
                    holder.mNumbar.setImageResource(R.mipmap.item3);
                    holder.mType.setImageResource(R.mipmap.integrity3);
                    holder.mImageHead.setBorderColor(getResources().getColor(R.color.san));
                }
                break;
        }
        holder.mNickname.setText(mRankList.get(i).getNickname() + "");
        holder.mNum.setText(mRankList.get(i).getNum() + "");
        holder.mAge.setText(mRankList.get(i).getAge() + "");
        holder.mIndustry.setText(mRankList.get(i).getIndustry() + "");
        //性别
        int sex = mRankList.get(i).getSex();
        GlideImgManager.loadImage(mActivity, IpConfig.HttpPic + mRankList.get(i).getImageHead(), holder.mImageHead, sex + "");
        if (sex == 1) { //男
            holder.mIndustry.setBackgroundResource(R.color.nan);
            holder.mSex.setImageResource(R.mipmap.nan);
            holder.mAge.setTextColor(getResources().getColor(R.color.nan));
        } else {
            holder.mIndustry.setBackgroundResource(R.color.nv);
            holder.mSex.setImageResource(R.mipmap.nv);
            holder.mAge.setTextColor(getResources().getColor(R.color.nv));
        }
    }

    /**
     * 我的排行榜信息展示信息展示
     */
    private void setMyRankView(ViewHolderHeadTow holder, int position) {
        RanKingBean.MyRankBean myRank = mBody.getMyRank();
        holder.mRowNo.setText("第" + myRank.getRowNo() + "名");
        holder.mAge.setText(myRank.getAge() + "");
        holder.mNickname.setText(myRank.getNickname() + "");
        holder.mIndustry.setText("" + myRank.getIndustry());
        holder.mNum.setText("拥有 " + myRank.getNum() + " 金币");
        int sex = myRank.getSex();
        GlideImgManager.loadImage(mActivity, IpConfig.HttpPic + myRank.getImageHead(), holder.mImageHead, sex + "");
        if (sex == 1) { //男
            holder.mIndustry.setBackgroundResource(R.color.nan);
            holder.mSex.setImageResource(R.mipmap.nan);
            holder.mAge.setTextColor(getResources().getColor(R.color.nan));
        } else {
            holder.mIndustry.setBackgroundResource(R.color.nv);
            holder.mSex.setImageResource(R.mipmap.nv);
            holder.mAge.setTextColor(getResources().getColor(R.color.nv));
        }

    }


    @Override
    public int getItemCount() {
        return mRankList.size() + mHeaderCount;
    }


    class ViewHolderTwo extends RecyclerView.ViewHolder {
        @InjectView(R.id.text4)
        TextView mText4;
        @InjectView(R.id.imageHead)
        CircleImageView mImageHead;
        @InjectView(R.id.nickname)
        TextView mNickname;
        @InjectView(R.id.sex)
        ImageView mSex;
        @InjectView(R.id.industry)
        TextView mIndustry;
        @InjectView(R.id.age)
        TextView mAge;
        @InjectView(R.id.num)
        TextView mNum;

        ViewHolderTwo(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        @InjectView(R.id.numbar)
        ImageView mNumbar;
        @InjectView(R.id.type)
        ImageView mType;
        @InjectView(R.id.imageHead)
        CircleImageView mImageHead;
        @InjectView(R.id.nickname)
        TextView mNickname;
        @InjectView(R.id.sex)
        ImageView mSex;
        @InjectView(R.id.age)
        TextView mAge;
        @InjectView(R.id.industry)
        TextView mIndustry;
        @InjectView(R.id.num)
        TextView mNum;

        ViewHolderOne(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }


    class ViewHolderHeadTow extends RecyclerView.ViewHolder {
        @InjectView(R.id.imageHead)
        CircleImageView mImageHead;
        @InjectView(R.id.rowNo)
        TextView mRowNo;
        @InjectView(R.id.nickname)
        TextView mNickname;
        @InjectView(R.id.sex)
        ImageView mSex;
        @InjectView(R.id.age)
        TextView mAge;
        @InjectView(R.id.industry)
        TextView mIndustry;
        @InjectView(R.id.num)
        TextView mNum;

        ViewHolderHeadTow(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    class ViewHolderHead extends RecyclerView.ViewHolder {
        @InjectView(R.id.nexttab)
        TabLayout mNexttab;

        public ViewHolderHead(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public void setHeaderView(final ViewHolderHead holder, final int position) {
        //设置TabLayout的模式
        holder.mNexttab.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        if (isShowTab) {
            for (String ss : s) {
                holder.mNexttab.addTab(holder.mNexttab.newTab().setText(ss));
            }
            isShowTab = false;
        }
        holder.mNexttab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                mListener.OnTabItemSelectedListener(tabPosition);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private OnTabSelectedListener mListener;

    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
        mListener = listener;
    }

    public interface OnTabSelectedListener {
        void OnTabItemSelectedListener(int tabPosition);
    }
}

package com.jsz.peini.ui.activity.square;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by th on 2017/2/11.
 */
public class MiBeOverdueCouponActivity extends BaseActivity {
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.right_button)
    TextView mRightButton;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.coupon_recyclerview)
    RecyclerView mCouponRecyclerview;
    public MiBeOverdueCouponActivity mActivity;
    List<String> mList = new ArrayList<>();

    @Override
    public int initLayoutId() {
        return R.layout.activity_coupon;
    }

    @Override
    public void initView() {

        mActivity = this;
        mTitle.setText("失效优惠券");
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        mCouponRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        mCouponRecyclerview.setAdapter(new MiBeOverDueCouponRecyclerviewAdapter(mActivity, mList));
    }

    class MiBeOverDueCouponRecyclerviewAdapter extends RecyclerView.Adapter {
        private int ITEM_TYPE_HEADER = 0;
        private int ITEM_TYPE_HEADER_TWO = 1;
        private int ITEM_TAIL = 2;
        public final Activity mActivity;
        public final List<String> mList;

        public MiBeOverDueCouponRecyclerviewAdapter(Activity activity, List<String> list) {
            mActivity = activity;
            mList = list;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                //头部View
                return ITEM_TYPE_HEADER;
            } else if (position == 2) {
                return ITEM_TAIL;
            } else {
                return ITEM_TYPE_HEADER_TWO;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == ITEM_TYPE_HEADER) {
                return new ViewHolderHead(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_micoupon_head, parent, false));
            } else if (viewType == ITEM_TAIL) {
                return new ViewHolderTail(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tail_miobsoletecoupon, parent, false));
            } else {
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.miobsoletecoupon_view_item, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int tion) {
            if (holder instanceof ViewHolderTail) {
                viewHolderTailData(holder, tion);
            }
        }

        /*底部条女的点击事件*/
        private void viewHolderTailData(RecyclerView.ViewHolder holder, int tion) {
            ViewHolderTail holderTail = (ViewHolderTail) holder;
            holderTail.mOneText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showToast(mActivity, "没有更多优惠券了");
                }
            });
            holderTail.mTowText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.startActivity(new Intent(mActivity, MiBeOverdueCouponActivity.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return 3;
        }

        class ViewHolderHead extends RecyclerView.ViewHolder {
            public ViewHolderHead(View view) {
                super(view);
            }
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View view) {
                super(view);
            }
        }

        class ViewHolderTail extends RecyclerView.ViewHolder {
            @InjectView(R.id.one_text)
            TextView mOneText;
            @InjectView(R.id.tow_text)
            TextView mTowText;

            public ViewHolderTail(View view) {
                super(view);
                ButterKnife.inject(this, view);
            }
        }
    }
}

package com.jsz.peini.ui.activity.square;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.square.ImageListBean;
import com.jsz.peini.ui.adapter.square.SquarePhotoAdapter;
import com.jsz.peini.ui.view.Popwindou;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.UiUtils;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.OnClick;

public class PhotoView extends BaseActivity {
    @InjectView(R.id.photo_vp)
    ViewPager mPhotoVp;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.photo_numbar)
    TextView mPhotoNumbar;
    @InjectView(R.id.photo_button)
    Button mPhotoButton;
    @InjectView(R.id.photo_time_linearlayout)
    LinearLayout mPhotoTimeLinearlayout;
    @InjectView(R.id.photo_time_textview)
    TextView mPhotoTimeTextview;
    @InjectView(R.id.photo_time_framelayout)
    FrameLayout mPhotoTimeFramelayout;
    private SquarePhotoAdapter mNormalAdapter;
    private ArrayList<ImageListBean> mMlist;
    public boolean mBoolean = true;
    public PhotoView mPhotoView;


    @Override
    public int initLayoutId() {
        return R.layout.activity_photoview;
    }

    @Override
    public void initView() {
        mPhotoView = this;
        ShowWindows(true);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mMlist = (ArrayList<ImageListBean>) getIntent().getSerializableExtra("mlist");
        LogUtil.i("图片集合打下", "大小是" + mMlist.size());
        mNormalAdapter = new SquarePhotoAdapter(mPhotoView, mMlist);
        mPhotoVp.setAdapter(mNormalAdapter);
        //初始化数据
        mPhotoNumbar.setText(1 + "/" + mMlist.size());
        mPhotoTimeTextview.setText(mMlist.get(0).getImageTime());
        //数据的监听
        mPhotoVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int i = position + 1;
                mPhotoNumbar.setText(i + "/" + mMlist.size());
                mPhotoTimeTextview.setText(mMlist.get(position).getImageTime());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //数据的点击事件
        mNormalAdapter.setPhotoItemClickListener(new SquarePhotoAdapter.OnPhotoItemClickListener() {
            @Override
            public void onItemClick(int position) {
                LogUtil.d("这个是图片查看器的item事件", "这个是返回的position--" + position);
                mBoolean = !mBoolean;
                isShowShare(mBoolean);
            }
        });

    }

    private void isShowShare(boolean b) {
        mPhotoTimeLinearlayout.setVisibility(b ? View.VISIBLE : View.GONE);
        mPhotoTimeFramelayout.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.photo_button)
    public void onClick() {
        final Popwindou popwindou = new Popwindou(mPhotoView, UiUtils.inflate(mPhotoView, R.layout.activity_photoview));
        View view = UiUtils.inflate(mPhotoView, R.layout.pop_share);
        popwindou.init(view, Gravity.BOTTOM, true);
        //朋友圈
        view.findViewById(R.id.share_wechatcircleoffriends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //微信
        view.findViewById(R.id.share_wechat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //空间
        view.findViewById(R.id.share_qqcircleoffriends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //qq
        view.findViewById(R.id.share_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //新浪
        view.findViewById(R.id.share_xinlangcircleoffriends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //取消
        view.findViewById(R.id.share_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popwindou.dismiss();
            }
        });
    }
}

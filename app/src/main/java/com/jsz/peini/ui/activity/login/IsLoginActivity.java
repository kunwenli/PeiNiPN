package com.jsz.peini.ui.activity.login;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.utils.CacheActivity;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import static com.jsz.peini.R.raw.islogin;

public class IsLoginActivity extends BaseActivity implements View.OnClickListener {
    private Button login_btn;
    private Button register_btn;
    Intent intent;
    private RollPagerView roll_view_pager;
    private VideoView mVideoView;

    @Override
    public int initLayoutId() {
        return R.layout.activity_is_login;
    }

    public void initView() {
        /**存储activity*/
        if (!CacheActivity.activityList.contains(IsLoginActivity.this)) {
            CacheActivity.addActivity(IsLoginActivity.this);
        }
        /**设置透明状态栏*/
        ShowWindows(true);

        login_btn = (Button) findViewById(R.id.login_btn);
        register_btn = (Button) findViewById(R.id.register_btn);
        roll_view_pager = (RollPagerView) findViewById(R.id.roll_view_pager);
        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);

        /*_视频播放控件*/
        mVideoView = (VideoView) this.findViewById(R.id.islogin_video);
        /**本地的视频*/
        String parse = "android.resource://" + getPackageName() + "/" + islogin;
//        Uri uri = Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
        Uri uri = Uri.parse(parse);
        mVideoView.setVideoURI(uri);
        mVideoView.start();

        //监听视频播放完的代码   循环播放的代码
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mPlayer) {
                mPlayer.start();
                mPlayer.setLooping(true);
            }
        });
//
//        //设置视频控制器
//        vv.setMediaController(new MediaController(this));
//
//        //播放完成回调
//        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//
//            }
//        });
    }

    public void initData() {
        //设置播放时间间隔
        roll_view_pager.setPlayDelay(3000);
        //设置透明度
        roll_view_pager.setAnimationDurtion(500);
        //设置适配器
        roll_view_pager.setAdapter(new TestNormalAdapter());

        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        //mRollViewPager.setHintView(news IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        roll_view_pager.setHintView(new ColorPointHintView(this, Color.GRAY, Color.BLACK));
        //mRollViewPager.setHintView(news TextHintView(this));
        //mRollViewPager.setHintView(null);
    }

    private class TestNormalAdapter extends StaticPagerAdapter {
        private String[] imgs = {
                "爱情是人与人之间的强烈的依恋、亲近、向往",
                "你是春天里的那一抹鹅黄",
                "没有挤不出的时间，只有不在乎的想念",
                "想你365天 日日思念"
        };

        @Override
        public View getView(ViewGroup container, int position) {
            TextView view = new TextView(container.getContext());
            view.setText(imgs[position]);
            view.setGravity(Gravity.CENTER);
            view.setTextColor(Color.WHITE);
            view.setMaxLines(1);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }


        @Override
        public int getCount() {
            return imgs.length;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn://注册
                intent = new Intent(this, RegistActivity.class);
                startActivity(intent);
                break;
            case R.id.register_btn://登陆
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            default:
                break;

        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        VideoView vv = (VideoView) this.findViewById(R.id.islogin_video);
        String uri = "android.resource://" + getPackageName() + "/" + islogin;
        vv.setVideoURI(Uri.parse(uri));
        vv.start();

        //监听视频播放完的代码   循环播放的代码
        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mPlayer) {
                mPlayer.start();
                mPlayer.setLooping(true);
            }
        });
    }
}

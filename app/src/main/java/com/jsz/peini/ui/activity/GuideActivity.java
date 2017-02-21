package com.jsz.peini.ui.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.ui.activity.login.IsLoginActivity;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.OnClick;

public class GuideActivity extends BaseActivity {
    @InjectView(R.id.guide_bt_tiaochu)
    Button guideBtTiaochu;
    private ViewPager guide_vp;
    private View view1, view2, view3;
    ArrayList<View> views;

    @Override
    public int initLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {
        ShowWindows(true);
    }

    @Override
    public void initData() {
        guide_vp = (ViewPager) findViewById(R.id.guide_vp);
        views = new ArrayList<>();
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.guide_1, null);
        view2 = inflater.inflate(R.layout.guide_2, null);
        view3 = inflater.inflate(R.layout.guide_3, null);

        views.add(view1);
        views.add(view2);
        views.add(view3);
        guide_vp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(views.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }
        });
        guide_vp.setOnTouchListener(new View.OnTouchListener() {
            int flage = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        flage = 0;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        flage = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (flage == 0) {
                            int item = guide_vp.getCurrentItem();
                            if (item == 0) {
                                Intent intent = new Intent(GuideActivity.this, IsLoginActivity.class);
                                GuideActivity.this.startActivity(intent);
                                finish();
                            } else if (item == 1) {
                                Intent intent = new Intent(GuideActivity.this, IsLoginActivity.class);
                                GuideActivity.this.startActivity(intent);
                                finish();
                            } else if (item == 2) {
                                Intent intent = new Intent(GuideActivity.this, IsLoginActivity.class);
                                GuideActivity.this.startActivity(intent);
                                finish();
                            } else if (item == 3) {

                            }
                        }
                        break;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.guide_bt_tiaochu)
    public void onClick() {
        Intent intent = new Intent(GuideActivity.this, IsLoginActivity.class);
        GuideActivity.this.startActivity(intent);
        finish();
    }
}
package com.jsz.peini.ui.activity.square;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.ui.fragment.SquareFragment;
import com.jsz.peini.utils.Conversion;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by th on 2017/2/8.
 */
public class TaSquareHomepageActivity extends BaseActivity {


    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.right_button)
    TextView mRightButton;
    @InjectView(R.id.kuntoolbar)
    RelativeLayout mKuntoolbar;
    @InjectView(R.id.tasquarehomepage)
    FrameLayout mTasquarehomepage;

    @Override
    public int initLayoutId() {
        return R.layout.activity_tasquarehomepage;
    }

    @Override
    public void initView() {
        super.initView();
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitle.setText("他人空间");
        addFragment();
    }

    private void addFragment() {
        //new出EaseChatFragment或其子类的实例
        SquareFragment chatFragment = new SquareFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putString(Conversion.TYPE, "2");
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.tasquarehomepage, chatFragment).commit();
    }
}

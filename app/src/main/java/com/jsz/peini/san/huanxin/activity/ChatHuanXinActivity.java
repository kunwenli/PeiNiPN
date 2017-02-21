package com.jsz.peini.san.huanxin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.san.huanxin.fragment.ChatFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 15089 on 2017/2/18.
 */
public class ChatHuanXinActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.right_button)
    TextView mRightButton;
    @InjectView(R.id.kuntoolbar)
    RelativeLayout mKuntoolbar;
    @InjectView(R.id.container)
    FrameLayout mContainer;
    @InjectView(R.id.ea_title_name)
    TextView mEaTitleName;
    @InjectView(R.id.ea_address)
    TextView mEaAddress;
    @InjectView(R.id.ea_time)
    TextView mEaTime;
    @InjectView(R.id.ea_image)
    CircleImageView mEaImage;
    @InjectView(R.id.ea_name)
    TextView mEaName;
    @InjectView(R.id.ea_name_type)
    View mEaNameType;
    @InjectView(R.id.ea_task)
    LinearLayout mEaTask;

    private String toChatUsername;

    @Override
    public int initLayoutId() {
        return R.layout.activity_chathuanxin;
    }

    @Override
    public void initView() {
        addFragment();
    }

    private void addFragment() {
        toChatUsername = getIntent().getExtras().getString(EaseConstant.EXTRA_USER_ID);
        mTitle.setText(toChatUsername);
        //new出EaseChatFragment或其子类的实例
        ChatFragment chatFragment = new ChatFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putString(EaseConstant.EXTRA_USER_ID, toChatUsername);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    @Override
    protected void initListener() {
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

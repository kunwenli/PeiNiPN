package com.jsz.peini.ui.activity.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.ui.activity.login.LoginActivity;
import com.jsz.peini.ui.view.password.newlook.GestureContentView;
import com.jsz.peini.ui.view.password.newlook.GestureDrawline;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.SpUtils;
import com.jsz.peini.utils.ToastUtils;

import butterknife.InjectView;


/**
 * Created by th on 2016/12/10.
 */
public class SetPassword extends BaseActivity {
    @InjectView(R.id.setpassword_toolbar)
    Toolbar setpassword_toolbar;
    /*头像*/
    @InjectView(R.id.image_tip)
    ImageView mImageTip;
    /*文字*/
    @InjectView(R.id.text_tip)
    TextView mTextTip;
    /*头像布局*/
    @InjectView(R.id.gesture_tip_layout)
    LinearLayout mGestureTipLayout;
    /*手势密码*/
    @InjectView(R.id.gesture_container)
    FrameLayout mGestureContainer;

    public SetPassword mActivity;
    public GestureContentView mGestureContentView;
    private boolean mIsFirstInput = true;
    private String mFirstPassword = null;
    /**
     * 是否校验密码
     */
    public boolean mIsVerify;
    /**
     * 密码信息
     */
    public String mLock;
    /**
     * 可输入次数
     */
    public int mIndex = 5;

    @Override
    public int initLayoutId() {
        return R.layout.activity_setpassword;
    }

    @Override
    public void initView() {
        mActivity = this;
        Intent intent = getIntent();
        mIsVerify = intent.getBooleanExtra("b", false);
        mLock = intent.getStringExtra("lock");
        LogUtil.d("收到传递过来的参数了" + mIsVerify + "--" + mLock);
        setpassword_toolbar.setTitle("");
        setSupportActionBar(setpassword_toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回????
        setpassword_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        mGestureContentView = new GestureContentView(mActivity, mIsVerify, mLock, new GestureDrawline.GestureCallBack() {
            @Override
            public void onGestureCodeInput(String inputCode) {
                if (!isInputPassValidate(inputCode)) {
                    mTextTip.setText("最少链接4个点, 请重新输入");
                    mGestureContentView.clearDrawlineState(0L);
                    return;
                }
                if (mIsFirstInput) {
                    mFirstPassword = inputCode;
                    mGestureContentView.clearDrawlineState(0L);
                    mTextTip.setText("请再次输入收拾密码");
                } else {
                    if (inputCode.equals(mFirstPassword)) {
                        ToastUtils.showToast(mActivity, "设置成功");
                        mGestureContentView.clearDrawlineState(0L);
                        SpUtils.put(mActivity, "lock", mFirstPassword);
                        mActivity.finish();
                    } else {
                        mTextTip.setText("与上一次绘制不一致，请重新绘制");
                        // 左右移动动画
                        Animation shakeAnimation = AnimationUtils.loadAnimation(mActivity, R.anim.shake);
                        mTextTip.startAnimation(shakeAnimation);
                        // 保持绘制的线，1.5秒后清除
                        mGestureContentView.clearDrawlineState(500L);
                    }
                }
                mIsFirstInput = false;
            }

            @Override
            public void checkedSuccess() {
                ToastUtils.showToast(mActivity, "取消收拾密码");
                SpUtils.put(mActivity, "lock", "");
                mGestureContentView.clearDrawlineState(0L);
                finish();
            }

            @Override
            public void checkedFail() {
                mIndex--;
                mTextTip.setText("密码错了,还可以输入" + mIndex + "次");
                // 左右移动动画
                Animation shakeAnimation = AnimationUtils.loadAnimation(mActivity, R.anim.shake);
                mTextTip.startAnimation(shakeAnimation);
                mGestureContentView.clearDrawlineState(500L);
                if (mIndex == 0) {
                    dialog();
                }
            }
        });
        // 设置手势解锁显示到哪个布局里面
        mGestureContentView.setParentView(mGestureContainer);
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage("请重新登录");
        builder.setTitle("手势密码已失效");
        builder.setCancelable(false);
        builder.setPositiveButton("重新登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Intent intent = new Intent(mActivity, LoginActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                SpUtils.get(mActivity, "b", false);
                SpUtils.put(mActivity, "phone", "");
                SpUtils.put(mActivity, "password", "");
                SpUtils.put(mActivity, "lock", "");
                SpUtils.put(mActivity, "mUserToken", "");
                dialog.dismiss();
                startActivity(intent);
            }
        });
        builder.create().show();
    }

    private boolean isInputPassValidate(String inputPassword) {
        if (TextUtils.isEmpty(inputPassword) || inputPassword.length() < 4) {
            return false;
        }
        return true;
    }
}

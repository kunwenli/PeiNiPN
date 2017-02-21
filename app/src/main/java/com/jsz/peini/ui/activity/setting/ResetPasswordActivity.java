package com.jsz.peini.ui.activity.setting;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import com.jsz.peini.utils.SpUtils;
import com.jsz.peini.utils.ToastUtils;

import butterknife.InjectView;

/**
 * Created by th on 2017/1/16.
 */
public class ResetPasswordActivity extends BaseActivity {
    @InjectView(R.id.setpassword_toolbar)
    Toolbar mSetpasswordToolbar;
    @InjectView(R.id.image_tip)
    ImageView mImageTip;
    @InjectView(R.id.text_tip)
    TextView mTextTip;
    @InjectView(R.id.gesture_tip_layout)
    LinearLayout mGestureTipLayout;
    @InjectView(R.id.gesture_container)
    FrameLayout mGestureContainer;
    public GestureContentView mGestureContentView;
    private boolean mIsFirstInput = true;
    private String mFirstPassword = null;
    public ResetPasswordActivity mActivity;
    public boolean mEquals = true;
    public int mIndex = 5;

    @Override
    public int initLayoutId() {
        return R.layout.activity_resetpassword;
    }

    @Override
    public void initView() {
        mActivity = this;
        Intent intent = getIntent();
        mSetpasswordToolbar.setTitle("");
        setSupportActionBar(mSetpasswordToolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回????
        mSetpasswordToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        mGestureContentView = new GestureContentView(mActivity, false, "", new GestureDrawline.GestureCallBack() {
            @Override
            public void onGestureCodeInput(String inputCode) {
                if (!isInputPassValidate(inputCode)) {
                    mTextTip.setText("最少链接4个点, 请重新输入");
                    mGestureContentView.clearDrawlineState(200L);
                    return;
                }
                if (mEquals) {
                    String lock = (String) SpUtils.get(mActivity, "lock", "");
                    if (!inputCode.equals(lock)) {
                        mIndex--;
                        mTextTip.setText("原始密码输入错误");
                        // 左右移动动画
                        Animation shakeAnimation = AnimationUtils.loadAnimation(mActivity, R.anim.shake);
                        mTextTip.startAnimation(shakeAnimation);
                        mGestureContentView.clearDrawlineState(500L);
                        if (mIndex == 0) {
                            dialog();
                        }
                        return;
                    }
                    mEquals = false;
                    mTextTip.setText("原始密码验证成功");
                    mGestureContentView.clearDrawlineState(0L);
                } else {
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
            }

            @Override
            public void checkedSuccess() {

            }

            @Override
            public void checkedFail() {

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

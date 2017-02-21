package com.jsz.peini.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

import butterknife.InjectView;

/**
 * Created by th on 2017/1/16.
 */
public class LockActivity extends BaseActivity {
    @InjectView(R.id.image_tip)
    ImageView mImageTip;
    @InjectView(R.id.text_tip)
    TextView mTextTip;
    @InjectView(R.id.gesture_tip_layout)
    LinearLayout mGestureTipLayout;
    @InjectView(R.id.gesture_container)
    FrameLayout mGestureContainer;
    public GestureContentView mGestureContentView;
    public LockActivity mActivity;
    public String mLock;
    public int mIndex = 5;

    @Override
    public int initLayoutId() {
        return R.layout.activity_lock;
    }

    @Override
    public void initView() {
        mActivity = this;
        mLock = (String) SpUtils.get(mActivity, "lock", "");

    }

    @Override
    public void initData() {
        mGestureContentView = new GestureContentView(mActivity, true, mLock, new GestureDrawline.GestureCallBack() {
            @Override
            public void onGestureCodeInput(String inputCode) {

            }

            @Override
            public void checkedSuccess() {
                finish();
            }

            @Override
            public void checkedFail() {
                mIndex--;
                mTextTip.setText("密码错了,还可以输入" + mIndex + "次");
                if (mIndex == 0) {
                    dialog();
                }
                mGestureContentView.clearDrawlineState(100L);
            }
        });
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
}

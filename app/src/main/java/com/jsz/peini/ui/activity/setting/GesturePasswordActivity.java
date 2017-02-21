package com.jsz.peini.ui.activity.setting;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.SpUtils;
import com.jsz.peini.utils.StringUtils;

import butterknife.InjectView;
import butterknife.OnClick;

import static com.jsz.peini.R.id.gesturepassword_toolbar;


/**
 * Created by th on 2016/12/10.
 */
public class GesturePasswordActivity extends BaseActivity {
    @InjectView(gesturepassword_toolbar)
    Toolbar mGesturepasswordToolbar;
    @InjectView(R.id.switch2)
    CheckBox mSwitch2;
    @InjectView(R.id.lock)
    LinearLayout mSetLock;
    @InjectView(R.id.gesturee_resetting_password)
    LinearLayout mGestureeResettingPassword;
    @InjectView(R.id.gesturee_getback_password)
    LinearLayout mGestureeGetbackPassword;
    @InjectView(R.id.gesturee_gan)
    LinearLayout mGestureeGan;
    /*存储的密码*/
    public String mLock;
    private boolean isShowL = false;
    public GesturePasswordActivity mActivity;
    public Intent mIntent;
    public boolean mNoNull;

    @Override
    public int initLayoutId() {
        return R.layout.activity_gesturepassword;
    }

    @Override
    public void initView() {
        mActivity = this;
        mLock = (String) SpUtils.get(mActivity, "lock", "");
        mGesturepasswordToolbar.setTitle("");
        setSupportActionBar(mGesturepasswordToolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回????
        mGesturepasswordToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void initData() {
        LogUtil.d("收拾密码管理界面可见");
        /*是否有密码*/
        mNoNull = StringUtils.isNoNull(mLock);
        isShowLView(mNoNull);

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        mLock = (String) SpUtils.get(mActivity, "lock", "");
          /*是否有密码*/
        mNoNull = StringUtils.isNoNull(mLock);
        isShowLView(mNoNull);
    }

    /**
     * 是否显示后两条布局
     * case R.id.start_service:
     * Intent startIntent = new Intent(this, MyService.class);
     * startService(startIntent);
     * break;
     * case R.id.stop_service:
     * Intent stopIntent = new Intent(this, MyService.class);
     * stopService(stopIntent);
     */
    private void isShowLView(boolean noNull) {
        mSwitch2.setChecked(noNull);
        mGestureeGan.setVisibility(noNull ? View.VISIBLE : View.GONE);

    }

    @OnClick({R.id.lock, R.id.gesturee_resetting_password, R.id.gesturee_getback_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lock:
                if (mNoNull) {
                    mIntent = new Intent(mActivity, SetPassword.class);
                    mIntent.putExtra("lock", mLock);
                    mIntent.putExtra("b", true);
                    startActivity(mIntent);
                } else {
                    mIntent = new Intent(mActivity, SetPassword.class);
                    mIntent.putExtra("lock", "");
                    mIntent.putExtra("b", false);
                    startActivity(mIntent);
                }
                break;
            case R.id.gesturee_resetting_password:
                LogUtil.d("重置手势密码");
                mIntent = new Intent(mActivity, ResetPasswordActivity.class);
                startActivity(mIntent);
                break;
            case R.id.gesturee_getback_password:
                LogUtil.d("找回收拾密码");
                mIntent = new Intent(mActivity, UpdataPasswordActivity.class);
                startActivity(mIntent);
                break;
        }
    }
}

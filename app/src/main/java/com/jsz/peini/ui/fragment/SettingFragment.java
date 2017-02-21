package com.jsz.peini.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.jsz.peini.R;
import com.jsz.peini.base.BaseFragment;
import com.jsz.peini.ui.activity.ShareActivity;
import com.jsz.peini.ui.activity.login.IsLoginActivity;
import com.jsz.peini.ui.activity.report.ReportActivity;
import com.jsz.peini.ui.activity.setting.AboutUsActivity;
import com.jsz.peini.ui.activity.setting.GesturePasswordActivity;
import com.jsz.peini.ui.activity.setting.LoginPasswordActivity;
import com.jsz.peini.ui.activity.setting.MerchantsSettledActivity;
import com.jsz.peini.ui.activity.setting.PayPasswordActivity;
import com.jsz.peini.utils.PeiNiUtils;
import com.jsz.peini.utils.SpUtils;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.utils.UiUtils;
import com.jsz.peini.utils.deletedata.DeleteDataManager;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kunwe on 2016/11/29.
 * 设置界面
 */

public class SettingFragment extends BaseFragment {
    @InjectView(R.id.button_1)
    TextView button1;
    @InjectView(R.id.button_2)
    TextView button2;
    @InjectView(R.id.button_3)
    TextView button3;
    @InjectView(R.id.button_4)
    TextView button4;
    @InjectView(R.id.button_6)
    TextView button6;
    @InjectView(R.id.button_7)
    TextView button7;
    @InjectView(R.id.button_8)
    TextView button8;
    @InjectView(R.id.button_9)
    LinearLayout button9;
    @InjectView(R.id.button_10)
    TextView button10;
    @InjectView(R.id.button_11)
    Button button11;
    @InjectView(R.id.ceshi)
    CheckBox ceshi;
    @InjectView(R.id.filter_ideoauthentication)
    CheckBox mFilterIdeoauthentication;
    private String version;
    public Intent mIntent;

    @Override
    public View initViews() {
        return UiUtils.inflate(mActivity, R.layout.fragment_setting);
    }

    @Override
    public void initData() {
        /*测试*/
        Boolean ceshi = (Boolean) SpUtils.get(mActivity, "ceshi", false);
        this.ceshi.setChecked(ceshi);
        this.ceshi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SpUtils.put(mActivity, "ceshi", b);
            }
        });
        mFilterIdeoauthentication.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            }
        });
    }

    @OnClick({R.id.button_1, R.id.button_2,
            R.id.button_3, R.id.button_4,
            R.id.button_6,
            R.id.button_7, R.id.button_8,
            R.id.button_9, R.id.button_10,
            R.id.button_11})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_1: //邀请好友
                startActivity(new Intent(mActivity, ShareActivity.class));
                break;
            case R.id.button_2://修改登陆密码
                startActivity(new Intent(mActivity, LoginPasswordActivity.class));
                break;
            case R.id.button_3://修改支付密码
                startActivity(new Intent(mActivity, PayPasswordActivity.class));
                break;
            case R.id.button_4://设置手势密码
                startActivity(new Intent(mActivity, GesturePasswordActivity.class));
                break;
            case R.id.button_6://清楚缓存
                deleteCache();
                break;
            case R.id.button_7://关于我们
                startActivity(new Intent(mActivity, AboutUsActivity.class));
                break;
            case R.id.button_8://问题反馈
                mIntent = new Intent(mActivity, ReportActivity.class);
                mIntent.putExtra("type", "1");
                startActivity(mIntent);
                break;
            case R.id.button_9://当前版本
                InitUpdate();
                break;
            case R.id.button_10://商家入驻
                startActivity(new Intent(mActivity, MerchantsSettledActivity.class));
                break;
            case R.id.button_11://注销
                dialog();
                break;
        }
    }

    //版本更新
    private void InitUpdate() {
        version = PeiNiUtils.getVersion(mActivity);
        Toast.makeText(mActivity, "当前版本:" + version, Toast.LENGTH_SHORT).show();

    }

    /*清除缓存*/
    private void deleteCache() {
        new AlertDialog.Builder(mActivity)
                .setTitle("清楚缓存吗?")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DeleteDataManager.cleanInternalCache(mActivity);
                        DeleteDataManager.cleanFiles(mActivity);
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    protected void dialog() {
        new AlertDialog.Builder(mActivity)
                .setTitle("注销登录")
                .setMessage("确定注销登录吗")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(mActivity, IsLoginActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        SpUtils.remove(mActivity, "mUserToken");
                        SpUtils.remove(mActivity, "password");
                        SpUtils.remove(mActivity, "phone");
                        EMClient.getInstance().logout(true, new EMCallBack() {

                            @Override
                            public void onSuccess() {
                                // TODO Auto-generated method stub
                                ToastUtils.showToast(mActivity, "退出登录");
                            }

                            @Override
                            public void onProgress(int progress, String status) {
                                // TODO Auto-generated method stub

                            }

                            @Override
                            public void onError(int code, String message) {
                                // TODO Auto-generated method stub

                            }
                        });
                        dialogInterface.dismiss();
                        startActivity(intent);
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
}

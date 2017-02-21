package com.jsz.peini.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.ad.AdModel;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.presenter.ad.Ad;
import com.jsz.peini.san.huanxin.HuanxinHeadBean;
import com.jsz.peini.ui.activity.home.HomeActivity;
import com.jsz.peini.ui.activity.login.IsLoginActivity;
import com.jsz.peini.utils.Bitmap.GlideImgManager;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.LoginDialogUtils;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.SpUtils;
import com.jsz.peini.utils.StringUtils;

import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvertisementActivity extends BaseActivity {

    private static final int SDK_PAY_FLAG = 100;
    @InjectView(R.id.ad)
    ImageView mAd;
    @InjectView(R.id.guide_bt_tiaochu)
    TextView mGuideBtTiaochu;

    private Handler handler;
    private AdvertisementActivity mActivity;
    public Intent mIntent;

    @Override
    public int initLayoutId() {
        return R.layout.activity_advertisement;
    }

    @Override
    public void initView() {
        ShowWindows(true);
        mActivity = this;
        //子线程跳转activity
        handler = new Handler();
        Boolean ceshi = (Boolean) SpUtils.get(mActivity, "ceshi", false);
        if (ceshi) {
            mIntent = new Intent(mActivity, IsLoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mIntent);
            return;
        }
        mGuideBtTiaochu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int guide = (int) SpUtils.get(mActivity, "Guide", 0);
                if (guide == 1) {
                    Message msg = new Message();
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                } else {
                    Message msg = new Message();
                    msg.what = 2;
                    mHandler.sendMessage(msg);
                }
                SpUtils.put(mActivity, "Guide", 1);
                handler.removeCallbacksAndMessages(null);
            }
        });

        handler.postDelayed(new Runnable() {
            public void run() {
                int guide = (int) SpUtils.get(mActivity, "Guide", 0);
                if (guide == 1) {
                    Message msg = new Message();
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                } else {
                    Message msg = new Message();
                    msg.what = 2;
                    mHandler.sendMessage(msg);
                }
                SpUtils.put(mActivity, "Guide", 1);
            }
        }, 2000);

    }

    @Override
    public void initData() {
        initNewWork();
    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    AdModel headBean = (AdModel) msg.obj;
                    GlideImgManager.loadImage(mActivity, IpConfig.HttpPic + headBean.getAdvertiseList().get(0).getAdImgUrl(), mAd);
                    break;
                case 1://启动Activity
                    mIntent = new Intent(mActivity, HomeActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(mIntent);
                    finish();
                    break;
                case 2://启动引导界面
                    mIntent = new Intent(mActivity, GuideActivity.class);
                    startActivity(mIntent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };    /*广告*/

    private void initNewWork() {
        RetrofitUtil.createService(Ad.class).getAdvertise("1").enqueue(new Callback<AdModel>() {
            @Override
            public void onResponse(Call<AdModel> call, Response<AdModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResultCode() == 1) {
                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = response.body();
                        mHandler.sendMessage(msg);
                    } else {
                        LogUtil.d("这个是启动界面广告失败");
                    }
                }
            }

            @Override
            public void onFailure(Call<AdModel> call, Throwable t) {
                LogUtil.d("这个是启动界面广告失败" + t.getMessage());
            }
        });
    }
}

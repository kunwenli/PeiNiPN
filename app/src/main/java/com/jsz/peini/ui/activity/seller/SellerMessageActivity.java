package com.jsz.peini.ui.activity.seller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.eventbus.SelectorSellerBean;
import com.jsz.peini.model.seller.SellerMessageInfoBean;
import com.jsz.peini.presenter.seller.SellerService;
import com.jsz.peini.ui.activity.pay.PaythebillActivity;
import com.jsz.peini.ui.activity.report.ReportActivity;
import com.jsz.peini.ui.adapter.seller.AdvertiseListStringAdapter;
import com.jsz.peini.ui.view.Popwindou;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.LoginDialogUtils;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.StringUtils;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.utils.UiUtils;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jsz.peini.R.id.couponMj;
import static com.jsz.peini.R.id.seller_message_text;
import static com.jsz.peini.R.id.sellermessage_paythebill;

public class SellerMessageActivity extends BaseActivity {
    @InjectView(R.id.seller_message_toolbar)
    Toolbar mSellerMessageToolbar;
    @InjectView(R.id.seller_title_title)
    TextView mSellerTitleTitle;
    @InjectView(R.id.seller_report)
    ImageView mSellerReport;
    @InjectView(R.id.seller_share)
    ImageView mSellerShare;
    @InjectView(R.id.imageList)
    RollPagerView mImageList;
    @InjectView(R.id.sellerName)
    TextView mSellerName;
    @InjectView(R.id.price)
    TextView mPrice;
    @InjectView(R.id.distance)
    TextView mDistance;
    @InjectView(R.id.districtNamesellerAddress)
    TextView mDistrictNamesellerAddress;
    @InjectView(R.id.sellerPhone)
    ImageView mSellerPhone;
    @InjectView(R.id.sellerMeal)
    TextView mSellerMeal;
    @InjectView(R.id.sellerCondition)
    TextView mSellerCondition;
    @InjectView(R.id.sellerServer)
    TextView mSellerServer;
    @InjectView(R.id.isWifi)
    ImageView mIsWifi;
    @InjectView(R.id.isParking)
    ImageView mIsParking;
    @InjectView(R.id.couponJb)
    TextView mCouponJb;
    @InjectView(couponMj)
    TextView mCouponMj;
    @InjectView(seller_message_text)
    TextView mSellerMessageText;
    @InjectView(R.id.seller_image)
    ImageView mSellerImage;
    @InjectView(R.id.sellermessage_paythebill)
    TextView mSellermessagePaythebill;
    @InjectView(R.id.maidan)
    LinearLayout mMaiDan;
    @InjectView(R.id.youhui)
    LinearLayout mYouHui;
    private AdvertiseListStringAdapter selleradapter;
    private int requesCodes = 1;
    public SellerMessageActivity mActivity;


    /*被举报人的id*/
    String mReportId = "1";
    /**
     * 广告信息
     */
    List<String> mAdvertiseListBeen;
    public Intent mIntent;
    public int mType;
    public String mId;
    public Popwindou mPopwindou;
    public SellerMessageInfoBean.SellerInfoBean mInfoBean;
    public List<SellerMessageInfoBean.SellerInfoBean.ImageListBean> mImageListBeen;
    public boolean mEquals;

    @Override
    public int initLayoutId() {
        return R.layout.activity_seller_message;
    }

    @Override
    public void initView() {
        mActivity = this;
        mAdvertiseListBeen = new ArrayList<>();
        mSellerMessageToolbar.setTitle("");
        setSupportActionBar(mSellerMessageToolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回????
        mSellerMessageToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mId = getIntent().getStringExtra("id");
        mType = getIntent().getIntExtra("type", 0);
        mEquals = mType == 1;
        if (mEquals) {
            mSellermessagePaythebill.setText("就选这家");
        } else {
            mSellermessagePaythebill.setText("优惠买单");
        }
        LogUtil.d("商家的id==" + mId + "\n点击商家的类型==" + mType);

    }

    @Override
    public void initData() {
        //设置透明度
        mImageList.setAnimationDurtion(500);
        selleradapter = new AdvertiseListStringAdapter(mActivity, mAdvertiseListBeen);
        mImageList.setAdapter(selleradapter);
        mImageList.setHintView(new ColorPointHintView(this, Color.YELLOW, Color.WHITE));
        initNetWork();
    }

    /*商家详情访问*/
    private void initNetWork() {
        RetrofitUtil.createService(SellerService.class)
                .getSellerInfo(mXpoint, mYpoint, mId)
                .enqueue(new Callback<SellerMessageInfoBean>() {
                    @Override
                    public void onResponse(Call<SellerMessageInfoBean> call, Response<SellerMessageInfoBean> response) {
                        if (response.isSuccessful()) {
                            SellerMessageInfoBean body = response.body();
                            ToastUtils.showToast(mActivity, body.getResultDesc());
                            if (body.getResultCode() == 1) {
                                mInfoBean = body.getSellerInfo();
                                mImageListBeen = body.getSellerInfo().getImageList();

                                if (mInfoBean != null) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            inItSetView();
                                        }
                                    });
                                }
                            } else if (body.getResultCode() == 9) {
                                LoginDialogUtils.isNewLogin(mActivity);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SellerMessageInfoBean> call, Throwable t) {
                        ToastUtils.showToast(mActivity, t.getMessage());
                    }
                });
    }

    /**
     * 填充数据
     */
    private void inItSetView() {
        mAdvertiseListBeen.clear();
        for (int i = 0; i < mImageListBeen.size(); i++) {
            mAdvertiseListBeen.add(mImageListBeen.get(i).getImageUrl());
        }
        selleradapter.notifyDataSetChanged();
        /*名称*/
        mSellerName.setText(mInfoBean.getSellerName());
        /*多少钱一个人*/
        String price = mInfoBean.getPrice() + "/人";
        mPrice.setText(price);
        /*地址*/
        String DistrictNamesellerAddress = mInfoBean.getDistrictName() + mInfoBean.getSellerAddress();
        mDistrictNamesellerAddress.setText(DistrictNamesellerAddress);
        /*描述*/
        mSellerMessageText.setText(mInfoBean.getInformation());
        /*优惠*/
        String couponJb = mInfoBean.getCouponJb();
        if (StringUtils.isNoNull(couponJb)) {
            mCouponJb.setText(couponJb);
            mCouponJb.setVisibility(View.VISIBLE);
            mMaiDan.setVisibility(View.VISIBLE);
        } else {
            mMaiDan.setVisibility(View.GONE);
        }
        String couponMj = mInfoBean.getCouponMj();
        if (StringUtils.isNoNull(couponMj)) {
            mCouponMj.setText(couponMj);
            mCouponMj.setVisibility(View.VISIBLE);
            mYouHui.setVisibility(View.VISIBLE);
        } else {
            mYouHui.setVisibility(View.GONE);
        }

        /*距离*/
        double size = (double) mInfoBean.getDistance() / 1000;
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
        String Distance = df.format(size) + "km";//返回的是String类型的
        mDistance.setText(Distance);

    }

    @OnClick({R.id.districtNamesellerAddress, R.id.sellerPhone, R.id.seller_report, R.id.seller_share, R.id.sellermessage_paythebill})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.districtNamesellerAddress:
                Toast.makeText(this, "地图", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sellerPhone:
                Toast.makeText(this, "电话", Toast.LENGTH_SHORT).show();
                isCall();
                break;
            case R.id.seller_report:
                Toast.makeText(this, "举报", Toast.LENGTH_SHORT).show();
                mIntent = new Intent(this, ReportActivity.class);
                mIntent.putExtra("type", "3");
                mIntent.putExtra("reportId", mReportId);
                startActivity(mIntent);
                break;
            case R.id.seller_share:
                ShowShred();
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                break;
            case sellermessage_paythebill:
                if (mEquals) {
                    SelectorSellerBean event = new SelectorSellerBean("美食", "大驴肉美食");
                    EventBus.getDefault().post(event);
                    LogUtil.d("点击了就选这家按钮");
                    finish();
                } else {
                    Toast.makeText(this, "购物了买单了", Toast.LENGTH_SHORT).show();
                    mIntent = new Intent(this, PaythebillActivity.class);
                    mIntent.putExtra("sellerInfoId", mInfoBean.getId()+"");
                    startActivity(mIntent);
                }
                break;
//            case R.id.seller_paythebill:
//                Toast.makeText(this, "领取优惠券", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(this, CouponActivity.class));
//                break;
        }
    }

    //显示分享底部分享栏目
    private void ShowShred() {
        mPopwindou = new Popwindou(mActivity, UiUtils.inflate(mActivity, R.layout.activity_seller_message));
        View view = UiUtils.inflate(mActivity, R.layout.pop_share);
        mPopwindou.init(view, Gravity.BOTTOM, true);
        view.findViewById(R.id.share_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopwindou.dismiss();
            }
        });

    }

    /**
     * 打电话的方法  动态权限
     */
    private void isCall() {
        //检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //权限未获得
            //用于给用户一个申请权限的解释，该方法只有在用户在上一次已经拒绝过你的这个权限申请。也就是说，用户已经拒绝一次了，你又弹个授权框，你需要给用户一个解释，为什么要授权，则使用该方法。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                isCall(); //重新请求一次
            } else {
                //请求权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, requesCodes);
            }
        } else {
            //权限已获得
            //拨打电话
            Intent intent = new Intent(Intent.ACTION_CALL);
            String sellerPhone = mInfoBean.getSellerPhone();
            Uri data = Uri.parse("tel:" + sellerPhone);
            intent.setData(data);
            startActivity(intent);
        }
    }

    //打电话的动态权限的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == requesCodes) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isCall();
            } else {
                // Permission Denied
                Toast.makeText(SellerMessageActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

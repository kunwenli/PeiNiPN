package com.jsz.peini.ui.activity.square;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.jsz.peini.PeiNiApp;
import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.listener.RetrofitCallback;
import com.jsz.peini.model.pay.ConversionListBean;
import com.jsz.peini.model.pay.PayOrderIdStrBean;
import com.jsz.peini.model.pay.PaySuccessfulBean;
import com.jsz.peini.model.pay.WeiXinPayOrderIdStrBean;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.presenter.PayService;
import com.jsz.peini.ui.adapter.square.GetConversionAdapter;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.LoginDialogUtils;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.utils.pay.PayResult;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by th on 2017/1/3.
 */
public class RechargeActivity extends BaseActivity {
    private static final int SDK_PAY_FLAG = 200;
    private static final int SDK_PAY_SUCCESS = 2000;
    private static final int SDK_PAY_FLAG_SUCCESS = 1;
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.right_button)
    TextView mRightButton;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.kuntoolbar)
    RelativeLayout mKuntoolbar;
    @InjectView(R.id.getConversionList)
    RecyclerView mGetConversionList;
    @InjectView(R.id.radiogroup_ItcSelect)
    RadioGroup mRadiogroupItcSelect;
    @InjectView(R.id.confirmpayment)
    TextView mConfirmpayment;
    /**
     * 初始化选择金币
     */
    private int mGold = 0;
    /**
     * 初始化选择支付方式
     */
    private int mPay = 0;
    private List<ConversionListBean.ListBean> mData;
    private RechargeActivity mActivity;
    private GetConversionAdapter mConversionAdapter;
    private int mId;

    @Override
    public int initLayoutId() {
        return R.layout.activity_recharge;
    }

    private String mOrderIdStr;
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    ConversionListBean headBean = (ConversionListBean) msg.obj;
                    mData.clear();
                    mData.addAll(headBean.getData());
                    mConversionAdapter.notifyDataSetChanged();
                    break;
                case SDK_PAY_SUCCESS:
                    PaySuccessfulBean successfulBean = (PaySuccessfulBean) msg.obj;
                    mOrderIdStr = successfulBean.getOrderIdStr();
                    LogUtil.d("successfulBean" + mOrderIdStr+"mGold"+mPay);
                    if (mPay == 1) {//微信
                        getWeiXinOrder(mOrderIdStr);
                    } else if (mPay == 2) {//支付宝
                        getZhiFuBaoOrder(mOrderIdStr);
                    }
                    break;
                case SDK_PAY_FLAG_SUCCESS:
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(mActivity, "支付成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(mActivity, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void initView() {
        super.initView();
        mActivity = this;
        mData = new ArrayList<>();
        mTitle.setText("金币充值");
    }

    @Override
    public void initData() {
        mGetConversionList.setLayoutManager(new LinearLayoutManager(mActivity));
        mConversionAdapter = new GetConversionAdapter(mActivity, mData);
        mGetConversionList.setAdapter(mConversionAdapter);
        initNetWork();
    }

    /*获取充值方式*/
    private void initNetWork() {
        RetrofitUtil.createService(PayService.class)
                .getConversionList()
                .enqueue(new RetrofitCallback<ConversionListBean>() {
                    @Override
                    public void onSuccess(Call<ConversionListBean> call, Response<ConversionListBean> response) {
                        if (response.isSuccessful()) {
                            ConversionListBean body = response.body();
                            if (body.getResultCode() == 1) {
                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = response.body();
                                mHandler.sendMessage(msg);
                            } else {
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ConversionListBean> call, Throwable t) {
                        ToastUtils.showToast(mActivity, t.getMessage());
                    }
                });
    }

    @Override
    protected void initListener() {
        mConversionAdapter.setitemClickListener(new GetConversionAdapter.OnitemClickListener() {
            @Override
            public void itemClick(int position, int payNum, int goldNum, int id) {
                mConversionAdapter.setGoldChoice(position);
                mGold = goldNum;
                mId = id;
            }
        });
        mRadiogroupItcSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.pay_weixin:
                        mPay = 1;
                        LogUtil.d("微信支付");
                        break;
                    case R.id.pay_zhifubao:
                        mPay = 2;
                        LogUtil.d("支付宝支付");
                        break;
                }
            }
        });
    }

    @OnClick(R.id.confirmpayment)
    public void onClick() {
        if (mGold == 0) {
            ToastUtils.showToast(mActivity, "请选择充值金额");
            return;
        }
        if (mPay == 0) {
            ToastUtils.showToast(mActivity, "请选择支付方式");
            return;
        }
        initPayData();
    }

    private void initPayData() {
        RetrofitUtil.createService(PayService.class)
                .saveBuyGoldOrder(mUserToken, mId)
                .enqueue(new RetrofitCallback<PaySuccessfulBean>() {
                    @Override
                    public void onSuccess(Call<PaySuccessfulBean> call, Response<PaySuccessfulBean> response) {
                        if (response.isSuccessful()) {
                            PaySuccessfulBean body = response.body();
                            if (body.getResultCode() == 1) {
                                Message msg = new Message();
                                msg.what = SDK_PAY_SUCCESS;
                                msg.obj = response.body();
                                mHandler.sendMessage(msg);
                            } else if (body.getResultCode() == 9) {
                                LoginDialogUtils.isNewLogin(mActivity);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PaySuccessfulBean> call, Throwable t) {
                        ToastUtils.showToast(mActivity, t.getMessage());
                    }
                });
    }

    /**
     * 微信预支付
     */
    private void getWeiXinOrder(String orderIdStr) {
        RetrofitUtil.createService(PayService.class)
                .payWx(mUserToken, orderIdStr)
                .enqueue(new RetrofitCallback<WeiXinPayOrderIdStrBean>() {
                    @Override
                    public void onSuccess(Call<WeiXinPayOrderIdStrBean> call, Response<WeiXinPayOrderIdStrBean> response) {
                        if (response.isSuccessful()) {
                            WeiXinPayOrderIdStrBean body = response.body();
                            if (body.getResultCode() == 1) {
                                LogUtil.d("微信预支付接口返回的数据" + body.toString());
                                WeiXinBaoPay(body);
                            } else if (body.getResultCode() == 9) {
                                LoginDialogUtils.isNewLogin(mActivity);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<WeiXinPayOrderIdStrBean> call, Throwable t) {
                        ToastUtils.showToast(mActivity, t.getMessage());
                    }
                });
    }

    /**
     * 微信支付接口会掉
     *
     * @param payInfo
     */
    private void WeiXinBaoPay(WeiXinPayOrderIdStrBean payInfo) {
        WeiXinPayOrderIdStrBean.DataBean data = payInfo.getData();
        IWXAPI wxapi = WXAPIFactory.createWXAPI(mActivity, PeiNiApp.WXAPIPAY);
        wxapi.registerApp(PeiNiApp.WXAPIPAY);
        PayReq payReq = new PayReq();
        payReq.appId = PeiNiApp.WXAPIPAY;
        payReq.partnerId = data.getPartnerid();
        payReq.prepayId = data.getPrepayid();
        payReq.packageValue = data.getPackageX();
        payReq.nonceStr = data.getNoncestr();
        payReq.timeStamp = data.getTimestamp();
        payReq.sign = data.getSign();
        wxapi.sendReq(payReq);
        finish();
    }

    /**
     * 支付宝预支付接口
     */
    private void getZhiFuBaoOrder(String orderIdStr) {
        RetrofitUtil.createService(PayService.class)
                .payAli(mUserToken, orderIdStr)
                .enqueue(new Callback<PayOrderIdStrBean>() {
                    @Override
                    public void onResponse(Call<PayOrderIdStrBean> call, Response<PayOrderIdStrBean> response) {
                        if (response.isSuccessful()) {
                            PayOrderIdStrBean body = response.body();
                            ToastUtils.showToast(mActivity, body.getResultDesc());
                            if (body.getResultCode() == 1) {
                                LogUtil.d("支付宝预支付接口返回的数据" + body.toString());
                                ZhiFuBaoPay(body.getData().getPayInfo());
                            } else if (body.getResultCode() == 9) {
                                LoginDialogUtils.isNewLogin(mActivity);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PayOrderIdStrBean> call, Throwable t) {

                    }
                });
    }

    /**
     * 支付宝支付
     *
     * @param data
     */
    private void ZhiFuBaoPay(final String data) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(mActivity);
                Map<String, String> result = alipay.payV2(data, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG_SUCCESS;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}

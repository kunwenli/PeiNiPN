package com.jsz.peini.ui.activity.pay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.baidu.mapapi.map.Text;
import com.jsz.peini.PeiNiApp;
import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.listener.OnPasswordInputFinish;
import com.jsz.peini.listener.RetrofitCallback;
import com.jsz.peini.model.pay.PayJinBiOrderIdStrBean;
import com.jsz.peini.model.pay.PayOrderIdStrBean;
import com.jsz.peini.model.pay.PaySuccessfulBean;
import com.jsz.peini.model.pay.WeiXinPayOrderIdStrBean;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.presenter.PayService;
import com.jsz.peini.ui.activity.seller.CouponActivity;
import com.jsz.peini.ui.view.PasswordView;
import com.jsz.peini.ui.view.Popwindou;
import com.jsz.peini.ui.view.password.common.Constants;
import com.jsz.peini.ui.view.pay.MyRadioButton;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.LoginDialogUtils;
import com.jsz.peini.utils.MD5Utils;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.SpUtils;
import com.jsz.peini.utils.StringUtils;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.utils.UiUtils;
import com.jsz.peini.utils.pay.PayResult;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.Map;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaythebillActivity extends BaseActivity {
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    @InjectView(R.id.pay_checkBox)
    CheckBox payCheckBox;
    @InjectView(R.id.pay_edt)
    EditText payEdt;
    @InjectView(R.id.pay_hui_edt)
    EditText payHuiEdt;
    @InjectView(R.id.pay_ispreferential)
    LinearLayout payIspreferential;
    @InjectView(R.id.radiogroup_ItcSelect)
    RadioGroup mitcSelect;
    @InjectView(R.id.pay_jinbi)
    RadioButton payJinbi;
    @InjectView(R.id.pay_weixin)
    RadioButton payWeixin;
    @InjectView(R.id.pay_zhifubao)
    RadioButton payZhifubao;
    @InjectView(R.id.pay_preferentialText)
    TextView payPreferential;
    @InjectView(R.id.pay_confirm_payok)
    Button pay_confirm_payok;
    @InjectView(R.id.pay_zhifu_ok)
    LinearLayout pay_zhifu_ok;
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    private int pay = 0;
    private PaythebillActivity mActivity;
    public String mTaskid = "";
    public String mSellerInfoId;
    public String mStrPassword;
    public int mAppA;
    /*支付*/
    int g = 1000001;
    int p = 3000;
    int a = 0;
    public Intent mIntent;
    private String mPayNum;
    private String mPayHuiNum;

    @Override
    public int initLayoutId() {
        return R.layout.activity_paythebill;
    }

    @Override
    public void initView() {
        mActivity = this;
        mTitle.setText("优惠买单");
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        mTaskid = intent.getStringExtra("taskid");
        mSellerInfoId = intent.getStringExtra("sellerInfoId");
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
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
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(mActivity, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }

        ;
    };

    @Override
    public void initData() {
        //判断需不需要优惠信息
        payCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                payIspreferential.setVisibility(b ? View.VISIBLE : View.GONE);
            }
        });
        PayWay();


    }

    private void PayWay() {
        mitcSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.pay_jinbi:
                        LogUtil.d("金币支付");
                        pay = 1;
                        break;
                    case R.id.pay_weixin:
                        LogUtil.d("微信支付");
                        pay = 2;
                        break;
                    case R.id.pay_zhifubao:
                        LogUtil.d("支付宝支付");
                        pay = 3;
                        break;
                }
            }
        });
    }

    @OnClick({R.id.pay_confirm_payok, R.id.pay_preferential})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay_confirm_payok:
//                EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
                mPayNum = payEdt.getText().toString().trim();
                mPayHuiNum = payHuiEdt.getText().toString().trim();
                if (TextUtils.isEmpty(mPayNum)) {
                    ToastUtils.showToast(mActivity, "请输入金额!");
                    return;
                }
                if (pay == 0) {
                    ToastUtils.showToast(mActivity, "请选择支付方式");
                    return;
                }
                if (pay == 1) {
                    initPasswordView();
                    return;
                }
                PayData();
                break;
            case R.id.pay_preferential:
                ToastUtils.showToast(mActivity, "选择优惠券");
                mIntent = new Intent(this, CouponActivity.class);
                startActivity(mIntent);
                break;
        }
    }

    private void initPasswordView() {
        final Popwindou popwindou = new Popwindou(mActivity, UiUtils.inflate(mActivity, R.layout.activity_paythebill));
        View view = UiUtils.inflate(mActivity, R.layout.pop_passwordview);
        popwindou.init(view, Gravity.BOTTOM, true);
        final PasswordView passwordView = (PasswordView) view.findViewById(R.id.pay_passwordpop);
        passwordView.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish() {
                mStrPassword = passwordView.getStrPassword();
                ToastUtils.showToast(mActivity, mStrPassword);
                if (mStrPassword.equals("123456") && mStrPassword != "" && mStrPassword.length() == 6) {
                    PayData();
                    popwindou.dismiss();
                }
            }
        });
        passwordView.getCancelImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePassword(passwordView);
                popwindou.dismiss();
            }
        });
        view.findViewById(R.id.passwordview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePassword(passwordView);
                popwindou.dismiss();
            }
        });
    }

    /**
     * 删除输入的密码
     */
    private void deletePassword(PasswordView passwordView) {
        passwordView.currentIndex = -1;
        for (int i = 0; i < passwordView.tvList.length; i++) {
            passwordView.tvList[i].setText("");
        }
    }

    /**
     * 支付的接口的调用
     */
    private void PayData() {

        http:
//192.168.200.254:8280/pnservice/saveTaskOrder?
        // userId=9e0bae1b9d244bd7a25c609e2d218be1&
        // taskId=&
        // sellerInfoId=8&
        // totalMoney=0.01
        // &exclusiveMoney
        // &payType=2
        // &couponId
        RetrofitUtil.createService(PayService.class)
                .saveTaskOrder(
                        mUserToken,
                        mTaskid,
                        mSellerInfoId + "",
                        mPayNum,
                        mPayHuiNum,
                        pay + "",
                        "")
                .enqueue(new Callback<PaySuccessfulBean>() {
                    @Override
                    public void onResponse(Call<PaySuccessfulBean> call, Response<PaySuccessfulBean> response) {
                        LogUtil.d("mUserToken=" + mUserToken +
                                "mTaskid=" + mTaskid +
                                "mSellerInfoId=" + mSellerInfoId
                        );
                        if (response.isSuccessful()) {
                            PaySuccessfulBean body = response.body();
                            LogUtil.d("body.getResultDesc()+body.getOrderIdStr()" + body.getResultDesc() + body.getOrderIdStr());
                            ToastUtils.showToast(mActivity, body.getResultDesc());
                            if (body.getResultCode() == 1) {
                                LogUtil.d("订单提交成功" + body.toString());
                                if (pay == 1) {
                                    getJInBiOrder(body.getOrderIdStr());
                                } else if (pay == 2) {
                                    getWeiXinOrder(body.getOrderIdStr());
                                } else if (pay == 3) {
                                    getZhiFuBaoOrder(body.getOrderIdStr());
                                }
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
     * 金币预支付接口
     */
    private void getJInBiOrder(String orderIdStr) {
        mAppA = (int) SpUtils.get(mActivity, "x", 0);
        a = g ^ mAppA % p;
        int serverB = (int) SpUtils.get(mActivity, "serverB", 0);
        String Token = MD5Utils.encode((serverB ^ mAppA) + "");
        LogUtil.d(Token + "-----------" + mAppA + "-----------" + mUserToken + "-----------" + orderIdStr);
        RetrofitUtil.createService(PayService.class)
                .payGold(Token, a, mUserToken, orderIdStr)
                .enqueue(new Callback<PayJinBiOrderIdStrBean>() {
                    @Override
                    public void onResponse(Call<PayJinBiOrderIdStrBean> call, Response<PayJinBiOrderIdStrBean> response) {
                        if (response.isSuccessful()) {
                            PayJinBiOrderIdStrBean body = response.body();
                            if (body.getResultCode() == 1) {
                                LogUtil.d("金币预支付接口返回的数据" + body.toString());
                                String payId = body.getData().getPayId();
                                int getServerB = body.getData().getServerB();
                                PayJinBiData(payId, getServerB);
                            } else if (body.getResultCode() == 9) {
                                LoginDialogUtils.isNewLogin(mActivity);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PayJinBiOrderIdStrBean> call, Throwable t) {

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
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 金币支付
     *
     * @param payId
     * @param getServerB
     */
    private void PayJinBiData(String payId, int getServerB) {
        String Token = MD5Utils.encode((getServerB ^ mAppA) + "");
        int x = new Random().nextInt(2500) + 1;
        a = g ^ x % p;
        LogUtil.d("登录生成的随机数-----" + x + "登录传输的appA------" + a);
        SpUtils.put(mActivity, "x", x);
        LogUtil.d(Token + "------------" + a + "------------" + payId + "------------" + "000000");
        RetrofitUtil.createService(PayService.class)
                .payGoldPrePay(Token, a, payId, mStrPassword)
                .enqueue(new Callback<PayJinBiOrderIdStrBean>() {
                    @Override
                    public void onResponse(Call<PayJinBiOrderIdStrBean> call, Response<PayJinBiOrderIdStrBean> response) {
                        LogUtil.d("金币支付返回的数据---" + response.body().toString());
                        if (response.isSuccessful()) {
                            PayJinBiOrderIdStrBean body = response.body();
                            if (body.getResultCode() == 1) {
                                ToastUtils.showToast(mActivity, "支付成功");
                                SpUtils.put(mActivity, "serverB", body.getData().getServerB());
                            } else if (body.getResultCode() == 9) {
                                LoginDialogUtils.isNewLogin(mActivity);
                            } else {
                                ToastUtils.showToast(mActivity, body.getResultDesc());
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<PayJinBiOrderIdStrBean> call, Throwable t) {
                        ToastUtils.showToast(mActivity, t.getMessage());
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}


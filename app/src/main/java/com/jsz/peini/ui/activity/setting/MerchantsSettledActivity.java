package com.jsz.peini.ui.activity.setting;

import android.app.Dialog;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.CrtyBean;
import com.jsz.peini.model.setting.SuccessfulBean;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.presenter.setting.SettingService;
import com.jsz.peini.ui.adapter.setting.SettingRootListViewAdapter;
import com.jsz.peini.ui.adapter.setting.SettingSubListViewAdapter;
import com.jsz.peini.utils.LogUtil;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by th on 2016/12/10.
 */
public class MerchantsSettledActivity extends BaseActivity {
    @InjectView(R.id.merchantssettled_toolbar)
    Toolbar merchantssettled_toolbar;
    @InjectView(R.id.setting_address)
    TextView settingAddress;
    @InjectView(R.id.setting_regist_address)
    LinearLayout settingRegistAddress;//地址
    @InjectView(R.id.setting_selectaddress)
    LinearLayout settingSelectaddress; //地址
    @InjectView(R.id.setting_nomenclature)
    EditText settingNomenclature;
    @InjectView(R.id.setting_type) //类型
            TextView settingType;
    @InjectView(R.id.setting_regist_type)
    LinearLayout settingRegistType;
    @InjectView(R.id.setting_name)
    EditText settingName;
    @InjectView(R.id.setting_telephone)
    EditText settingTelephone;
    @InjectView(R.id.Seting_button)
    Button SetingButton;
    private ListView settingRootListview;
    private ListView settingSubListview;
    private List<CrtyBean.AreaCityBean> setiingCity;
    private Retrofit retrofit;
    private String cityNeme;
    private int provinceId;
    private int cityId;
    private LinearLayout settingPopupwindow;


    private View inflate;
    private Button choosePhoto;
    private Button takePhoto;
    private Button cancel;
    public MerchantsSettledActivity mActivity;
    /*---------------------------*/

    @Override
    public int initLayoutId() {
        return R.layout.activity_mechantssettled;
    }

    @Override
    public void initView() {
        mActivity = this;
        merchantssettled_toolbar.setTitle("");
        setSupportActionBar(merchantssettled_toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回????
        merchantssettled_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        /** 增加返回值为String的支持
         * 增加返回值为Gson的支持(以实体类返回)
         */
        retrofit = new Retrofit.Builder()
                .baseUrl(IpConfig.HttpPeiniIp)
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 发起网络请求
     */
    @Override
    public void initData() {
        super.initData();
        initCity();
    }

    /**
     * 访问城市
     */
    private void initCity() {
        SettingService settingService = retrofit.create(SettingService.class);
        Call<CrtyBean> settingSellerEnterCall = settingService.getCityList(mUserToken);
        settingSellerEnterCall.enqueue(new Callback<CrtyBean>() {
            @Override
            public void onResponse(Call<CrtyBean> call, Response<CrtyBean> response) {
                CrtyBean body = response.body();
                if (body.getResultCode() == 1) {
                    setiingCity = body.getAreaCity();
                }
            }

            @Override
            public void onFailure(Call<CrtyBean> call, Throwable t) {

            }
        });
    }

    @OnClick({R.id.setting_regist_address, R.id.setting_regist_type, R.id.Seting_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_regist_address:
                if (setiingCity.size() > 0) {
                    showPopwindow(setiingCity);
                }
                break;
            case R.id.setting_regist_type:
                show();
                break;
            case R.id.Seting_button:
                initNetWork(provinceId + "", cityId + "", settingNomenclature.getText().toString().trim(), tag, settingName.getText().toString().trim(), settingTelephone.getText().toString().trim());
                break;
        }
    }

    private void initNetWork(String cityNeme1, String cityNeme, String neme, String trim1, String s, String trim) {
        SettingService settingService = retrofit.create(SettingService.class);
        Call<SuccessfulBean> settingSellerEnterCall = settingService.sellerJoin(cityNeme, cityNeme1, neme, trim1, s, trim);
        settingSellerEnterCall.enqueue(new Callback<SuccessfulBean>() {
            @Override
            public void onResponse(Call<SuccessfulBean> call, Response<SuccessfulBean> response) {
                SuccessfulBean body = response.body();
                if (body.getResultCode() == 1) {
                    LogUtil.i("商家入驻", "" + body.toString());
                    finish();
                }
            }

            @Override
            public void onFailure(Call<SuccessfulBean> call, Throwable t) {

            }
        });
    }

    /**
     * 自定义控件  弹窗
     */
    Dialog dialog;
    String tag;

    public void show() {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        inflate = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        choosePhoto = (Button) inflate.findViewById(R.id.choosePhoto);
        takePhoto = (Button) inflate.findViewById(R.id.takePhoto);
        cancel = (Button) inflate.findViewById(R.id.btn_cancel);
        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.i("选择", "" + 1);
                settingType.setText("商户");
                dialog.dismiss();
                tag = "1";
            }
        });
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.i("选择", "" + 2);
                settingType.setText("个体");
                dialog.dismiss();
                tag = "2";
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.i("取消", "" + 0);
                settingType.setText("");
                dialog.dismiss();
            }
        });
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;
        lp.x = 0;
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    /**
     * setting_root_listview 左边
     * setting_sub_listview" 右边
     *
     * @param setiingCity
     */
    int selled = 0;

    private void showPopwindow(final List<CrtyBean.AreaCityBean> setiingCity) {
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        final View popView = View.inflate(this, R.layout.setting_crty, null);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        final PopupWindow popWindow = new PopupWindow(popView, width, height, true);
        //设置PopupWindow弹出窗体可点击
        popWindow.setTouchable(true);
        popWindow.setFocusable(true);
        //设置点击屏幕其它地方弹出框消失
        popWindow.setOutsideTouchable(true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setOutsideTouchable(true);
        popWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        settingRootListview = (ListView) popView.findViewById(R.id.setting_root_listview);
        settingSubListview = (ListView) popView.findViewById(R.id.setting_sub_listview);
        settingPopupwindow = (LinearLayout) popView.findViewById(R.id.setting_popupwindow);
        /**适配器*/
        SettingRootListViewAdapter rootListViewAdapter = new SettingRootListViewAdapter(mActivity,setiingCity);
        settingRootListview.setAdapter(rootListViewAdapter);
        SettingSubListViewAdapter subListViewAdapter = new SettingSubListViewAdapter(mActivity, setiingCity.get(selled).getProvinceObject(), selled);
        settingSubListview.setAdapter(subListViewAdapter);
        /**点击事件*/
        settingPopupwindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow.dismiss();
            }
        });
        settingRootListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {//左边
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SettingSubListViewAdapter subListViewAdapter = new SettingSubListViewAdapter(mActivity,setiingCity.get(i).getProvinceObject(), i);
                settingSubListview.setAdapter(subListViewAdapter);
                selled = i;
            }
        });
        settingSubListview.setOnItemClickListener(new AdapterView.OnItemClickListener() { //右边
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                popWindow.dismiss();
                cityNeme = setiingCity.get(selled).getProvinceName() + " " + setiingCity.get(selled).getProvinceObject().get(i).getCityName();
                settingAddress.setText(cityNeme);
                provinceId = setiingCity.get(selled).getProvinceId();
                cityId = setiingCity.get(selled).getProvinceObject().get(i).getCityId();
            }
        });

    }

}

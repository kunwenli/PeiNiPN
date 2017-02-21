package com.jsz.peini.ui.activity.square;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.square.UserInfoCodesBean;
import com.jsz.peini.model.square.UserInfoIdBean;
import com.jsz.peini.presenter.square.SquareService;
import com.jsz.peini.ui.view.Popwindou;
import com.jsz.peini.ui.view.square.MessageProgressBar;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.LoginDialogUtils;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.StringUtils;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.utils.UiUtils;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jsz.peini.R.id.emotionText;
import static com.jsz.peini.R.id.nickname;
import static com.jsz.peini.R.id.nowProvinceTextnowCityTextnowCountyText;

/**
 * Created by th on 2017/1/4.
 */
public class TaSquareMessageActivity extends BaseActivity {
    @InjectView(R.id.square_message_ScrollView1)
    ScrollView mSquareMessageScrollView1;
    @InjectView(R.id.selfNum)
    MessageProgressBar mSelfNum;
    @InjectView(R.id.nickname)
    TextView mNickname;
    @InjectView(R.id.kuntoolbar)
    RelativeLayout mKuntoolbar;
    @InjectView(R.id.sexText)
    TextView mSexText;
    @InjectView(R.id.ageText)
    TextView mAge;
    @InjectView(R.id.heightText)
    TextView mHeight;
    @InjectView(R.id.weightText)
    TextView mWeight;
    @InjectView(R.id.constellationText)
    TextView mConstellationText;
    @InjectView(R.id.nationText)
    TextView mNationText;
    @InjectView(emotionText)
    TextView mEmotionText;
    @InjectView(R.id.bigIncomesmallIncomeText)
    TextView mBigIncomesmallIncome;
    @InjectView(R.id.degreeText)
    TextView mDegreeText;
    @InjectView(R.id.industryText)
    TextView mIndustryText;
    @InjectView(R.id.isHouseText)
    TextView mIsHouseText;
    @InjectView(R.id.isCarText)
    TextView mIsCarText;
    @InjectView(nowProvinceTextnowCityTextnowCountyText)
    TextView mNowProvinceTextnowCityTextnowCountyText;
    @InjectView(R.id.oldProvinceTextoldCityTextoldCountyText)
    TextView mOldProvinceTextoldCityTextoldCountyText;
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.right_button)
    TextView mRightButton;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    private TaSquareMessageActivity mActivity;
    public UserInfoCodesBean mResponse;
    public Popwindou mPop;
    public WheelView mWheelView, mWheelViewMain, mWheelViewSub;
    public List<UserInfoCodesBean.EmotionListBean> mEmotionList;
    public int mSign;
    public List<UserInfoCodesBean.NationListBean> mNationList;
    public List<UserInfoCodesBean.CarListBean> mCarList;
    public List<UserInfoCodesBean.ConstellationListBean> mConstellationList;
    public List<UserInfoCodesBean.DegreeListBean> mDegreeList;
    public List<UserInfoCodesBean.IndustryListBean> mIndustryList;
    public List<UserInfoCodesBean.HouseListBean> mHouseList;
    public String mO;
    public List<UserInfoCodesBean.AreaCityBean> mAreaCity;
    private int mSignAddress;
    public List<UserInfoCodesBean.AreaCityBean.ProvinceObjectBean> mProvinceObject;
    public UserInfoCodesBean.AreaCityBean mAreaCityBean;
    public int mPosition;
    public String provinceName;
    public String cityName;
    public int mProvinceId;
    public int mCityId;
    public String mSmall_income;
    public String mBig_income;
    public int mEmotionListNum;
    public int mNationListNum;
    public int mCarListNum;
    public int mmConstellationListNum;
    public int mDegreeListNum;
    public int mIndustryListNum;
    public int mHouseListNum;
    public String mWide;
    public String mMHighNum;
    private Intent mIntent;

    @Override
    public int initLayoutId() {
        return R.layout.activity_tasquare_message;
    }

    @Override
    public void initView() {
        super.initView();
        mActivity = this;
        ShowWindows(true);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRightButton.setText("完成");
        mToolbar.setBackgroundResource(R.color.RED_FB4E30);
        mKuntoolbar.setBackgroundResource(R.color.RED_FB4E30);
        mTitle.setTextColor(getResources().getColor(R.color.white000));
        mRightButton.setTextColor(getResources().getColor(R.color.white000));
        inItNetWork();
    }

    /**
     * 获取网络信息数据
     */
    private void inItNetWork() {
        mDialog.setText("正在加载");
        mDialog.show();
        RetrofitUtil.createService(SquareService.class)
                .getUserInfo(mUserToken)
                .enqueue(new Callback<UserInfoIdBean>() {
                    @Override
                    public void onResponse(Call<UserInfoIdBean> call, final Response<UserInfoIdBean> response) {
                        if (response.isSuccessful()) {
                            LogUtil.d("我的资料== " + response.body().toString());
                            int resultCode = response.body().getResultCode();
                            if (resultCode == 1) {
                                mActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateUi(response.body().getUserInfo());
                                    }
                                });
                                mDialog.dismiss();
                                ToastUtils.showToast(mActivity, response.body().getResultDesc());
                            } else if (resultCode == 9) {
                                ToastUtils.showToast(mActivity, response.body().getResultDesc());
                                LoginDialogUtils.isNewLogin(mActivity);
                            } else {
                                ToastUtils.showToast(mActivity, response.body().getResultDesc());
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<UserInfoIdBean> call, Throwable t) {
                        ToastUtils.showToast(mActivity, t.getMessage());

                    }
                });
        RetrofitUtil.createService(SquareService.class)
                .getUserInfoCodes()
                .enqueue(new Callback<UserInfoCodesBean>() {
                    @Override
                    public void onResponse(Call<UserInfoCodesBean> call, Response<UserInfoCodesBean> response) {
                        if (response.isSuccessful()) {
                            int resultCode = response.body().getResultCode();
                            if (resultCode == 1) {
                                ToastUtils.showToast(mActivity, response.body().getResultDesc());
                                UserInfoCodesBean body = response.body();
                                if (body != null) {
                                    mResponse = body;
                                }
                                mDialog.dismiss();
                            } else if (resultCode == 9) {
                                ToastUtils.showToast(mActivity, response.body().getResultDesc());
                                LoginDialogUtils.isNewLogin(mActivity);
                            } else {
                                ToastUtils.showToast(mActivity, response.body().getResultDesc());
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<UserInfoCodesBean> call, Throwable t) {
                        ToastUtils.showToast(mActivity, t.getMessage());

                    }
                });
    }

    /**
     * 他人信息信息的填充
     */
    private void updateUi(UserInfoIdBean.UserInfoBean userInfo) {
        if (userInfo == null) {
            LogUtil.d(getLocalClassName(), "他人资料信息为空" + userInfo.toString());
            return;
        }
        LogUtil.d(getLocalClassName(), "" + userInfo.toString()); //他人资料数据

        String nickname = userInfo.getNickname(); //用户名字
        if (StringUtils.isNoNull(nickname)) {
            mNickname.setText(nickname);
            mTitle.setText(nickname);
        }
        String sexText = userInfo.getSexText(); //性别
        if (StringUtils.isNoNull(sexText)) {
            mSexText.setText(sexText);
        }
        int sex = userInfo.getSex();
        mSexText.setText(sex == 1 ? "男" : "女");

        String age = userInfo.getAge() + ""; //年龄
        if (StringUtils.isNoNull(age)) {
            mAge.setText(age);
        }
        String nowProvinceText = userInfo.getNowProvinceText(); //地址
        String nowCityText = userInfo.getNowCityText();
        String nowCountyText = (String) userInfo.getNowCountyText();
        if (StringUtils.isNoNull(nowProvinceText)) {
            mNowProvinceTextnowCityTextnowCountyText.setText(nowProvinceText);
        }
        if (StringUtils.isNoNull(nowProvinceText) && StringUtils.isNoNull(nowCityText)) {
            mNowProvinceTextnowCityTextnowCountyText.setText(nowProvinceText + nowCityText);
        }
        if (StringUtils.isNoNull(nowProvinceText) && StringUtils.isNoNull(nowCityText) && StringUtils.isNoNull(nowCountyText)) {
            mNowProvinceTextnowCityTextnowCountyText.setText(nowProvinceText + nowCityText + nowCountyText);
        }

        String oldProvinceText = (String) userInfo.getOldProvinceText(); //故乡
        String oldCityText = (String) userInfo.getOldCityText();
        String oldCountyText = (String) userInfo.getOldCountyText();
        if (StringUtils.isNoNull(oldProvinceText)) {
            mNowProvinceTextnowCityTextnowCountyText.setText(oldProvinceText);
        }
        if (StringUtils.isNoNull(oldProvinceText) && StringUtils.isNoNull(oldCityText)) {
            mNowProvinceTextnowCityTextnowCountyText.setText(oldProvinceText + oldCityText);
        }
        if (StringUtils.isNoNull(oldProvinceText) && StringUtils.isNoNull(oldCityText) && StringUtils.isNoNull(oldCountyText)) {
            mNowProvinceTextnowCityTextnowCountyText.setText(oldProvinceText + oldCityText + oldCountyText);
        }

        String nationText = userInfo.getNationText();//汉族
        if (StringUtils.isNoNull(nationText)) {
            mNationText.setText(nationText);
        }
        String constellationText = userInfo.getConstellationText(); //星座
        if (StringUtils.isNoNull(constellationText)) {
            mConstellationText.setText(constellationText);
        }
        String degreeText = userInfo.getDegreeText();//小学
        if (StringUtils.isNoNull(degreeText)) {
            mDegreeText.setText(degreeText);
        }

        String industryText = userInfo.getIndustryText(); //行业
        if (StringUtils.isNoNull(industryText)) {
            mIndustryText.setText(industryText);
        }

        String isCarText = userInfo.getIsCarText(); //购车
        if (StringUtils.isNoNull(isCarText)) {
            mIsCarText.setText(isCarText);
        }
        String isHouseText = (String) userInfo.getIsHouseText();//购房
        if (StringUtils.isNoNull(isHouseText)) {
            mIsHouseText.setText(isHouseText);
        }
        String emotionText = userInfo.getEmotionText();//单身
        if (StringUtils.isNoNull(emotionText)) {
            mEmotionText.setText(emotionText);
        }

        String bigIncome = (String) userInfo.getBigIncome();//收入高
        String smallIncome = userInfo.getSmallIncome() + ""; //收入
        if (StringUtils.isNoNull(bigIncome)) {
            mBigIncomesmallIncome.setText(bigIncome);
        }
        if (StringUtils.isNoNull(smallIncome)) {
            mBigIncomesmallIncome.setText(smallIncome);
        }
        if (StringUtils.isNoNull(smallIncome) && StringUtils.isNoNull(bigIncome)) {
            mBigIncomesmallIncome.setText(smallIncome + "-" + bigIncome);
        }
        int selfNum = userInfo.getSelfNum(); //资料完整度
        if (selfNum != 0) {
            mSelfNum.setProgress(selfNum);
        }
        String height = userInfo.getHeight() + ""; //身高
        if (StringUtils.isNoNull(height)) {
            mHeight.setText(height);
        }
        String weight = userInfo.getWeight() + "";//体重
        if (StringUtils.isNoNull(weight)) {
            mWeight.setText(weight);
        }
    }

    /**
     * 我的空间点击事件
     */
    @OnClick({R.id.emotion, R.id.nation, R.id.height, R.id.weight, R.id.oldProvinceTextoldCityTextoldCounty, R.id.bigIncomesmallIncome, R.id.nowProvinceTextnowCityTextnowCounty, R.id.isHouse, R.id.nickActivity, R.id.industry, R.id.degree, R.id.constellation, R.id.isCar, R.id.right_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.emotion://情感
                mSign = 1;
                mEmotionList = mResponse.getEmotionList();
                PopWindowsWheelView(mEmotionText);
                break;
            case R.id.nation://民族
                mSign = 2;
                mNationList = mResponse.getNationList();
                PopWindowsWheelView(mNationText);
                break;
            case R.id.height://身高
                mSign = 3;
                PopWindowsWheelView(mHeight);
                break;
            case R.id.weight://体重
                mSign = 9;
                PopWindowsWheelView(mWeight);
                break;
            case R.id.isCar://购车
                mSign = 4;
                mCarList = mResponse.getCarList();
                PopWindowsWheelView(mIsCarText);
                break;
            case R.id.constellation://星座
                mSign = 5;
                mConstellationList = mResponse.getConstellationList();
                PopWindowsWheelView(mConstellationText);
                break;
            case R.id.degree://学历
                mSign = 6;
                mDegreeList = mResponse.getDegreeList();
                PopWindowsWheelView(mDegreeText);
                break;
            case R.id.industry://行业
                mSign = 7;
                mIndustryList = mResponse.getIndustryList();
                PopWindowsWheelView(mIndustryText);
                break;
            case R.id.isHouse://房子
                mSign = 8;
                mHouseList = mResponse.getHouseList();
                PopWindowsWheelView(mIsHouseText);
                break;
            case R.id.nowProvinceTextnowCityTextnowCounty://提交
                ToastUtils.showToast(mActivity, "地址选择!");
                mSignAddress = 1;
                mAreaCity = mResponse.getAreaCity();
                PopWindowsWheelViewTow(mNowProvinceTextnowCityTextnowCountyText);
                break;
            case R.id.oldProvinceTextoldCityTextoldCounty://提交
                ToastUtils.showToast(mActivity, "地址选择!");
                mSignAddress = 1;
                mAreaCity = mResponse.getAreaCity();
                PopWindowsWheelViewTow(mNowProvinceTextnowCityTextnowCountyText);
                break;
            case R.id.bigIncomesmallIncome://月收入
                ToastUtils.showToast(mActivity, "收入选择选择!");
                mSignAddress = 2;
                PopWindowsWheelViewTow(mBigIncomesmallIncome);
                break;
            case R.id.nickActivity://昵称
                mIntent = new Intent(mActivity, NickNameActivity.class);
                mIntent.putExtra("type", "2");
                mIntent.putExtra("title", "修改昵称");
                startActivityForResult(mIntent, 1);
                break;
            case R.id.right_button://提交
                ToastUtils.showToast(mActivity, "提交按钮!");
                SubmitMiData();
                break;
        }
    }

    private void SubmitMiData() {
        RetrofitUtil.createService(SquareService.class)
                .updateUserInfo(
                        mUserToken,
                        mNickname.getText().toString().trim(),
                        "",
                        mMHighNum,
                        mWide,
                        mProvinceId + "",
                        mCityId + "",
                        "", "", "",
                        mmConstellationListNum + "",
                        mNationListNum + "",
                        mEmotionListNum + "",
                        mSmall_income,
                        mBig_income,
                        mDegreeListNum + "",
                        mIndustryListNum + "",
                        mHouseListNum + "",
                        mCarListNum + ""
                ).enqueue(new Callback<UserInfoCodesBean>() {
            @Override
            public void onResponse(Call<UserInfoCodesBean> call, Response<UserInfoCodesBean> response) {
                if (response.isSuccessful()) {
                    int resultCode = response.body().getResultCode();
                    UserInfoCodesBean body = response.body();
                    ToastUtils.showToast(mActivity, body.getResultDesc());
                    if (resultCode == 1) {

                    } else if (resultCode == 9) {
                        LoginDialogUtils.isNewLogin(mActivity);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserInfoCodesBean> call, Throwable t) {
                ToastUtils.showToast(mActivity, t.getMessage());
            }
        });
    }

    /**
     * 两级列表联动
     *
     * @param textView
     */
    private void PopWindowsWheelViewTow(final TextView textView) {
        mPop = new Popwindou(mActivity, UiUtils.inflate(mActivity, R.layout.activity_tasquare_message));
        View viewTow = UiUtils.inflate(mActivity, R.layout.pop_tow_selector);
        mPop.init(viewTow, Gravity.BOTTOM, true);
        viewTow.findViewById(R.id.cancel_selector).setOnClickListener(new View.OnClickListener() { //取消
            @Override
            public void onClick(View view) {
                mPop.dismiss();
            }
        });
        viewTow.findViewById(R.id.ok_selector).setOnClickListener(new View.OnClickListener() { //确定
            @Override
            public void onClick(View view) {

                if (mSignAddress == 2) {
                    mSmall_income = provinceName;
                    mBig_income = cityName;
                    textView.setText(provinceName + "-" + cityName);
                } else {
                    textView.setText(provinceName + " " + cityName);
                }
                mPop.dismiss();
            }
        });
        mWheelViewMain = (WheelView) viewTow.findViewById(R.id.main_wheelview);
        mWheelViewSub = (WheelView) viewTow.findViewById(R.id.sub_wheelview);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.holoBorderColor = getResources().getColor(R.color.backgroundf1f1f1);
        style.selectedTextSize = 20;
        style.textSize = 18;
        style.textAlpha = 0.5f;
        style.selectedTextZoom = 5;
        /**第一级*/
        mWheelViewMain.setWheelAdapter(new ArrayWheelAdapter(this));
        mWheelViewMain.setSkin(WheelView.Skin.Holo);
        mWheelViewMain.setWheelSize(5);
        mWheelViewMain.setWheelData(addressMainDatas());
        mWheelViewMain.setStyle(style);
        mWheelViewMain.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                mPosition = position;
                if (mSignAddress == 2) {
                    if (position == 0) {
                        provinceName = "1000";
                    } else {
                        provinceName = (position + 1) + "000";
                    }
                    ToastUtils.showToast(mActivity, provinceName);
                } else {
                    provinceName = (String) o;
                    mProvinceId = mAreaCity.get(position).getProvinceId();
                    ToastUtils.showToast(mActivity, provinceName + mProvinceId);
                }

            }
        });

        /**第二级*/
        mWheelViewSub.setWheelAdapter(new ArrayWheelAdapter(this));
        mWheelViewSub.setSkin(WheelView.Skin.Holo);
        mWheelViewSub.setWheelSize(5);
        mWheelViewSub.setStyle(style);
        if (mSignAddress == 2) {
            mWheelViewSub.setWheelData(addressMainDatas());
        } else {
            mWheelViewSub.setWheelData(createSubDatas().get(addressMainDatas().get(mWheelViewMain.getSelection())));
            mWheelViewMain.join(mWheelViewSub);
            mWheelViewMain.joinDatas(createSubDatas());
        }
        mWheelViewSub.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {

                if (mSignAddress == 2) {
                    if (position == 0) {
                        cityName = "1000";
                    } else {
                        cityName = (position + 1) + "000";
                    }
                    ToastUtils.showToast(mActivity, cityName);
                } else {
                    cityName = (String) o;
                    mCityId = mAreaCity.get(mPosition).getProvinceObject().get(position).getCityId();
                    ToastUtils.showToast(mActivity, cityName + mCityId);
                }
            }
        });

    }


    /**
     * 单个列表联动
     */
    private void PopWindowsWheelView(final TextView emotionText) {
        mPop = new Popwindou(mActivity, UiUtils.inflate(mActivity, R.layout.activity_tasquare_message));
        View view = UiUtils.inflate(mActivity, R.layout.pop_one_selector);
        mPop.init(view, Gravity.BOTTOM, true);
        view.findViewById(R.id.cancel_selector).setOnClickListener(new View.OnClickListener() { //取消
            @Override
            public void onClick(View view) {
                mPop.dismiss();
            }
        });
        view.findViewById(R.id.ok_selector).setOnClickListener(new View.OnClickListener() { //确定
            @Override
            public void onClick(View view) {
                emotionText.setText(mO);
                mPop.dismiss();
            }
        });
        mWheelView = (WheelView) view.findViewById(R.id.one_wheelview);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.holoBorderColor = getResources().getColor(R.color.backgroundf1f1f1);
        style.selectedTextSize = 20;
        style.textSize = 18;
        style.textAlpha = 0.5f;
        style.selectedTextZoom = 5;
        mWheelView.setWheelAdapter(new ArrayWheelAdapter(this));
        mWheelView.setSkin(WheelView.Skin.Holo);
        mWheelView.setWheelSize(5);
        mWheelView.setWheelData(createMainDatas());
        mWheelView.setStyle(style);
        mWheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                switch (mSign) {
                    case 1:
                        mEmotionListNum = mEmotionList.get(position).getCodesNum();
                        ToastUtils.showToast(mActivity, mEmotionListNum + "-----" + o);
                        mO = (String) o;
                        break;
                    case 2:
                        mNationListNum = mNationList.get(position).getCodesNum();
                        ToastUtils.showToast(mActivity, mNationListNum + "-----" + o);
                        mO = (String) o;
                        break;
                    case 3:
                        mMHighNum = position + 100 + "";
                        ToastUtils.showToast(mActivity, mMHighNum);
                        mO = (String) o;
                        break;
                    case 4:
                        mCarListNum = mCarList.get(position).getCodesNum();
                        ToastUtils.showToast(mActivity, mCarListNum + "-----" + o);
                        mO = (String) o;
                        break;
                    case 5:
                        mmConstellationListNum = mConstellationList.get(position).getCodesNum();
                        ToastUtils.showToast(mActivity, mmConstellationListNum + "-----" + o);
                        mO = (String) o;
                        break;
                    case 6:
                        mDegreeListNum = mDegreeList.get(position).getCodesNum();
                        ToastUtils.showToast(mActivity, mDegreeListNum + "-----" + o);
                        mO = (String) o;
                        break;
                    case 7:
                        mIndustryListNum = mIndustryList.get(position).getCodesNum();
                        ToastUtils.showToast(mActivity, mIndustryListNum + "-----" + o);
                        mO = (String) o;
                        break;
                    case 8:
                        mHouseListNum = mHouseList.get(position).getCodesNum();
                        ToastUtils.showToast(mActivity, mHouseListNum + "-----" + o);
                        mO = (String) o;
                        break;
                    case 9:
                        mWide = (position + 30) + "";
                        ToastUtils.showToast(mActivity, mWide + "--" + o);
                        mO = (String) o;
                        break;

                }
            }
        });
    }


    List<String> mStringList;

    private List createMainDatas() {
        mStringList = new ArrayList<>();
        mStringList.clear();
        switch (mSign) {
            case 1:
                for (int i = 0; i < mEmotionList.size(); i++) {
                    mStringList.add(mEmotionList.get(i).getCodesName());
                }
                break;
            case 2:
                for (int i = 0; i < mNationList.size(); i++) {
                    mStringList.add(mNationList.get(i).getCodesName());
                }
                break;
            case 3:
                for (int i = 100; i < 190; i++) {
                    mStringList.add(i + "cm");
                }
                break;
            case 4:
                for (int i = 0; i < mCarList.size(); i++) {
                    mStringList.add(mCarList.get(i).getCodesName());
                }
                break;
            case 5:
                for (int i = 0; i < mConstellationList.size(); i++) {
                    mStringList.add(mConstellationList.get(i).getCodesName());
                }
                break;
            case 6:
                for (int i = 0; i < mDegreeList.size(); i++) {
                    mStringList.add(mDegreeList.get(i).getCodesName());
                }
                break;
            case 7:
                for (int i = 0; i < mIndustryList.size(); i++) {
                    mStringList.add(mIndustryList.get(i).getCodesName());
                }
                break;
            case 8:
                for (int i = 0; i < mHouseList.size(); i++) {
                    mStringList.add(mHouseList.get(i).getCodesName());
                }
                break;
            case 9:
                for (int i = 30; i <= 1400; i++) {
                    mStringList.add(i + "kg");
                }
                break;
        }
        if (mStringList.size() > 0) {
            return mStringList;
        } else {
            return null;
        }
    }

    /**
     * 地址的第一集
     */
    private List addressMainDatas() {
        mStringList = new ArrayList<>();
        mStringList.clear();
        int iii = 30;
        switch (mSignAddress) {
            case 1:
                for (int i = 0; i < mAreaCity.size(); i++) {
                    mStringList.add(mAreaCity.get(i).getProvinceName());
                }
                break;
            case 2:
                for (int i = 1; i <= iii; i++) {
                    if (i == 30) {
                        mStringList.add(i + "000元以上");
                    } else if (i == 1) {
                        mStringList.add("2000元以下");
                    } else {
                        mStringList.add(i + "000元");
                    }
                }
                break;
        }
        if (mStringList.size() > 0) {
            return mStringList;
        } else {
            return null;
        }
    }

    /**
     * 地址联动第二级
     */

    private HashMap<String, List<String>> createSubDatas() {
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        String[] strings = new String[mAreaCity.size()];
        String[][] ss = new String[mAreaCity.size()][];
        for (int i = 0; i < mAreaCity.size(); i++) {
            mAreaCityBean = mAreaCity.get(i);
            strings[i] = (mAreaCityBean.getProvinceName());
            ss[i] = new String[mAreaCityBean.getProvinceObject().size()];
            mProvinceObject = mAreaCityBean.getProvinceObject();
            for (int j = 0; j < mProvinceObject.size(); j++) {
                ss[i][j] = mProvinceObject.get(j).getCityName();
            }
        }
        for (int i = 0; i < strings.length; i++) {
            map.put(strings[i], Arrays.asList(ss[i]));
        }
        return map;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 0 && data != null) {
            String name = data.getStringExtra("name");
            if (StringUtils.isNoNull(name)) {
                ToastUtils.showToast(mActivity, name);
                mNickname.setText(name);
                mTitle.setText(name);
            }
        }
    }

}

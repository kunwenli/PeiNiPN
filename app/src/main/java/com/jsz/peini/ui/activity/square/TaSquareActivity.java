package com.jsz.peini.ui.activity.square;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.setting.SuccessfulBean;
import com.jsz.peini.model.square.UserInfoByOtherId;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.presenter.square.SquareRetrofitHttp;
import com.jsz.peini.presenter.square.SquareService;
import com.jsz.peini.san.huanxin.HuanxinHeadBean;
import com.jsz.peini.ui.adapter.square.MiImageListAdapter;
import com.jsz.peini.ui.adapter.square.TaImageListAdapter;
import com.jsz.peini.ui.view.Popwindou;
import com.jsz.peini.ui.view.TextProgressBar;
import com.jsz.peini.utils.Bitmap.GlideImgManager;
import com.jsz.peini.utils.Conversion;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.LoginDialogUtils;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.utils.UiUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jsz.peini.R.id.credit;
import static com.jsz.peini.R.id.square_more;

public class TaSquareActivity extends BaseActivity {

    @InjectView(R.id.square_bj)
    ImageView mSquareBj;
    @InjectView(R.id.imageHead)
    CircleImageView mImageHead;
    @InjectView(R.id.sex)
    ImageView mSex;
    @InjectView(R.id.frameLayout)
    FrameLayout mFrameLayout;
    @InjectView(R.id.isConcern)
    RadioButton mIsConcern;
    @InjectView(R.id.nickname)
    TextView mNickname;
    @InjectView(R.id.signWord)
    TextView mSignWord;
    @InjectView(R.id.agenowProvinceTextnowCityText)
    TextView mAgenowProvinceTextnowCityText;
    @InjectView(R.id.myConcernmyFans)
    TextView mMyConcernmyFans;
    @InjectView(R.id.imageList)
    RecyclerView mImageList;
    @InjectView(R.id.lableList)
    TagFlowLayout mLableList;
    @InjectView(R.id.userLoginId)
    TextView mUserLoginId;
    @InjectView(R.id.goldList)
    ImageView mGoldList;
    @InjectView(R.id.buyList)
    ImageView mBuyList;
    @InjectView(R.id.integrityList)
    ImageView mIntegrityList;
    @InjectView(R.id.credittext)
    TextView mCredittext;
    @InjectView(credit)
    TextProgressBar mCredit;
    @InjectView(R.id.sellerBigType)
    ImageView mSellerBigType;
    @InjectView(R.id.taskName)
    TextView mTaskName;
    @InjectView(R.id.taskScore)
    TextView mTaskScore;
    @InjectView(R.id.isIdcard)
    ImageView mIsIdcard;
    @InjectView(R.id.isPhone)
    ImageView mIsPhone;
    @InjectView(R.id.content)
    TextView mContent;
    @InjectView(R.id.squareLastInfo)
    ImageView mSquareLastInfo;
    @InjectView(R.id.square_toolbar)
    LinearLayout mSquareToolbar;
    @InjectView(R.id.square_more)
    ImageView mSquareMore;
    private TaSquareActivity mActivity;
    private Popwindou mPopwindou;
    private TagAdapter<UserInfoByOtherId.LableListBean> mLableListBeanTagAdapter;
    private boolean mIsConcernSelector;
    /**
     * 他人空间 id
     */
    private String mOtherId;
    public UserInfoByOtherId mUserAllInfo;

    @Override
    public int initLayoutId() {
        return R.layout.activity_ta_square;
    }

    @Override
    public void initView() {
        super.initView();
        mActivity = this;
        ShowWindows(true);

        mSquareToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        mOtherId = intent.getStringExtra("userId");
        LogUtil.d(getLocalClassName(), "他人空间ID---> " + mOtherId + "");
    }

    @Override
    public void initData() {
        initNetWork();
    }

    /**
     * 网络访问
     */
    private void initNetWork() {
        mDialog.setText("正在加载");
        mDialog.show();
        RetrofitUtil.createService(SquareService.class)
                .getUserInfoByOtherId(mOtherId, mUserToken)
                .enqueue(new Callback<UserInfoByOtherId>() {
                    @Override
                    public void onResponse(Call<UserInfoByOtherId> call, final Response<UserInfoByOtherId> response) {
                        if (response.isSuccessful()) {
                            int resultCode = response.body().getResultCode();
                            ToastUtils.showToast(mActivity, response.body().getResultDesc());
                            if (resultCode == 1) {
                                UserInfoByOtherId body = response.body();
                                Message message = new Message();
                                message.what = Conversion.DATASUCCESS;
                                message.obj = body;
                                mHandler.sendMessage(message);
                            } else if (resultCode == 9) {
                                LoginDialogUtils.isNewLogin(mActivity);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserInfoByOtherId> call, Throwable t) {
                        ToastUtils.showToast(mActivity, t.getMessage());
                    }
                });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Conversion.DATASUCCESS:
                    UserInfoByOtherId headBean = (UserInfoByOtherId) msg.obj;
                    mDialog.dismiss();
                    initShowData(headBean);
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 展示数据
     *
     * @param userAllInfo
     */
    private void initShowData(final UserInfoByOtherId userAllInfo) {
        UserInfoByOtherId.OtherInfoBean otherInfo = userAllInfo.getOtherInfo();
        int credit = otherInfo.getCredit();
        mCredit.setProgress(credit);
        mCredittext.setText(otherInfo.getCredit() > 80 ? "信誉值 - 高" : "信誉值 - 低");
        mMyConcernmyFans.setText("粉丝" + otherInfo.getMyConcern() + " | " + "关注" + otherInfo.getMyFans());
        /**关注的点击事件*/
        mIsConcernSelector = otherInfo.getIsConcern() == 1 ? true : false;
        mIsConcern.setChecked(mIsConcernSelector);
        mIsConcern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsConcernSelector = !mIsConcernSelector;
                mIsConcern.setChecked(mIsConcernSelector);
                if (mIsConcernSelector) {
                    LogUtil.d(getLocalClassName(), "关注" + mIsConcernSelector);
                    ConcernSuccess();
                } else {
                    LogUtil.d(getLocalClassName(), "取消" + mIsConcernSelector);
                    delConcernSuccess();
                }
            }
        });
        UserInfoByOtherId.UserInfoBean infoBean = userAllInfo.getUserInfo();
        mUserLoginId.setText(infoBean.getUserLoginId() + "");
        mAgenowProvinceTextnowCityText.setText(infoBean.getAge() + "岁 - " + infoBean.getNowProvinceText() + " " + infoBean.getNowCityText());
        mSex.setImageResource(infoBean.getSex() == 1 ? R.mipmap.sqnan : R.mipmap.sqnv);
        //头像  和  背景
        String imageHead = infoBean.getImageHead();
        GlideImgManager.loadImage(mActivity, IpConfig.HttpPic + imageHead, mImageHead);
        //背景图片
        GlideImgManager.loadImage(mActivity, IpConfig.HttpPic + imageHead, mSquareBj);
        /**他人空间信息*/
//        mImageHead.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(mActivity, TaSquareMessageActivity.class));
//            }
//        });
        mNickname.setText(infoBean.getNickname());
        mSignWord.setText(infoBean.getSignWord());
        mGoldList.setVisibility(infoBean.getGoldList() == 1 ? View.VISIBLE : View.GONE);
        mBuyList.setVisibility(infoBean.getBuyList() == 1 ? View.VISIBLE : View.GONE);
        mIntegrityList.setVisibility(infoBean.getIntegrityList() == 1 ? View.VISIBLE : View.GONE);
         /*-------------相册----------------*/
        List<UserInfoByOtherId.ImageListBean> imageList = userAllInfo.getImageList();
        LinearLayoutManager layout = new LinearLayoutManager(mActivity);
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        mImageList.setLayoutManager(layout);
        TaImageListAdapter adapter = new TaImageListAdapter(mActivity, imageList);
        adapter.addFootView(UiUtils.inflate(mActivity, R.layout.mi_release_photo_foot));
        mImageList.setAdapter(adapter);
        adapter.setOnClicPhotokListener(new MiImageListAdapter.OnClicPhotokListener() {
            @Override
            public void mPhotoItemkListener(int position) {
                ToastUtils.showToast(mActivity, "进入他人相册列表");
                startActivity(new Intent(mActivity, TaPhotoImageActivity.class));
            }

            @Override
            public void FootViewItemkListener(int position) {
                ToastUtils.showToast(mActivity, "无图片");
                startActivity(new Intent(mActivity, TaPhotoImageActivity.class));
            }
        });
         /*-------------认证----------------*/
        mIsIdcard.setImageResource(infoBean.getIsIdcard() == 1 ? R.mipmap.idcard2 : R.mipmap.idcard1);
        mIsPhone.setImageResource(infoBean.getIsPhone() == 1 ? R.mipmap.phone2 : R.mipmap.phone1);
        /*-============广场===============*/
        String Content = userAllInfo.getSquareLastInfo().getContent() + "";
        mContent.setText(Content);
        /*----========---底部-------------------------*/
        String string = IpConfig.HttpPic + userAllInfo.getSquareLastInfo().getImageSrc();
        GlideImgManager.loadImage(mActivity, string, mSquareLastInfo);
        /*-------------标签----------------*/
        List<UserInfoByOtherId.LableListBean> list = userAllInfo.getLableList();
        final LayoutInflater mInflater = LayoutInflater.from(this);
        mLableList.setAdapter(mLableListBeanTagAdapter = new TagAdapter<UserInfoByOtherId.LableListBean>(list) {
            @Override
            public View getView(FlowLayout parent, int position, UserInfoByOtherId.LableListBean s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv2,
                        mLableList, false);
                tv.setText(s.getLabelName());
                return tv;
            }
        });
        UserInfoByOtherId.TaskLastInfoBean taskLastInfo = userAllInfo.getTaskLastInfo();
        mTaskName.setText(taskLastInfo.getTaskName());
        String text = "达成率" + taskLastInfo.getTaskScore() + "%";
        LogUtil.d(getLocalClassName(), text);
        mTaskScore.setText(text);
        switch (taskLastInfo.getSellerBigType() + "") {
            case 101 + "":
                mSellerBigType.setImageResource(R.mipmap.cf);
                break;
            case 102 + "":
                mSellerBigType.setImageResource(R.mipmap.cg);
                break;
            case 103 + "":
                mSellerBigType.setImageResource(R.mipmap.dy);
                break;
            case 104 + "":
                mSellerBigType.setImageResource(R.mipmap.yd);
                break;
            case 105 + "":
                mSellerBigType.setImageResource(R.mipmap.xx);
                break;
            case 106 + "":
                mSellerBigType.setImageResource(R.mipmap.lx);
                break;
            case 107 + "":
                mSellerBigType.setImageResource(R.mipmap.lr);
                break;
            case 108 + "":
                mSellerBigType.setImageResource(R.mipmap.zl);
                break;
            case 109 + "":
                mSellerBigType.setImageResource(R.mipmap.qt);
                break;
        }
    }

    /**
     * 取消关注
     */
    private void delConcernSuccess() {
        SquareRetrofitHttp.getHttp().delConcern(mUserToken, mOtherId).enqueue(new Callback<SuccessfulBean>() {
            @Override
            public void onResponse(Call<SuccessfulBean> call, Response<SuccessfulBean> response) {
                if (response.isSuccessful()) {
                    int resultCode = response.body().getResultCode();
                    ToastUtils.showToast(mActivity, response.body().getResultDesc());
                    if (resultCode == 1) {
                    } else if (resultCode == 9) {
                        LoginDialogUtils.isNewLogin(mActivity);
                    }
                }
            }

            @Override
            public void onFailure(Call<SuccessfulBean> call, Throwable t) {
                ToastUtils.showToast(mActivity, t.getMessage());
            }
        });
    }

    /**
     * 添加关注
     */
    private void ConcernSuccess() {
        SquareRetrofitHttp.getHttp().goConcern(mUserToken, mOtherId).enqueue(new Callback<SuccessfulBean>() {
            @Override
            public void onResponse(Call<SuccessfulBean> call, Response<SuccessfulBean> response) {
                if (response.isSuccessful()) {
                    int resultCode = response.body().getResultCode();
                    ToastUtils.showToast(mActivity, response.body().getResultDesc());
                    if (resultCode == 1) {
                    } else if (resultCode == 9) {
                        LoginDialogUtils.isNewLogin(mActivity);
                    }
                }
            }

            @Override
            public void onFailure(Call<SuccessfulBean> call, Throwable t) {
                ToastUtils.showToast(mActivity, t.getMessage());
            }
        });
    }

    @OnClick({square_more, R.id.ta_task, R.id.ta_square})
    public void onClick(View view) {
        switch (view.getId()) {
            case square_more:
                ToastUtils.showToast(mActivity, "更多");
                squareTaMoreShowPop();
                break;
            case R.id.ta_task:
                ToastUtils.showToast(mActivity, "他人任务");
                startActivity(new Intent(mActivity, TaTaskActivity.class));
                break;
            case R.id.ta_square:
                ToastUtils.showToast(mActivity, "他人空间");
                startActivity(new Intent(mActivity, TaSquareHomepageActivity.class));
                break;
        }
    }

    private void squareTaMoreShowPop() {
        mPopwindou = new Popwindou(mActivity, mActivity.findViewById(R.id.square_ta));
        View view = UiUtils.inflate(mActivity, R.layout.pop);
        mPopwindou.init(view, Gravity.BOTTOM, true);
        view.findViewById(R.id.item_dismis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopwindou.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(0);
    }
}

package com.jsz.peini.ui.activity.square;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.setting.SuccessfulBean;
import com.jsz.peini.model.square.UserAllInfo;
import com.jsz.peini.model.square.UserInfoByOtherId;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.presenter.square.SquareRetrofitHttp;
import com.jsz.peini.presenter.square.SquareService;
import com.jsz.peini.ui.adapter.square.MiImageListAdapter;
import com.jsz.peini.ui.view.Popwindou;
import com.jsz.peini.ui.view.TextProgressBar;
import com.jsz.peini.utils.Bitmap.BitmapAndStringUtils;
import com.jsz.peini.utils.Bitmap.GlideImgManager;
import com.jsz.peini.utils.Conversion;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.LoginDialogUtils;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.StringUtils;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.utils.UiUtils;
import com.jsz.peini.utils.Utils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.File;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jsz.peini.R.id.imageHead;
import static com.jsz.peini.R.id.square_bj;

public class MiSquareActivity extends BaseActivity {

    private static final int PHOTO_REQUEST_CUT = 100;//截取图片
    private static final int RESULT_LOAD_IMAGE2 = 200;
    private static final int RESULT_LOAD_IMAGE = 300;
    private static final int PHOTO_BACKGROUND = 400;//更换背景
    @InjectView(imageHead)
    CircleImageView mImageHead;
    @InjectView(R.id.sex)
    ImageView mSex;
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
    @InjectView(R.id.goldList)
    ImageView mGoldList;
    @InjectView(R.id.buyList)
    ImageView mBuyList;
    @InjectView(R.id.integrityList)
    ImageView mIntegrityList;
    @InjectView(R.id.credittext)
    TextView mCredittext;
    @InjectView(R.id.credit)
    TextProgressBar mCredit;
    @InjectView(R.id.signStatusText)
    TextView mSignStatus;
    @InjectView(R.id.taskScore)
    LinearLayout mTaskScore;
    @InjectView(R.id.taskScoreName)
    TextView mTaskScoreName;
    @InjectView(R.id.selfCountText)
    TextView mSelfCount;
    @InjectView(R.id.isIdcard)
    ImageView mIsIdcard;
    @InjectView(R.id.isPhone)
    ImageView mIsPhone;
    @InjectView(R.id.gold)
    TextView mGold;
    @InjectView(R.id.score)
    TextView mScore;
    @InjectView(R.id.lableList)
    TagFlowLayout mLableList;
    @InjectView(R.id.content)
    TextView mContent;
    @InjectView(R.id.squareLastInfo)
    ImageView mSquareLastInfo;
    @InjectView(R.id.sellerBigType)
    ImageView mSellerBigType;
    @InjectView(R.id.taskName)
    TextView mTaskName;
    @InjectView(R.id.square_toolbar)
    LinearLayout mSquareToolbar;
    @InjectView(R.id.square_more)
    ImageView mSquareMore;
    @InjectView(R.id.userLoginId)
    TextView mUserLoginId;
    @InjectView(square_bj)
    ImageView mSquareBj;
    @InjectView(R.id.ll_isIdcard)
    LinearLayout mLlIsIdcard;
    @InjectView(R.id.ll_isPhone)
    LinearLayout mLlIsPhone;
    @InjectView(R.id.jinbi_fen_wealth)
    LinearLayout mJinbiFenWealth;
    @InjectView(R.id.is_show_photo)
    LinearLayout mIsShowPhoto;
    @InjectView(R.id.square_progressbar)
    LinearLayout mSquareProgressbar;
    /*任务点击条目*/
    @InjectView(R.id.mi_task)
    LinearLayout mMiTask;
    private String mMuId;
    private MiSquareActivity mActivity;
    private TagAdapter<UserAllInfo.LableListBean> mLableListBeanTagAdapter;
    public Intent mIntent;
    private Uri tempUri;

    @Override
    public int initLayoutId() {
        return R.layout.activity_mi_square;
    }

    @Override
    public void initView() {
        super.initView();
        mActivity = this;
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        mSquareToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        mMuId = intent.getStringExtra("mUserToken");
        LogUtil.d(getLocalClassName(), mUserToken + "");
    }

    @Override
    public void initData() {
        mDialog.setText("正在加载");
        mDialog.show();
        initNetWork();
    }

    private void initNetWork() {
        LogUtil.d("访问我的空间传递的参数mUserToken---" + mUserToken);
        SquareRetrofitHttp.getHttp().getUserAllInfo(mUserToken).enqueue(new Callback<UserAllInfo>() {
            @Override
            public void onResponse(Call<UserAllInfo> call, final Response<UserAllInfo> response) {
                if (response.isSuccessful()) {
                    int resultCode = response.body().getResultCode();
                    if (resultCode == 1) {
                        UserAllInfo body = response.body();
                        Message message = new Message();
                        message.what = Conversion.DATASUCCESS;
                        message.obj = body;
                        mHandler.sendMessage(message);
                    } else if (resultCode == 9) {
                        LogUtil.d("我的空间访问--" + response.body().getResultDesc());
                        LoginDialogUtils.isNewLogin(mActivity);
                    }
                }

            }

            @Override
            public void onFailure(Call<UserAllInfo> call, Throwable t) {
                LogUtil.d("我的空间界面访问网络失败--", t.getMessage());
            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Conversion.DATASUCCESS:
                    UserAllInfo headBean = (UserAllInfo) msg.obj;
                    mDialog.dismiss();
                    initShowData(headBean);
                    break;
                default:
                    break;
            }
        }
    };

    private void initShowData(UserAllInfo userAllInfo) {
        UserAllInfo.UserInfoBean infoBean = userAllInfo.getUserInfo();
        //性别显示
        int sex = infoBean.getSex();
        mSex.setImageResource(sex == 1 ? R.mipmap.sqnan : R.mipmap.sqnv);
        //账号
        mUserLoginId.setText(infoBean.getUserLoginId() + "");
        //头像  和  背景
        String imageHead = infoBean.getImageHead();
        GlideImgManager.loadImage(mActivity, IpConfig.HttpPic + imageHead, mImageHead, sex + "");
        //背景图片
        String spaceBgImg = infoBean.getSpaceBgImg();
        GlideImgManager.loadImage(mActivity, IpConfig.HttpPic + spaceBgImg, mSquareBj);

        //底部的图片
        String imageSrc = userAllInfo.getSquareLastInfo().getImageSrc();
        if (StringUtils.isNoNull(imageSrc)) {
            GlideImgManager.loadRoundCornerImage(mActivity, IpConfig.HttpPic + imageSrc, mSquareLastInfo);
        } else {
            mSquareLastInfo.setVisibility(View.GONE);
        }
        /*名字*/
        mNickname.setText(infoBean.getNickname());
        //签名
        mSignWord.setText(infoBean.getSignWord());

        mAgenowProvinceTextnowCityText.setText(infoBean.getAge() + "岁 - " + infoBean.getNowProvinceText() + " " + infoBean.getNowCityText());
        mGoldList.setVisibility(infoBean.getGoldList() == 1 ? View.VISIBLE : View.GONE);
        mBuyList.setVisibility(infoBean.getBuyList() == 1 ? View.VISIBLE : View.GONE);
        mIntegrityList.setVisibility(infoBean.getIntegrityList() == 1 ? View.VISIBLE : View.GONE);
        UserAllInfo.OtherInfoBean otherInfo = userAllInfo.getOtherInfo();
        mSignStatus.setText(otherInfo.getSignStatus() == "1" ? "已签到" : "未签到");
        mSelfCount.setText("资料完整度" + otherInfo.getSelfCount() + "%");
        int credit = otherInfo.getCredit();
        mCredit.setProgress(credit);
        mCredittext.setText(credit > 80 ? "信誉值 - 高" : "信誉值 - 低");
        mMyConcernmyFans.setText("粉丝" + otherInfo.getMyConcern() + " | " + "关注" + otherInfo.getMyFans());
        /*-------------金币----------------*/
        String Gold = otherInfo.getGold() + "";
        mGold.setText(Gold);
        String Score = otherInfo.getScore() + "";
        mScore.setText(Score);
        /*-------------标签----------------*/
        List<UserAllInfo.LableListBean> list = userAllInfo.getLableList();
        final LayoutInflater mInflater = LayoutInflater.from(this);
        mLableList.setAdapter(mLableListBeanTagAdapter = new TagAdapter<UserAllInfo.LableListBean>(list) {
            @Override
            public View getView(FlowLayout parent, int position, UserAllInfo.LableListBean s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv2,
                        mLableList, false);
                tv.setText(s.getLabelName());
                return tv;
            }
        });
        mLableList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(mActivity, MiLabelActivity.class);
                startActivity(mIntent);
            }
        });
        /*-------------相册----------------*/
        List<UserAllInfo.ImageListBean> imageList = userAllInfo.getImageList();
        MiImageListAdapter adapter = new MiImageListAdapter(mActivity, imageList);
        adapter.addFootView(UiUtils.inflate(mActivity, R.layout.mi_release_photo_foot));
        LinearLayoutManager layout = new LinearLayoutManager(mActivity);
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        mImageList.setLayoutManager(layout);
        mImageList.setAdapter(adapter);
        adapter.setOnClicPhotokListener(new MiImageListAdapter.OnClicPhotokListener() {
            @Override
            public void mPhotoItemkListener(int position) {
                startActivity(new Intent(mActivity, AlbumManagementActivity.class));
                LogUtil.d(getLocalClassName(), "点击了我的相册" + position);
            }

            @Override
            public void FootViewItemkListener(int position) {
                LogUtil.d(getLocalClassName(), "点击了我空间拍照功能" + position);
                startActivity(new Intent(mActivity, AlbumManagementActivity.class));
            }
        });


        /*-------------认证----------------*/
        mIsIdcard.setImageResource(infoBean.getIsIdcard() == 1 ? R.mipmap.idcard2 : R.mipmap.idcard1);
        mIsPhone.setImageResource(infoBean.getIsPhone() == 1 ? R.mipmap.phone2 : R.mipmap.phone1);
        mLlIsIdcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**身份认证认证*/
                startActivity(new Intent(mActivity, IdentityAuthenticationActivity.class));
            }
        });
        mLlIsPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**手机认证*/
                startActivity(new Intent(mActivity, PhoneAuthenticationActivity.class));
            }
        });
        /*广场信息*/
        String content = userAllInfo.getSquareLastInfo().getContent() + "";
        if (StringUtils.isNoNull(content)) {
            mContent.setText(content);
        } else {
            mContent.setVisibility(View.GONE);
        }

        /*===============taskLastInfo==================*/
        UserAllInfo.TaskLastInfoBean taskLastInfo = userAllInfo.getTaskLastInfo();
        mTaskName.setText(taskLastInfo.getTaskName());
        mTaskScoreName.setText("达成率" + taskLastInfo.getTaskScore() + "%");
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
/*
        =============================我的财富========================
*/
    }

    @OnClick({R.id.imageHead, R.id.mi_square, R.id.square_more, R.id.signStatus, R.id.LabelActivity, R.id.selfCount, R.id.jinbi_fen_wealth, R.id.square_progressbar, R.id.square_bj, R.id.mi_task, R.id.mi_details})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.square_more: //更多
                break;
            case R.id.imageHead: //点击更换头像
                showPopselector();
                break;
            case R.id.square_bj: //点击背景
                mIntent = new Intent(mActivity, MyBackgroundActivity.class);
                startActivityForResult(mIntent, PHOTO_BACKGROUND);
                break;
            case R.id.signStatus: //签到界面
                mIntent = new Intent(mActivity, MiSignActivity.class);
                startActivity(mIntent);
                break;
            case R.id.jinbi_fen_wealth: //财富
                mIntent = new Intent(mActivity, MyWealthActivity.class);
                startActivity(mIntent);
                break;
            case R.id.square_progressbar: //信用
                mIntent = new Intent(mActivity, MyCreditActivity.class);
                startActivity(mIntent);
                break;
            case R.id.mi_task://我的任务列表
                mIntent = new Intent(mActivity, MiTaskActivity.class);
                startActivity(mIntent);
                break;
            case R.id.mi_details://我的详细资料界面
            case R.id.selfCount://我的详细资料界面
                mIntent = new Intent(mActivity, TaSquareMessageActivity.class);
                startActivity(mIntent);
                break;
            case R.id.LabelActivity://我的标签选择
                mIntent = new Intent(mActivity, MiLabelActivity.class);
                startActivity(mIntent);
                break;
            case R.id.mi_square://跳转到我的空间看看
                mIntent = new Intent(mActivity, MiSquareHomepageActivity.class);
                startActivity(mIntent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initNetWork();
    }

    private void showPopselector() {
        final Popwindou popwindou = new Popwindou(mActivity, UiUtils.inflate(mActivity, R.layout.activity_mi_square));
        View view = UiUtils.inflate(mActivity, R.layout.pop);
        popwindou.init(view, Gravity.BOTTOM, true);
        TextView item_popupwindows1 = (TextView) view.findViewById(R.id.item_popupwindows1);
        TextView item_popupwindows2 = (TextView) view.findViewById(R.id.item_popupwindows2);
        TextView item_dismis = (TextView) view.findViewById(R.id.item_dismis);
        item_popupwindows1.setText("拍照");
        item_popupwindows2.setText("相册");
        item_dismis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popwindou.dismiss();
            }
        });
        //拍照
        item_popupwindows1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 在启动拍照之前最好先判断一下sdcard是否可用
                 */
                String state = Environment.getExternalStorageState(); //拿到sdcard是否可用的状态码
                if (state.equals(Environment.MEDIA_MOUNTED)) {   //如果可用
                    mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
                    mIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                    startActivityForResult(mIntent, RESULT_LOAD_IMAGE2);
                } else {
                    ToastUtils.showToast(mActivity, "sdcard不可用");
                }
                popwindou.dismiss();
            }
        });
        //相册选择
        item_popupwindows2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent = new Intent(Intent.ACTION_GET_CONTENT);
                mIntent.setType("image/*");
                startActivityForResult(mIntent, RESULT_LOAD_IMAGE);
                popwindou.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_BACKGROUND && null != data) {
            Bundle bundle = data.getExtras();
            String img = bundle.getString("img");
            LogUtil.d("我的空间获取返回的背景图片" + img);
            GlideImgManager.loadImage(mActivity, IpConfig.HttpPic + img, mSquareBj);
        }
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            crop(selectedImage);
        }
        if (requestCode == RESULT_LOAD_IMAGE2 && resultCode == RESULT_OK && null != data) {
            crop(tempUri);
        }
        if (requestCode == PHOTO_REQUEST_CUT && resultCode == RESULT_OK && data != null) {
            Bundle selectedImage = data.getExtras();
            Bitmap data1 = selectedImage.getParcelable("data");
            final String imagePath = Utils.savePhoto(data1, Environment
                    .getExternalStorageDirectory().getAbsolutePath(), String
                    .valueOf(System.currentTimeMillis()));
            LogUtil.d("imagePath" + imagePath);
            if (StringUtils.isNoNull(imagePath)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String bitmap = BitmapAndStringUtils.saveBitmap(mActivity, imagePath, 30);
                        if (StringUtils.isNoNull(bitmap)) {
                            initUpdateUserImageHead(bitmap);
                        }
                    }
                }).start();
            }
        }
    }

    /*
     * 剪切图片
     */
    private void crop(Uri uri) {
        if (uri == null) {
            LogUtil.i("alanjet", "The uri is not exist.");
        }
        tempUri = uri;
        LogUtil.d("tempUri" + tempUri);
        Intent intent = new Intent("com.android.camera.action.CROP");
        //com.android.camera.action.CROP这个action是用来裁剪图片用的
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 260);
        intent.putExtra("outputY", 260);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    private void initUpdateUserImageHead(final String bitmap) {
        final File file = new File(bitmap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RetrofitUtil.createService(SquareService.class).updateUserImageHead(part, mId).enqueue(new Callback<SuccessfulBean>() {
            @Override
            public void onResponse(Call<SuccessfulBean> call, Response<SuccessfulBean> response) {
                int resultCode = response.body().getResultCode();
                if (response.isSuccessful() && resultCode == 1) {
                    LogUtil.d("更换头像成功" + response.body().toString());
                    initNetWork();
                } else if (resultCode == 9) {
                    LoginDialogUtils.isNewLogin(mActivity);
                }
            }

            @Override
            public void onFailure(Call<SuccessfulBean> call, Throwable t) {
                LogUtil.d("更换头像失败--" + t.getMessage());
            }
        });
    }

}

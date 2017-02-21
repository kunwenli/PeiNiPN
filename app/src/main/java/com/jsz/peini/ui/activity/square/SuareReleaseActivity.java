package com.jsz.peini.ui.activity.square;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.setting.SuccessfulBean;
import com.jsz.peini.presenter.square.SquareService;
import com.jsz.peini.ui.adapter.square.ReleasePhotoAdapter;
import com.jsz.peini.ui.view.Popwindou;
import com.jsz.peini.utils.Bitmap.BitmapAndStringUtils;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.StringUtils;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.utils.UiUtils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by th on 2016/12/27.
 */
public class SuareReleaseActivity extends BaseActivity {
    private static final String TAG = "SuareReleaseActivity";
    private static final int RESULT_LOAD_IMAGE2 = 200;
    private static final int RESULT_LOAD_IMAGE = 100;
    private static final int RESULT_LOAD_IMAGE3 = 300;
    @InjectView(R.id.release_return)
    Toolbar mReleaseReturn;
    @InjectView(R.id.seller_title_title)
    TextView mSellerTitleTitle;
    @InjectView(R.id.release_button)
    Button mReleaseButton;
    @InjectView(R.id.release_content)
    EditText mReleaseContent;
    @InjectView(R.id.release_photo)
    RecyclerView mReleasePhoto;
    @InjectView(R.id.release_address)
    TextView mReleaseAddress;
    private SuareReleaseActivity mActivity;
    List<String> mList = new ArrayList<>();
    private ReleasePhotoAdapter mPhotoAdapter;
    private Intent mIntent;
    /**
     * 返回的地址数据
     *
     * @deprecated mAddress 文字位置
     */
    private String mAddress;
    private double mLatitude;
    private double mLongitude;
    private Call<SuccessfulBean> mSetSquareInfoBySquareInfo;
    public Popwindou mPop;

    @Override
    public int initLayoutId() {
        return R.layout.activity_suare_release;
    }

    @Override
    public void initView() {
        super.initView();
        mActivity = this;
        mReleaseReturn.setTitle("");
        setSupportActionBar(mReleaseReturn);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回????
        mReleaseReturn.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void initData() {
        super.initData();
        InitLodingPhoto();
    }

    /**
     * 这个是加载图片的列表
     */
    private void InitLodingPhoto() {
        mPhotoAdapter = new ReleasePhotoAdapter(mActivity, mList);
        mPhotoAdapter.addFootView(UiUtils.inflate(mActivity, R.layout.item_release_photo_foot));
        LinearLayoutManager layout = new LinearLayoutManager(mActivity);
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        GridLayoutManager manager = new GridLayoutManager(mActivity, 3);
        mReleasePhoto.setLayoutManager(layout);
        mReleasePhoto.setAdapter(mPhotoAdapter);
        mPhotoAdapter.setOnPhotoFootClickListener(new ReleasePhotoAdapter.OnPhotoFootClickListener() {
            @Override
            public void FootClickListener(int position) {
                initSelect();
                isShowEdittext(false);
            }
        });
    }

    @OnClick({R.id.release_button, R.id.release_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.release_button:
                String trim = mReleaseContent.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    ToastUtils.showToast(mActivity, "请输入内容");
                    return;
                }
                initNetWork(mUserToken, mLatitude, mLongitude, trim, mAddress);
                break;
            case R.id.release_address:
                mIntent = new Intent(mActivity, SquarePeriphery.class);
                mIntent.putExtra("Address", mAddress);
                startActivityForResult(mIntent, RESULT_LOAD_IMAGE3);
                isShowEdittext(false);
                break;
        }
    }

    /**
     * 广场发布
     *
     * @param muId
     * @param latitude
     * @param longitude
     * @param trim
     * @param address
     */
    private void initNetWork(final String muId, double latitude, double longitude, final String trim, final String address) {
        DecimalFormat df = new DecimalFormat("######0.000000");
        final String sexLatitude = df.format(latitude);
        final String sexLongitude = df.format(longitude);
        LogUtil.i(TAG, "第一个参数" + muId);
        LogUtil.i(TAG, "第二个参数" + sexLatitude);
        LogUtil.i(TAG, "第三个参数" + sexLongitude);
        LogUtil.i(TAG, "第四个参数" + trim);
        LogUtil.i(TAG, "第五个参数" + address);

        if (mList.size() != 0) {
            final MultipartBody.Part[] parts = new MultipartBody.Part[mList.size()];
            for (int i = 0; i < parts.length; i++) {
                File file = new File(mList.get(i));
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
                parts[i] = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    /*有图发布空间信息*/
                    spaceuUpload(muId, trim, address, sexLatitude, sexLongitude, parts);
                }
            }).start();

        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                  /*无图发布空间信息*/
                    spaceUpload(muId, trim, address, sexLatitude, sexLongitude);
                }
            }).start();

        }

    }

    private void initSelect() {
        mPop = new Popwindou(mActivity, UiUtils.inflate(mActivity, R.layout.activity_suare_release));
        View view = UiUtils.inflate(mActivity, R.layout.item_popupwindows_selete);
        mPop.init(view, Gravity.BOTTOM, true);
        view.findViewById(R.id.item_popupwindows_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //从相册选择
                mPop.dismiss();

                mIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(mIntent, RESULT_LOAD_IMAGE);
            }
        });
        final Button bt2 = (Button) view
                .findViewById(R.id.item_popupwindows_Photo); //拍照
        Button bt3 = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);

        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {  //拍照
                /**
                 * 在启动拍照之前最好先判断一下sdcard是否可用
                 */
                String state = Environment.getExternalStorageState(); //拿到sdcard是否可用的状态码
                if (state.equals(Environment.MEDIA_MOUNTED)) {   //如果可用
                    mIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(mIntent, RESULT_LOAD_IMAGE2);
                } else {
                    Toast.makeText(mActivity, "sdcard不可用", Toast.LENGTH_SHORT).show();
                }
                mPop.dismiss();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //取消
                mPop.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();

            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            final String imagePath = c.getString(columnIndex);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String bitmap = null;
                    try {
                        bitmap = BitmapAndStringUtils.saveBitmap(mActivity, imagePath, 500);
                        mList.add(bitmap);
                    } catch (Exception e) {
                        finish();
                    }
                    if (StringUtils.isNoNull(bitmap)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPhotoAdapter.setPhotoList(mList);
                            }
                        });
                    }
                }
            }).start();
        }
        if (requestCode == RESULT_LOAD_IMAGE2 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            final String imagePath = c.getString(columnIndex);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String bitmap = null;
                    try {
                        bitmap = BitmapAndStringUtils.saveBitmap(mActivity, imagePath, 500);
                        mList.add(bitmap);
                    } catch (Exception e) {
                        finish();
                    }
                    if (StringUtils.isNoNull(bitmap)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPhotoAdapter.setPhotoList(mList);
                            }
                        });
                    }
                }
            }).start();
        }
        if (requestCode == RESULT_LOAD_IMAGE3 && null != data) {
            Bundle bundle = data.getExtras();
            mAddress = bundle.getString("Address");
            mLatitude = bundle.getDouble("latitude");
            mLongitude = bundle.getDouble("longitude");
            mReleaseAddress.setText(mAddress);
            mReleaseAddress.setTextColor(Color.BLACK);
        }
    }

    public void isShowEdittext(boolean isshow) {
        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString("发个说说,缓解下心情吧~~~");
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(10, true);
        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置hint
        mReleaseContent.setHint(new SpannedString(ss));
        mReleaseContent.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isshow) {
            imm.showSoftInput(mReleaseContent, 0);
        } else {
            imm.hideSoftInputFromWindow(mReleaseContent.getWindowToken(), 0);
        }
    }

    /*无图发布空间信息*/
    private void spaceUpload(String muId, String trim, String address, String sexLatitude, String sexLongitude) {
        RetrofitUtil.createService(SquareService.class).setSquareInfoBySquareInfo(mUserToken, sexLatitude + "", sexLongitude + "", trim + "", address + "")
                .enqueue(new Callback<SuccessfulBean>() {
                    @Override
                    public void onResponse(Call<SuccessfulBean> call, Response<SuccessfulBean> response) {
                        if (response.isSuccessful()) {
                            SuccessfulBean body = response.body();
                            if (body.getResultCode() == 1) {
                                finish();
                            } else {
                                ToastUtils.showToast(mActivity, "发布失败");
                            }
                        } else {
                            ToastUtils.showToast(mActivity, "发布失败");
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessfulBean> call, Throwable t) {

                    }
                });
    }

    /*有图发布空间信息*/
    private void spaceuUpload(String muId, String trim, String address, String sexLatitude, String sexLongitude, MultipartBody.Part[] parts) {
        RetrofitUtil.createService(SquareService.class).setSquareInfoBySquareInfo(mUserToken, sexLatitude + "", sexLongitude + "", trim + "", address + "", parts).enqueue(new Callback<SuccessfulBean>() {
            @Override
            public void onResponse(Call<SuccessfulBean> call, Response<SuccessfulBean> response) {
                if (response.isSuccessful() && response.body().getResultCode() == 1) {
                    SuccessfulBean enter = response.body();
                    LogUtil.d(TAG, "上传成功" + enter.toString());
                    finish();
                } else {
                    LogUtil.d(TAG, "上传失败");
                }
            }

            @Override
            public void onFailure(Call<SuccessfulBean> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        isShowEdittext(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mList.clear();
        mList.clear();
    }


}

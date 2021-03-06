package com.jsz.peini.ui.activity.square;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.square.MyBackgroundBean;
import com.jsz.peini.model.square.UpdateUserBgImgBean;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.presenter.square.SquareService;
import com.jsz.peini.ui.adapter.square.MiBackgroundAdapter;
import com.jsz.peini.ui.view.Popwindou;
import com.jsz.peini.ui.view.SpacesItemDecoration;
import com.jsz.peini.utils.Bitmap.BitmapAndStringUtils;
import com.jsz.peini.utils.Bitmap.GlideImgManager;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.LoginDialogUtils;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.StringUtils;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.utils.UiUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
 * Created by th on 2017/1/21.
 */
public class MyBackgroundActivity extends BaseActivity {
    private static final int RESULT_LOAD_IMAGE2 = 200;
    private static final int RESULT_LOAD_IMAGE = 300;
    private static final int PHOTO_BACKGROUND = 400;//图片背景返回
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.right_button)
    TextView mRightButton;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.square_background)
    RecyclerView mSquareBackground;
    @InjectView(R.id.selector_mi_square_background)
    Button mSelectorMiSquareBackground;
    public MyBackgroundActivity mActivity;
    List<String> mList;
    public Intent mIntent;
    public MiBackgroundAdapter mAdapter;

    @Override
    public int initLayoutId() {
        return R.layout.activity_mybackground;
    }

    @Override
    public void initView() {
        mActivity = this;
        mTitle.setText("选择封面");
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mList = new ArrayList<>();
        mSquareBackground.addItemDecoration(new SpacesItemDecoration(3, 20, true));
        mSquareBackground.setLayoutManager(new GridLayoutManager(mActivity, 3));
        mAdapter = new MiBackgroundAdapter(mActivity, mList);
        mSquareBackground.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        initNetWork();
    }

    @Override
    protected void initListener() {
        mAdapter.setItemClickListener(new MiBackgroundAdapter.ItemClickListener() {
            @Override
            public void OnClickListener(String img) {
                showPopselectorBackground(img);
            }
        });
    }

    /**
     * 选择封面大图浏览
     *
     * @param imgage //图片地址
     */
    private void showPopselectorBackground(final String imgage) {
        final Popwindou popwindou = new Popwindou(mActivity, UiUtils.inflate(mActivity, R.layout.activity_mybackground));
        View view = UiUtils.inflate(mActivity, R.layout.see_background);
        popwindou.init(view, Gravity.CENTER, true);
        ImageView img = (ImageView) view.findViewById(R.id.img);
        Button background = (Button) view.findViewById(R.id.ok_background);
        GlideImgManager.loadImage(mActivity, IpConfig.HttpPic + imgage, img);
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.d("设置封面");
                setBackgroundImage(imgage, popwindou);
            }
        });

    }

    /**
     * 设置空间背景
     */
    private void setBackgroundImage(String imgage, final Popwindou popwindou) {
        RetrofitUtil.createService(SquareService.class)
                .updateUserBgImg(mUserToken, imgage)
                .enqueue(new Callback<UpdateUserBgImgBean>() {
                    @Override
                    public void onResponse(Call<UpdateUserBgImgBean> call, Response<UpdateUserBgImgBean> response) {
                        if (response.isSuccessful()) {
                            LogUtil.d("设置照片成功" + response.body().toString());
                            popwindou.dismiss();
                            mIntent = new Intent();
                            mIntent.putExtra("img", response.body().getSpaceBgImg() + "");
                            setResult(PHOTO_BACKGROUND, mIntent);
                            finish();
                        } else {
                            LogUtil.d("设置图片失败" + response.body().toString());
                            popwindou.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateUserBgImgBean> call, Throwable t) {
                        popwindou.dismiss();
                        LogUtil.d("设置图片失败" + t.getMessage());
                    }
                });
    }

    /**
     * 获取封面
     */
    private void initNetWork() {
        RetrofitUtil.createService(SquareService.class)
                .getSysBagImages()
                .enqueue(new Callback<MyBackgroundBean>() {
                    @Override
                    public void onResponse(Call<MyBackgroundBean> call, Response<MyBackgroundBean> response) {
                        if (response.isSuccessful()) {
                            MyBackgroundBean body = response.body();
                            if (body.getResultCode() == 1) {
                                LogUtil.d("获取图片成功" + body.toString());
                                String str = body.getImgs();//根据逗号分隔到List数组中
                                String str2 = str.replace(" ", "");//去掉所用空格
                                mList.clear();
                                mList.addAll(Arrays.asList(str2.split(",")));
                                mAdapter.notifyDataSetChanged();
                            } else {
                                LogUtil.d("获取图片失败" + body.toString());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MyBackgroundBean> call, Throwable t) {
                        LogUtil.d("获取图片失败" + t.getMessage());
                    }
                });
    }

    @OnClick(R.id.selector_mi_square_background)
    public void onClick() {
        showPopselector();
    }

    private void showPopselector() {
        final Popwindou popwindou = new Popwindou(mActivity, UiUtils.inflate(mActivity, R.layout.activity_mybackground));
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
                    mIntent = new Intent("android.media.action.IMAGE_CAPTURE");
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
                mIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(mIntent, RESULT_LOAD_IMAGE);
                popwindou.dismiss();
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
            if (StringUtils.isNoNull(imagePath)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String bitmap = BitmapAndStringUtils.saveBitmap(mActivity, imagePath, 100);
                        if (StringUtils.isNoNull(bitmap)) {
                            initUpdateUserImageHead(bitmap);
                        }
                    }
                }).start();
            }
        }
        if (requestCode == RESULT_LOAD_IMAGE2 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            final String imagePath = c.getString(columnIndex);
            if (StringUtils.isNoNull(imagePath)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String bitmap = BitmapAndStringUtils.saveBitmap(mActivity, imagePath, 100);
                        if (StringUtils.isNoNull(bitmap)) {
                            initUpdateUserImageHead(bitmap);
                        }
                    }
                }).start();
            }
        }
    }


    private void initUpdateUserImageHead(final String bitmap) {
        final File file = new File(bitmap);
        final RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RetrofitUtil.createService(SquareService.class)
                .updateUserBgImg(mUserToken, part, "")
                .enqueue(new Callback<UpdateUserBgImgBean>() {
                    @Override
                    public void onResponse(Call<UpdateUserBgImgBean> call, Response<UpdateUserBgImgBean> response) {
                        if (response.isSuccessful()) {
                            UpdateUserBgImgBean body = response.body();
                            if (body.getResultCode() == 1) {
                                LogUtil.d("选择照片成功" + response.body().toString());
                                mIntent = new Intent();
                                mIntent.putExtra("img", response.body().getSpaceBgImg());
                                setResult(PHOTO_BACKGROUND, mIntent);
                                finish();
                            } else if (body.getResultCode() == 9) {
                                LoginDialogUtils.isNewLogin(mActivity);
                            }else {
                                ToastUtils.showToast(mActivity,body.getResultDesc());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateUserBgImgBean> call, Throwable t) {
                        ToastUtils.showToast(mActivity, t.getMessage());
                    }
                });
    }
}

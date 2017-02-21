package com.jsz.peini.ui.view.ImageSelector;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsz.peini.R;
import com.jsz.peini.listener.RetrofitCallback;
import com.jsz.peini.model.setting.SuccessfulBean;
import com.jsz.peini.presenter.square.SquareService;
import com.jsz.peini.ui.view.ImageSelector.common.Callback;
import com.jsz.peini.ui.view.ImageSelector.common.Constant;
import com.jsz.peini.ui.view.ImageSelector.utils.FileUtils;
import com.jsz.peini.ui.view.ImageSelector.utils.StatusBarCompat;
import com.jsz.peini.utils.Bitmap.BitmapAndStringUtils;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.widget.FileRequestBody;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class ImgSelActivity extends FragmentActivity implements View.OnClickListener, Callback {

    public static final String INTENT_RESULT = "result";
    private static final int IMAGE_CROP_CODE = 1;
    private static final int STORAGE_REQUEST_CODE = 1;

    private ImgSelConfig config;

    private RelativeLayout rlTitleBar;
    private TextView tvTitle;
    private Button btnConfirm;
    private ImageView ivBack;
    private String cropImagePath;
    private TextView mNumbar;
    private Button mUpload;
    private ImgSelFragment fragment;
    private ArrayList<String> result = new ArrayList<>();
    private TextView mUpland_numbar, mUpland_sign;
    private ProgressBar mUpland_numbar_progressbar;
    private ArrayList<String> mImageList;
    private LinearLayout mIsGon;

    public static void startActivity(Activity activity, ImgSelConfig config, int RequestCode) {
        Intent intent = new Intent(activity, ImgSelActivity.class);
        Constant.config = config;
        activity.startActivityForResult(intent, RequestCode);
    }

    public static void startActivity(Fragment fragment, ImgSelConfig config, int RequestCode) {
        Intent intent = new Intent(fragment.getActivity(), ImgSelActivity.class);
        Constant.config = config;
        fragment.startActivityForResult(intent, RequestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_sel);
        config = Constant.config;
        // Android 6.0 checkSelfPermission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    STORAGE_REQUEST_CODE);
        } else {
            fragment = ImgSelFragment.instance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fmImageList, fragment, null)
                    .commit();
        }

        initView();
        if (!FileUtils.isSdCardAvailable()) {
            Toast.makeText(this, getString(R.string.sd_disable), Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        mImageList = Constant.imageList;
        rlTitleBar = (RelativeLayout) findViewById(R.id.rlTitleBar);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(this);

        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        //显示
        mIsGon = (LinearLayout) findViewById(R.id.isGon);

        mUpland_numbar = (TextView) findViewById(R.id.upland_numbar); //个数
        mUpland_numbar_progressbar = (ProgressBar) findViewById(R.id.upland_numbar_progressbar); //进度
        mUpland_sign = (TextView) findViewById(R.id.upland_sign); //百分比

        //选择的数量
        mNumbar = (TextView) findViewById(R.id.selector_photo_number);
        //确定上传
        mUpload = (Button) findViewById(R.id.selector_determine_upload);
        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mImageList.size() != 0) {
                    setUserImage(mImageList);
                    mIsGon.setVisibility(View.VISIBLE);
                }
            }
        });

        if (config != null) {
            if (config.backResId != -1) {
                ivBack.setImageResource(config.backResId);
            }
            if (config.statusBarColor != -1) {
                StatusBarCompat.compat(this, config.statusBarColor);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                        && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                }
            }
            rlTitleBar.setBackgroundColor(config.titleBgColor);
            tvTitle.setTextColor(config.titleColor);
            tvTitle.setText(config.title);
            mNumbar.setBackgroundColor(config.btnBgColor);
            mNumbar.setTextColor(config.btnTextColor);
            if (config.multiSelect) {
                /* + String.format(getString(R.string.confirm_format), config.btnText, Constant.imageList.size(), config.maxNum)*/
                mNumbar.setText("已选择 " + Constant.imageList.size() + " 张照片");
            } else {
                Constant.imageList.clear();
                mNumbar.setText(config.btnText);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnConfirm:
                if (Constant.imageList != null && !Constant.imageList.isEmpty()) {
                    exit();
                }
                break;
            case R.id.ivBack:
                if (fragment == null || !fragment.hidePreview()) {
                    Constant.imageList.clear();
                    finish();
                }
                break;
        }

    }

    int isnumbar = 0;
    long all = 0;

    /*8我的相册 图片的上传*/
    private void setUserImage(final ArrayList<String> imageList) {
        LogUtil.d("这个是选择的几个图片", "个数--" + imageList.size());
        final int size = imageList.size();
        mUpland_numbar_progressbar.setMax(size);
        all = size;
        final NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (isnumbar = 0; isnumbar < imageList.size(); isnumbar++) {
                    uplandImage(imageList.get(isnumbar));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mUpland_numbar.setText("正在上传" + (isnumbar) + "/" + (size) + "张");
                            mUpland_sign.setText(nt.format((float) isnumbar / all));
                            mUpland_numbar_progressbar.setProgress(isnumbar);
                            if (isnumbar == size) {
                                if (fragment == null || !fragment.hidePreview()) {
                                    Constant.imageList.clear();
                                    finish();
                                }
                                mIsGon.setVisibility(View.GONE);
                            }
                        }
                    });
                    LogUtil.d("正在上传", "正在上传" + (isnumbar) + "/" + (size) + "张");
                }
            }
        }).start();

    }


    private void uplandImage(final String imageList) {
        RetrofitCallback<SuccessfulBean> callback = new RetrofitCallback<SuccessfulBean>() {
            @Override
            public void onSuccess(Call<SuccessfulBean> call, Response<SuccessfulBean> response) {
                //进度更新结束
                if (response.isSuccessful() && response.body().getResultCode() == 1) {
                    LogUtil.d("这个是上传成功的图片---", "成功" + response.body().toString());

                }
            }

            @Override
            public void onFailure(Call<SuccessfulBean> call, Throwable t) {
                //进度更新结束
                LogUtil.d("这个是上传成功的图片---", "失败" + t.getMessage());

            }

            @Override
            public void onLoading(long total, long progress) {
                super.onLoading(total, progress);
                //此处进行进度更新
                LogUtil.d("这个是上传成功的图片---", "总大小" + total + "进度条" + progress);
            }
        };
        String path = BitmapAndStringUtils.saveBitmap(this, imageList,300);
        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
        //通过该行代码将RequestBody转换成特定的FileRequestBody
        FileRequestBody body = new FileRequestBody(requestBody, callback);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body);
        RetrofitUtil.createService(SquareService.class).setUserImages("2", part).enqueue(callback);
    }

    @Override
    public void onSingleImageSelected(String path) {
        if (config.needCrop) {
            crop(path);
        } else {
            Constant.imageList.add(path);
            exit();
        }
    }

    @Override
    public void onImageSelected(String path) {
        mNumbar.setText("已选择 " + Constant.imageList.size() + " 张照片");
    }

    @Override
    public void onImageUnselected(String path) {
        mNumbar.setText("已选择 " + Constant.imageList.size() + " 张照片");
    }

    @Override
    public void onCameraShot(File imageFile) {
        if (imageFile != null) {
            if (config.needCrop) {
                crop(imageFile.getAbsolutePath());
            } else {
                Constant.imageList.add(imageFile.getAbsolutePath());
                exit();
            }
        }
    }

    @Override
    public void onPreviewChanged(int select, int sum, boolean visible) {
        if (visible) {
            tvTitle.setText(select + "/" + sum);
        } else {
            tvTitle.setText(config.title);
        }
    }

    private void crop(String imagePath) {
        File file = new File(FileUtils.createRootPath(this) + "/" + System.currentTimeMillis() + ".jpg");
        cropImagePath = file.getAbsolutePath();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(new File(imagePath)), "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", config.aspectX);
        intent.putExtra("aspectY", config.aspectY);
        intent.putExtra("outputX", config.outputX);
        intent.putExtra("outputY", config.outputY);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, IMAGE_CROP_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_CROP_CODE && resultCode == RESULT_OK) {
            Constant.imageList.add(cropImagePath);
            exit();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void exit() {
        Intent intent = new Intent();
        result.clear();
        result.addAll(Constant.imageList);
        intent.putStringArrayListExtra(INTENT_RESULT, result);
        setResult(RESULT_OK, intent);

        if (!config.multiSelect) {
            Constant.imageList.clear();
        }

        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case STORAGE_REQUEST_CODE:
                if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fmImageList, ImgSelFragment.instance(), null)
                            .commitAllowingStateLoss();
                } else {
                    Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (fragment == null || !fragment.hidePreview()) {
            Constant.imageList.clear();
            super.onBackPressed();
        }
    }
}

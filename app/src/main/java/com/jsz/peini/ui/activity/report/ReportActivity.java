package com.jsz.peini.ui.activity.report;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.question.QuestionEnterBean;
import com.jsz.peini.model.report.ReportModel;
import com.jsz.peini.presenter.question.QuestionService;
import com.jsz.peini.presenter.report.ReportService;
import com.jsz.peini.ui.adapter.seller.ReportAdapter;
import com.jsz.peini.ui.adapter.square.ReleasePhotoAdapter;
import com.jsz.peini.ui.view.Popwindou;
import com.jsz.peini.utils.Bitmap.BitmapAndStringUtils;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.PeiNiUtils;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.StringUtils;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.utils.UiUtils;

import java.io.File;
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

public class ReportActivity extends BaseActivity {
    private static final int RESULT_LOAD_IMAGE2 = 200;
    private static final int RESULT_LOAD_IMAGE = 100;
    @InjectView(R.id.back_btn)
    RelativeLayout backBtn;
    @InjectView(R.id.seller_select_accusation)
    TextView seller_select_accusation;
    @InjectView(R.id.report_yijian)
    EditText report_yijian;
    @InjectView(R.id.report_number)
    TextView report_number;
    @InjectView(R.id.seller_question)
    Button mSellerQuestion;
    @InjectView(R.id.report_recyclerview)
    RecyclerView mReportRecyclerview;
    @InjectView(R.id.image_numbar)
    TextView mImageNumbar;
    @InjectView(R.id.report_come)
    FrameLayout mReportCome;
    @InjectView(R.id.report_ok)
    FrameLayout mReportOk;
    @InjectView(R.id.button)
    Button mButton;
    private String content = ""; //举报内容
    private String reportId = ""; //被举报对象id
    private String reportCause = "";//举报原因
    public String mReportType;//举报类型

    /**
     * 举报
     */
    List<ReportModel.ReportReasonBean> mReportModels;
    public ReportActivity mActivity;
    private Popwindou mPopwindou;
    /*图片的列表*/
    List<String> mList;
    public ReleasePhotoAdapter mPhotoAdapter;
    public Popwindou mPop;
    public Intent mIntent;
    public MultipartBody.Part[] mParts;

    @Override
    public int initLayoutId() {
        return R.layout.activity_seller_report;
    }

    @Override
    public void initView() {
        mActivity = this;
        mReportModels = new ArrayList<>();
        mList = new ArrayList<>();
        isSuccessful(false);
        Intent intent = getIntent();
        mReportType = intent.getStringExtra("type");
        /**
         * 1问题反馈；2用户举报；3举报商家；4任务举报；5图片举报
         */
        if (mReportType.equals("2")) {
            reportId = intent.getStringExtra("reportId");
        }
        if (mReportType.equals("3")) {
            reportId = intent.getStringExtra("reportId");
        }
        if (mReportType.equals("4")) {
            reportId = intent.getStringExtra("reportId");
        }

    }

    @Override
    public void initData() {
        initNetWork();
        initAddImage();
        //输入框的监听器
        report_yijian.addTextChangedListener(getWatcher());
    }


    /*选择图片*/
    private void initAddImage() {
        /**
         * 这个是加载图片的列表
         */
        mPhotoAdapter = new ReleasePhotoAdapter(mActivity, mList);
        mPhotoAdapter.addFootView(UiUtils.inflate(mActivity,R.layout.item_release_photo_foot));
        LinearLayoutManager layout = new LinearLayoutManager(mActivity);
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        final GridLayoutManager manager = new GridLayoutManager(mActivity, 3);
        mReportRecyclerview.setLayoutManager(layout);
        mReportRecyclerview.setAdapter(mPhotoAdapter);
        mPhotoAdapter.setOnPhotoFootClickListener(new ReleasePhotoAdapter.OnPhotoFootClickListener() {
            @Override
            public void FootClickListener(int position) {
                initImageSelect();
                PeiNiUtils.getOffKeyset(mActivity);
            }
        });

    }

    /**
     * 图片选择
     */
    private void initImageSelect() {
        mPop = new Popwindou(mActivity, UiUtils.inflate(mActivity,R.layout.activity_suare_release));
        View view = UiUtils.inflate(mActivity,R.layout.item_popupwindows_selete);
        mPop.init(view, Gravity.BOTTOM, true);
        view.findViewById(R.id.item_popupwindows_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //从相册选择
                mIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(mIntent, RESULT_LOAD_IMAGE);
                mPop.dismiss();
            }
        });
        view.findViewById(R.id.item_popupwindows_Photo)
                .setOnClickListener(new View.OnClickListener() {
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
        view.findViewById(R.id.item_popupwindows_cancel).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //取消
                mPop.dismiss();
            }
        });
    }

    @OnClick({R.id.back_btn, R.id.seller_select_accusation, R.id.seller_question})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.seller_select_accusation:
                /*这个是举报i界面的 弹窗选择按钮的*/
                if (mReportModels.size() > 0) {
                    initSelect();
                    PeiNiUtils.getOffKeyset(mActivity);
                } else {
                    ToastUtils.showToast(mActivity, "网络请求失败");
                }
                break;
            case R.id.seller_question:
                /*这个是举报i界面的 弹窗选择按钮的*/
                if (!StringUtils.isNoNull(content)) {
                    ToastUtils.showToast(mActivity, "请输入举报内容");
                    return;
                }
                if (!StringUtils.isNoNull(reportCause)) {
                    ToastUtils.showToast(mActivity, "请选择举报类型");
                    initSelect();
                    return;
                }
                initWork();
                break;
        }
    }

    private void initSelect() {
        mPopwindou = new Popwindou(this, UiUtils.inflate(mActivity,R.layout.activity_seller_report));
        View view = UiUtils.inflate(mActivity,R.layout.pop_recyclerview);
        mPopwindou.init(view, Gravity.BOTTOM, true);
        RecyclerView report_recyclerview = (RecyclerView) view.findViewById(R.id.report_recyclerview);
        report_recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        ReportAdapter adapter = new ReportAdapter(mActivity, mReportModels);
        report_recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(new ReportAdapter.OnItemClickListener() {
            @Override
            public void ItemClic(int position) {
                mPopwindou.dismiss();
                ReportModel.ReportReasonBean reportReasonBean = mReportModels.get(position);
                mReportType = reportReasonBean.getTypeid() + "";
                reportCause = reportReasonBean.getResid() + "";
                seller_select_accusation.setText(reportReasonBean.getResname() + "");
                LogUtil.d("选择的那个条目-" + position + "--" + reportReasonBean.getResname());
            }
        });
        view.findViewById(R.id.item_popupwindows_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopwindou.dismiss();
            }
        });
    }

    /**
     * 获取举报内容
     */
    private void initNetWork() {
        RetrofitUtil.createService(ReportService.class).getReportReasons(mReportType).enqueue(new Callback<ReportModel>() {
            @Override
            public void onResponse(Call<ReportModel> call, Response<ReportModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResultCode() == 1) {
                        LogUtil.d("这个是获取举报内容-成功" + response.body().toString());
                        mReportModels.clear();
                        mReportModels.addAll(response.body().getReportReason());
                    } else {
                        LogUtil.d("这个是获取举报内容-失败" + response.body().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ReportModel> call, Throwable t) {
            }
        });
    }

    /**
     * 点击按钮举报
     */
    private void initWork() {
        QuestionService service = RetrofitUtil.createService(QuestionService.class);
        Call<QuestionEnterBean> beanCall = null;
        mParts = new MultipartBody.Part[mList.size()];
        for (int i = 0; i < mParts.length; i++) {
            File file = new File(mList.get(i));
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
            mParts[i] = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        }
        if (mList.size() != 0) {
            beanCall = service.setQuestion(mUserToken, content, mReportType, reportId, reportCause, mParts);
        } else {
            beanCall = service.setQuestionNull(mUserToken, content, mReportType, reportId, reportCause);
        }
        if (beanCall == null) {
            return;
        }
        beanCall.enqueue(new Callback<QuestionEnterBean>() {
            @Override
            public void onResponse(Call<QuestionEnterBean> call, Response<QuestionEnterBean> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResultCode() == 1) {
                        LogUtil.d("举报成功商家举报界面成功" + response.body().toString());
                        isSuccessful(true);
                    } else {
                        LogUtil.d("举报成功商家举报界面失败" + response.body().toString());
                        isSuccessful(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<QuestionEnterBean> call, Throwable t) {
                LogUtil.d("举报成功商家举报界面失败" + t.getMessage());
                isSuccessful(false);
            }
        });
    }

    /**
     * 成功
     *
     * @param b
     */
    private void isSuccessful(boolean b) {
        mReportCome.setVisibility(b ? View.GONE : View.VISIBLE);
        mReportOk.setVisibility(b ? View.VISIBLE : View.GONE);
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
                        bitmap = BitmapAndStringUtils.saveBitmap(mActivity, imagePath, 80);
                        mList.add(bitmap);
                    } catch (Exception e) {
                        finish();
                    }
                    if (StringUtils.isNoNull(bitmap)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPhotoAdapter.setPhotoList(mList);
                                initSetImageNumbar(mList);
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
                        bitmap = BitmapAndStringUtils.saveBitmap(mActivity, imagePath, 80);
                        mList.add(bitmap);
                    } catch (Exception e) {
                        finish();
                    }
                    if (StringUtils.isNoNull(bitmap)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPhotoAdapter.setPhotoList(mList);
                                initSetImageNumbar(mList);
                            }
                        });
                    }
                }
            }).start();
        }
    }

    private void initSetImageNumbar(List<String> list) {
        mImageNumbar.setText(list.size() + "/" + 9);
    }

    @NonNull
    private TextWatcher getWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                report_number.setText((charSequence.length()) + "/" + "200");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                content = report_yijian.getText().toString().trim();
            }
        };
    }

    @OnClick(R.id.button)
    public void onClick() {
        finish();
    }
}

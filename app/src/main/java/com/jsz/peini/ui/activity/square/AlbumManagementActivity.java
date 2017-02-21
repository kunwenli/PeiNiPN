package com.jsz.peini.ui.activity.square;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.setting.SuccessfulBean;
import com.jsz.peini.model.square.ImageListBean;
import com.jsz.peini.model.square.UserImageAllByUserId;
import com.jsz.peini.presenter.square.SquareRetrofitHttp;
import com.jsz.peini.ui.adapter.square.MIPhotoAdapter;
import com.jsz.peini.ui.view.ImageSelector.ImageLoader;
import com.jsz.peini.ui.view.ImageSelector.ImgSelActivity;
import com.jsz.peini.ui.view.ImageSelector.ImgSelConfig;
import com.jsz.peini.utils.LogUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by th on 2017/1/5.
 */
public class AlbumManagementActivity extends BaseActivity {
    private static final int REQUEST_CODE = 13579;
    @InjectView(R.id.mi_photo_manage)
    RecyclerView mMiPhotoManage;
    @InjectView(R.id.mi_image_title)
    TextView mMiImageTitle;
    @InjectView(R.id.mi_image_manage)
    Button mMiImageManage;
    @InjectView(R.id.album_upload)
    TextView mAlbumUpload;
    @InjectView(R.id.album_delete_number)
    TextView mAlbumDeleteNumber;
    @InjectView(R.id.album_delete_button)
    TextView mAlbumDeleteButton;
    @InjectView(R.id.album_delete)
    LinearLayout mAlbumDelete;
    @InjectView(R.id.ivBack)
    ImageView mIvBack;
    private AlbumManagementActivity mActivity;
    private MIPhotoAdapter mMiPhotoAdapter;
    List<UserImageAllByUserId.UserImageListBean> mUserImageList;

    /*是否管理*/
    boolean isManage = false;
    /**
     * 记录
     */
    boolean isManageNext = false;
    /**
     * 图片的集合
     */
    private ArrayList<ImageListBean> mList;

    @Override
    public int initLayoutId() {
        return R.layout.activity_album_management;
    }

    @Override
    public void initView() {
        super.initView();
        ShowWindows(true);
        mActivity = this;
        mUserImageList = new ArrayList<>();
        /**初始化数据*/
        mMiPhotoManage.setLayoutManager(new LinearLayoutManager(this));
        mMiPhotoAdapter = new MIPhotoAdapter(mActivity, mUserImageList);
        mMiPhotoManage.setAdapter(mMiPhotoAdapter);
        mMiPhotoAdapter.setOnCheckedChangedlistenr(new MIPhotoAdapter.onCheckedChangedlistenr() {
            @Override
            public void setIsCheckPhoto(int positio, int position, String imageView) {
                mList = new ArrayList<ImageListBean>();
                for (int i = 0; i < mUserImageList.size(); i++) {
                    List<ImageListBean> userImageAll = mUserImageList.get(i).getUserImageAll();
                    mList.addAll(userImageAll);
                }
                Intent intent = new Intent(mActivity, PhotoView.class);
                intent.putExtra("mlist", (Serializable) mList);
                mActivity.startActivity(intent);
            }
        });
        inItNetWork();
    }

    /**
     * 发起网络请求
     */
    private void inItNetWork() {
        SquareRetrofitHttp.getHttp().getUserImageAllByUserId("1").enqueue(new Callback<UserImageAllByUserId>() {
            @Override
            public void onResponse(Call<UserImageAllByUserId> call, Response<UserImageAllByUserId> response) {
                if (response.isSuccessful() && response.body().getResultCode() == 1 && response.body() != null) {
                    /**更新数据*/
                    List<UserImageAllByUserId.UserImageListBean> userImageList = response.body().getUserImageList();
                    mUserImageList.addAll(userImageList);
                    mMiPhotoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<UserImageAllByUserId> call, Throwable t) {

            }
        });
    }

    /**
     * 点击事件
     */
    @OnClick({R.id.ivBack, R.id.mi_image_manage, R.id.album_upload, R.id.album_delete_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mi_image_manage:
                managePhoto();
                break;
            case R.id.album_upload:
                isOpenPhoto();
                break;
            case R.id.album_delete_button:
                inItNetWorkDelete();
                break;
            case R.id.ivBack: //返回的事件
                finish();
                break;
        }
    }


    /**
     * 打开相册
     */
    private void isOpenPhoto() {
        // 自由配置选项
        ImgSelConfig config = new ImgSelConfig.Builder(mActivity, new ImageLoader() {
            // 自定义图片加载器
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        }).multiSelect(true)
                // 第一个是否显示相机
                .needCamera(false)
                // 最大选择图片数量
                .maxNum(9)
                .build();
        // 跳转到图片选择器
        ImgSelActivity.startActivity(this, config, REQUEST_CODE);
    }

    /**
     * 删除照片的接口
     */
    private void inItNetWorkDelete() {
        SquareRetrofitHttp.getHttp().deleteUserImage("").enqueue(new Callback<SuccessfulBean>() {
            @Override
            public void onResponse(Call<SuccessfulBean> call, Response<SuccessfulBean> response) {

            }

            @Override
            public void onFailure(Call<SuccessfulBean> call, Throwable t) {

            }
        });
    }

    /**
     * 管理相册的按钮
     */
    private void managePhoto() {
        isManageNext = !isManage;
        isManage = !isManage;
        LogUtil.d(getLocalClassName(), "" + isManageNext);
        if (isManageNext) {
            mMiImageTitle.setText("管理照片");
            mMiImageManage.setText("取消");
            mMiImageManage.setTextColor(getResources().getColor(R.color.RED_FB4E30));
            mMiPhotoAdapter.setImageManage(isManageNext);
            mAlbumUpload.setVisibility(View.GONE);
            mAlbumDelete.setVisibility(View.VISIBLE);
        } else {
            mMiImageTitle.setText("我的照片");
            mMiImageManage.setText("管理");
            mMiImageManage.setTextColor(getResources().getColor(R.color.text333));
            mMiPhotoAdapter.setImageManage(isManageNext);
            mAlbumUpload.setVisibility(View.VISIBLE);
            mAlbumDelete.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            for (String path : pathList) {
                LogUtil.d("返回的地址", path + "\n");
            }
        }
    }
}

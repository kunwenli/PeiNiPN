package com.jsz.peini.ui.activity.square;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.model.square.UserImageAllByUserId;
import com.jsz.peini.presenter.square.SquareService;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.LoginDialogUtils;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.ToastUtils;

import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by th on 2017/2/8.
 */
public class TaPhotoImageActivity extends BaseActivity {
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.right_button)
    TextView mRightButton;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.photo_image)
    RecyclerView mPhotoImage;
    public TaPhotoImageActivity mActivity;

    @Override
    public int initLayoutId() {
        return R.layout.activity_taphotoimage;
    }

    @Override
    public void initView() {
        mActivity = this;
        mTitle.setText("他人相册");
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {

        inItNetWork();
    }

    private void inItNetWork() {
        RetrofitUtil.createService(SquareService.class)
                .getUserImageAllByUserId(mUserToken)
                .enqueue(new Callback<UserImageAllByUserId>() {
                    @Override
                    public void onResponse(Call<UserImageAllByUserId> call, Response<UserImageAllByUserId> response) {
                        if (response.isSuccessful()) {
                            UserImageAllByUserId body = response.body();
                            int resultCode = body.getResultCode();
                            ToastUtils.showToast(mActivity, body.getResultDesc());
                            if (resultCode == 1) {
                                LogUtil.d("相册列表---" + body.toString());
                            } else if (resultCode == 9) {
                                LoginDialogUtils.isNewLogin(mActivity);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserImageAllByUserId> call, Throwable t) {

                    }
                });
    }
}

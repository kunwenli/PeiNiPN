package com.jsz.peini.ui.activity.square;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.listener.RetrofitCallback;
import com.jsz.peini.model.setting.SuccessfulBean;
import com.jsz.peini.presenter.square.SquareService;
import com.jsz.peini.utils.RetrofitUtil;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by th on 2017/2/7.
 */
public class NickNameActivity extends BaseActivity {

    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.right_button)
    TextView mRightButton;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.nickname)
    EditText mNickname;
    @InjectView(R.id.delete_nickname)
    ImageView mDeleteNickname;
    private String mType;
    private String mStringExtra;

    @Override
    public int initLayoutId() {
        return R.layout.activity_nickname;
    }

    @Override
    public void initView() {
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRightButton.setText("保存");


        Intent intent = getIntent();
        mType = intent.getStringExtra("type");
        mStringExtra = intent.getStringExtra("title");
        mTitle.setText(mStringExtra);
    }

    @Override
    public void initData() {

    }

    private void initBNetWork(String result) {
        RetrofitUtil.createService(SquareService.class)
                .updateUserSignWord(mUserToken, result)
                .enqueue(new RetrofitCallback<SuccessfulBean>() {
                    @Override
                    public void onSuccess(Call<SuccessfulBean> call, Response<SuccessfulBean> response) {
                        if (response.isSuccessful()) {
                            SuccessfulBean body = response.body();
                            if (body.getResultCode() == 1) {
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessfulBean> call, Throwable t) {

                    }
                });
    }

    @OnClick({R.id.right_button, R.id.delete_nickname})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_button:
                String result = mNickname.getText().toString().trim();
                if (mType.equals("1")) {
                    initBNetWork(result);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("name", result);
                    setResult(0, intent);
                    //    结束当前这个Activity对象的生命
                    finish();
                }

                break;
            case R.id.delete_nickname:
                mNickname.setText("");
                break;
        }
    }
}

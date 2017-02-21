package com.jsz.peini.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hyphenate.easeui.EaseConstant;
import com.jsz.peini.R;
import com.jsz.peini.base.BaseFragment;
import com.jsz.peini.model.ad.AdModel;
import com.jsz.peini.model.setting.SuccessfulBean;
import com.jsz.peini.model.square.AddCommentListBean;
import com.jsz.peini.model.square.AddLikeListBean;
import com.jsz.peini.model.square.SquareBean;
import com.jsz.peini.presenter.ad.Ad;
import com.jsz.peini.presenter.square.SquareService;
import com.jsz.peini.ui.activity.square.MiSquareActivity;
import com.jsz.peini.ui.activity.square.TaSquareActivity;
import com.jsz.peini.ui.adapter.square.SquareAdapter;
import com.jsz.peini.ui.view.Popwindou;
import com.jsz.peini.utils.Conversion;
import com.jsz.peini.utils.KeyBoardUtils;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.LoginDialogUtils;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.StringUtils;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.utils.UiUtils;
import com.jsz.peini.widget.RecyclerView.LoadMoreFooterView;
import com.jsz.peini.widget.RecyclerView.RefreshHeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kunwe on 2016/11/25.
 * 广场界面
 */

public class SquareFragment extends BaseFragment {
    @InjectView(R.id.square_input_field)
    EditText mSquareInputField;
    @InjectView(R.id.square_send)
    Button mSquareSend;
    @InjectView(R.id.square_input)
    LinearLayout mSquareInput;
    @InjectView(R.id.swipe_refresh_header)
    RefreshHeaderView mSwipeRefreshHeader;
    @InjectView(R.id.swipe_target)
    RecyclerView mSwipeTarget;
    @InjectView(R.id.swipe_load_more_footer)
    LoadMoreFooterView mSwipeLoadMoreFooter;
    @InjectView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;
    @InjectView(R.id.square_swipetoloadlayout)
    LinearLayout mSquareSwipetoloadlayout;
    private View mHeadView;
    private String mUserId;
    private int mId;
    private int mPosition;
    private String mToUserId;
    private String mContent;
    private String mSpId;
    private boolean mIsShowEdittext;
    private SquareAdapter mSquareAdapter;
    private int mIndex;
    private Popwindou pop;
    private String mTrim;
    private Intent mIntent;


    /**
     * 空间的数据
     */
    List<SquareBean.SquareListBean> mSquareList;

    /**
     * 广场的广告信息
     */
    List<AdModel.AdvertiseListBean> mAdModels;
    public View mView;
    private String mString;
    private String mType = "0";

    @Override
    public View initViews() {
        return View.inflate(mActivity, R.layout.fragment_square, null);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    initNetWork();
                    //execute the task
                }
            }, 2000);
        } else {
            //相当于Fragment的onPause
        }
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        mDialog.show();
        mSquareList = new ArrayList<>();
        mAdModels = new ArrayList<>();
        mSquareInput.setVisibility(View.GONE);
        mSquareAdapter = new SquareAdapter(mActivity, mSquareList, mAdModels);
        mHeadView = View.inflate(mActivity, R.layout.square_head, null);
        mSquareAdapter.addHeadView(mHeadView);
        LinearLayoutManager layout = new LinearLayoutManager(mActivity);
        mSwipeTarget.setLayoutManager(layout);
        mSwipeTarget.setAdapter(mSquareAdapter);
        initModel();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        mType = arguments.getString(Conversion.TYPE);
        LogUtil.d("mType" + mType);
        super.onActivityCreated(savedInstanceState);

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Conversion.DATASUCCESS:
                    SquareBean squareBean = (SquareBean) msg.obj;
                    mSquareList.clear();
                    mSquareList.addAll(squareBean.getSquareList());
                    mSquareAdapter.notifyDataSetChanged();
                    mDialog.dismiss();
                    break;
                case Conversion.DATA_SUCCESS:
                    AdModel adModel = (AdModel) msg.obj;
                    mAdModels.clear();
                    mAdModels.addAll(adModel.getAdvertiseList());
                    mSquareAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    /**
     * 发起网络请求
     */
    private void initNetWork() {
        /**获取广告界面*/
        RetrofitUtil.createService(Ad.class).getAdvertise("4").enqueue(new Callback<AdModel>() {
            @Override
            public void onResponse(Call<AdModel> call, Response<AdModel> response) {
                if (response.isSuccessful()) {
                    AdModel body = response.body();
                    LogUtil.d("广告信息---" + body.toString());
                    if (body.getResultCode() == 1) {
                        Message message = new Message();
                        message.what = Conversion.DATA_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    }
                }

            }

            @Override
            public void onFailure(Call<AdModel> call, Throwable t) {
                ToastUtils.showToast(mActivity, t.getMessage());
            }
        });
        /*获取空间数据*/
        RetrofitUtil.createService(SquareService.class)
                .getSquareInfoList(mUserToken, "", mType, "1", "10")
                .enqueue(new Callback<SquareBean>() {
                    @Override
                    public void onResponse(Call<SquareBean> call, Response<SquareBean> response) {
                        if (response.isSuccessful()) {
                            int resultCode = response.body().getResultCode();
                            SquareBean body = response.body();
                            if (resultCode == 1) {
                                Message message = new Message();
                                message.what = Conversion.DATASUCCESS;
                                message.obj = response.body();
                                mHandler.sendMessage(message);
                            } else if (resultCode == 9) {
                                LoginDialogUtils.isNewLogin(mActivity);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<SquareBean> call, Throwable t) {
                        ToastUtils.showToast(mActivity, t.getMessage());
                    }
                });

    }

    /**
     * 滑动的监听
     */
    public void initModel() {
        mSwipeTarget.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LogUtil.i("这个是广场界面滑动的监听", "滑动的监听--" + newState + "");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LogUtil.i("这个是广场界面滑动的监听", "滑动的监听--" + dx + "--" + dy);
                if (dy != 0) {
                    isShowEdittext(false, "");
                }
            }
        });
        mSquareAdapter.setOnCommentClickListener(new SquareAdapter.OnCommentClickListener() {
            @Override
            public void OnUserId(String id, String userId, int position, int SpId) {
                LogUtil.i("这个是主界面的回调", "-" + id + "-" + userId + "-" + position);
                startSquareActivity(userId);
            }

            @Override
            public void OnToUserId(String id, String toUserId, int position, int SpId) {
                startSquareActivity(toUserId);
            }

            @Override
            public void OnContent(String id, String UserId, String UserNickname, String toUserId, String ToUserNickname, String Content, int position, int SpId, int index) {
                mString = UserId;
                LogUtil.d("内容文字" + "id--" + id
                        + "UserId--" + UserId
                        + "UserNickname--" + UserNickname
                        + "toUserId--" + toUserId
                        + "ToUserNickname--" + ToUserNickname
                        + "Content--" + Content
                        + "position--" + position
                        + "SpId--" + SpId
                        + "index--" + index);
                mId = mSquareList.get(index).getId();
                mPosition = position;
                mContent = Content;
                mSpId = mSquareList.get(index).getCommentList().get(position).getId() + "";
                mUserId = muId;
                mToUserId = mSquareList.get(index).getCommentList().get(position).getToUserId();
                mIndex = index;

                if (UserId.equals(mUserToken)) {
                    LogUtil.i("这个是广场界面", "这里要执行删除操作muId" + muId + "mUserId" + mUserId);
                    initSelect();
                } else {
                    isShowEdittext(true, UserNickname);
                }


                LogUtil.i("这个是主界面的回调整个条目的id", "mSpId" + mSpId);
            }

            @Override
            public void OnItemClick(String id, String Content, int position, int SpId) {
            }

            @Override
            public void OnOneItemClick(int position, int SpId) {
            }

            @Override
            public void OnDelete(int index) {
                String id = mSquareList.get(index).getId() + "";
                LogUtil.d("执行空间的删除操作--删除的ID--" + id);
                if (StringUtils.isNoNull(id)) {
                    initNetWorkDelete(id);
                }
            }

            @Override
            public void OnContentId(int index) {
                mId = mSquareList.get(index).getId();
                mUserId = muId;
                mToUserId = "0";
                isShowEdittext(true, "");
            }

            @Override
            public void OnLike(int index) {
                int id = mSquareList.get(index).getId();
                String userId = mSquareList.get(index).getUserId();
                LogUtil.e("点赞的按钮", "广场的ID" + id + "UserId" + userId);
                goSquareLike(id + "", muId + "");
            }

            @Override
            public void onID(int index, String id, String userId) {
                startSquareActivity(mSquareList.get(index).getUserId() + "");
            }
        });
    }

    /**
     * 跳转界面 到个人控件
     */
    private void startSquareActivity(String userId) {
        if (mUserToken.equals(userId)) {
            mIntent = new Intent(mActivity, MiSquareActivity.class);
        } else {
            mIntent = new Intent(mActivity, TaSquareActivity.class);
        }
        LogUtil.d("点击后面人的id" + mUserToken + "------------" + userId);
        mIntent.putExtra("userId", userId);
        startActivity(mIntent);
    }

    /**
     * 删除按钮
     *
     * @param id
     */
    private void initNetWorkDelete(String id) {
        RetrofitUtil.createService(SquareService.class).deleteSquareAll(mUserToken, id)
                .enqueue(new Callback<SuccessfulBean>() {
                    @Override
                    public void onResponse(Call<SuccessfulBean> call, Response<SuccessfulBean> response) {
                        if (response.isSuccessful()) {
                            SuccessfulBean body = response.body();
                            if (body.getResultCode() == 1) {
                                LogUtil.d("删除成功返回的数据" + response.body().toString());
                                initNetWork();
                            } else if (body.getResultCode() == 9) {
                                LogUtil.d("删除失败返回的数据 userToken 失效" + response.body().toString());
                            } else {
                                LogUtil.d("删除失败" + response.body().toString());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessfulBean> call, Throwable t) {
                        LogUtil.d("删除失败" + t.getMessage());
                    }
                });
    }


    @OnClick(R.id.square_send)
    public void onClick() {
        mTrim = mSquareInputField.getText().toString().trim();
        if (TextUtils.isEmpty(mTrim)) {
            LogUtil.i("广场主界面", "没有输入内容 不用请求网络");
            isShowEdittext(false, "");
            return;
        }
        initNetWorkCommon(mId + "", mUserToken, mString, mTrim);
        KeyBoardUtils.hideKeyBoard(mActivity, mSquareInputField);
        mSquareInput.setVisibility(View.GONE);

    }

    /**
     * squareInfoId	Int		广场id
     * userId	Int		评论用户id
     * toUserId	Int		被回复用户id
     * content	String		评论内容
     * 这个是评论的数据
     */
    private void initNetWorkCommon(String squareInfoId, String mUserId, String mToUserId, String content) {
        LogUtil.i("这个是广场界面的唯一标识", "第一个参数" + squareInfoId);
        LogUtil.i("这个是广场界面的唯一标识", "第二个参数" + mUserId);
        LogUtil.i("这个是广场界面的唯一标识", "第三个参数" + mToUserId);
        LogUtil.i("这个是广场界面的唯一标识", "第四个参数" + content);
        RetrofitUtil.createService(SquareService.class).goComment(squareInfoId, mUserId, mToUserId, content)
                .enqueue(new Callback<AddCommentListBean>() {
                    @Override
                    public void onResponse(Call<AddCommentListBean> call, Response<AddCommentListBean> response) {
                        if (response.isSuccessful()) {
                            AddCommentListBean body = response.body();
                            if (body.getResultCode() == 1) {
                                initNetWork();
                                KeyBoardUtils.hideKeyBoard(mActivity, mSquareInputField);
                            } else if (body.getResultCode() == 9) {
                                LoginDialogUtils.isNewLogin(mActivity);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AddCommentListBean> call, Throwable t) {
                        ToastUtils.showToast(mActivity, t.getMessage());
                    }
                });
    }

    /**
     * 删除的按钮
     */
    private void initDelete(String id, String spId, final int position) {
        RetrofitUtil.createService(SquareService.class)
                .delComment(spId, mUserToken)
                .enqueue(new Callback<SuccessfulBean>() {
                    @Override
                    public void onResponse(Call<SuccessfulBean> call, Response<SuccessfulBean> response) {
                        if (response.isSuccessful()) {
                            SuccessfulBean body = response.body();
                            if (body.getResultCode() == 1) {
                                initNetWork();
                            } else if (body.getResultCode() == 9) {
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
     * 这个 是点赞的按钮
     */
    private void goSquareLike(String squareId, String userid) {
        RetrofitUtil.createService(SquareService.class).goSquareLike(squareId, userid)
                .enqueue(new Callback<AddLikeListBean>() {
                    @Override
                    public void onResponse(Call<AddLikeListBean> call, Response<AddLikeListBean> response) {
                        if (response.isSuccessful()) {
                            AddLikeListBean body = response.body();
                            if (body.getResultCode() == 1) {
                                initNetWork();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AddLikeListBean> call, Throwable t) {

                    }
                });
    }

    private void initSelect() {
        pop = new Popwindou(mActivity, UiUtils.inflate(mActivity, R.layout.fragment_square));
        View mView = UiUtils.inflate(mActivity, R.layout.item_popupwindows_delete);
        pop.init(mView, Gravity.BOTTOM, true);
        mView.findViewById(R.id.item_popupwindows_Photo).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LogUtil.d("这个是要删除的弹窗" + mId + " mspis" + mSpId + "mp" + mPosition);
                initDelete(mId + "", mSpId, mPosition);
                pop.dismiss();
            }
        });
        mView.findViewById(R.id.item_popupwindows_cancel).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();

            }
        });
    }

    public void isShowEdittext(boolean isshow, String userNickname) {
        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString("回复:" + userNickname);
        SpannableString nulls = new SpannableString(userNickname);
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(15, true);
        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置hint
        mSquareInputField.setHint(new SpannedString(ss));
        mSquareInput.setVisibility(isshow ? View.VISIBLE : View.GONE);
        mSquareInputField.requestFocus();
        InputMethodManager imm = (InputMethodManager) mSquareInputField.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isshow) {
            mSquareInputField.setHint(new SpannedString(ss));
            imm.showSoftInput(mSquareInputField, 0);
        } else {
            mSquareInputField.setHint(new SpannedString(nulls));
            imm.hideSoftInputFromWindow(mSquareInputField.getWindowToken(), 0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                initNetWork();
                KeyBoardUtils.hideKeyBoard(mActivity, new View(mActivity));
            }
        }).start();
    }
}

package com.jsz.peini.ui.fragment.news;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.jsz.peini.R;
import com.jsz.peini.base.BaseFragment;
import com.jsz.peini.listener.RetrofitCallback;
import com.jsz.peini.san.huanxin.HuanXinService;
import com.jsz.peini.san.huanxin.HuanxinHeadBean;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.UiUtils;
import com.jsz.peini.widget.RecyclerView.LoadMoreFooterView;
import com.jsz.peini.widget.RecyclerView.RefreshHeaderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by kunwe on 2016/12/2.
 */
public class AttentionrateFragment extends BaseFragment {
    private static final int SDK_PAY_FLAG = 456;
    @InjectView(R.id.swipe_refresh_header)
    RefreshHeaderView mSwipeRefreshHeader;
    @InjectView(R.id.swipe_target)
    RecyclerView mSwipeTarget;
    @InjectView(R.id.swipe_load_more_footer)
    LoadMoreFooterView mSwipeLoadMoreFooter;
    @InjectView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;
    List<String> mList;
    public BuddyListAdapter mAdapter;
    public Intent mIntent;
    private StringBuilder mBuilder;
    private List<HuanxinHeadBean.DataBean> mHeadBean;
    private Map<String, EMConversation> mPeiNiFriend;
    private boolean isLock = false;

    @Override
    public View initViews() {
        return UiUtils.inflate(mActivity, R.layout.fragment_attentionrate);
    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    HuanxinHeadBean headBean = (HuanxinHeadBean) msg.obj;
                    LogUtil.d("头像---" + headBean.toString());
                    mHeadBean.clear();
                    mHeadBean.addAll(headBean.getData());
                    mAdapter.notifyDataSetChanged();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    public void initData() {
        mList = new ArrayList<>();
        mBuilder = new StringBuilder();
        mHeadBean = new ArrayList<>();


        getManagerData();

        mSwipeTarget.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new BuddyListAdapter(mActivity, mPeiNiFriend, mList, mHeadBean);
        mSwipeTarget.setAdapter(mAdapter);


    }

    private void getManagerData() {
        mList.clear();
        mHeadBean.clear();
        mBuilder.reverse();
        mPeiNiFriend = EMClient.getInstance().chatManager().getAllConversations();
        LogUtil.d("获取环信回话记录---" + mPeiNiFriend.size());
        for (Map.Entry<String, EMConversation> entry : mPeiNiFriend.entrySet()) {
            String form = entry.getKey();

            mList.add(form);

            mBuilder.append(form + ",");

            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue().getLastMessage());
        }
        if (!mBuilder.toString().isEmpty()) {
            getEnqueue();
        }
    }

    private void getEnqueue() {
        RetrofitUtil.createService(HuanXinService.class)
                .getEmUserHeadAndNickname(mBuilder.toString())
                .enqueue(new RetrofitCallback<HuanxinHeadBean>() {
                    @Override
                    public void onSuccess(Call<HuanxinHeadBean> call, Response<HuanxinHeadBean> response) {
                        if (response.isSuccessful()) {
                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = response.body();
                            mHandler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onFailure(Call<HuanxinHeadBean> call, Throwable t) {

                    }
                });
    }

    @Override
    public void initListentr() {
        mAdapter.setOnClickItemListener(new BuddyListAdapter.OnClickItemListener() {
            @Override
            public void ClickItem(String username, String to, int position) {
                EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
                conversation.markAllMessagesAsRead();
                //跳转聊天界面
                Intent intent = new Intent(mActivity, ChatActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }
}

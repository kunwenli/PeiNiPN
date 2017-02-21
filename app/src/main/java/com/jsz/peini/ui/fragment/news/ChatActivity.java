package com.jsz.peini.ui.fragment.news;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.jsz.peini.R;
import com.jsz.peini.base.BaseActivity;
import com.jsz.peini.utils.EMUtils;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.StringUtils;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.widget.AudioRecordButton;
import com.jsz.peini.widget.RecyclerView.LoadMoreFooterView;
import com.jsz.peini.widget.RecyclerView.RefreshHeaderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by th on 2017/1/25.
 */
public class ChatActivity extends BaseActivity {
    public ChatActivity mActivity;
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.right_button)
    TextView mRightButton;
    @InjectView(R.id.toolbar)
    LinearLayout mToolbar;
    @InjectView(R.id.swipe_refresh_header)
    RefreshHeaderView mSwipeRefreshHeader;
    @InjectView(R.id.swipe_target)
    RecyclerView mSwipeTarget;
    @InjectView(R.id.swipe_load_more_footer)
    LoadMoreFooterView mSwipeLoadMoreFooter;
    @InjectView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;
    List<PeiniMessage> mList;
    @InjectView(R.id.chat_more)
    LinearLayout mChatMore;
    @InjectView(R.id.send_more)
    Button mSendMore;
    @InjectView(R.id.voice_btn)
    AudioRecordButton mVoiceBtn;
    @InjectView(R.id.send_content)
    TextView mSendContent;
    @InjectView(R.id.content)
    EditText mContent;
    private String mUsername;

    @Override
    public int initLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    public void initView() {
        mActivity = this;
        mList = new ArrayList<>();
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        mUsername = intent.getStringExtra("username");
        mTitle.setText(mUsername);

        /**获取聊天记录*/
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(mUsername);
        //获取此会话的所有消息
        List<EMMessage> messages = conversation.getAllMessages();
        LogUtil.d("获取此会话的所有消息" + messages.size());
        for (int i = 0; i < messages.size(); i++) {
            EMMessage emMessage = messages.get(i);
            LogUtil.d("emMessage.getType()" + emMessage.getType());
            mList.add(new PeiniMessage(emMessage.getFrom(), emMessage.getTo(), emMessage.getType(), emMessage.getBody()));

        }
        mSwipeToLoadLayout.setLoadMoreEnabled(false);
        mSwipeTarget.setLayoutManager(new LinearLayoutManager(mActivity));
        ChatAdapter adapter = new ChatAdapter(mActivity, mList, mPhone);
        mSwipeTarget.setAdapter(adapter);
        mSwipeTarget.smoothScrollToPosition(mList.size());
    }

    @Override
    protected void initListener() {
        mVoiceBtn.setAudioFinishRecorderListener(new AudioRecordButton.AudioFinishRecorderListener() {

            @Override
            public void onFinished(float seconds, String filePath) {
                EMUtils.SendVoice(mActivity, filePath, seconds, mUsername);
            }

            @Override
            public void onStart() {

            }
        });
    }

    @OnClick({R.id.send_more, R.id.send_content})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_more:
                mChatMore.setVisibility(mChatMore.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            //发送文本消息
            case R.id.send_content:
                String trim = mContent.getText().toString().trim();
                if (StringUtils.isNoNull(trim)) {
                    EMUtils.SendCanten(mActivity, trim, mUsername);
                } else {
                    ToastUtils.showToast(mActivity, "空白内容! ");
                }
                break;
        }
    }
}


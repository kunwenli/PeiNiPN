package com.jsz.peini.san.huanxin.fragment;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.jsz.peini.utils.LogUtil;

/**
 * Created by 15089 on 2017/2/20.
 */

public class ChatFragment extends EaseChatFragment {
    @Override
    protected void registerExtendMenuItem() {
        super.registerExtendMenuItem();
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        //设置item里的控件的点击事件
        messageList.setItemClickListener(new EaseChatMessageList.MessageListItemClickListener() {

            @Override
            public void onUserAvatarClick(String username) {
                //头像点击事件
                LogUtil.d("点击事件--头像" + username);
            }

            @Override
            public void onUserAvatarLongClick(String username) {

            }

            @Override
            public void onResendClick(final EMMessage message) {
                //重发消息按钮点击事件
            }

            @Override
            public void onBubbleLongClick(EMMessage message) {
                //气泡框长按事件
            }

            @Override
            public boolean onBubbleClick(EMMessage message) {
                //气泡框点击事件，EaseUI有默认实现这个事件，如果需要覆盖，return值要返回true
                return false;
            }
        });
    }
}

package com.jsz.peini.utils;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.Map;

/**
 * Created by th on017/1/25.
 */

public class EMUtils {

    public static Activity activity;

    /**
     * 获取好友列表
     */
    public static Map<String, EMConversation> getPeiNiFriend(final Activity activity) {
        Gson gson = new Gson();
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        return conversations;
    }

    /**
     * 发送文本消息
     */
    public static void SendCanten(Activity activity, String mContent, String toChatUsername) {
        EMUtils.activity = activity;
        EMMessage message = EMMessage.createTxtSendMessage(mContent, toChatUsername);
        EMClient.getInstance().chatManager().sendMessage(message);
        message.setMessageStatusCallback(getCallback());
    }

    /**
     * 发送文本消息
     */
    public static void SendVoice(Activity activity, String filePath, float length, String toChatUsername) {
        EMUtils.activity = activity;
        //filePath为语音文件路径，length为录音时间(秒)
        EMMessage message = EMMessage.createVoiceSendMessage(filePath, (int) length, toChatUsername);
        EMClient.getInstance().chatManager().sendMessage(message);
        message.setMessageStatusCallback(getCallback());
    }

    /**
     * 发送图片
     */
    public static void SendVoiceImage(String imagePath, boolean b, String toChatUsername) {
        //imagePath为图片本地路径，false为不发送原图（默认超过100k的图片会压缩后发给对方），需要发送原图传true
        EMMessage emMessage = EMMessage.createImageSendMessage(imagePath, b, toChatUsername);
        EMClient.getInstance().chatManager().sendMessage(emMessage);
    }

    @NonNull
    private static EMCallBack getCallback() {
        return new EMCallBack() {
            @Override
            public void onSuccess() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(activity, "发送成功");
                    }
                });
            }

            @Override
            public void onError(int code, String error) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(activity, "发送失败");
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(activity, "正在发送");
                    }
                });
            }
        };
    }
}

package com.jsz.peini.ui.fragment.news;


import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.jsz.peini.R;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.widget.BubbleImageView;
import com.jsz.peini.widget.GifTextView;

import java.util.List;

/**
 * Created by th on 2017/1/25.
 */
public class ChatAdapter extends RecyclerView.Adapter {
    public static final int FROM_USER_MSG = 0;//接收消息类型
    public static final int TO_USER_MSG = 1;//发送消息类型

    public static final int FROM_USER_IMG = 2;//接收消息类型
    public static final int TO_USER_IMG = 3;//发送消息类型

    public static final int FROM_USER_VOICE = 4;//接收消息类型
    public static final int TO_USER_VOICE = 5;//发送消息类型
    public final ChatActivity mActivity;
    public final List<PeiniMessage> mList;
    public final Handler handler;
    private final String mPhone;
    public boolean isGif = true;

    public ChatAdapter(ChatActivity activity, List<PeiniMessage> list, String phone) {
        mActivity = activity;
        mList = list;
        handler = new Handler();
        mPhone = phone;
    }

    public void setIsGif(boolean isGif) {
        this.isGif = isGif;
    }

    @Override
    public int getItemViewType(int position) {
        LogUtil.d("position"+position);
        PeiniMessage peiniMessage = mList.get(position);
        LogUtil.d("mPhone" + mPhone + "peiniMessage.getTo()" + peiniMessage.getTo());
        if (peiniMessage.getType().equals(EMMessage.Type.TXT) && peiniMessage.getTo() != mPhone) {
            return 0;
        } else if (peiniMessage.getType().equals(EMMessage.Type.TXT) && peiniMessage.getTo().equals(mPhone)) {
            return 1;
        }
        if (peiniMessage.getType().equals(EMMessage.Type.IMAGE) && peiniMessage.getTo() != mPhone) {
            return 2;
        } else if (peiniMessage.getType().equals(EMMessage.Type.IMAGE) && peiniMessage.getTo().equals(mPhone)) {
            return 3;
        }
        if (peiniMessage.getType().equals(EMMessage.Type.VOICE) && peiniMessage.getTo() != mPhone) {
            return 4;
        } else if (peiniMessage.getType().equals(EMMessage.Type.VOICE) && peiniMessage.getTo().equals(mPhone)) {
            return 5;
        } else {
            return 0;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case FROM_USER_MSG:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_msgfrom_list_item, parent, false);
                holder = new FromUserMsgViewHolder(view);
                break;
            case FROM_USER_IMG:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_imagefrom_list_item, parent, false);
                holder = new FromUserImageViewHolder(view);
                break;
            case FROM_USER_VOICE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_voicefrom_list_item, parent, false);
                holder = new FromUserVoiceViewHolder(view);
                break;
            case TO_USER_MSG:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_msgto_list_item, parent, false);
                holder = new ToUserMsgViewHolder(view);
                break;
            case TO_USER_IMG:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_imageto_list_item, parent, false);
                holder = new ToUserImgViewHolder(view);
                break;
            case TO_USER_VOICE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_voiceto_list_item, parent, false);
                holder = new ToUserVoiceViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PeiniMessage peiniMessage = mList.get(position);
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case FROM_USER_MSG:
                fromMsgUserLayout((FromUserMsgViewHolder) holder, peiniMessage, position);
                break;
            case FROM_USER_IMG:
                fromImgUserLayout((FromUserImageViewHolder) holder, peiniMessage, position);
                break;
            case FROM_USER_VOICE:
                fromVoiceUserLayout((FromUserVoiceViewHolder) holder, peiniMessage, position);
                break;
            case TO_USER_MSG:
                toMsgUserLayout((ToUserMsgViewHolder) holder, peiniMessage, position);
                break;
            case TO_USER_IMG:
                toImgUserLayout((ToUserImgViewHolder) holder, peiniMessage, position);
                break;
            case TO_USER_VOICE:
                toVoiceUserLayout((ToUserVoiceViewHolder) holder, peiniMessage, position);
                break;
        }
    }

    /**
     * 发送的语音
     */
    private void toVoiceUserLayout(ToUserVoiceViewHolder holder, PeiniMessage peiniMessage, int position) {

    }

    /**
     * 发送的图片
     */
    private void toImgUserLayout(ToUserImgViewHolder holder, PeiniMessage peiniMessage, int position) {

    }

    /**
     * 发送的消息
     */
    private void toMsgUserLayout(ToUserMsgViewHolder holder, PeiniMessage peiniMessage, int position) {
        holder.content.setSpanText(handler, peiniMessage.getContent().toString(), isGif);
    }

    /**
     * 接收到的语音
     */
    private void fromVoiceUserLayout(FromUserVoiceViewHolder holder, PeiniMessage peiniMessage, int position) {

    }

    /**
     * 接收到的图片
     */
    private void fromImgUserLayout(FromUserImageViewHolder holder, PeiniMessage peiniMessage, int position) {

    }

    /*接受到的消息*/
    private void fromMsgUserLayout(FromUserMsgViewHolder holder, PeiniMessage peiniMessage, int position) {
        holder.content.setSpanText(handler, peiniMessage.getContent().toString(), isGif);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class FromUserMsgViewHolder extends RecyclerView.ViewHolder {
        private ImageView headicon;
        private TextView chat_time;
        private GifTextView content;

        public FromUserMsgViewHolder(View view) {
            super(view);
            headicon = (ImageView) view
                    .findViewById(R.id.tb_other_user_icon);
            chat_time = (TextView) view.findViewById(R.id.chat_time);
            content = (GifTextView) view.findViewById(R.id.content);
        }
    }

    class FromUserImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView headicon;
        private TextView chat_time;
        private BubbleImageView image_Msg;

        public FromUserImageViewHolder(View view) {
            super(view);
            headicon = (ImageView) view
                    .findViewById(R.id.tb_other_user_icon);
            chat_time = (TextView) view.findViewById(R.id.chat_time);
            image_Msg = (BubbleImageView) view
                    .findViewById(R.id.image_message);
        }
    }

    class FromUserVoiceViewHolder extends RecyclerView.ViewHolder {
        private ImageView headicon;
        private TextView chat_time;
        private LinearLayout voice_group;
        private TextView voice_time;
        private FrameLayout voice_image;
        private View receiver_voice_unread;
        private View voice_anim;

        public FromUserVoiceViewHolder(View view) {
            super(view);
            headicon = (ImageView) view
                    .findViewById(R.id.tb_other_user_icon);
            chat_time = (TextView) view.findViewById(R.id.chat_time);
            voice_group = (LinearLayout) view
                    .findViewById(R.id.voice_group);
            voice_time = (TextView) view
                    .findViewById(R.id.voice_time);
            receiver_voice_unread = (View) view
                    .findViewById(R.id.receiver_voice_unread);
            voice_image = (FrameLayout) view
                    .findViewById(R.id.voice_receiver_image);
            voice_anim = (View) view
                    .findViewById(R.id.id_receiver_recorder_anim);
        }
    }

    class ToUserMsgViewHolder extends RecyclerView.ViewHolder {
        private ImageView headicon;
        private TextView chat_time;
        private GifTextView content;
        private ImageView sendFailImg;

        public ToUserMsgViewHolder(View view) {
            super(view);
            headicon = (ImageView) view
                    .findViewById(R.id.tb_my_user_icon);
            chat_time = (TextView) view
                    .findViewById(R.id.mychat_time);
            content = (GifTextView) view
                    .findViewById(R.id.mycontent);
            sendFailImg = (ImageView) view
                    .findViewById(R.id.mysend_fail_img);
        }
    }

    class ToUserImgViewHolder extends RecyclerView.ViewHolder {
        private ImageView headicon;
        private TextView chat_time;
        private LinearLayout image_group;
        private BubbleImageView image_Msg;
        private ImageView sendFailImg;

        public ToUserImgViewHolder(View view) {
            super(view);
            headicon = (ImageView) view
                    .findViewById(R.id.tb_my_user_icon);
            chat_time = (TextView) view
                    .findViewById(R.id.mychat_time);
            sendFailImg = (ImageView) view
                    .findViewById(R.id.mysend_fail_img);
            image_group = (LinearLayout) view
                    .findViewById(R.id.image_group);
            image_Msg = (BubbleImageView) view
                    .findViewById(R.id.image_message);
        }
    }

    class ToUserVoiceViewHolder extends RecyclerView.ViewHolder {
        private ImageView headicon;
        private TextView chat_time;
        private LinearLayout voice_group;
        private TextView voice_time;
        private FrameLayout voice_image;
        private View receiver_voice_unread;
        private View voice_anim;
        private ImageView sendFailImg;

        public ToUserVoiceViewHolder(View view) {
            super(view);
            headicon = (ImageView) view
                    .findViewById(R.id.tb_my_user_icon);
            chat_time = (TextView) view
                    .findViewById(R.id.mychat_time);
            voice_group = (LinearLayout) view
                    .findViewById(R.id.voice_group);
            voice_time = (TextView) view
                    .findViewById(R.id.voice_time);
            voice_image = (FrameLayout) view
                    .findViewById(R.id.voice_image);
            voice_anim = (View) view
                    .findViewById(R.id.id_recorder_anim);
            sendFailImg = (ImageView) view
                    .findViewById(R.id.mysend_fail_img);
        }
    }
}

package com.jsz.peini.ui.fragment.news;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.jsz.peini.R;
import com.jsz.peini.san.huanxin.HuanxinHeadBean;
import com.jsz.peini.utils.Bitmap.GlideImgManager;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.UiUtils;
import com.jsz.peini.utils.time.TimeUtils;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by th on 2017/1/25.
 */
public class BuddyListAdapter extends RecyclerView.Adapter {

    public final Activity mActivity;
    public final Map<String, EMConversation> mList;
    public final List<String> mStrings;
    private final List<HuanxinHeadBean.DataBean> mHeadBean;

    public BuddyListAdapter(Activity activity, Map<String, EMConversation> list, List<String> strings, List<HuanxinHeadBean.DataBean> headBean) {
        mActivity = activity;
        mList = list;
        mStrings = strings;
        mHeadBean = headBean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(UiUtils.inflate(mActivity, R.layout.item_buddylist));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final String key = mStrings.get(position);
        EMConversation emConversation = mList.get(key);

        //最后一条
        String LastMessage = emConversation.getLastMessage().getBody().toString();
        String substring = LastMessage.substring(5, LastMessage.length() - 1);
        viewHolder.mLastMessage.setText(substring);

        //时间
        long time = emConversation.getLastMessage().getMsgTime();
        String s = TimeUtils.longToDate(time, "HH:mm");
        viewHolder.mTime.setText(s);

        //获取未读消息数量
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(key);
        int unreadMsgCount = conversation.getUnreadMsgCount();
        if (unreadMsgCount != 0) {
            LogUtil.d(unreadMsgCount + "unreadMsgCount");
            String Unread = (unreadMsgCount < 100 ? unreadMsgCount : 99) + "";
            viewHolder.mUnread.setText(Unread);
        } else {
            viewHolder.mUnread.setVisibility(View.GONE);
        }

        final String to = emConversation.getLastMessage().getTo();

        //头像
        if (mHeadBean.size() > 0) {
            //获取头像的Bean
            HuanxinHeadBean.DataBean dataBean = mHeadBean.get(position);
            //头像
            GlideImgManager.loadImage(mActivity, dataBean.getHeadImg(), viewHolder.mImagehead);


            //名称
            String from = dataBean.getNickName();
            LogUtil.d("form----------" + from);
            viewHolder.mForm.setText(from);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发送人,接收人,条目
                mListener.ClickItem(key, to, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHeadBean.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.imagehead)
        CircleImageView mImagehead;
        @InjectView(R.id.Unread)
        TextView mUnread;
        @InjectView(R.id.form)
        TextView mForm;
        @InjectView(R.id.time)
        TextView mTime;
        @InjectView(R.id.LastMessage)
        TextView mLastMessage;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    private OnClickItemListener mListener;

    public void setOnClickItemListener(OnClickItemListener listener) {
        mListener = listener;
    }

    public interface OnClickItemListener {
        void ClickItem(String username, String to, int position);
    }
}

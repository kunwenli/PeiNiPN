package com.jsz.peini.san.huanxin.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.jsz.peini.san.huanxin.activity.ChatHuanXinActivity;
import com.jsz.peini.utils.LogUtil;

/**
 * Created by 15089 on 2017/2/18.
 */
public class HuanXinFragment extends EaseConversationListFragment {

    private Intent mIntent;
    private EMConversation mConversation;

    @Override
    protected void setUpView() {
        super.setUpView();
        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mConversation = conversationListView.getItem(position);
                mIntent = new Intent(getActivity(), ChatHuanXinActivity.class)
                        .putExtra(EaseConstant.EXTRA_USER_ID, mConversation.conversationId());
                startActivity(mIntent);
            }
        });
        conversationListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                LogUtil.d("删除" + position);
                new AlertDialog.Builder(getActivity())
                        .setMessage("删除列表及记录")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteItem(position);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                return true;
            }
        });
    }

    /**
     * 删除聊天列表记录
     */
    private void deleteItem(int position) {
        mConversation = conversationListView.getItem(position);
        String from = mConversation.conversationId();
        conversationList.remove(position);
        conversationListView.refresh();
        //删除和某个user会话，如果需要保留聊天记录，传false
        EMClient.getInstance().chatManager().deleteConversation(from, true);
    }


}

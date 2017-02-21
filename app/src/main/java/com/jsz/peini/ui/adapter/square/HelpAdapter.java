package com.jsz.peini.ui.adapter.square;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jsz.peini.R;
import com.jsz.peini.ui.activity.square.IntegraHelpActivity;
import com.jsz.peini.utils.ToastUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.jsz.peini.utils.UiUtils.getResources;

/**
 * Created by th on 2017/1/17.
 */
public class HelpAdapter extends RecyclerView.Adapter {

    private static final int ITEM_TAIL = 0;
    private static final int ITEM_TYPE_HEADER_TWO = 1;
    public final IntegraHelpActivity mActivity;
    public final List<String> mList;

    public HelpAdapter(IntegraHelpActivity activity, List<String> list) {
        mActivity = activity;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TAIL) {
            return new ViewHolderTail(LayoutInflater.from(mActivity).inflate(R.layout.item_photo, parent, false));
        } else {
            return new ViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.item_integral_help, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderTail) {
            viewHolderTailData(holder, position);
        } else if (holder instanceof ViewHolder) {
            viewHolder(holder, position);
        }
    }

    private void viewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(mActivity, "--" + position);
            }
        });
    }

    private void viewHolderTailData(RecyclerView.ViewHolder holder, int position) {
        ViewHolderTail holderTail = (ViewHolderTail) holder;
        String i = "13263181110";
        String s = "陪你客服电话: " + i;
        SpannableString ss2 = new SpannableString(s);
        ss2.setSpan(new ClickableSpan() {

            @Override
            public void onClick(View widget) {
                Toast.makeText(mActivity, "13263181110", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
//                super.updateDrawState(ds);
            }
        }, s.length() - i.length(), s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.RED_FB4E30)), s.length() - i.length(), s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //电话
        holderTail.mPeiniPhoto.setText(ss2);
        // 设置tv2为可点击状态
        holderTail.mPeiniPhoto.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mList.size()) {
            return ITEM_TAIL;
        } else {
            return ITEM_TYPE_HEADER_TWO;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.text_title)
        TextView mTextTitle;
        @InjectView(R.id.text)
        TextView mText;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    class ViewHolderTail extends RecyclerView.ViewHolder {
        @InjectView(R.id.peini_photo)
        TextView mPeiniPhoto;

        public ViewHolderTail(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}

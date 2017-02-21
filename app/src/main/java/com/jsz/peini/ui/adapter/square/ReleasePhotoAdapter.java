package com.jsz.peini.ui.adapter.square;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jsz.peini.R;
import com.jsz.peini.ui.adapter.BaseRecyclerViewAdapter;
import com.jsz.peini.utils.UiUtils;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.jsz.peini.utils.UiUtils.dip2px;

/**
 * Created by th on 2016/12/27.
 */
public class ReleasePhotoAdapter extends BaseRecyclerViewAdapter {

    private final Activity mActivity;
    private List<String> mList;

    public ReleasePhotoAdapter(Activity context, List<String> list) {
        super(list);
        mList = list;
        mActivity = context;
    }

    @Override
    public void onBindBodyViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (mList.size() != 0) {
            String pathname = mList.get(position);
            File file = new File(pathname);
            Glide.with(mActivity)
                    .load(file)
                    .override(dip2px(mActivity, 100), dip2px(mActivity, 100))
                    .into(holder.mReleasePhoto);
            holder.mReleasePhoto.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(mActivity)
                            .setTitle("确定要删除照片吗?")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    mList.remove(position);
                                    list = mList;
                                    notifyItemRemoved(position);
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
                    return true;
                }
            });
        }
    }

    @Override
    public void onBindFootViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        super.onBindFootViewHolder(viewHolder, position);
        FootViewHolder holder = (FootViewHolder) viewHolder;
        if (mList.size() > 8) {
            holder.mReleasePhotoFoot.setVisibility(View.GONE);
        } else {
            holder.mReleasePhotoFoot.setVisibility(View.VISIBLE);
        }
        holder.mReleasePhotoFoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnPhotoFootClickListener.FootClickListener(position);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder setBodyViewHolder(ViewGroup viewGroup) {
        View view = UiUtils.inflate(mActivity,R.layout.item_release_photo);
        return new ViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder setFootViewHolder(ViewGroup viewGroup, View footView) {
        return new FootViewHolder(footView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.release_photo)
        ImageView mReleasePhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.release_photo_foot)
        ImageView mReleasePhotoFoot;

        public FootViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public OnPhotoFootClickListener mOnPhotoFootClickListener;

    public void setOnPhotoFootClickListener(OnPhotoFootClickListener onPhotoFootClickListener) {
        mOnPhotoFootClickListener = onPhotoFootClickListener;
    }

    public interface OnPhotoFootClickListener {
        void FootClickListener(int position);
    }

    public void setPhotoList(List photoList) {
        mList = photoList;
        notifyDataSetChanged();
    }
}

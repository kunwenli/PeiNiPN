package com.jsz.peini.ui.view;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.jsz.peini.R;


/**
 * Created by th on 2016/12/29.
 */

public class Popwindou {
    private PopupWindow pop; //弹出窗口
    private View parentView; //窗口依赖的父类view
    private Activity mContext;

    public Popwindou(Activity context, View pView) {
        this.mContext = context;
        this.parentView = pView;

    }

    //创建一个panel
    public void init(View view, int gravity, boolean b) {
        pop = new PopupWindow(mContext);
        pop.setContentView(view);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 实例化一个ColorDrawable颜色为半透明
        pop.setFocusable(b); // 设置PopupWindow可获得焦点
        pop.setTouchable(b); // 设置PopupWindow可触摸
        pop.setOutsideTouchable(b); // 设置非PopupWindow区域可触摸
//        pop.setClippingEnabled(false);
        ColorDrawable dw = new ColorDrawable(0x44000000);
        pop.setBackgroundDrawable(dw);
//         设置popWindow的显示和消失动画
        if (b) {
//            pop.setAnimationStyle(R.style.mypopwindow_anim_style);
        }
        pop.showAtLocation(parentView, gravity, 0, 0);
        if (b) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pop.dismiss();
                }
            });
        }
    }

    //关闭
    public void dismiss() {
        if (null != pop) {
            pop.dismiss();
        }
    }
}

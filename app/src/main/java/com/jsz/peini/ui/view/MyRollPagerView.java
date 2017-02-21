package com.jsz.peini.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.jude.rollviewpager.RollPagerView;

/**
 * Created by th on 2016/12/24.
 */

public class MyRollPagerView extends RollPagerView {

    private boolean isLocked;

    public MyRollPagerView(Context context) {
        super(context);
        isLocked =false;
    }

    public MyRollPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        isLocked =false;
    }

    public MyRollPagerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        isLocked =false;
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isLocked) {
            try {
                return super.onInterceptTouchEvent(ev);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return !isLocked && super.onTouchEvent(event);
    }

    public void toggleLock() {
        isLocked = !isLocked;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public boolean isLocked() {
        return isLocked;
    }
}

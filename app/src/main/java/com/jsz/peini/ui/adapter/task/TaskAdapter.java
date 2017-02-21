package com.jsz.peini.ui.adapter.task;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jsz.peini.PeiNiApp;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

/**
 * Created by kunwe on 2016/12/2.
 */
public class TaskAdapter extends StaticPagerAdapter {
    private String[] imgs = {
            "http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg",
            "http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg",
            "http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg",
            "http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg",
            "http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg",
            "http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg",};



    /**
     * Glide.with(PeiNiApp.context)
     * .load("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg")
     * .into(taskIcen);
     */

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        Glide.with(PeiNiApp.context)
                .load(imgs[position])
                .into(view);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }
}
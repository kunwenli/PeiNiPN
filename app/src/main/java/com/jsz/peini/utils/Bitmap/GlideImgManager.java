package com.jsz.peini.utils.Bitmap;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.util.Util;
import com.jsz.peini.R;
import com.jsz.peini.ui.view.GlideRoundTransform;

import java.io.File;

/**
 * Created by th on 2017/1/6.
 */

public class GlideImgManager {

    /*//加载网络图片（普通）
        GlideImgManager.loadImage(this, imageArr[0], imageview1);

        //加载网络图片（圆角）
        GlideImgManager.loadRoundCornerImage(this, imageArr[1], imageview2);

        //加载网络图片（圆形）
        GlideImgManager.loadManCircleImage(this, imageArr[2], imageview3);

        //加载项目中的图片
        GlideImgManager.loadImage(this, R.mipmap.ic_launcher, imageview4);

        //加载网络图片（GIF）
        String gifUrl = "http://ww4.sinaimg.cn/mw690/bcc93f49gw1f6r1nce77jg207x07sx6q.gif";
        GlideImgManager.loadGifImage(this, gifUrl, imageview5);  */

        /*    //加载进度监听
            private void loadListener() {
                Glide.with(this)
                        .load(imageArr[0])
                        .into(new GlideDrawableImageViewTarget(imageview6) {
                            @Override
                            public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                                super.onResourceReady(drawable, anim);
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onStart() {
                                super.onStart();
                                progressBar.setVisibility(View.VISIBLE);
                            }
                        });
            }*/

    /**
     * 加载网络图片（普通）
     */
    public static void loadImage(Context context, String url, int erroImg, int emptyImg, ImageView iv) {
        Glide.with(context).load(url).placeholder(emptyImg).error(erroImg).into(iv);
        //原生 API
    }

    /**
     * 加载网络图片（普通）
     */
    public static void loadImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).into(iv);
    }

    /**
     * 加载网络图片（普通）
     */
    public static void loadImage(Context context, String url, ImageView iv, String sex) {
        switch (sex) {
            case "1"://男
                Glide.with(context).load(url).error(R.mipmap.ic_nan).into(iv);
                break;
            case "2"://女
                Glide.with(context).load(url).error(R.mipmap.ic_nv).into(iv);
                break;
            default:
                Glide.with(context).load(url).error(R.mipmap.addphotos).into(iv);
                break;
        }


    }

    /**
     * 加载网络图片（GIF）
     */
    public static void loadGifImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.mipmap.addphotos).error(R.mipmap.addphotos).into(iv);
    }

    /**
     * 加载网络图片（圆角）
     */
    public static void loadRoundCornerImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).placeholder(R.mipmap.addphotos).error(R.mipmap.addphotos).transform(new GlideRoundTransform(context, 10)).into(iv);
    }

    /**
     * 加载项目中的图片
     */
    public static void loadImage(Context context, final File file, final ImageView imageView) {
        Glide.with(context)
                .load(file)
                .into(imageView);
    }
}

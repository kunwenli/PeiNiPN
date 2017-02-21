package com.jsz.peini;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.jsz.peini.gen.HistoryHotBeanDao;
import com.jsz.peini.gen.SerachInfoDao;
import com.jsz.peini.ui.activity.LockActivity;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.SpUtils;
import com.jsz.peini.utils.StringUtils;
import com.umeng.socialize.PlatformConfig;

import java.io.File;

/**
 * Created by jinshouzhi on 2016/11/25.
 */

public class PeiNiApp extends MultiDexApplication {
    public int count = 0;
    public static Context context = null;
    /**
     * 数据库
     */
    public static SerachInfoDao SerachInfoDao;
    public static HistoryHotBeanDao HistoryHotBeanDao;
    /**
     * 程序锁
     */
    public String mLock;
    /**
     * 微信appAY
     */
    public static String WXAPIPAY = "wx4a856dc3b666fe4d";

    {
        PlatformConfig.setWeixin("wx4a856dc3b666fe4d", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getContext();
        /**全局Activity*/
        isrRegisterActivityLifecycleCallbacks();

        SplashService.start(this);

    }

    public Context getContext() {
        return getApplicationContext();
    }

    /**
     * 全局Activity 的监听
     */
    private void isrRegisterActivityLifecycleCallbacks() {
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityStopped(Activity activity) {
                count--;
                if (count == 0) {
                    LogUtil.d("**********切到后台**********");
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (count == 0) {
                    LogUtil.d("**********切到前台**********");
                    mLock = (String) SpUtils.get(getContext(), "lock", "");
                    if (StringUtils.isNoNull(mLock)) {
                        Intent intent = new Intent(getContext(), LockActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        startActivity(intent);
                    }
                }
                count++;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }
        });
    }

    @Override
    public File getCacheDir() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File cacheDir = getExternalCacheDir();
            if (cacheDir != null && (cacheDir.exists() || cacheDir.mkdirs())) {
                return cacheDir;
            }
        }
        return super.getCacheDir();
    }
}

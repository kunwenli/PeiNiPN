package com.jsz.peini.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.jsz.peini.PeiNiApp;
import com.jsz.peini.R;

import java.util.Iterator;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;
import static com.jsz.peini.utils.UiUtils.getContext;
import static com.jsz.peini.utils.UiUtils.getResources;

/**
 * Created by th on 2016/12/6.
 */

public class PeiNiUtils {

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * addViewContent 添加一个布局专函推片
     */
    public static Bitmap getViewBitmap(View addViewContent) {
        addViewContent.setDrawingCacheEnabled(true);
        addViewContent.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0, addViewContent.getMeasuredWidth(), addViewContent.getMeasuredHeight());
        addViewContent.setBackgroundColor(getResources().getColor(R.color.white0000));
        addViewContent.buildDrawingCache();
        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        return bitmap;
    }

    public static void getOffKeyset(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null) {
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    /**
     * 获取包名字
     */
    public static String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) getContext().getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = getContext().getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

}

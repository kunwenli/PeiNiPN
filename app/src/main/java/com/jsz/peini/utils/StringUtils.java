package com.jsz.peini.utils;

import android.text.TextUtils;

/**
 * Created by th on 2017/1/4.
 */

public class StringUtils {
    public static boolean isNoNull(String s) {
        if (!TextUtils.isEmpty(s) && s != "null" && s != "" && !s.equals("null") && !s.equals("")) {
            return true;
        }
        return false;
    }
}

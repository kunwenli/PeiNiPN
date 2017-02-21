package com.jsz.peini.presenter;

import com.jsz.peini.PeiNiApp;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.SpUtils;

/**
 * Created by th on 2016/12/9.
 */

public class IpConfig {

    /**
     * 基本网址
     */
    public static String HttpPeiniIp = getString();


    private static String getString() {
        String ips = (String) SpUtils.get(PeiNiApp.context, "ips", "");
        if (ips == "") {
//            ips = "http://60.205.168.94:8080/pnservice/";
            ips = "http://192.168.200.254:8280/pnservice/";
        }
        LogUtil.d("ipipipipipipipipipipipipipipipip" + ips);
        return ips;
    }

    public static String HttpPic = "http://okxm4c2gg.bkt.clouddn.com/";
    public static String cityCode = "5";


}

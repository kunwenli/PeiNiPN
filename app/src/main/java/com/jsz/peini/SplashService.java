package com.jsz.peini;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.igexin.sdk.PushManager;
import com.jsz.peini.gen.DaoMaster;
import com.jsz.peini.gen.DaoSession;
import com.jsz.peini.san.getui.PushService;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.PeiNiUtils;
import com.jsz.peini.utils.time.TimeUtils;
import com.umeng.socialize.UMShareAPI;

import static com.jsz.peini.PeiNiApp.HistoryHotBeanDao;
import static com.jsz.peini.PeiNiApp.SerachInfoDao;

/**
 * Created by th on 2017/2/3.
 */

public class SplashService extends IntentService {
    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.jsz.peini.splashservice";
    public Context mContext;


    public SplashService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, SplashService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit();
            }
        }
    }

    private void performInit() {
        mContext = PeiNiApp.context;
        /**环信*/
        initHuanXin();
        /**友盟*/
        getUMShareAPI();
        /*个推*/
        initGetUi();
        /*数据库*/
        initDao();
    }

    /**
     * 初始化数据库
     */
    private void initDao() {
        LogUtil.d("陪你App 初始化的时间----------" + TimeUtils.getCurrentTime());
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(mContext, "peini_db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SerachInfoDao = daoSession.getSerachInfoDao();
        HistoryHotBeanDao = daoSession.getHistoryHotBeanDao();
        LogUtil.d("陪你App 初始化结束的时间的时间----------" + TimeUtils.getCurrentTime());
    }

    /**
     * 初始化友盟
     */
    private UMShareAPI getUMShareAPI() {
        return UMShareAPI.get(mContext);
    }

    /**
     * 初始化个推
     */
    private void initGetUi() {
        // com.getui.demo.DemoPushService 为第三方自定义推送服务
        PushManager.getInstance().initialize(mContext, PushService.class);
        // com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(mContext, com.jsz.peini.san.getui.IntentService.class);
    }

    /**
     * 初始化环信
     */
    private void initHuanXin() {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        int pid = android.os.Process.myPid();
        String processAppName = PeiNiUtils.getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        if (processAppName == null || !processAppName.equalsIgnoreCase(getPackageName())) {
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        //初始化
        EMClient.getInstance().init(PeiNiApp.context, options);
        //easeui初始化
        EaseUI.getInstance().init(PeiNiApp.context, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

    }
}

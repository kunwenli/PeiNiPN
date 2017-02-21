package com.jsz.peini.ui.activity.home;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.NetUtils;
import com.igexin.sdk.PushManager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.jsz.peini.PeiNiApp;
import com.jsz.peini.R;
import com.jsz.peini.base.BaseFragment;
import com.jsz.peini.model.search.LatLngBean;
import com.jsz.peini.model.square.UserAllInfo;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.presenter.square.SquareRetrofitHttp;
import com.jsz.peini.presenter.square.SquareService;
import com.jsz.peini.service.FloatViewService;
import com.jsz.peini.ui.Factory.FragmentFactory;
import com.jsz.peini.ui.activity.filter.FilterActivity;
import com.jsz.peini.ui.activity.filter.TaskSearchActivity;
import com.jsz.peini.ui.activity.search.IsSearchActivity;
import com.jsz.peini.ui.activity.square.MiSquareActivity;
import com.jsz.peini.ui.activity.square.MyWealthActivity;
import com.jsz.peini.ui.activity.square.NickNameActivity;
import com.jsz.peini.ui.activity.square.SuareReleaseActivity;
import com.jsz.peini.ui.view.NoScrollViewPager;
import com.jsz.peini.utils.Bitmap.GlideImgManager;
import com.jsz.peini.utils.CacheActivity;
import com.jsz.peini.utils.Conversion;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.LoginDialogUtils;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.SpUtils;
import com.jsz.peini.utils.StringUtils;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.utils.UiUtils;
import com.jsz.peini.utils.deletedata.DeleteDataManager;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends SlidingFragmentActivity implements View.OnClickListener {
    private static final int FILTER = 1000;
    /**
     * 地址搜索
     */
    private static final int TASK_SEARCH = 2000;
    private FrameLayout mHoemSquareRelease;
    private SlidingMenu slideMenu;
    private RelativeLayout home_squarefragment;
    private FragmentManager mFm;
    private RelativeLayout home_sellerfragment;
    private TextView title_bar_name;
    private FragmentTransaction mTransaction;
    private RelativeLayout home_homefragmen;
    private FrameLayout hoem_search_activity;
    private FrameLayout hoem_filter_activity;
    private TextView mTabulation;
    private RelativeLayout home_setting;
    private RelativeLayout home_fragment_ranking;
    private RelativeLayout home_fragment_manage;
    private Intent mIntent;
    private BaseFragment mFragment;
    public HomeActivity mActivity;
    /**
     * 用户mUserToken
     */
    public String mUserToken;
    /**
     * 用户mId
     */
    public String mId;
    /**
     * 头像
     */
    public String mImageHead;
    /*名字*/
    public String mNickname;
    /**
     * 用户的性别
     */
    public String mSex;
    public View mView;
    public TextView mName, mSignWord;
    public CircleImageView mMenu_imagehead;
    public ImageView mMenu_imageheadOpen;
    /**
     * mNoScrollViewPager 切换界面
     */
    public NoScrollViewPager mNoScrollViewPager;
    List<BaseFragment> mBaseFragments;
    public String mPhone;
    public EditText mEditText;
    private FrameLayout mTaskSearchActivity;
    private HomeViewPager mHomeViewPager;
    private RelativeLayout mMyWealth;
    private LinearLayout mLinearLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CacheActivity.finishActivity();
        setContentView(R.layout.activity_home);
        mActivity = this;
        ButterKnife.inject(this);
        /**启动悬浮窗*/
        mIntent = new Intent(mActivity, FloatViewService.class);
        startService(mIntent);

        slideMenu = getSlidingMenu();
        //设置侧边栏宽度
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();//屏幕宽度
        slideMenu.setBehindOffset(width * 100 / 380);
        mView = UiUtils.inflate(mActivity, R.layout.layout_menu);
        setBehindContentView(mView);
        //初始化控件
        initView();
        //初始化数据
        initData();
    }

    /*加载View*/
    private void initView() {
        mBaseFragments = new ArrayList<>();
        mBaseFragments.add(FragmentFactory.setFragmentView(FragmentFactory.FRAGMENT_HOME));//首页
        mBaseFragments.add(FragmentFactory.setFragmentView(FragmentFactory.FRAGMEN_TABULATION));//列表
        mBaseFragments.add(FragmentFactory.setFragmentView(FragmentFactory.FRAGMENT_SQUARE));//广场
        mBaseFragments.add(FragmentFactory.setFragmentView(FragmentFactory.FRAGMENT_SELLER));//商家
        mBaseFragments.add(FragmentFactory.setFragmentView(FragmentFactory.FRAGMENT_RANKING));//排行榜
        mBaseFragments.add(FragmentFactory.setFragmentView(FragmentFactory.FRAGMENT_MANAGE));//店铺管理
        mBaseFragments.add(FragmentFactory.setFragmentView(FragmentFactory.FRAGMEN_SETTING));//设置
        /**侧边栏*/
        mMenu_imagehead = (CircleImageView) mView.findViewById(R.id.menu_imagehead);
//        mMenu_imagehead.setOnClickListener(this);

        /*财富*/
        mMyWealth = (RelativeLayout) mView.findViewById(R.id.myWealth);
        mMyWealth.setOnClickListener(this);
        /**首页展示的头像*/
        mMenu_imageheadOpen = (ImageView) findViewById(R.id.menu_imagehead_open);
        mMenu_imageheadOpen.setOnClickListener(this);
        /**用户的名字*/
        mName = (TextView) mView.findViewById(R.id.menu_name);
        /**签名*/
        mSignWord = (TextView) mView.findViewById(R.id.signWord);
        mSignWord.setOnClickListener(this);
        /*点击头像*/
        mLinearLayout = (LinearLayout) mView.findViewById(R.id.icon_buju);
        mLinearLayout.setOnClickListener(this);
        //首页字体查找
        title_bar_name = (TextView) findViewById(R.id.title_bar_name);
        title_bar_name.setOnClickListener(this);
        /*先初始化首页*/
        //这个是要显示的列表
        mTabulation = (TextView) findViewById(R.id.title_bar_name_tabulation);
        mTabulation.setVisibility(View.VISIBLE);
        mTabulation.setOnClickListener(this);
        //搜索
        hoem_search_activity = (FrameLayout) findViewById(R.id.hoem_search_activity);
        hoem_search_activity.setOnClickListener(this);
        //筛选
        hoem_filter_activity = (FrameLayout) findViewById(R.id.hoem_filter_activity);
        hoem_filter_activity.setOnClickListener(this);
        hoem_filter_activity.setVisibility(View.VISIBLE);
        //广场
        home_squarefragment = (RelativeLayout) findViewById(R.id.home_squarefragment);
        home_squarefragment.setOnClickListener(this);
        //商家
        home_sellerfragment = (RelativeLayout) findViewById(R.id.home_sellerfragment);
        home_sellerfragment.setOnClickListener(this);
        ///首页
        home_homefragmen = (RelativeLayout) findViewById(R.id.home_homefragmen);
        home_homefragmen.setOnClickListener(this);
        //设置界面
        home_setting = (RelativeLayout) findViewById(R.id.home_setting);
        home_setting.setOnClickListener(this);
        //排行榜
        home_fragment_ranking = (RelativeLayout) findViewById(R.id.home_fragment_ranking);
        home_fragment_ranking.setOnClickListener(this);
        //店铺管理
        home_fragment_manage = (RelativeLayout) findViewById(R.id.home_fragment_manage);
        home_fragment_manage.setOnClickListener(this);
        //这个是发布按钮
        mHoemSquareRelease = (FrameLayout) findViewById(R.id.hoem_square_release);
        mHoemSquareRelease.setOnClickListener(this);

        //任务搜索按钮
        mTaskSearchActivity = (FrameLayout) findViewById(R.id.task_search_activity);
        mTaskSearchActivity.setOnClickListener(this);


        /**Viewpager*/
        mNoScrollViewPager = (NoScrollViewPager) findViewById(R.id.home_vp);
        mHomeViewPager = new HomeViewPager(getSupportFragmentManager(), mBaseFragments);
        mNoScrollViewPager.setAdapter(mHomeViewPager);
        mNoScrollViewPager.setOffscreenPageLimit(mBaseFragments.size());
        mNoScrollViewPager.setCurrentItem(0, false);

        /*默认显示搜索任务*/
        ShowSearch1vs2(true);

        mNoScrollViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("测试代码", "onPageScrolled滑动中" + position);
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        ShowSearch1vs2(true);
                        hoem_search_activity.setVisibility(View.GONE);
                        break;
                    case 1:
                        ShowSearch1vs2(true);
                        hoem_search_activity.setVisibility(View.GONE);
                        break;
                    case 2:
                        ShowSearch1vs2(false);
                        break;
                    case 3:
                        ShowSearch1vs2(false);

                        break;
                    case 4:
                        ShowSearch1vs2(false);

                        break;
                    case 5:
                        ShowSearch1vs2(false);

                        break;
                    case 6:
                        ShowSearch1vs2(false);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    //正在滑动   pager处于正在拖拽中
                    Log.d("测试代码", "onPageScrollStateChanged=======正在滑动" + "SCROLL_STATE_DRAGGING");
                } else if (state == ViewPager.SCROLL_STATE_SETTLING) {
                    //pager正在自动沉降，相当于松手后，pager恢复到一个完整pager的过程
                    Log.d("测试代码", "onPageScrollStateChanged=======自动沉降" + "SCROLL_STATE_SETTLING");
                } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                    //空闲状态  pager处于空闲状态
                    Log.d("测试代码", "onPageScrollStateChanged=======空闲状态" + "SCROLL_STATE_IDLE");
                }


            }
        });
    }

    /*加载数据*/
    private void initData() {
        mUserToken = (String) SpUtils.get(mActivity, "mUserToken", "");
        LogUtil.d("mUserToken" + mUserToken);
        mId = (String) SpUtils.get(mActivity, "id", "");
        mNickname = (String) SpUtils.get(mActivity, "nickname", "");
        mImageHead = (String) SpUtils.get(mActivity, "imageHead", "");
        mSex = (String) SpUtils.get(mActivity, "sex", "");
        mPhone = (String) SpUtils.get(mActivity, "phone", "");

        boolean b = PushManager.getInstance().bindAlias(PeiNiApp.context, mPhone);
        if (b) {
            LogUtil.d("绑定别名成功");
        } else {
            LogUtil.d("绑定别名失败");
            PushManager.getInstance().bindAlias(PeiNiApp.context, mPhone);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //首页的加载
            case R.id.title_bar_name:
                /*先初始化首页*/
                mNoScrollViewPager.setCurrentItem(0, false);
                //设置打点击列表界面 更改字体 和颜色
                title_bar_name.setClickable(true);
                title_bar_name.setTextSize(18);
                title_bar_name.setTextColor(getResources().getColorStateList(R.color.black));
                mTabulation.setTextColor(getResources().getColorStateList(R.color.text999));
                mTabulation.setTextSize(16);
                isSquare(false);
                ShowSearch1vs2(true);
                break;
            //列表界面
            case R.id.title_bar_name_tabulation:
//                mTransaction = mFm.beginTransaction();
//                mTransaction.replace(R.id.home_fragment, FragmentFactory.setFragmentView(FragmentFactory.FRAGMEN_TABULATION));
//                mTransaction.commit();
                mNoScrollViewPager.setCurrentItem(1, false);
                //设置打点击列表界面 更改字体 和颜色
                title_bar_name.setTextSize(16);
                title_bar_name.setTextColor(getResources().getColorStateList(R.color.text999));
                mTabulation.setTextSize(18);
                mTabulation.setTextColor(Color.BLACK);
                isSquare(false);
                ShowSearch1vs2(true);
                break;
            //广场
            case R.id.home_squarefragment:
//                mTransaction = mFm.beginTransaction();
//                mTransaction.replace(R.id.home_fragment, FragmentFactory.setFragmentView(FragmentFactory.FRAGMENT_SQUARE));
//                mTransaction.commit();
                mNoScrollViewPager.setCurrentItem(2, false);
                hoem_filter_activity.setVisibility(View.GONE);//筛选
                mTabulation.setVisibility(View.GONE);
                title_bar_name.setText("广场");
                ShowTitle();
                ShowSearch(false);
                slideMenu.toggle();
                isSquare(true);
                break;
            //商家
            case R.id.home_sellerfragment:
//                mTransaction = mFm.beginTransaction();
//                mTransaction.replace(R.id.home_fragment, FragmentFactory.setFragmentView(FragmentFactory.FRAGMENT_SELLER));
//                mTransaction.commit();
                mNoScrollViewPager.setCurrentItem(3, false);
                isShowAndFilter("商家");
                ShowTitle();
                ShowSearch(true);
                slideMenu.toggle();
                isSquare(false);
                ShowSearch1vs2(false);
                break;
            //侧拉的首页
            case R.id.home_homefragmen:
//                mTransaction = mFm.beginTransaction();
//                mTransaction.replace(R.id.home_fragment, FragmentFactory.setFragmentView(FragmentFactory.FRAGMENT_HOME));
//                mTransaction.commit();
                mNoScrollViewPager.setCurrentItem(0, false);
                hoem_filter_activity.setVisibility(View.VISIBLE);//筛选
                mTabulation.setVisibility(View.VISIBLE);//列表
                mTabulation.setTextSize(16);
                mTabulation.setTextColor(getResources().getColorStateList(R.color.text999));
                mTabulation.setText("列表");
                title_bar_name.setText("首页");
                title_bar_name.setClickable(true);
                title_bar_name.setTextSize(18);
                title_bar_name.setTextColor(getResources().getColorStateList(R.color.black));

                slideMenu.toggle();
                isSquare(false);
                break;
            //排行榜界面
            case R.id.home_fragment_ranking:
//                mTransaction = mFm.beginTransaction();
//                mTransaction.replace(R.id.home_fragment, FragmentFactory.setFragmentView(FragmentFactory.FRAGMENT_RANKING));
//                mTransaction.commit();
                mNoScrollViewPager.setCurrentItem(4, false);
                slideMenu.toggle();
                ShowTitle();
                ShowSearch(false);
                isShowAndFilter("排行榜");
                isSquare(false);
                break;
            //店铺管理界面
            case R.id.home_fragment_manage:
//                mTransaction = mFm.beginTransaction();
//                mTransaction.replace(R.id.home_fragment, FragmentFactory.setFragmentView(FragmentFactory.FRAGMENT_MANAGE));
//                mTransaction.commit();
                mNoScrollViewPager.setCurrentItem(5, false);
                slideMenu.toggle();
                ShowTitle();
                isShowAndFilter("店铺管理");
                ShowSearch(false);
                isSquare(false);
                break;
            //设置界面
            case R.id.home_setting:
//                mTransaction = mFm.beginTransaction();
//                mFragment = FragmentFactory.setFragmentView(FragmentFactory.FRAGMEN_SETTING);
//                mTransaction.replace(R.id.home_fragment, mFragment);
//                mTransaction.commit();
                mNoScrollViewPager.setCurrentItem(6, false);
                slideMenu.toggle();
                ShowTitle();
                isShowAndFilter("设置");
                ShowSearch(false);
                isSquare(false);
                break;
            case R.id.icon_buju:
                LogUtil.d("homeActivity 点击头像");
                slideMenu.toggle();
                isSquare(false);
                mIntent = new Intent(mActivity, MiSquareActivity.class);
                startActivity(mIntent);
                break;
            case R.id.menu_imagehead_open:
                slideMenu.toggle();
                break;
            //搜索列表
            case R.id.hoem_search_activity:
                mIntent = new Intent(this, IsSearchActivity.class);
                startActivity(mIntent);
                isSquare(false);
                break;
            //筛选列表
            case R.id.hoem_filter_activity:
                mIntent = new Intent(this, FilterActivity.class);
                startActivityForResult(mIntent, FILTER);
                isSquare(false);
                break;
            //广场发布按钮
            case R.id.hoem_square_release:
                LogUtil.i("这个是广场发布的按钮", "广场发布的按钮");
                mIntent = new Intent(this, SuareReleaseActivity.class);
                startActivity(mIntent);
                break;
            case R.id.task_search_activity:
                mIntent = new Intent(this, TaskSearchActivity.class);
                startActivityForResult(mIntent, TASK_SEARCH);
                isSquare(false);
                break;
            case R.id.myWealth:
                mIntent = new Intent(this, MyWealthActivity.class);
                startActivityForResult(mIntent, TASK_SEARCH);
                isSquare(false);
                break;
            case R.id.signWord:
                mIntent = new Intent(this, NickNameActivity.class);
                mIntent.putExtra("type", "1");
                mIntent.putExtra("title", "修改签名");
                startActivity(mIntent);
                break;
        }
    }

    /*是否显示搜索框*/
    private void ShowSearch(boolean b) {
        hoem_search_activity.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    /**
     * 显示那个搜索
     */
    private void ShowSearch1vs2(boolean b) {
        if (b) {
            hoem_search_activity.setVisibility(View.GONE);
            mTaskSearchActivity.setVisibility(View.VISIBLE);
        } else {
            hoem_search_activity.setVisibility(View.VISIBLE);
            mTaskSearchActivity.setVisibility(View.GONE);
        }
    }


    private void isShowAndFilter(String s) {
        title_bar_name.setText(s);
        hoem_filter_activity.setVisibility(View.GONE);//筛选
        mTabulation.setVisibility(View.GONE);//列表
    }

    private void ShowTitle() {
        title_bar_name.setClickable(false);
        title_bar_name.setTextSize(18);
        title_bar_name.setTextColor(getResources().getColorStateList(R.color.black));
    }

    // 定义一个变量，来标识是否退出
    private boolean isExit = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            isExit = false;
            switch (msg.what) {
                case Conversion.DATASUCCESS:
                    UserAllInfo userAllInfo = (UserAllInfo) msg.obj;
                    LogUtil.d("首页面获取个人资料" + userAllInfo.toString());
                    initShowView(userAllInfo);
                    break;
            }
        }
    };

    private void initShowView(UserAllInfo userAllInfo) {
        //签名
        UserAllInfo.UserInfoBean userInfo = userAllInfo.getUserInfo();
        String signWord = userInfo.getSignWord();
        mSignWord.setText(signWord);
        //显示侧滑栏头像
        String imageHead = userInfo.getImageHead();
        String url = IpConfig.HttpPic + imageHead;
        GlideImgManager.loadImage(mActivity, url, mMenu_imagehead, mSex);
        //显示侧滑栏名称
        String nickname = userInfo.getNickname();
        mName.setText(nickname);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            mNoScrollViewPager.setCurrentItem(0, false);
            Toast.makeText(getApplicationContext(), "再按一次后退键退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            Boolean ceshi = (Boolean) SpUtils.get(mActivity, "ceshi", false);
            if (ceshi) {
                mEditText = new EditText(mActivity);
                new AlertDialog.Builder(mActivity)
                        .setTitle("请输入下次启动时的ip")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(mEditText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SpUtils.put(PeiNiApp.context, "ips", "http://" + mEditText.getText().toString() + "/pnservice/");
                                dialogInterface.dismiss();
                                deleteDataImage();
                                finish();
                                System.exit(0);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SpUtils.remove(PeiNiApp.context, "ips");
                                dialogInterface.dismiss();
                                finish();
                                System.exit(0);
                            }
                        })
                        .show();

            } else {
                new AlertDialog.Builder(mActivity)
                        .setTitle("确认退出吗")
                        .setMessage("确定吗？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SpUtils.remove(PeiNiApp.context, "ips");
                                dialogInterface.dismiss();
                                deleteDataImage();
                                finish();
                                System.exit(0);
                            }
                        })
                        .setNegativeButton("否", null)
                        .show();
            }
        }
    }

    private void deleteDataImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String saveDir = Environment.getExternalStorageDirectory()
                        + "/compress";
                LogUtil.d("删除压缩后的图片===" + DeleteDataManager.deleteDirectory(saveDir));
            }
        }).start();
    }

    /*发布显示不显示*/
    private void isSquare(boolean isSquare) {
        mHoemSquareRelease.setVisibility(isSquare ? View.VISIBLE : View.GONE);
    }

    private class HomeViewPager extends FragmentPagerAdapter {
        public final FragmentManager mFm;
        public final List<BaseFragment> mBaseFragments;

        public HomeViewPager(FragmentManager fm, List<BaseFragment> baseFragments) {
            super(fm);
            mFm = fm;
            mBaseFragments = baseFragments;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            fragment = mBaseFragments.get(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return mBaseFragments.size();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }


    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //注册失败会抛出HyphenateException
                if (!StringUtils.isNoNull(mPhone)) {
                    return;
                }
                loginHuanxin();
            }
        }).start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initgetuserInfo();
    }

    private void loginHuanxin() {
        try {
            EMClient.getInstance().createAccount(mPhone, "123456");//同步方法
        } catch (HyphenateException e) {
            e.printStackTrace();
            LogUtil.d("注册聊天服务器失败");
            LogUtil.d("注册失败: " + e.getMessage());
        }
        EMClient.getInstance().login(mPhone, "123456", new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                LogUtil.d("登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {
                LogUtil.d("正在登录聊天服务器！");

            }

            @Override
            public void onError(int code, String message) {
                LogUtil.d("登录聊天服务器失败！");
            }
        });
        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            LogUtil.d("收到消息---" + messages.toString());
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
            LogUtil.d("收到透传消息---" + messages.toString());
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
            LogUtil.d("收到消息的回执---" + messages.toString());
        }

        @Override
        public void onMessageDelivered(List<EMMessage> messages) {
            //收到已送达回执
            LogUtil.d("收到已送达回执---" + messages.toString());
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        ToastUtils.showToast(mActivity, "显示帐号已经被移除");
                        // 显示帐号已经被移除
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        ToastUtils.showToast(mActivity, "显示帐号在其他设备登录");
                        // 显示帐号在其他设备登录
                    } else {
                        if (NetUtils.hasNetwork(mActivity)) {
                            ToastUtils.showToast(mActivity, "连接不到聊天服务器");
                            //连接不到聊天服务器
                        } else {
                            ToastUtils.showToast(mActivity, "当前网络不可用，请检查网络设置");
                            //当前网络不可用，请检查网络设置
                        }
                    }
                }
            });
        }
    }

    //    获取我的个人信息
    private void initgetuserInfo() {
        RetrofitUtil.createService(SquareService.class)
                .getUserAllInfo(mUserToken)
                .enqueue(new Callback<UserAllInfo>() {
                    @Override
                    public void onResponse(Call<UserAllInfo> call, final Response<UserAllInfo> response) {
                        if (response.isSuccessful()) {
                            int resultCode = response.body().getResultCode();
                            if (resultCode == 1) {
                                UserAllInfo body = response.body();
                                Message message = new Message();
                                message.what = Conversion.DATASUCCESS;
                                message.obj = body;
                                mHandler.sendMessage(message);
                            } else if (resultCode == 9) {
                                LoginDialogUtils.isNewLogin(mActivity);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<UserAllInfo> call, Throwable t) {
                        LogUtil.d("我的空间界面访问网络失败--", t.getMessage());
                    }
                });
    }

}

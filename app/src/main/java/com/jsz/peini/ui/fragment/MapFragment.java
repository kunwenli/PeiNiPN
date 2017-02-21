package com.jsz.peini.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;


import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.baidu.mapapi.model.LatLng;
import com.jsz.peini.PeiNiApp;
import com.jsz.peini.R;
import com.jsz.peini.base.BaseFragment;
import com.jsz.peini.model.eventbus.FilterReturnBean;
import com.jsz.peini.model.map.BaiduMapBean;
import com.jsz.peini.model.search.LatLngBean;
import com.jsz.peini.presenter.IpConfig;
import com.jsz.peini.presenter.map.BaiduMapService;
import com.jsz.peini.ui.activity.news.MapNewsActivity;
import com.jsz.peini.ui.activity.square.MiTaskActivity;
import com.jsz.peini.ui.activity.task.MoreTaskActivity;
import com.jsz.peini.ui.activity.task.TaskActivity;
import com.jsz.peini.utils.Bitmap.GlideImgManager;
import com.jsz.peini.utils.LogUtil;
import com.jsz.peini.utils.LoginDialogUtils;
import com.jsz.peini.utils.PeiNiUtils;
import com.jsz.peini.utils.RetrofitUtil;
import com.jsz.peini.utils.SpUtils;
import com.jsz.peini.utils.ToastUtils;
import com.jsz.peini.utils.UiUtils;
import com.jsz.peini.utils.time.TimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by kunwe on 2016/11/25.
 * 首界面
 */

public class MapFragment extends BaseFragment implements MKOfflineMapListener {

    @InjectView(R.id.map_news)
    Button map_news; //消息按钮
    @InjectView(R.id.map_publish)
    Button map_Button; //发布按钮
    private TextureMapView mapView;
    private BaiduMap mBaiduMap;

    private LocationClient mLocationClient;
    BDLocationListener myListener;
    private double latitude;//精度
    private double longitude;//维度
    //定位图层显示模式 (普通-跟随-罗盘)
    private LocationMode mCurrentMode = LocationMode.COMPASS;
    //是否已经定位了
    boolean isLocation = true;
    private float mCurrentAccracy;
    private int mapLevel;
    private double latitudeDouble;//初始化的状态
    private double longitudeDouble;//初始化的状态
    private double latitudeDoubleNext;//改变地图后的状态
    private double longitudeDoubleNext;//改变地图后的状态

    /**
     * ==================
     */
    private String mTaskCity = "130100";
    private String mSort = "1";
    private String mOtherSex = "";
    private String mOtherLowAge = "";
    private String mOtherHignAge = "";
    private String mOtherLowheight = "";
    private String mOtherHignheight = "";
    private String mIsVideo = "";
    private String mIsIdcard = "";
    private String mSellerType = "";
    private int mapLevelNext; //比例尺
    public boolean mBoolean;
    public MarkerOptions mMarkerOptions;
    public Bitmap mViewBitmap;
    public BitmapDescriptor mBitmapDescripter;
    public LatLng mLoc;
    public Marker mMarker;
    public Bundle mBundle;
    public List<BaiduMapBean.TaskMapListBean> mTaskMapList;
    public Intent mIntent;
    private boolean isnewlatLng = false;
    private BaiduMapOptions mBaiduMapOptions;
    private double latLngBeanLatitude;
    private double latLngBeanLongitude;

    /*离线地图*/
    MKOfflineMap mMKOfflineMap;
    private LinearLayout mMapMarkerView;
    private TextView mMapMarkerViewViewById;
    private CircleImageView mPointIcronin;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(PeiNiApp.context);
        //注册事件
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        // 地图初始化
        mapView = (TextureMapView) view.findViewById(R.id.bmapView);
        mBaiduMap = mapView.getMap();
        ButterKnife.inject(this, view);
        return view;
    }


    @Override
    public View initViews() {
        return null;
    }

    @Override
    public void initData() {
        mTaskMapList = new ArrayList<>();
        myListener = new MyLocationListener();
        mBaiduMapOptions = new BaiduMapOptions();
        /*离线地图*/
        mMKOfflineMap = new MKOfflineMap();
        mMKOfflineMap.init(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                offlineMap();

            }
        }).start();
        mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LogUtil.i("地图界面", "这个是加载地图成功走的方法");
            }
        });
        //隐藏百度地图的logo
        View child = mapView.getChildAt(1);
        if (child != null &&
                (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.GONE);
        }
        // 设置是否显示缩放控件
        mapView.showZoomControls(false);
        mapView.showScaleControl(true);

        //设置是否允许俯视手势，默认允许
        mBaiduMapOptions.overlookingGesturesEnabled(false);
        //设置是否允许旋转手势，默认允许
        mBaiduMapOptions.rotateGesturesEnabled(false);
        //地图模式
        mBaiduMapOptions.mapType(BaiduMap.MAP_TYPE_NORMAL);
        //设置缩放层级
        mBaiduMap.setMaxAndMinZoomLevel(21, 10);
        //开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //定位初始化
        mLocationClient = new LocationClient(PeiNiApp.context);
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        //打开GPS
        option.setOpenGps(true);
        //设置坐标类型
        option.setCoorType("bd09ll");
        //设置发起定位请求的间隔时间为5000ms
        option.setScanSpan(1000);
        //设置定位参数
        mLocationClient.setLocOption(option);
        //MyLocationData.Builder定位数据建造器
        //设置地图现实的级别
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(16).build()));
        //获取比例尺的大小
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
                if (isLocation) {
                    DecimalFormat df = new DecimalFormat("######0.000000");
                    String DecimalFormatLatitude = df.format(mapStatus.target.latitude);
                    String DecimalFormatLongitude = df.format(mapStatus.target.longitude);
                    latitudeDouble = Double.parseDouble(DecimalFormatLatitude);
                    longitudeDouble = Double.parseDouble(DecimalFormatLongitude);
                    LogUtil.i("截取后的字符串", latitudeDouble + "===" + longitudeDouble);
                }
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                mapLevel = mapView.getMapLevel();
                LogUtil.i("获取的比例尺大小", "" + mapLevel);
                DecimalFormat df = new DecimalFormat("######0.000000");
                String DecimalFormatLatitude = df.format(mapStatus.target.latitude);
                String DecimalFormatLongitude = df.format(mapStatus.target.longitude);
                latitudeDoubleNext = Double.parseDouble(DecimalFormatLatitude);
                longitudeDoubleNext = Double.parseDouble(DecimalFormatLongitude);

                LogUtil.i("截取后的字符串", latitudeDoubleNext + "===" + longitudeDoubleNext);
                if (mapLevel != mapLevelNext) {
                    mapLevelNext = mapLevel;
                    if (!isnewlatLng) {
                        initNetWork(latitude + "", longitude + "",
                                mapLevelNext, mSort, mOtherSex, mOtherLowAge, mOtherHignAge,
                                mOtherLowheight, mOtherHignheight, mIsVideo, mIsIdcard,
                                mSellerType, mTaskCity);
                    }
                    LogUtil.i("百度地图界面", "比例尺改变走这里");
                } else if (latitudeDouble - latitudeDoubleNext > 0.0010 | latitudeDoubleNext - latitudeDouble > 0.0010 | longitudeDouble - longitudeDoubleNext > 0.0010 | longitudeDoubleNext - longitudeDouble > 0.0010) {
                    LogUtil.i("百度地图界面", "经纬度改变走这里" + (latitudeDouble - latitudeDoubleNext));
                    latitudeDouble = latitudeDoubleNext;
                    longitudeDouble = longitudeDoubleNext;
                    if (!isnewlatLng) {
                        initNetWork(latitudeDouble + "", longitudeDouble + "",
                                mapLevelNext, mSort, mOtherSex, mOtherLowAge, mOtherHignAge,
                                mOtherLowheight, mOtherHignheight, mIsVideo, mIsIdcard,
                                mSellerType, mTaskCity);
                    }
                }


            }
        });
        //添加marker点击事件的监听
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //从marker中获取info信息
                mBundle = marker.getExtraInfo();
                int id = mBundle.getInt("id");
                int sum = mBundle.getInt("sum");
                String idStr = mBundle.getString("idStr");
                LogUtil.d("点击事件获取的信息---" + "id" + id + "sum" + sum + "--" + TimeUtils.getCurrentTime());
                if (id == 36524) {
                    return true;
                }
                if (sum != 1) {
                    /*进入多级任务列表*/
                    mIntent = new Intent(mActivity, MoreTaskActivity.class);
                    mIntent.putExtra("idStr", idStr);
                    startActivity(mIntent);
                } else {
                    /*进入任务详情界面*/
                }
                return true;
            }
        });
    }

    /**
     * 离线地图
     */
    private void offlineMap() {
        ArrayList<MKOLSearchRecord> offlineCityList = mMKOfflineMap.getOfflineCityList();
        for (MKOLSearchRecord r : offlineCityList) {
            int cityID = r.cityID;
            String cityName = r.cityName;
            LogUtil.d("offlineCityList" + cityID + "--" + cityName);
            if (r.childCities != null && r.childCities.size() != 0) {
                ArrayList<MKOLSearchRecord> childCities = r.childCities;
                for (MKOLSearchRecord cr : childCities) {
                    LogUtil.d("childCities" + cr.cityID + "--" + cr.cityName);
                }
            }

        }
        boolean start = mMKOfflineMap.start(150);
        LogUtil.d("离线地图下载成功否---" + start);
    }

    /**
     * 网络请求借口
     */
    private void initNetWork(
            String xpoint,
            String ypoint,
            int mapLevelNext,
            String sort,
            String otherSex,
            String otherLowAge,
            String otherHignAge,
            String otherLowheight,
            String otherHignheight,
            String isVideo,
            String isIdcard,
            String sellerType,
            String taskCity) {
        RetrofitUtil.createService(BaiduMapService.class).getTaskMapList(
                xpoint + "",
                ypoint + "",
                mapLevelNext,
                sort + "",
                otherSex + "",
                otherLowAge + "",
                otherHignAge + "",
                otherLowheight + "",
                otherHignheight + "",
                isVideo + "",
                isIdcard + "",
                sellerType + "",
                taskCity + "")
                .enqueue(new Callback<BaiduMapBean>() {
                    @Override
                    public void onResponse(Call<BaiduMapBean> call, Response<BaiduMapBean> response) {
                        if (response.isSuccessful()) {
                            int resultCode = response.body().getResultCode();
                            if (resultCode == 1) {
                                BaiduMapBean body = response.body();
                                mTaskMapList.clear();
                                mTaskMapList.addAll(body.getTaskMapList());
                                addOverlay();
                                LogUtil.d("首页地图加载数据成功" + body.toString());
                            } else if (resultCode == 9) {
                                LoginDialogUtils.isNewLogin(mActivity);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaiduMapBean> call, Throwable t) {
                        ToastUtils.showToast(mActivity, t.getMessage());
                    }
                });
    }

    /**
     * 实时定位 监听位置信息 填充数据
     */
    private void addOverlay() {
        //清空地图
        mBaiduMap.clear();

        for (BaiduMapBean.TaskMapListBean taskMapListBeen : mTaskMapList) {
            List<BaiduMapBean.TaskMapListBean.TaskObjectBean> taskObjectBeen = taskMapListBeen.getTaskObject();
            int sum = taskMapListBeen.getSum();
            //创建布局
            mMapMarkerView = (LinearLayout) UiUtils.inflate(mActivity, R.layout.point);
            //寻找布局的id 显示的数量
            mMapMarkerViewViewById = (TextView) mMapMarkerView.findViewById(R.id.point_number);
            //显示的头像
            mPointIcronin = (CircleImageView) mMapMarkerView.findViewById(R.id.point_icronin);


            String url = "http://7xi8d6.com1.z0.glb.clouddn.com/2017-02-08-16230686_191036801373876_4789664128824246272_n.jpg";

            if (sum == 1) {
                //填充数量
                mMapMarkerViewViewById.setText(sum + "");
                //填充头像
//                String url = IpConfig.HttpPic + taskObjectBeen.get(0).getImageHead();
                GlideImgManager.loadImage(mActivity, url, mPointIcronin, "1");
                mMarkerOptions = new MarkerOptions();
                mViewBitmap = PeiNiUtils.getViewBitmap(mMapMarkerView);
                mBitmapDescripter = BitmapDescriptorFactory.fromBitmap(mViewBitmap);
                mLoc = new LatLng(Double.parseDouble(taskObjectBeen.get(0).getXpoint()), Double.parseDouble(taskObjectBeen.get(0).getYpoint()));
                mMarkerOptions.position(mLoc)//设置位置
                        .icon(mBitmapDescripter) //设置图片
                        .draggable(false);//设置可以拖
                mMarker = (Marker) mBaiduMap.addOverlay(mMarkerOptions);
                //info必须实现序列化接口
                mBundle = new Bundle();
                mBundle.putInt("id", taskMapListBeen.getId());
                mBundle.putInt("sum", taskMapListBeen.getSum());
                mBundle.putString("idStr", taskMapListBeen.getIdStr());
                mMarker.setExtraInfo(mBundle);
            } else {
                //填充数量
                mMapMarkerViewViewById.setText(sum + "");
                //填充头像
//                String url1 = IpConfig.HttpPic + taskObjectBeen.get(0).getImageHead();
                GlideImgManager.loadImage(mActivity, url, mPointIcronin, "1");
                mMarkerOptions = new MarkerOptions();
                mViewBitmap = PeiNiUtils.getViewBitmap(mMapMarkerView);
                mBitmapDescripter = BitmapDescriptorFactory.fromBitmap(mViewBitmap);
                mLoc = new LatLng(Double.parseDouble(taskObjectBeen.get(0).getXpoint()), Double.parseDouble(taskObjectBeen.get(0).getYpoint()));
                mMarkerOptions.position(mLoc)//设置位置
                        .icon(mBitmapDescripter) //设置图片
                        .draggable(false);//设置可以拖
                mMarker = (Marker) mBaiduMap.addOverlay(mMarkerOptions);
                //info必须实现序列化接口
                mBundle = new Bundle();
                mBundle.putInt("id", taskMapListBeen.getId());
                mBundle.putInt("sum", taskMapListBeen.getSum());
                mBundle.putString("idStr", taskMapListBeen.getIdStr());
                mMarker.setExtraInfo(mBundle);
            }
        }
        /**设置搜索地图位置定位*/
        if (isnewlatLng) {
//            isnewlatLng = false;
            // 定义marker坐标点
            LatLng point = new LatLng(latLngBeanLatitude, latLngBeanLongitude);
            mBitmapDescripter = BitmapDescriptorFactory.fromResource(R.mipmap.dingwei);
            // 构建markerOption，用于在地图上添加marker
            OverlayOptions options = new MarkerOptions()//
                    .position(point)// 设置marker的位置
                    .icon(mBitmapDescripter)// 设置marker的图标
                    .zIndex(9)// 設置marker的所在層級
                    .draggable(false);// 设置手势拖拽
            // 在地图上添加marker，并显示
            mMarker = (Marker) mBaiduMap.addOverlay(options);
            //info必须实现序列化接口
            mBundle = new Bundle();
            mBundle.putInt("id", 36524);
            mBundle.putInt("sum", 0);
            mBundle.putString("idStr", "");
            mMarker.setExtraInfo(mBundle);
        }
    }

    /*百度地图离线地图*/
    @Override
    public void onGetOfflineMapState(int i, int i1) {

    }

    /**
     * 百度定位的监听结果
     */
    class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapview 销毁后不在处理新接收的位置
            if (location == null || mBaiduMap == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .direction(0)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
            //设置定位数据
            mCurrentAccracy = location.getRadius();
            mBaiduMap.setMyLocationData(locData);
            //获取经纬度
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            SpUtils.put(mActivity, "xpoint", location.getLatitude());
            SpUtils.put(mActivity, "ypoint", location.getLongitude());

            LogUtil.i("百度地图定位到的坐标", latitude + "-----" + longitude + "---");

            if (isLocation) {
                mapLevelNext = mapView.getMapLevel();
                LatLng loc = new LatLng(latitude, longitude);
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(loc);
                mBaiduMap.animateMapStatus(msu);
                isLocation = false;
                initNetWork(location.getLatitude() + "", location.getLongitude() + "",
                        mapLevelNext, mSort, mOtherSex, mOtherLowAge, mOtherHignAge,
                        mOtherLowheight, mOtherHignheight, mIsVideo, mIsIdcard,
                        mSellerType, mTaskCity);
            }

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }
    @OnClick({R.id.map_publish, R.id.map_news, R.id.map_refresh, R.id.mi_task})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.map_publish:
                startActivity(new Intent(getActivity(), TaskActivity.class));
                break;
            case R.id.map_news:
                startActivity(new Intent(getActivity(), MapNewsActivity.class));
                break;
            case R.id.mi_task:
                startActivity(new Intent(getActivity(), MiTaskActivity.class));
                break;
            case R.id.map_refresh:
                mapView.refreshDrawableState();
                isLocation = true;
                isnewlatLng = false;
                mLocationClient.start();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.d("百度地图,当界面可见的时候开启定位");
        // 开启定位
        mBaiduMap.setMyLocationEnabled(true);
        mLocationClient.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.d("百度地图,当界面不可见的时候关闭定位");
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(" //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理");
        mapView.onResume();
        mBaiduMap.setMyLocationEnabled(true);
        isLocation = true;
        if (!isnewlatLng) {
            mLocationClient.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isLocation = false;
        mLocationClient.stop();
        LogUtil.d("//在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理");
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if (mapView != null) {
            mapView.onDestroy();
        }
        isLocation = true;
        /**
         * 退出时，销毁离线地图模块
         */
        mMKOfflineMap.destroy();
        //取消事件注册
        EventBus.getDefault().unregister(this);
    }

    /*筛选返回的数据*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowMessageEvent(FilterReturnBean messageEvent) {
        mSort = messageEvent.getSort();
        mOtherSex = messageEvent.getOtherSex();
        mOtherLowAge = messageEvent.getOtherLowAge();
        mOtherHignAge = messageEvent.getOtherHignAge();
        mOtherLowheight = messageEvent.getOtherLowheight();
        mOtherHignheight = messageEvent.getOtherHignheight();
        mIsVideo = messageEvent.getIsVideo();
        mIsIdcard = messageEvent.getIsIdcard();
        mSellerType = messageEvent.getSellerType();
        mTaskCity = messageEvent.getTaskCity();
        LogUtil.d(mActivity.getPackageName(), messageEvent.toString());
        if (!isnewlatLng) {
            initNetWork(latitude + "", longitude + "", mapLevelNext, mSort, mOtherSex, mOtherLowAge, mOtherHignAge, mOtherLowheight, mOtherHignheight, mIsVideo, mIsIdcard, mSellerType, mTaskCity);
        }
    }

    /**
     * 任务定位返回的数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowMessageEvent(LatLngBean latLngBean) {
        if (latLngBean != null) {
            isnewlatLng = true;
        }
        ToastUtils.showToast(mActivity, latLngBean.toString());
        latLngBeanLatitude = latLngBean.getLatitude();
        latLngBeanLongitude = latLngBean.getLongitude();
        LatLng loc = new LatLng(latLngBeanLatitude, latLngBeanLongitude);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(loc);
        mBaiduMap.animateMapStatus(msu);


        initNetWork(latLngBeanLatitude + "", latLngBeanLongitude + "",
                mapLevelNext, mSort, mOtherSex, mOtherLowAge, mOtherHignAge,
                mOtherLowheight, mOtherHignheight, mIsVideo, mIsIdcard,
                mSellerType, mTaskCity);
    }

}
package com.example.q_kang.pmsystem.ui.view.Utils.map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.modules.bean.bean.EventBusMessage;
import com.example.q_kang.pmsystem.ui.activity.event.EventDetailActivity;
import com.example.q_kang.pmsystem.ui.activity.project.ProjectDetailActivity;
import com.example.q_kang.pmsystem.ui.activity.work.WorkDetailActivity;
import com.example.q_kang.pmsystem.ui.view.Utils.CommonUtils;
import com.example.q_kang.pmsystem.ui.view.Utils.map.map_cluster.MapMarkerItem;
import com.example.q_kang.pmsystem.ui.view.Utils.map.map_cluster.clustering.view.Cluster;
import com.example.q_kang.pmsystem.ui.view.Utils.map.map_cluster.clustering.view.ClusterManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MapControl implements BaiduMap.OnMapLoadedCallback {

    private static String TAG = "MapControl";

    private static Context context;
    private GeoCoder geoCoder;
    private MapView mapView;

    private static BaiduMap baiduMap;
    private LocationClient locationClient;
    private BDLocationListener bdLocationListener = new MyLocationListener();
    private LatLng latLng;

    private boolean isFirstLoc = true;
    private float lastX;
    private String address, geoAddress;
    private LatLng returnLatlng;

    private MapStatus mapStatus;
    private ClusterManager clusterManager;


    public MapControl(Context context, MapView mapView) {
        this.context = context;
        this.mapView = mapView;

        initMapBasic(mapView);

//        location();
    }


    private void initMapBasic(MapView mapView) {
        baiduMap = mapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        baiduMap.setMyLocationEnabled(true);

        //隐藏百度地图logo
        View child = mapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        //显示地图上比例尺
        mapView.showScaleControl(true);

        //指南针
        UiSettings uiSettings = baiduMap.getUiSettings();
        uiSettings.setCompassEnabled(false);
        uiSettings.setRotateGesturesEnabled(false);
        uiSettings.setOverlookingGesturesEnabled(false);//不倾斜


        //创建GeoCoder实例对象
        geoCoder = GeoCoder.newInstance();

    }

    public String getGeoAddress(TextView textView) {


        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            //反地理编码查询结果回调函数
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

                address = reverseGeoCodeResult.getAddress();
                Log.i("1111", "onGetReverseGeoCodeResult: " + address);
                textView.setText(address);

            }
        });

        return address;
    }


    /**
     * 返回经纬度
     */
    public LatLng getGeoLatlng() {
        return latLng;
    }


    public void location() {

        // 设置自定义图标  定位到当前位置
        MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.navi_map_gps_locked);
        MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
        baiduMap.setMyLocationConfigeration(config);


        /**
         * 手势操作地图，设置地图状态等操作导致地图状态开始改变
         * @param mapStatus 地图状态改变开始时的地图状态
         */
        //地图状态改变相关监听
        baiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                //地图操作的中心点
                latLng = mapStatus.target;
                //创建GeoCoder实例对象
                geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
                Log.i("1111", "onMapStatusChangeFinish: " + latLng);

            }
        });

        locationClient = new LocationClient(context.getApplicationContext());
        initLocation();
        locationClient.registerLocationListener(bdLocationListener);
        locationClient.start();
        locationClient.requestLocation();

//        把定位点再次显现出来
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.animateMapStatus(mapStatusUpdate);

    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        option.setOpenGps(true); // 打开gps

        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        locationClient.setLocOption(option);
    }


    //实现BDLocationListener接口,BDLocationListener为结果监听接口，异步获取定位结果
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            // 构造定位数据

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(lastX)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
            // 设置定位数据
            baiduMap.setMyLocationData(locData);
            // 当不需要定位图层时关闭定位图层
            //mBaiduMap.setMyLocationEnabled(false);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                    // GPS定位结果
                    Toast.makeText(context, location.getAddrStr(), Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    // 网络定位结果
                    Toast.makeText(context, location.getAddrStr(), Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                    // 离线定位结果
                    Toast.makeText(context, location.getAddrStr(), Toast.LENGTH_SHORT).show();

                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    Toast.makeText(context, "服务器错误，请检查", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    Toast.makeText(context, "网络错误，请检查", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    Toast.makeText(context, "手机模式错误，请检查是否飞行", Toast.LENGTH_SHORT).show();
                }
            }


        }
    }


    public void destory() {//退出时停止定位
//        locationClient.stop();
        //退出时关闭定位图层
        baiduMap.setMyLocationEnabled(false);

        // activity 销毁时同时销毁地图控件
        mapView.onDestroy();

        //释放资源
        if (geoCoder != null) {
            geoCoder.destroy();
        }

        mapView = null;
    }


    public String showAddress(double latitude, double longtitude, TextView textView) {
        LatLng latLng = new LatLng(latitude, longtitude);

        // 设置反地理经纬度坐标,请求位置时,需要一个经纬度
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
        //设置地址或经纬度反编译后的监听,这里有两个回调方法,
        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            /**
             *
             * @param reverseGeoCodeResult
             */
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(context, "找不到该地址！", Toast.LENGTH_SHORT).show();
                }
                geoAddress = reverseGeoCodeResult.getAddress();
                Log.i("", "geoAddress: " + geoAddress);
                textView.setText(geoAddress);


                EventBusMessage event = new EventBusMessage("address");
                event.setAddress(address);
                EventBus.getDefault().post(event);


                Bitmap bitmap = MarkerView(geoAddress);
                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
                OverlayOptions options = new MarkerOptions()
                        .position(latLng)//设置位置
                        .icon(bitmapDescriptor)//设置图标样式

                        .zIndex(13) // 设置marker所在层级
                        .draggable(false);

                //添加marker
                Marker marker = (Marker) baiduMap.addOverlay(options);


                Log.i("", "onGetGeoCodeResult111: " + geoAddress);
            }
        });

//        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
//        baiduMap.animateMapStatus(u);


        return geoAddress;
    }

    private Bitmap MarkerView(final String info) {
        View view = LayoutInflater.from(context).inflate(R.layout.location_layout, null);
        TextView tv_markerTitle = view.findViewById(R.id.tv_marker_location);
        ImageView iv = view.findViewById(R.id.iv_marker_site);


        if (info.contains("项目")) {
            iv.setImageResource(R.mipmap.classify_pro);
            tv_markerTitle.setBackgroundResource(R.drawable.text_bg);
        } else if (info.contains("工作")) {
            iv.setImageResource(R.mipmap.classify_work);
            tv_markerTitle.setBackgroundResource(R.drawable.shape_location_work_bg);
        } else if (info.contains("事件")) {
            iv.setImageResource(R.mipmap.classify_event);
        }
        if ("".equals(info)) {
            tv_markerTitle.setVisibility(View.GONE);
        }
        tv_markerTitle.setText(info);

        Bitmap viewBitmap = CommonUtils.getViewBitmap(view);
        return viewBitmap;
    }


    public static BitmapDescriptor getBitmapDescriptor(String name, int flag, LatLng latLng, String id) {


//        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
//        baiduMap.animateMapStatus(u);

        Bitmap bitmap = LayerMarkerView(name, flag);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);

        OverlayOptions options = new MarkerOptions()
                .position(latLng)//设置位置
                .icon(bitmapDescriptor)//设置图标样式
                .zIndex(13) // 设置marker所在层级
                .draggable(false);


        //添加marker
        Marker marker = (Marker) baiduMap.addOverlay(options);

        Bundle bundle = new Bundle();
        bundle.putString("marker_id", id);
        marker.setExtraInfo(bundle);//将bundle值传入marker中，给baiduMap设置监听时可以得到它

        return bitmapDescriptor;
    }

    public static BitmapDescriptor getBitmapDescriptor(MapMarkerItem mapMarkerItem) {


//        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
//        baiduMap.animateMapStatus(u);

        Bitmap bitmap = LayerMarkerView(mapMarkerItem.getName(), mapMarkerItem.getFlag());
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);

        OverlayOptions options = new MarkerOptions()
                .position(mapMarkerItem.getmPosition())//设置位置
                .icon(bitmapDescriptor)//设置图标样式
                .zIndex(13) // 设置marker所在层级
                .draggable(false);


        //添加marker
        Marker marker = (Marker) baiduMap.addOverlay(options);

        Bundle bundle = new Bundle();
        bundle.putParcelable("marker", mapMarkerItem);
        marker.setExtraInfo(bundle);//将bundle值传入marker中，给baiduMap设置监听时可以得到它

        return bitmapDescriptor;
    }


    public static void markerClick() {

        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle extraInfo = marker.getExtraInfo();
                if (null != extraInfo) {
//                    String marker_id = extraInfo.getString("marker_id");
                    MapMarkerItem mapMarkerItem = extraInfo.getParcelable("marker");
                    Intent intent = null;
                    int flag = mapMarkerItem.getFlag();
                    String id = mapMarkerItem.getId();
                    if (null != id && !"null".equals(id) && !"".equals(id)) {
                        if (flag == 1) {
                            intent = new Intent(context, ProjectDetailActivity.class);
                        } else if (flag == 2) {
                            intent = new Intent(context, WorkDetailActivity.class);
                        } else if (flag == 3) {
                            intent = new Intent(context, EventDetailActivity.class);
                        }

                        intent.putExtra("marker_id", id);
                        intent.putExtra("flag", 5);
                        context.startActivity(intent);

                    }


                }

                return false;
            }
        });
    }


    public static void markerClick(int flag) {
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle extraInfo = marker.getExtraInfo();
                if (null != extraInfo) {
                    String marker_id = extraInfo.getString("marker_id");
                    Log.i(TAG, "markerClick flag: " + flag);
                    Log.i(TAG, "markerClick marker_id: " + marker_id);
                    Intent intent = null;
                    if (flag == 0) {

                    } else {
                        if (flag == 1) {
                            intent = new Intent(context, ProjectDetailActivity.class);
                        } else if (flag == 2) {
                            intent = new Intent(context, WorkDetailActivity.class);
                        } else if (flag == 3) {
                            intent = new Intent(context, EventDetailActivity.class);
                        }

                        intent.putExtra("marker_id", marker_id);
                        intent.putExtra("flag", 5);
                        context.startActivity(intent);
                    }

                }

                return false;
            }
        });

    }


    private static Bitmap LayerMarkerView(final String info, int flag) {
        View view = LayoutInflater.from(context).inflate(R.layout.location_layout, null);
        TextView tv_markerTitle = view.findViewById(R.id.tv_marker_location);
        ImageView iv = view.findViewById(R.id.iv_marker_site);
        if (flag == 1) {
            iv.setImageResource(R.mipmap.classify_pro);
            tv_markerTitle.setBackgroundResource(R.drawable.text_bg);
        } else if (flag == 2) {
            iv.setImageResource(R.mipmap.classify_work);
            tv_markerTitle.setBackgroundResource(R.drawable.shape_location_work_bg);
        } else if (flag == 3) {
            iv.setImageResource(R.mipmap.classify_event);
        }
//        iv.setImageResource(R.mipmap.image_location);
        if ("".equals(info)) {
            tv_markerTitle.setVisibility(View.GONE);
        }
        tv_markerTitle.setText(info);


        Bitmap viewBitmap = CommonUtils.getViewBitmap(view);

        return viewBitmap;
    }


    public void projectLocation(double latitude, double longtitude, String type, String name) {
        LatLng latLng = new LatLng(latitude, longtitude);

        if ("event".equals(type)) {
            name = "事件： " + name;
        } else if ("work".equals(type)) {
            name = "工作： " + name;
        } else if ("project".equals(type)) {
            name = "项目： " + name;
        } else {
            name = name + "";
        }

        Bitmap bitmap = MarkerView(name);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
        OverlayOptions options = new MarkerOptions()
                .position(latLng)//设置位置
                .icon(bitmapDescriptor)//设置图标样式
                .zIndex(16) // 设置marker所在层级
                .draggable(false);

        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.animateMapStatus(u);


        //添加marker
        Marker marker = (Marker) baiduMap.addOverlay(options);

    }



    public LatLng getCenterLatlng(List<MapMarkerItem> markerList){
        List<Double> latitudeList = new ArrayList<>();
        List<Double> longitudeList = new ArrayList<>();
        double maxLatitude, maxLongitude, minLatitude, minLongitude;
        for (int i = 0; i < markerList.size(); i++) {
            double latitude111 = markerList.get(i).getmPosition().latitude;
            double longitude111 = markerList.get(i).getmPosition().longitude;
            latitudeList.add(latitude111);
            longitudeList.add(longitude111);
        }
        maxLatitude = Collections.max(latitudeList);
        minLatitude = Collections.min(latitudeList);
        maxLongitude = Collections.max(longitudeList);
        minLongitude = Collections.min(longitudeList);
        LatLng center = new LatLng((maxLatitude + minLatitude) / 2, (maxLongitude + minLongitude) / 2);
        return center;
    }


    /**
     * 覆盖物多点聚合
     * 聚合点坐标   多个点坐标集合
     */
    public void mulMarkerMerge(List<MapMarkerItem> markerList) {

        if (markerList != null && markerList.size() > 0){
            LatLng centerLatlng = getCenterLatlng(markerList);

            mapStatus = new MapStatus.Builder().target(new LatLng(centerLatlng.latitude, centerLatlng.longitude)).zoom(13).build();
            baiduMap = mapView.getMap();
            baiduMap.setOnMapLoadedCallback(this);
            baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mapStatus));
        }


//        if (markerList != null && markerList.size() > 0) {
//            // 将地图移到到当前点击经纬度位置 并增加缩放等级
//            baiduMap.animateMapStatus(MapStatusUpdateFactory
//                    .newMapStatus(new MapStatus.Builder()
//                            .zoom(baiduMap.getMapStatus().zoom + 1)
//                            .target(markerList.get(0).getPosition())
//                            .build()));
//        }


        //定义点聚合管理类
        clusterManager = new ClusterManager<MapMarkerItem>(context, baiduMap);
        //添加marker点

        Log.i(TAG, "mulMarkerMerge markerList: " + markerList.size() + markerList);
        addMarkers(markerList);


        //设置地图监听，当地图状态发生改变时，进行点聚合运算
        baiduMap.setOnMapStatusChangeListener(clusterManager);
        //设置marker点击时的响应
        baiduMap.setOnMarkerClickListener(clusterManager);


        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MapMarkerItem>() {
            @Override
            public boolean onClusterClick(Cluster<MapMarkerItem> cluster) {
//                Toast.makeText(context, "有" + cluster.getSize() + "个点", Toast.LENGTH_SHORT).show();
                clickGroup(cluster);
                return false;
            }
        });

        clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MapMarkerItem>() {
            @Override
            public boolean onClusterItemClick(MapMarkerItem item) {

                // Toast.makeText(context, "点击单个item", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }


    /*将聚合点打开*/
    private boolean clickGroup(Cluster<MapMarkerItem> clusterBaiduItems) {
        Log.i(TAG, "clickGroup: " + clusterBaiduItems.getSize() + clusterBaiduItems);
        if (baiduMap == null) {
            return true;
        }
        if (clusterBaiduItems.getItems().size() > 0) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (MapMarkerItem clusterBaiduItem : clusterBaiduItems.getItems()) {
                builder.include(clusterBaiduItem.getPosition());
            }
            baiduMap.animateMapStatus(MapStatusUpdateFactory
                    .newLatLngBounds(builder.build()));
        }
        return false;
    }

    private void addMarkers(List<MapMarkerItem> markerItemList) {
        clusterManager.addItems(markerItemList);
    }


    public void clearMarkerItems() {
//        clusterManager.clearItems();//清除所有的items
//        clusterManager.getMarkerCollection().clear();
//        clusterManager.getClusterMarkerCollection().clear();
        baiduMap.clear();

    }


    public void mapClear() {
        baiduMap.clear();
    }


    @Override
    public void onMapLoaded() {
        mapStatus = new MapStatus.Builder().zoom(13).build();
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mapStatus));
    }


    public void setMap_Shiliang() {
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
    }

    public void setMap_Yingxiang() {
        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
    }


}
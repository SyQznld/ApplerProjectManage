package com.example.q_kang.pmsystem.ui.activity.map;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.dao.AdminDao;
import com.example.q_kang.pmsystem.modules.bean.bean.AllData;
import com.example.q_kang.pmsystem.modules.bean.bean.EventBusMessage;
import com.example.q_kang.pmsystem.modules.bean.bean.Frame;
import com.example.q_kang.pmsystem.present.im.DataByIdPresenter;
import com.example.q_kang.pmsystem.ui.view.Utils.map.MapControl;
import com.example.q_kang.pmsystem.ui.view.Utils.map.MapLayerPop;
import com.example.q_kang.pmsystem.ui.view.Utils.map.MapShowAddressPop;
import com.example.q_kang.pmsystem.ui.view.Utils.map.map_cluster.MapMarkerItem;
import com.example.q_kang.pmsystem.view.DataByIdView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaseMapActivity extends AppCompatActivity implements DataByIdView {
    //AK：he8P5gQVddueGSSrNaqEfAD2wa1yEn7x
    @BindView(R.id.ib_basemap_back)
    ImageButton ibBack;
    @BindView(R.id.ib_basemap_location)
    ImageButton ibLocation;
    @BindView(R.id.ib_basemap_sure)
    ImageButton ibProjectLocation;
    @BindView(R.id.iv_projectlocation)
    ImageView iv;
    @BindView(R.id.mv_base_mapview)
    MapView mapView;
    @BindView(R.id.ib_basemap_shiliang)
    TextView ib_shiliang;
    @BindView(R.id.ib_basemap_yingxiang)
    TextView ib_yingxiang;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.iv_map_search)
    ImageView iv_search;
    @BindView(R.id.iv_map_layer)
    ImageView iv_layer;


    private String mapState;
    private String address;
    private LatLng latLng;

    private MapControl mapControl;
    private MapShowAddressPop mapShowAddressPop;
    private String name = "";
    private int flag = 1;  //默认拖动地图定位

    private MapLayerPop layerPop;
    private DataByIdPresenter presenter;


    private List<MapMarkerItem> pro_items = new ArrayList<MapMarkerItem>();
    private List<MapMarkerItem> work_items = new ArrayList<MapMarkerItem>();
    private List<MapMarkerItem> event_items = new ArrayList<MapMarkerItem>();
    private List<MapMarkerItem> items = new ArrayList<MapMarkerItem>();

    private AllData allShowData;
    private boolean proClick = false;
    private boolean workClick = false;
    private boolean eventClick = false;

    private PopupWindow popupWindow;

    private CheckBox cb_project, cb_work, cb_event;
    private View view_;
    private int markerClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_base_map);


        ButterKnife.bind(this);

        EventBus.getDefault().register(this);
        presenter = new DataByIdPresenter(this);

        mapShowAddressPop = new MapShowAddressPop(this);
        layerPop = new MapLayerPop(this);

        mapControl = new MapControl(this, mapView);

        double latitude = getIntent().getDoubleExtra("lat", 0);
        double longtitude = getIntent().getDoubleExtra("long", 0);
        String type = getIntent().getStringExtra("type");
        name = getIntent().getStringExtra("name");


        mapState = getIntent().getStringExtra("map");
        if ("project".equals(mapState)) {     //定位，返回位置   新建项目、工作、事件
            mapControl.location();
            iv_search.setVisibility(View.VISIBLE);
            ibLocation.setVisibility(View.GONE);
            iv_layer.setVisibility(View.GONE);


//            ibProjectLocation.setVisibility(View.GONE);
//            iv.setVisibility(View.GONE);
//            tv_address.setVisibility(View.GONE);

            /**
             * 拖动地图定位
             * */
            iv.setVisibility(View.VISIBLE);
            tv_address.setVisibility(View.VISIBLE);
            ibProjectLocation.setVisibility(View.VISIBLE);

            latLng = mapControl.getGeoLatlng();
            Log.i("", "onCreate: " + latitude + longtitude);
            address = mapControl.showAddress(latitude, longtitude, tv_address);

        } else if ("location_".equals(mapState)){
            mapControl.projectLocation(latitude, longtitude, type, name);

        }else {   //从主页直接进入地图
            mapControl.location();
            presenter.getDataById(AdminDao.getUserID());
            iv_search.setVisibility(View.GONE);
            ibLocation.setVisibility(View.VISIBLE);
            iv_layer.setVisibility(View.VISIBLE);
//            iv_layer.setVisibility(View.GONE);
            iv.setVisibility(View.GONE);
            tv_address.setVisibility(View.GONE);
            ibProjectLocation.setVisibility(View.GONE);

//            mapControl.projectLocation(latitude, longtitude, type, name);


            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view_ = layoutInflater.inflate(R.layout.map_layer_layout, null);

            cb_project = view_.findViewById(R.id.cb_layer_project);
            cb_work = view_.findViewById(R.id.cb_layer_work);
            cb_event = view_.findViewById(R.id.cb_layer_event);


            cb_project.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        showProjectData(allShowData);
                        items.addAll(pro_items);
                        mapControl.mulMarkerMerge(items);
                    } else {
                        mapControl.mapClear();
                        items.clear();
                        cbIsChecked();
                    }
                }
            });
            cb_work.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        showWorkData(allShowData);
                        items.addAll(work_items);
                        mapControl.mulMarkerMerge(items);
                    } else {
                        mapControl.mapClear();
                        items.clear();
                        cbIsChecked();
                    }
                }
            });
            cb_event.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        showEventData(allShowData);
                        items.addAll(event_items);
                        mapControl.mulMarkerMerge(items);
                    } else {
                        mapControl.mapClear();
                        items.clear();
                        cbIsChecked();
                    }
                }
            });

            mapControl.mulMarkerMerge(items);
            mapControl.markerClick();

        }
    }

    private void cbIsChecked() {
        items.clear();
        if (cb_project.isChecked()) {
            showProjectData(allShowData);
            items.addAll(pro_items);
            mapControl.mulMarkerMerge(items);

        }
        if (cb_work.isChecked()) {
            showWorkData(allShowData);
            items.addAll(work_items);
            mapControl.mulMarkerMerge(items);
        }
        if (cb_event.isChecked()) {
            showEventData(allShowData);
            items.addAll(event_items);
            mapControl.mulMarkerMerge(items);
        }

    }


    @OnClick({R.id.ib_basemap_back, R.id.ib_basemap_location, R.id.ib_basemap_sure,
            R.id.iv_map_search,
            R.id.ib_basemap_shiliang,
            R.id.ib_basemap_yingxiang,
            R.id.iv_map_layer})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.ib_basemap_back:
                finish();
                break;
            case R.id.iv_map_layer:
//                layerPop.showLayerPop(iv_layer);

                WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                int width = manager.getDefaultDisplay().getWidth();
                popupWindow = new PopupWindow(view_, width / 2, ViewGroup.LayoutParams.WRAP_CONTENT, false);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOutsideTouchable(true);
                popupWindow.setTouchable(true);
                popupWindow.showAsDropDown(iv_layer, 0, 20);

                break;
            case R.id.iv_map_search://搜索框输入定位
                flag = 2;
                mapShowAddressPop.showpupwindow(iv_search, mapControl);
                ibProjectLocation.setVisibility(View.VISIBLE);

                iv.setVisibility(View.GONE);
                tv_address.setVisibility(View.GONE);
                break;
            case R.id.ib_basemap_location:
                mapControl.location();
                break;
            case R.id.ib_basemap_shiliang:
                mapControl.setMap_Shiliang();
                ib_yingxiang.setVisibility(View.VISIBLE);
                ib_shiliang.setVisibility(View.GONE);
                break;
            case R.id.ib_basemap_yingxiang:
                mapControl.setMap_Yingxiang();
                ib_shiliang.setVisibility(View.VISIBLE);
                ib_yingxiang.setVisibility(View.GONE);
                break;
            case R.id.ib_basemap_sure:
//                address = mapControl.showAddress(0, 0, tv_address);
//
//                Intent intent = new Intent();
//                latLng = mapControl.getGeoLatlng();
//                intent.putExtra("latitude", latLng.latitude);
//                intent.putExtra("longitude", latLng.longitude);
//                intent.putExtra("address", address);
                if (flag == 1) {
                    address = mapControl.showAddress(0, 0, tv_address);
                    latLng = mapControl.getGeoLatlng();
                }
                if (latLng != null) {
                    Intent intent = new Intent();
                    intent.putExtra("latitude", latLng.latitude);
                    intent.putExtra("longitude", latLng.longitude);
                    intent.putExtra("address", address);
                    setResult(RESULT_OK, intent);
                    finish();
                }

                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(EventBusMessage messageEvent) {
        switch (messageEvent.getMessage()) {
            case "returnAddr":
                latLng = messageEvent.getLatLng();
                address = messageEvent.getAddress();
                Log.i("", "onMoonEvent latLng: " + latLng);
                Log.i("", "onMoonEvent address: " + address);

                if (latLng == null) {
                    iv.setVisibility(View.VISIBLE);
                    tv_address.setVisibility(View.VISIBLE);
                    ibProjectLocation.setVisibility(View.VISIBLE);

                    latLng = mapControl.getGeoLatlng();
                    address = mapControl.showAddress(0, 0, tv_address);
                }
                break;
            case "returnAddr_empty":      //点击popupwindow 关闭
                iv.setVisibility(View.VISIBLE);
                tv_address.setVisibility(View.VISIBLE);
                ibProjectLocation.setVisibility(View.VISIBLE);

                latLng = mapControl.getGeoLatlng();
                address = mapControl.showAddress(0, 0, tv_address);
                break;
        }
    }


    //回退键
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // activity 恢复时同时恢复地图控件
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // activity 暂停时同时暂停地图控件
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapControl.destory();
    }

    @Override
    public void setState(int state) {

    }

    @Override
    public void getDataById(AllData allData) {
        Log.i("", "getDataById allData: " + allData);

        allShowData = allData;

    }

    private void showProjectData(AllData allData) {
        pro_items.clear();
        markerClick = 1;
        MapMarkerItem mapMarkerItem;
        List<AllData.ProListBean> proList = allData.getProList();
        for (int i = 0; i < proList.size(); i++) {
            mapMarkerItem = new MapMarkerItem();
            String location = proList.get(i).getLocation();
            if (!"".equals(location) && !"null".equals(location) && null != location) {
                if (location.contains(",")) {
                    String substring = location.substring(6, location.length() - 1);
                    String longitude = substring.split(",")[0];
                    String latitude = substring.split(",")[1];
                    mapMarkerItem.setFlag(1);
                    mapMarkerItem.setName(proList.get(i).getName());
                    mapMarkerItem.setmPosition(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)));
                    mapMarkerItem.setId(proList.get(i).getId());
                    pro_items.add(mapMarkerItem);
                }
            }

            Log.i("items", "showProjectData: " + items);
//            items.addAll(pro_items);
//            mapControl.mulMarkerMerge(34.876091, 113.649413, items);
        }
    }

    private void showWorkData(AllData allData) {
        work_items.clear();
        markerClick = 2;
        MapMarkerItem mapMarkerItem;
        List<AllData.WorkListBean> workList = allData.getWorkList();
        for (int j = 0; j < workList.size(); j++) {
            mapMarkerItem = new MapMarkerItem();
            String location = workList.get(j).getLocation();
            Log.i("items", "showWorkData  location: " + location);
            if (!"".equals(location) && !"null".equals(location) && null != location) {
                if (location.contains(",")) {
                    String substring = location.substring(6, location.length() - 1);
                    String longitude = substring.split(",")[0];
                    String latitude = substring.split(",")[1];
                    mapMarkerItem.setFlag(2);
                    mapMarkerItem.setName(workList.get(j).getName());
                    mapMarkerItem.setmPosition(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)));
                    mapMarkerItem.setId(workList.get(j).getId());
                    work_items.add(mapMarkerItem);
                }
            }
        }
        Log.i("items", "showWorkData: " + items);
//        items.addAll(work_items);
//        mapControl.mulMarkerMerge(34.876091, 113.649413, items);
    }

    private void showEventData(AllData allData) {
        event_items.clear();
        markerClick = 3;
        MapMarkerItem mapMarkerItem;
        List<AllData.EventListBean> eventList = allData.getEventList();
        for (int k = 0; k < eventList.size(); k++) {
            mapMarkerItem = new MapMarkerItem();
            String location = eventList.get(k).getLocation();
            Log.i("", "showEventData location: " + location);
            if (!"".equals(location) && !"null".equals(location) && null != location) {
                if (location.contains(",")) {
                    String substring = location.substring(6, location.length() - 1);
                    String longitude = substring.split(",")[0];
                    String latitude = substring.split(",")[1];
                    mapMarkerItem.setFlag(3);
                    mapMarkerItem.setName(eventList.get(k).getTitle());
                    mapMarkerItem.setmPosition(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)));
                    mapMarkerItem.setId(eventList.get(k).getId());
                    event_items.add(mapMarkerItem);
                }
            }

        }

        Log.i("", "getDataById: " + items);
//        items.addAll(event_items);
        //113.649413,34.876091
//        mapControl.mulMarkerMerge(34.876091, 113.649413, items);
    }


    @Override
    public void showSearchUser(List<Frame> frames) {

    }


}

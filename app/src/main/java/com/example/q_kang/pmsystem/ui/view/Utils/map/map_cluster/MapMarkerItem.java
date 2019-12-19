package com.example.q_kang.pmsystem.ui.view.Utils.map.map_cluster;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.model.LatLng;
import com.example.q_kang.pmsystem.ui.view.Utils.map.MapControl;
import com.example.q_kang.pmsystem.ui.view.Utils.map.map_cluster.clustering.view.ClusterItem;


/**
 * 每个Marker点，包含Marker点坐标以及图标
 */
public class MapMarkerItem implements ClusterItem,Parcelable {
    private  LatLng mPosition;
    private int flag;
    private String name;
    private String id;

    public MapMarkerItem() {
    }

    public MapMarkerItem(LatLng latLng, String name, int flag,String id) {
        this.mPosition = latLng;
        this.flag = flag;
        this.name = name;
        this.id = id;
    }

    protected MapMarkerItem(Parcel in) {
        mPosition = in.readParcelable(LatLng.class.getClassLoader());
        flag = in.readInt();
        name = in.readString();
        id = in.readString();
    }

    public static final Creator<MapMarkerItem> CREATOR = new Creator<MapMarkerItem>() {
        @Override
        public MapMarkerItem createFromParcel(Parcel in) {
            return new MapMarkerItem(in);
        }

        @Override
        public MapMarkerItem[] newArray(int size) {
            return new MapMarkerItem[size];
        }
    };

    @Override
    public LatLng getPosition() {
        return mPosition;
    }


//    @Override
//    public BitmapDescriptor getBitmapDescriptor() {
//        int image = 0;
//        if (flag == 1){
//            image = R.mipmap.classify_pro;
//        }else if (flag == 2){
//            image = R.mipmap.classify_work;
//        }else if (flag == 3){
//            image = R.mipmap.classify_event;
//        }
//        return BitmapDescriptorFactory.fromResource(image);
//    }

    @Override
    public BitmapDescriptor getBitmapDescriptor() {

//        return MapControl.getBitmapDescriptor(name,flag,mPosition,id);
        return MapControl.getBitmapDescriptor(new MapMarkerItem(mPosition,name,flag,id));
    }


    public LatLng getmPosition() {
        return mPosition;
    }

    public void setmPosition(LatLng mPosition) {
        this.mPosition = mPosition;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MapMarkerItem{" +
                "mPosition=" + mPosition +
                ", flag=" + flag +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(mPosition, i);
        parcel.writeInt(flag);
        parcel.writeString(name);
        parcel.writeString(id);
    }
}
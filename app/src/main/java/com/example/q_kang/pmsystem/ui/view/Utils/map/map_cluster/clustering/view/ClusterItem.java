package com.example.q_kang.pmsystem.ui.view.Utils.map.map_cluster.clustering.view;

/**
 * Created by appler on 2018/6/21.
 */

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.model.LatLng;

/**
 * ClusterItem represents a marker on the map.
 */
public interface ClusterItem {

    /**
     * The position of this marker. This must always return the same value.
     */
    LatLng getPosition();

    BitmapDescriptor getBitmapDescriptor();
}
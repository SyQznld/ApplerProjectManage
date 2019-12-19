package com.example.q_kang.pmsystem.ui.view.Utils.map.map_cluster.clustering.view;

/**
 * Created by appler on 2018/6/21.
 */

import com.baidu.mapapi.model.LatLng;

import java.util.Collection;

/**
 * A collection of ClusterItems that are nearby each other.
 */
public interface Cluster<T extends ClusterItem> {
    public LatLng getPosition();

    Collection<T> getItems();

    int getSize();
}
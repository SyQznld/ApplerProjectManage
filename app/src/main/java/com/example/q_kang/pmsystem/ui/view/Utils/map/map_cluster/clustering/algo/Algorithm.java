package com.example.q_kang.pmsystem.ui.view.Utils.map.map_cluster.clustering.algo;

/**
 * Created by appler on 2018/6/21.
 */


import com.example.q_kang.pmsystem.ui.view.Utils.map.map_cluster.clustering.view.Cluster;
import com.example.q_kang.pmsystem.ui.view.Utils.map.map_cluster.clustering.view.ClusterItem;

import java.util.Collection;
import java.util.Set;

/**
 * Logic for computing clusters
 */
public interface Algorithm<T extends ClusterItem> {
    void addItem(T item);

    void addItems(Collection<T> items);

    void clearItems();

    void removeItem(T item);

    Set<? extends Cluster<T>> getClusters(double zoom);

    Collection<T> getItems();
}
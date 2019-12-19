/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */

package com.example.q_kang.pmsystem.ui.view.Utils.map.map_cluster.projection;

public class Point {
    public final double x;
    public final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{"
                + "x=" + x
                +  ", y=" + y
                + '}';
    }
}

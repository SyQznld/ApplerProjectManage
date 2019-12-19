package com.example.q_kang.pmsystem.modules.bean.bean;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by appler on 2018/6/19.
 */

public class EventBusMessage {
    private String recordPath;
    private float recordTime;
    private String address;

    private String message;

    private LatLng latLng;

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EventBusMessage(String message) {
        this.message = message;
    }

    public EventBusMessage(String recordPath, float recordTime) {
        this.recordPath = recordPath;
        this.recordTime = recordTime;
    }

    public float getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(float recordTime) {
        this.recordTime = recordTime;
    }

    public String getRecordPath() {
        return recordPath;
    }

    public void setRecordPath(String recordPath) {
        this.recordPath = recordPath;
    }
}

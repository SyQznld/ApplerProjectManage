package com.example.q_kang.pmsystem.modules.bean.bean.work;

/**
 * Created by appler on 2018/6/12.
 */

public class ModelWorkBean {
    private String workName;
    private  String head;
    private String fuzerenName;
    private String userId;

    public ModelWorkBean() {
    }

    public ModelWorkBean(String workName, String head, String fuzerenName) {
        this.workName = workName;
        this.head = head;
        this.fuzerenName = fuzerenName;
    }

    public ModelWorkBean(String workName, String head, String fuzerenName, String userId) {
        this.workName = workName;
        this.head = head;
        this.fuzerenName = fuzerenName;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getHead() {
        return head == null ? "" : head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getFuzerenName() {
        return fuzerenName;
    }

    public void setFuzerenName(String fuzerenName) {
        this.fuzerenName = fuzerenName;
    }

    @Override
    public String toString() {
        return "ModelWorkBean{" +
                "workName='" + workName + '\'' +
                ", head='" + head + '\'' +
                ", fuzerenName='" + fuzerenName + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}



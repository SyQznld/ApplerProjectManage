package com.example.q_kang.pmsystem.modules.bean.bean;

import android.graphics.Bitmap;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

public class Person extends BaseIndexPinyinBean {

    private String name;
    private String phone;
    private Bitmap iamge;

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Bitmap getIamge() {
        return iamge;
    }

    public void setIamge(Bitmap iamge) {
        this.iamge = iamge;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", iamge=" + iamge +
                '}';
    }

    @Override
    public String getTarget() {
        return name;
    }
}

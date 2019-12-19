package com.example.q_kang.pmsystem.modules.bean.bean.photo;


import com.example.q_kang.pmsystem.ui.view.Utils.imageloader.ImageSelectUtils;

import java.util.ArrayList;

/**
 * 图片文件夹实体类
 */
public class Folder {

    private String name;
    private ArrayList<ImageItemBean> images;

    public Folder(String name) {
        this.name = name;
    }

    public Folder(String name, ArrayList<ImageItemBean> images) {
        this.name = name;
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ImageItemBean> getImages() {
        return images;
    }

    public void setImages(ArrayList<ImageItemBean> images) {
        this.images = images;
    }

    public void addImage(ImageItemBean image) {
        if (image != null && ImageSelectUtils.isNotEmptyString(image.getImagePath())) {
            if (images == null) {
                images = new ArrayList<>();
            }
            images.add(image);
        }
    }

    @Override
    public String toString() {
        return "Folder{" +
                "name='" + name + '\'' +
                ", images=" + images +
                '}';
    }
}

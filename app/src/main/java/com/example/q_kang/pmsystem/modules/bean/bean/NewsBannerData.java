package com.example.q_kang.pmsystem.modules.bean.bean;


public class NewsBannerData {
    private int images;
    private String title;

    public NewsBannerData() {
    }

    public NewsBannerData(int images, String title) {
        this.images = images;
        this.title = title;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "NewsBannerData{" +
                "images='" + images + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}

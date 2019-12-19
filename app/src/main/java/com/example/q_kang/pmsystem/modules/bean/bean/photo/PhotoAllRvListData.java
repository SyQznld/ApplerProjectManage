package com.example.q_kang.pmsystem.modules.bean.bean.photo;



public class PhotoAllRvListData {
    private String imagePath;
    private String time;

    public PhotoAllRvListData() {
    }

    public PhotoAllRvListData(String imagePath, String time) {
        this.imagePath = imagePath;
        this.time = time;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "PhotoAllRvListData{" +
                "imagePath='" + imagePath + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}

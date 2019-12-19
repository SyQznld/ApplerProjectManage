package com.example.q_kang.pmsystem.modules.bean.bean;


public class NewsItemData {
    private String title;
    private String author;
    private String time;
    private String imagePath;

    private int type;

    public NewsItemData() {
    }

    public NewsItemData(String title, String author, String time, String imagePath, int type) {
        this.title = title;
        this.author = author;
        this.time = time;
        this.imagePath = imagePath;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "NewsItemData{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", time='" + time + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", type=" + type +
                '}';
    }
}

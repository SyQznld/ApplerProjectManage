package com.example.q_kang.pmsystem.modules.bean.bean.photo;



public class SystemPicBean {
    private String firstImagePath;
    private String folderName;
    private int imageCounts;

    public SystemPicBean() {
    }

    public SystemPicBean(String firstImagePath, String folderName, int imageCounts) {
        this.firstImagePath = firstImagePath;
        this.folderName = folderName;
        this.imageCounts = imageCounts;
    }

    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getImageCounts() {
        return imageCounts;
    }

    public void setImageCounts(int imageCounts) {
        this.imageCounts = imageCounts;
    }

    @Override
    public String toString() {
        return "SystemPicBean{" +
                "firstImagePath='" + firstImagePath + '\'' +
                ", folderName='" + folderName + '\'' +
                ", imageCounts=" + imageCounts +
                '}';
    }
}

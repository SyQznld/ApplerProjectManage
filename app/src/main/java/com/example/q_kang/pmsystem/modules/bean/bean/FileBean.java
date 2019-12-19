package com.example.q_kang.pmsystem.modules.bean.bean;


import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class FileBean implements Parcelable{

    @Id
    private Long id;
    private String fileName;
    private String filePath;
    private String chooseTime;

    public FileBean() {
    }

    public FileBean(String fileName, String filePath, String chooseTime) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.chooseTime = chooseTime;
    }

    @Generated(hash = 2048233865)
    public FileBean(Long id, String fileName, String filePath, String chooseTime) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.chooseTime = chooseTime;
    }

    protected FileBean(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        fileName = in.readString();
        filePath = in.readString();
        chooseTime = in.readString();
    }

    public static final Creator<FileBean> CREATOR = new Creator<FileBean>() {
        @Override
        public FileBean createFromParcel(Parcel in) {
            return new FileBean(in);
        }

        @Override
        public FileBean[] newArray(int size) {
            return new FileBean[size];
        }
    };

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getChooseTime() {
        return chooseTime;
    }

    public void setChooseTime(String chooseTime) {
        this.chooseTime = chooseTime;
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", chooseTime='" + chooseTime + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(fileName);
        dest.writeString(filePath);
        dest.writeString(chooseTime);
    }
}

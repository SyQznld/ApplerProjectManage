package com.example.q_kang.pmsystem.modules.bean.bean.photo;


import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ImageItemBean implements Parcelable{

    @Id
    private Long id;
    
    private String imageName;
    private String imagePath;
    private String time;
    @Generated(hash = 149173377)
    public ImageItemBean(Long id, String imageName, String imagePath, String time) {
        this.id = id;
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.time = time;
    }
    @Generated(hash = 1604742006)
    public ImageItemBean() {
    }

    public ImageItemBean(String imageName, String imagePath, String time) {
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.time = time;
    }

    protected ImageItemBean(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        imageName = in.readString();
        imagePath = in.readString();
        time = in.readString();
    }

    public static final Creator<ImageItemBean> CREATOR = new Creator<ImageItemBean>() {
        @Override
        public ImageItemBean createFromParcel(Parcel in) {
            return new ImageItemBean(in);
        }

        @Override
        public ImageItemBean[] newArray(int size) {
            return new ImageItemBean[size];
        }
    };

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getImageName() {
        return this.imageName;
    }
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    public String getImagePath() {
        return this.imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "ImageItemBean{" +
                "id=" + id +
                ", imageName='" + imageName + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", time='" + time + '\'' +
                '}';
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
        dest.writeString(imageName);
        dest.writeString(imagePath);
        dest.writeString(time);
    }
}

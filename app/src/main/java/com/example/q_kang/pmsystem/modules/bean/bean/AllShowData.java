package com.example.q_kang.pmsystem.modules.bean.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by appler on 2018/7/24.
 */

public class AllShowData implements Parcelable {

    private String id;
    private String time;
    private String name;
    private int flag;

    private String leaderId;
    private String addUserId;
    private String joinPerson;

    public AllShowData() {
    }

    protected AllShowData(Parcel in) {
        id = in.readString();
        time = in.readString();
        name = in.readString();
        flag = in.readInt();
    }

    public static final Creator<AllShowData> CREATOR = new Creator<AllShowData>() {
        @Override
        public AllShowData createFromParcel(Parcel in) {
            return new AllShowData(in);
        }

        @Override
        public AllShowData[] newArray(int size) {
            return new AllShowData[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "AllShowData{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", flag=" + flag +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(time);
        dest.writeString(name);
        dest.writeInt(flag);
    }
}


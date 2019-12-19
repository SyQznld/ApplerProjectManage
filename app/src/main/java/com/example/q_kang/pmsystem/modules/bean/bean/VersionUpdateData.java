package com.example.q_kang.pmsystem.modules.bean.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by appler on 2018/9/14.
 */

public class VersionUpdateData implements Parcelable{


    /**
     * URL : http://192.168.2.221:8004/App/长垣规划.apk
     * Version : 1.03
     * Content :
     版本更新信息：
     测试输入行1
     测试输入行2
     */

    private String URL;
    private String Version;
    private String Content;

    protected VersionUpdateData(Parcel in) {
        URL = in.readString();
        Version = in.readString();
        Content = in.readString();
    }

    public static final Creator<VersionUpdateData> CREATOR = new Creator<VersionUpdateData>() {
        @Override
        public VersionUpdateData createFromParcel(Parcel in) {
            return new VersionUpdateData(in);
        }

        @Override
        public VersionUpdateData[] newArray(int size) {
            return new VersionUpdateData[size];
        }
    };

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String Version) {
        this.Version = Version;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(URL);
        dest.writeString(Version);
        dest.writeString(Content);
    }

    @Override
    public String toString() {
        return "VersionUpdateData{" +
                "URL='" + URL + '\'' +
                ", Version='" + Version + '\'' +
                ", Content='" + Content + '\'' +
                '}';
    }
}



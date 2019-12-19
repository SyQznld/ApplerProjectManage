package com.example.q_kang.pmsystem.modules.bean.bean;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ModelData implements Parcelable {
    /**
     * Id : c235fb49-5bca-44b3-a7ee-429d871d2ecc
     * Name : 软件开发
     * mContentList :
     * [{"Id":"ede14ed4-43a5-42d8-bcaf-9884b0625437","Name":"需求分析","Sort":1,"pId":"00000000-0000-0000-0000-000000000000"},
     * {"Id":"6ea9db44-dd6f-4602-9a27-93464f14fdd6","Name":"项目设计","Sort":2,"pId":"00000000-0000-0000-0000-000000000000"},
     * {"Id":"5a6ad0b6-de06-48b9-9415-5f3c9676745e","Name":"交接","Sort":6,"pId":"00000000-0000-0000-0000-000000000000"},
     * {"Id":"3a8d3979-261b-47bc-852f-8d18bb014782","Name":"测试","Sort":5,"pId":"00000000-0000-0000-0000-000000000000"}]
     */

    private String Id;
    private String Name;
    private List<MContentListBean> mContentList;

    public ModelData() {
    }

    protected ModelData(Parcel in) {
        Id = in.readString();
        Name = in.readString();
    }

    public static final Creator<ModelData> CREATOR = new Creator<ModelData>() {
        @Override
        public ModelData createFromParcel(Parcel in) {
            return new ModelData(in);
        }

        @Override
        public ModelData[] newArray(int size) {
            return new ModelData[size];
        }
    };

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public List<MContentListBean> getMContentList() {
        return mContentList;
    }

    public void setMContentList(List<MContentListBean> mContentList) {
        this.mContentList = mContentList;
    }

    @Override
    public String toString() {
        return "ModelData{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", mContentList=" + mContentList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(Name);
    }

    public static class MContentListBean implements Parcelable {
        /**
         * Id : ede14ed4-43a5-42d8-bcaf-9884b0625437
         * Name : 需求分析
         * Sort : 1
         * pId : 00000000-0000-0000-0000-000000000000
         */

        private String Id;
        private String Name;
        private int Sort;
        private String pId;

        public MContentListBean() {
        }

        public MContentListBean(Parcel in) {
            Id = in.readString();
            Name = in.readString();
            Sort = in.readInt();
            pId = in.readString();
        }

        public static final Creator<MContentListBean> CREATOR = new Creator<MContentListBean>() {
            @Override
            public MContentListBean createFromParcel(Parcel in) {
                return new MContentListBean(in);
            }

            @Override
            public MContentListBean[] newArray(int size) {
                return new MContentListBean[size];
            }
        };

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public int getSort() {
            return Sort;
        }

        public void setSort(int Sort) {
            this.Sort = Sort;
        }

        public String getPId() {
            return pId;
        }

        public void setPId(String pId) {
            this.pId = pId;
        }

        @Override
        public String toString() {
            return "MContentListBean{" +
                    "Id='" + Id + '\'' +
                    ", Name='" + Name + '\'' +
                    ", Sort=" + Sort +
                    ", pId='" + pId + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(Id);
            dest.writeString(Name);
            dest.writeInt(Sort);
            dest.writeString(pId);
        }
    }
}

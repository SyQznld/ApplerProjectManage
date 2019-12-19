package com.example.q_kang.pmsystem.modules.bean.bean.event;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by appler on 2018/8/6.
 */

public class EventComment implements Parcelable{


    /**
     * count : 1
     * data : [{"Content":"测试批示功能1111","IsDeleted":false,"AddTime":"2018-08-15","Flag":"","Pid":"585a5da0-4bc4-41bf-95f7-cf5e89f74a4a","Id":"5e0df64a-c9a0-444b-bdad-39a50ce2fb6b","AddUserId":"85722c8b-7122-e811-9c45-80a589abefb8","Adduser":"李峰","CanEdit":false}]
     */

    private int count;
    private List<DataBean> data;

    protected EventComment(Parcel in) {
        count = in.readInt();
        data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Creator<EventComment> CREATOR = new Creator<EventComment>() {
        @Override
        public EventComment createFromParcel(Parcel in) {
            return new EventComment(in);
        }

        @Override
        public EventComment[] newArray(int size) {
            return new EventComment[size];
        }
    };

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "EventComment{" +
                "count=" + count +
                ", data=" + data +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(count);
        dest.writeTypedList(data);
    }

    public static class DataBean implements Parcelable{
        /**
         * Content : 测试批示功能1111
         * IsDeleted : false
         * AddTime : 2018-08-15
         * Flag :
         * Pid : 585a5da0-4bc4-41bf-95f7-cf5e89f74a4a
         * Id : 5e0df64a-c9a0-444b-bdad-39a50ce2fb6b
         * AddUserId : 85722c8b-7122-e811-9c45-80a589abefb8
         * Adduser : 李峰
         * CanEdit : false
         */

        private String Content;
        private boolean IsDeleted;
        private String AddTime;
        private String Flag;
        private String Pid;
        private String Id;
        private String AddUserId;
        private String Adduser;
        private boolean CanEdit;

        protected DataBean(Parcel in) {
            Content = in.readString();
            IsDeleted = in.readByte() != 0;
            AddTime = in.readString();
            Flag = in.readString();
            Pid = in.readString();
            Id = in.readString();
            AddUserId = in.readString();
            Adduser = in.readString();
            CanEdit = in.readByte() != 0;
        }


        @Override
        public String toString() {
            return "DataBean{" +
                    "Content='" + Content + '\'' +
                    ", IsDeleted=" + IsDeleted +
                    ", AddTime='" + AddTime + '\'' +
                    ", Flag='" + Flag + '\'' +
                    ", Pid='" + Pid + '\'' +
                    ", Id='" + Id + '\'' +
                    ", AddUserId='" + AddUserId + '\'' +
                    ", Adduser='" + Adduser + '\'' +
                    ", CanEdit=" + CanEdit +
                    '}';
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public boolean isIsDeleted() {
            return IsDeleted;
        }

        public void setIsDeleted(boolean IsDeleted) {
            this.IsDeleted = IsDeleted;
        }

        public String getAddTime() {
            return AddTime;
        }

        public void setAddTime(String AddTime) {
            this.AddTime = AddTime;
        }

        public String getFlag() {
            return Flag;
        }

        public void setFlag(String Flag) {
            this.Flag = Flag;
        }

        public String getPid() {
            return Pid;
        }

        public void setPid(String Pid) {
            this.Pid = Pid;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getAddUserId() {
            return AddUserId;
        }

        public void setAddUserId(String AddUserId) {
            this.AddUserId = AddUserId;
        }

        public String getAdduser() {
            return Adduser;
        }

        public void setAdduser(String Adduser) {
            this.Adduser = Adduser;
        }

        public boolean isCanEdit() {
            return CanEdit;
        }

        public void setCanEdit(boolean CanEdit) {
            this.CanEdit = CanEdit;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(Content);
            dest.writeByte((byte) (IsDeleted ? 1 : 0));
            dest.writeString(AddTime);
            dest.writeString(Flag);
            dest.writeString(Pid);
            dest.writeString(Id);
            dest.writeString(AddUserId);
            dest.writeString(Adduser);
            dest.writeByte((byte) (CanEdit ? 1 : 0));
        }
    }
}

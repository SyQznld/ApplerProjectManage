package com.example.q_kang.pmsystem.modules.bean.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by appler on 2018/8/25.
 */

public class DepartmentData implements Parcelable{


    /**
     * count : 9
     * data : [{"Id":"2429fdd1-4fd3-4e6a-928a-f36745221edd","Name":"长垣规划局","pId":"00000000-0000-0000-0000-000000000000","LeaderId":"797008e0-5a19-4d87-b59e-af36c0c5812d","IsParent":true},{"Id":"15d5729e-9936-42ad-a03c-d78b528a17a8","Name":"主管副局1","pId":"2429fdd1-4fd3-4e6a-928a-f36745221edd","LeaderId":"ae6dde53-1307-4a6d-bd5a-e0f083a92acc","IsParent":true},{"Id":"6729e59a-1457-4445-ad21-fb1e4043c744","Name":"规划科","pId":"15d5729e-9936-42ad-a03c-d78b528a17a8","LeaderId":"fac4c1ab-904d-4d5e-87a4-d98aec6c67ac","IsParent":false},{"Id":"a7e35a81-c3c7-4ecf-8300-8e4bc09cdc30","Name":"主管副局3","pId":"2429fdd1-4fd3-4e6a-928a-f36745221edd","LeaderId":"22103b61-7303-4608-8402-d36488d60e3b","IsParent":true},{"Id":"3351e736-72eb-4cd4-804f-32f3072483f9","Name":"工程科","pId":"dc8b5129-9e0a-4a44-ab33-11bd7a3879d9","LeaderId":"23c315d6-9daf-42cd-b99a-04f6d915c352","IsParent":false},{"Id":"c031bf55-44f6-4ea7-94bc-2179c6488c9c","Name":"其它科","pId":"15d5729e-9936-42ad-a03c-d78b528a17a8","LeaderId":"9cbe899d-13ad-4d76-8568-a967151bcf15","IsParent":false},{"Id":"e756c2a8-680a-4af4-8c95-9c811dfb1654","Name":"纪检室","pId":"2429fdd1-4fd3-4e6a-928a-f36745221edd","LeaderId":"00000000-0000-0000-0000-000000000000","IsParent":false},{"Id":"dc8b5129-9e0a-4a44-ab33-11bd7a3879d9","Name":"主管副局2","pId":"2429fdd1-4fd3-4e6a-928a-f36745221edd","LeaderId":"14229e34-52a4-4323-8bc5-439b7c194b99","IsParent":true},{"Id":"32570b29-7b4b-418d-a6d0-4167bb2db443","Name":"监察队","pId":"a7e35a81-c3c7-4ecf-8300-8e4bc09cdc30","LeaderId":"259b5d01-add8-4d1a-82ae-42d2c6b6e2e3","IsParent":false}]
     */

    private int count;
    private List<DataBean> data;

    protected DepartmentData(Parcel in) {
        count = in.readInt();
        data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Creator<DepartmentData> CREATOR = new Creator<DepartmentData>() {
        @Override
        public DepartmentData createFromParcel(Parcel in) {
            return new DepartmentData(in);
        }

        @Override
        public DepartmentData[] newArray(int size) {
            return new DepartmentData[size];
        }
    };

    @Override
    public String toString() {
        return "DepartmentData{" +
                "count=" + count +
                ", data=" + data +
                '}';
    }

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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(count);
        dest.writeTypedList(data);
    }

    public static class DataBean implements Parcelable{
        /**
         * Id : 2429fdd1-4fd3-4e6a-928a-f36745221edd
         * Name : 长垣规划局
         * pId : 00000000-0000-0000-0000-000000000000
         * LeaderId : 797008e0-5a19-4d87-b59e-af36c0c5812d
         * IsParent : true
         */

        private String Id;
        private String Name;
        private String pId;
        private String LeaderId;
        private boolean IsParent;

        protected DataBean(Parcel in) {
            Id = in.readString();
            Name = in.readString();
            pId = in.readString();
            LeaderId = in.readString();
            IsParent = in.readByte() != 0;
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

        @Override
        public String toString() {
            return "DataBean{" +
                    "Id='" + Id + '\'' +
                    ", Name='" + Name + '\'' +
                    ", pId='" + pId + '\'' +
                    ", LeaderId='" + LeaderId + '\'' +
                    ", IsParent=" + IsParent +
                    '}';
        }

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

        public String getPId() {
            return pId;
        }

        public void setPId(String pId) {
            this.pId = pId;
        }

        public String getLeaderId() {
            return LeaderId;
        }

        public void setLeaderId(String LeaderId) {
            this.LeaderId = LeaderId;
        }

        public boolean isIsParent() {
            return IsParent;
        }

        public void setIsParent(boolean IsParent) {
            this.IsParent = IsParent;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(Id);
            dest.writeString(Name);
            dest.writeString(pId);
            dest.writeString(LeaderId);
            dest.writeByte((byte) (IsParent ? 1 : 0));
        }
    }
}

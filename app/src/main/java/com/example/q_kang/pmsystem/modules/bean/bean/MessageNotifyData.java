package com.example.q_kang.pmsystem.modules.bean.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by appler on 2018/9/3.
 */

public class MessageNotifyData implements Parcelable{


    /**
     * count : 10
     * data : [{"PartA":"您负责的 ","PartB":"0716下午测试测试","PartC":" 工作已更新","Id":"a1062173-da25-45f1-a8db-a83c2ee98539","_Type":"1","IsRead":true,"Message_Id":"f8c3f99e-73b2-4afd-8d1e-2fdf7b235d1c"},{"PartA":null,"PartB":"0913","PartC":null,"Id":"cfd3eacb-bcb1-4bc4-859f-0821eb64626e","_Type":"3","IsRead":false,"Message_Id":"c75bfa29-f7e8-42e5-8a06-68b17251e290"},{"PartA":null,"PartB":"0913","PartC":null,"Id":"cfd3eacb-bcb1-4bc4-859f-0821eb64626e","_Type":"3","IsRead":false,"Message_Id":"cb228b48-9a99-429f-aecb-7583ce142e5b"},{"PartA":"您有 ","PartB":"0829测试","PartC":" 事件需要负责","Id":"c639cd71-89a2-42c8-8275-61f4cf7c2554","_Type":"2","IsRead":true,"Message_Id":"3acc563a-68c7-48f8-ba53-7dddd588cd93"},{"PartA":"您有 ","PartB":"0829测试","PartC":" 事件需要负责","Id":"c639cd71-89a2-42c8-8275-61f4cf7c2554","_Type":"2","IsRead":true,"Message_Id":"050485da-8964-4d41-b6f4-8800bb59706b"},{"PartA":"您负责的 ","PartB":"0716下午测试测试","PartC":" 工作已更新","Id":"a1062173-da25-45f1-a8db-a83c2ee98539","_Type":"1","IsRead":true,"Message_Id":"d5a10d79-08bb-41c4-9e03-a46adb56fd0c"},{"PartA":"您负责的 ","PartB":"0716下午测试测试","PartC":" 工作已更新","Id":"a1062173-da25-45f1-a8db-a83c2ee98539","_Type":"1","IsRead":true,"Message_Id":"9fb3b83f-2343-4665-bfad-aaa472b0c64b"},{"PartA":null,"PartB":"0913","PartC":null,"Id":"cfd3eacb-bcb1-4bc4-859f-0821eb64626e","_Type":"3","IsRead":false,"Message_Id":"50b6005c-7748-41f8-b84a-b55ddea2b4fe"},{"PartA":"您有 ","PartB":"0829测试","PartC":" 事件需要负责","Id":"c639cd71-89a2-42c8-8275-61f4cf7c2554","_Type":"2","IsRead":false,"Message_Id":"e7b44442-3d0c-4041-9279-f30c58202c8b"},{"PartA":"您有 ","PartB":"0829测试","PartC":" 事件需要负责","Id":"c639cd71-89a2-42c8-8275-61f4cf7c2554","_Type":"2","IsRead":false,"Message_Id":"b0cf82fe-4a49-46f0-9088-f3fba4166a32"}]
     */

    private int count;
    private List<DataBean> data;

    protected MessageNotifyData(Parcel in) {
        count = in.readInt();
    }

    public static final Creator<MessageNotifyData> CREATOR = new Creator<MessageNotifyData>() {
        @Override
        public MessageNotifyData createFromParcel(Parcel in) {
            return new MessageNotifyData(in);
        }

        @Override
        public MessageNotifyData[] newArray(int size) {
            return new MessageNotifyData[size];
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(count);
    }

    @Override
    public String toString() {
        return "MessageNotifyData{" +
                "count=" + count +
                ", data=" + data +
                '}';
    }

    public static class DataBean implements Parcelable{
        /**
         * PartA : 您负责的
         * PartB : 0716下午测试测试
         * PartC :  工作已更新
         * Id : a1062173-da25-45f1-a8db-a83c2ee98539
         * _Type : 1
         * IsRead : true
         * Message_Id : f8c3f99e-73b2-4afd-8d1e-2fdf7b235d1c
         */

        private String PartA;
        private String PartB;
        private String PartC;
        private String Id;
        private String _Type;
        private boolean IsRead;
        private String Message_Id;

        public DataBean() {
        }

        public DataBean(String partA, String partB, String partC, String id, String _Type, boolean isRead, String message_Id) {
            PartA = partA;
            PartB = partB;
            PartC = partC;
            Id = id;
            this._Type = _Type;
            IsRead = isRead;
            Message_Id = message_Id;
        }

        protected DataBean(Parcel in) {
            PartA = in.readString();
            PartB = in.readString();
            PartC = in.readString();
            Id = in.readString();
            _Type = in.readString();
            IsRead = in.readByte() != 0;
            Message_Id = in.readString();
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

        public String getPartA() {
            return PartA;
        }

        public void setPartA(String PartA) {
            this.PartA = PartA;
        }

        public String getPartB() {
            return PartB;
        }

        public void setPartB(String PartB) {
            this.PartB = PartB;
        }

        public String getPartC() {
            return PartC;
        }

        public void setPartC(String PartC) {
            this.PartC = PartC;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String get_Type() {
            return _Type;
        }

        public void set_Type(String _Type) {
            this._Type = _Type;
        }

        public boolean isIsRead() {
            return IsRead;
        }

        public void setIsRead(boolean IsRead) {
            this.IsRead = IsRead;
        }

        public String getMessage_Id() {
            return Message_Id;
        }

        public void setMessage_Id(String Message_Id) {
            this.Message_Id = Message_Id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(PartA);
            dest.writeString(PartB);
            dest.writeString(PartC);
            dest.writeString(Id);
            dest.writeString(_Type);
            dest.writeByte((byte) (IsRead ? 1 : 0));
            dest.writeString(Message_Id);
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "PartA='" + PartA + '\'' +
                    ", PartB='" + PartB + '\'' +
                    ", PartC='" + PartC + '\'' +
                    ", Id='" + Id + '\'' +
                    ", _Type='" + _Type + '\'' +
                    ", IsRead=" + IsRead +
                    ", Message_Id='" + Message_Id + '\'' +
                    '}';
        }
    }
}

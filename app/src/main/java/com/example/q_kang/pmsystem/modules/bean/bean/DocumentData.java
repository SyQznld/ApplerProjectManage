package com.example.q_kang.pmsystem.modules.bean.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by appler on 2018/8/10.
 */

public class DocumentData implements Parcelable{


    /**
     * count : 1
     * data : [{"Id":"760685fe-0cd1-40f7-bdc1-31fc68c0abc2","AddUserId":"9cbe899d-13ad-4d76-8568-a967151bcf15","AddTime":"2018-08-15T00:00:00","UpdateTime":"2018-08-15T00:00:00","Upload":"/Upload/2018年08月15日 17时07分48秒/image6ddc8ffb0a8d4360bba5ac3041011739photo.jpg","Content":"测试测试","Name":"公文测试111                                                                                             ","IsSend":true,"send_receive":[{"Id":"daddef5f-9717-4bb8-b9b1-0b9ee2d8ce15","Pid":"760685fe-0cd1-40f7-bdc1-31fc68c0abc2","SendPerson":"9cbe899d-13ad-4d76-8568-a967151bcf15","ReceivePerson":"85722c8b-7122-e811-9c45-80a589abefb8","IsRepost":false,"IsRead":false,"SendTime":"2018-08-15T00:00:00","RealName":"李峰","image":"/Upload/2018年06月25日 15时46分24秒/3.jpg"},{"Id":"384436f8-b0cf-4546-a1b3-21ea881a2f0d","Pid":"760685fe-0cd1-40f7-bdc1-31fc68c0abc2","SendPerson":"9cbe899d-13ad-4d76-8568-a967151bcf15","ReceivePerson":"e727e2fd-8742-4aa7-aba4-938c40c145e7","IsRepost":false,"IsRead":false,"SendTime":"2018-08-15T00:00:00","RealName":"姚博文","image":"/Upload/2018年06月25日 15时46分24秒/3.jpg"},{"Id":"d9aa8894-ac4e-42d0-b0da-5a3eb3b44b64","Pid":"760685fe-0cd1-40f7-bdc1-31fc68c0abc2","SendPerson":"9cbe899d-13ad-4d76-8568-a967151bcf15","ReceivePerson":"9cbe899d-13ad-4d76-8568-a967151bcf15","IsRepost":false,"IsRead":false,"SendTime":"2018-08-15T00:00:00","RealName":"段妞妞","image":"/Upload/2018年06月25日 15时46分24秒/3.jpg"}],"RealName":null,"image":null}]
     */

    private int count;
    private List<DataBean> data;

    protected DocumentData(Parcel in) {
        count = in.readInt();
    }

    public static final Creator<DocumentData> CREATOR = new Creator<DocumentData>() {
        @Override
        public DocumentData createFromParcel(Parcel in) {
            return new DocumentData(in);
        }

        @Override
        public DocumentData[] newArray(int size) {
            return new DocumentData[size];
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
        return "DocumentData{" +
                "count=" + count +
                ", data=" + data +
                '}';
    }

    public static class DataBean implements Parcelable{
        /**
         * Id : 760685fe-0cd1-40f7-bdc1-31fc68c0abc2
         * AddUserId : 9cbe899d-13ad-4d76-8568-a967151bcf15
         * AddTime : 2018-08-15T00:00:00
         * UpdateTime : 2018-08-15T00:00:00
         * Upload : /Upload/2018年08月15日 17时07分48秒/image6ddc8ffb0a8d4360bba5ac3041011739photo.jpg
         * Content : 测试测试
         * Name : 公文测试111
         * IsSend : true
         * send_receive : [{"Id":"daddef5f-9717-4bb8-b9b1-0b9ee2d8ce15","Pid":"760685fe-0cd1-40f7-bdc1-31fc68c0abc2","SendPerson":"9cbe899d-13ad-4d76-8568-a967151bcf15","ReceivePerson":"85722c8b-7122-e811-9c45-80a589abefb8","IsRepost":false,"IsRead":false,"SendTime":"2018-08-15T00:00:00","RealName":"李峰","image":"/Upload/2018年06月25日 15时46分24秒/3.jpg"},{"Id":"384436f8-b0cf-4546-a1b3-21ea881a2f0d","Pid":"760685fe-0cd1-40f7-bdc1-31fc68c0abc2","SendPerson":"9cbe899d-13ad-4d76-8568-a967151bcf15","ReceivePerson":"e727e2fd-8742-4aa7-aba4-938c40c145e7","IsRepost":false,"IsRead":false,"SendTime":"2018-08-15T00:00:00","RealName":"姚博文","image":"/Upload/2018年06月25日 15时46分24秒/3.jpg"},{"Id":"d9aa8894-ac4e-42d0-b0da-5a3eb3b44b64","Pid":"760685fe-0cd1-40f7-bdc1-31fc68c0abc2","SendPerson":"9cbe899d-13ad-4d76-8568-a967151bcf15","ReceivePerson":"9cbe899d-13ad-4d76-8568-a967151bcf15","IsRepost":false,"IsRead":false,"SendTime":"2018-08-15T00:00:00","RealName":"段妞妞","image":"/Upload/2018年06月25日 15时46分24秒/3.jpg"}]
         * RealName : null
         * image : null
         */

        private String Id;
        private String AddUserId;
        private String AddTime;
        private String UpdateTime;
        private String Upload;
        private String Content;
        private String Name;
        private boolean IsSend;
        private String RealName;
        private String image;
        private List<SendReceiveBean> send_receive;

        protected DataBean(Parcel in) {
            Id = in.readString();
            AddUserId = in.readString();
            AddTime = in.readString();
            UpdateTime = in.readString();
            Upload = in.readString();
            Content = in.readString();
            Name = in.readString();
            IsSend = in.readByte() != 0;
            RealName = in.readString();
            image = in.readString();
            send_receive = in.createTypedArrayList(SendReceiveBean.CREATOR);
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "Id='" + Id + '\'' +
                    ", AddUserId='" + AddUserId + '\'' +
                    ", AddTime='" + AddTime + '\'' +
                    ", UpdateTime='" + UpdateTime + '\'' +
                    ", Upload='" + Upload + '\'' +
                    ", Content='" + Content + '\'' +
                    ", Name='" + Name + '\'' +
                    ", IsSend=" + IsSend +
                    ", RealName='" + RealName + '\'' +
                    ", image='" + image + '\'' +
                    ", send_receive=" + send_receive +
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(Id);
            dest.writeString(AddUserId);
            dest.writeString(AddTime);
            dest.writeString(UpdateTime);
            dest.writeString(Upload);
            dest.writeString(Content);
            dest.writeString(Name);
            dest.writeByte((byte) (IsSend ? 1 : 0));
            dest.writeString(RealName);
            dest.writeString(image);
            dest.writeTypedList(send_receive);
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getAddUserId() {
            return AddUserId;
        }

        public void setAddUserId(String addUserId) {
            AddUserId = addUserId;
        }

        public String getAddTime() {
            return AddTime;
        }

        public void setAddTime(String addTime) {
            AddTime = addTime;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String updateTime) {
            UpdateTime = updateTime;
        }

        public String getUpload() {
            return Upload;
        }

        public void setUpload(String upload) {
            Upload = upload;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String content) {
            Content = content;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public boolean isSend() {
            return IsSend;
        }

        public void setSend(boolean send) {
            IsSend = send;
        }

        public String getRealName() {
            return RealName;
        }

        public void setRealName(String realName) {
            RealName = realName;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public List<SendReceiveBean> getSend_receive() {
            return send_receive;
        }

        public void setSend_receive(List<SendReceiveBean> send_receive) {
            this.send_receive = send_receive;
        }

        public static Creator<DataBean> getCREATOR() {
            return CREATOR;
        }

        public static class SendReceiveBean implements Parcelable{
            /**
             * Id : daddef5f-9717-4bb8-b9b1-0b9ee2d8ce15
             * Pid : 760685fe-0cd1-40f7-bdc1-31fc68c0abc2
             * SendPerson : 9cbe899d-13ad-4d76-8568-a967151bcf15
             * ReceivePerson : 85722c8b-7122-e811-9c45-80a589abefb8
             * IsRepost : false
             * IsRead : false
             * SendTime : 2018-08-15T00:00:00
             * RealName : 李峰
             * image : /Upload/2018年06月25日 15时46分24秒/3.jpg
             */

            private String Id;
            private String Pid;
            private String SendPerson;
            private String ReceivePerson;
            private boolean IsRepost;
            private boolean IsRead;
            private String SendTime;
            private String RealName;
            private String image;

            protected SendReceiveBean(Parcel in) {
                Id = in.readString();
                Pid = in.readString();
                SendPerson = in.readString();
                ReceivePerson = in.readString();
                IsRepost = in.readByte() != 0;
                IsRead = in.readByte() != 0;
                SendTime = in.readString();
                RealName = in.readString();
                image = in.readString();
            }

            public static final Creator<SendReceiveBean> CREATOR = new Creator<SendReceiveBean>() {
                @Override
                public SendReceiveBean createFromParcel(Parcel in) {
                    return new SendReceiveBean(in);
                }

                @Override
                public SendReceiveBean[] newArray(int size) {
                    return new SendReceiveBean[size];
                }
            };

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public String getPid() {
                return Pid;
            }

            public void setPid(String Pid) {
                this.Pid = Pid;
            }

            public String getSendPerson() {
                return SendPerson;
            }

            public void setSendPerson(String SendPerson) {
                this.SendPerson = SendPerson;
            }

            public String getReceivePerson() {
                return ReceivePerson;
            }

            public void setReceivePerson(String ReceivePerson) {
                this.ReceivePerson = ReceivePerson;
            }

            public boolean isIsRepost() {
                return IsRepost;
            }

            public void setIsRepost(boolean IsRepost) {
                this.IsRepost = IsRepost;
            }

            public boolean isIsRead() {
                return IsRead;
            }

            public void setIsRead(boolean IsRead) {
                this.IsRead = IsRead;
            }

            public String getSendTime() {
                return SendTime;
            }

            public void setSendTime(String SendTime) {
                this.SendTime = SendTime;
            }

            public String getRealName() {
                return RealName;
            }

            public void setRealName(String RealName) {
                this.RealName = RealName;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(Id);
                dest.writeString(Pid);
                dest.writeString(SendPerson);
                dest.writeString(ReceivePerson);
                dest.writeByte((byte) (IsRepost ? 1 : 0));
                dest.writeByte((byte) (IsRead ? 1 : 0));
                dest.writeString(SendTime);
                dest.writeString(RealName);
                dest.writeString(image);
            }

            @Override
            public String toString() {
                return "SendReceiveBean{" +
                        "Id='" + Id + '\'' +
                        ", Pid='" + Pid + '\'' +
                        ", SendPerson='" + SendPerson + '\'' +
                        ", ReceivePerson='" + ReceivePerson + '\'' +
                        ", IsRepost=" + IsRepost +
                        ", IsRead=" + IsRead +
                        ", SendTime='" + SendTime + '\'' +
                        ", RealName='" + RealName + '\'' +
                        ", image='" + image + '\'' +
                        '}';
            }
        }
    }
}

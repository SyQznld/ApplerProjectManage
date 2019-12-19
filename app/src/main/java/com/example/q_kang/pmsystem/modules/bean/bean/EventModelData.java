package com.example.q_kang.pmsystem.modules.bean.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by appler on 2018/8/31.
 */

public class EventModelData implements Parcelable{


    /**
     * code : 0
     * msg :
     * count : 6
     * data : [{"Id":"66701f1b-b1af-4bd6-b0eb-6edeb733a67b","PId":"00000000-0000-0000-0000-000000000000","Name":"事件模板测试","Sort":0,"templets":[{"Id":"4755a650-3ed4-4406-a2d7-4f42b4bdf444","PId":"66701f1b-b1af-4bd6-b0eb-6edeb733a67b","Name":"事件模板111****","Sort":1,"templets":[],"Count":0,"cNameList":[]},{"Id":"302da150-5662-4522-b1aa-0ec781f90da7","PId":"66701f1b-b1af-4bd6-b0eb-6edeb733a67b","Name":"事件模板测试22","Sort":2,"templets":[],"Count":0,"cNameList":[]},{"Id":"bf902c9d-f6c8-45a2-827a-9a974aaa2032","PId":"66701f1b-b1af-4bd6-b0eb-6edeb733a67b","Name":"事件模板测试333","Sort":3,"templets":[],"Count":0,"cNameList":[]},{"Id":"4123def5-d429-44e0-90c4-2f9ddc4172ed","PId":"66701f1b-b1af-4bd6-b0eb-6edeb733a67b","Name":"444444","Sort":4,"templets":[],"Count":0,"cNameList":[]}],"Count":4,"cNameList":[]},{"Id":"1f4bbc47-dbe6-4978-be89-d337e49c083c","PId":"00000000-0000-0000-0000-000000000000","Name":"新建事件模板流程0903","Sort":0,"templets":[{"Id":"da65ced2-1113-482c-bca9-fa2cde570e44","PId":"1f4bbc47-dbe6-4978-be89-d337e49c083c","Name":"事件流程 0","Sort":1,"templets":[],"Count":0,"cNameList":[]},{"Id":"9e84a919-734b-44d3-9853-0da5c51e0452","PId":"1f4bbc47-dbe6-4978-be89-d337e49c083c","Name":"事件流程 1","Sort":2,"templets":[],"Count":0,"cNameList":[]},{"Id":"177c6a52-debe-4ca9-950f-c9e0d023daab","PId":"1f4bbc47-dbe6-4978-be89-d337e49c083c","Name":"事件流程 2","Sort":3,"templets":[],"Count":0,"cNameList":[]}],"Count":3,"cNameList":[]},{"Id":"930c3073-49ac-47ac-8991-d50aca62ce83","PId":"00000000-0000-0000-0000-000000000000","Name":"QW","Sort":0,"templets":[{"Id":"fdbf69af-e050-482f-ab71-fc690fbd0e47","PId":"930c3073-49ac-47ac-8991-d50aca62ce83","Name":"wq","Sort":1,"templets":[],"Count":0,"cNameList":[]},{"Id":"ef855d53-43f1-4944-95b9-079659a4e203","PId":"930c3073-49ac-47ac-8991-d50aca62ce83","Name":"wwww","Sort":2,"templets":[],"Count":0,"cNameList":[]},{"Id":"c4b13ad5-e33d-4383-b687-bc4042bc8d18","PId":"930c3073-49ac-47ac-8991-d50aca62ce83","Name":"sa","Sort":3,"templets":[],"Count":0,"cNameList":[]},{"Id":"fc9eeb29-ad5d-4520-8c9b-b348c9b613c0","PId":"930c3073-49ac-47ac-8991-d50aca62ce83","Name":"dss","Sort":4,"templets":[],"Count":0,"cNameList":[]},{"Id":"0d6a483f-99c5-44dc-99d3-311af2182be4","PId":"930c3073-49ac-47ac-8991-d50aca62ce83","Name":"ada","Sort":5,"templets":[],"Count":0,"cNameList":[]}],"Count":5,"cNameList":[]},{"Id":"9ad3a500-443a-4c0e-b36e-f37eec69144e","PId":"00000000-0000-0000-0000-000000000000","Name":"事件模板0903","Sort":0,"templets":[{"Id":"eee77e57-dc91-4e64-9f03-6700442aefb3","PId":"9ad3a500-443a-4c0e-b36e-f37eec69144e","Name":"事件模板0903","Sort":1,"templets":[],"Count":0,"cNameList":[]},{"Id":"3c2038c6-9089-4476-834f-5755cccd4059","PId":"9ad3a500-443a-4c0e-b36e-f37eec69144e","Name":"事件模板测试","Sort":2,"templets":[],"Count":0,"cNameList":[]},{"Id":"57f6d107-0aeb-4cb8-a51c-22264abf512f","PId":"9ad3a500-443a-4c0e-b36e-f37eec69144e","Name":"事件模板模板","Sort":3,"templets":[],"Count":0,"cNameList":[]}],"Count":3,"cNameList":[]},{"Id":"a8ce6feb-fea9-455c-8bc0-f83d90a688bf","PId":"00000000-0000-0000-0000-000000000000","Name":"qq","Sort":0,"templets":[{"Id":"4f86c36b-1138-406a-9cf1-55324e167094","PId":"a8ce6feb-fea9-455c-8bc0-f83d90a688bf","Name":"wq","Sort":1,"templets":[],"Count":0,"cNameList":[]}],"Count":1,"cNameList":[]}]
     */

    private int code;
    private String msg;
    private int count;
    private List<DataBean> data;

    protected EventModelData(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        count = in.readInt();
        data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Creator<EventModelData> CREATOR = new Creator<EventModelData>() {
        @Override
        public EventModelData createFromParcel(Parcel in) {
            return new EventModelData(in);
        }

        @Override
        public EventModelData[] newArray(int size) {
            return new EventModelData[size];
        }
    };

    @Override
    public String toString() {
        return "EventModelData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeInt(count);
        dest.writeTypedList(data);
    }

    public static class DataBean implements Parcelable{
        /**
         * Id : 66701f1b-b1af-4bd6-b0eb-6edeb733a67b
         * PId : 00000000-0000-0000-0000-000000000000
         * Name : 事件模板测试
         * Sort : 0
         * templets : [{"Id":"4755a650-3ed4-4406-a2d7-4f42b4bdf444","PId":"66701f1b-b1af-4bd6-b0eb-6edeb733a67b","Name":"事件模板111****","Sort":1,"templets":[],"Count":0,"cNameList":[]},{"Id":"302da150-5662-4522-b1aa-0ec781f90da7","PId":"66701f1b-b1af-4bd6-b0eb-6edeb733a67b","Name":"事件模板测试22","Sort":2,"templets":[],"Count":0,"cNameList":[]},{"Id":"bf902c9d-f6c8-45a2-827a-9a974aaa2032","PId":"66701f1b-b1af-4bd6-b0eb-6edeb733a67b","Name":"事件模板测试333","Sort":3,"templets":[],"Count":0,"cNameList":[]},{"Id":"4123def5-d429-44e0-90c4-2f9ddc4172ed","PId":"66701f1b-b1af-4bd6-b0eb-6edeb733a67b","Name":"444444","Sort":4,"templets":[],"Count":0,"cNameList":[]}]
         * Count : 4
         * cNameList : []
         */

        private String Id;
        private String PId;
        private String Name;
        private int Sort;
        private int Count;
        private List<TempletsBean> templets;
        private List<String> cNameList;

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            Id = in.readString();
            PId = in.readString();
            Name = in.readString();
            Sort = in.readInt();
            Count = in.readInt();
            templets = in.createTypedArrayList(TempletsBean.CREATOR);
            cNameList = in.createStringArrayList();
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "Id='" + Id + '\'' +
                    ", PId='" + PId + '\'' +
                    ", Name='" + Name + '\'' +
                    ", Sort=" + Sort +
                    ", Count=" + Count +
                    ", templets=" + templets +
                    ", cNameList=" + cNameList +
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

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getPId() {
            return PId;
        }

        public void setPId(String PId) {
            this.PId = PId;
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

        public int getCount() {
            return Count;
        }

        public void setCount(int Count) {
            this.Count = Count;
        }

        public List<TempletsBean> getTemplets() {
            return templets;
        }

        public void setTemplets(List<TempletsBean> templets) {
            this.templets = templets;
        }

        public List<String> getCNameList() {
            return cNameList;
        }

        public void setCNameList(List<String> cNameList) {
            this.cNameList = cNameList;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(Id);
            dest.writeString(PId);
            dest.writeString(Name);
            dest.writeInt(Sort);
            dest.writeInt(Count);
            dest.writeTypedList(templets);
            dest.writeStringList(cNameList);
        }

        public static class TempletsBean implements Parcelable{
            /**
             * Id : 4755a650-3ed4-4406-a2d7-4f42b4bdf444
             * PId : 66701f1b-b1af-4bd6-b0eb-6edeb733a67b
             * Name : 事件模板111****
             * Sort : 1
             * templets : []
             * Count : 0
             * cNameList : []
             */

            private String Id;
            private String PId;
            private String Name;
            private int Sort;
            private int Count;
            private List<?> templets;
            private List<?> cNameList;

            public TempletsBean() {
            }

            protected TempletsBean(Parcel in) {
                Id = in.readString();
                PId = in.readString();
                Name = in.readString();
                Sort = in.readInt();
                Count = in.readInt();
            }

            public static final Creator<TempletsBean> CREATOR = new Creator<TempletsBean>() {
                @Override
                public TempletsBean createFromParcel(Parcel in) {
                    return new TempletsBean(in);
                }

                @Override
                public TempletsBean[] newArray(int size) {
                    return new TempletsBean[size];
                }
            };

            @Override
            public String toString() {
                return "TempletsBean{" +
                        "Id='" + Id + '\'' +
                        ", PId='" + PId + '\'' +
                        ", Name='" + Name + '\'' +
                        ", Sort=" + Sort +
                        ", Count=" + Count +
                        ", templets=" + templets +
                        ", cNameList=" + cNameList +
                        '}';
            }

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public String getPId() {
                return PId;
            }

            public void setPId(String PId) {
                this.PId = PId;
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

            public int getCount() {
                return Count;
            }

            public void setCount(int Count) {
                this.Count = Count;
            }

            public List<?> getTemplets() {
                return templets;
            }

            public void setTemplets(List<?> templets) {
                this.templets = templets;
            }

            public List<?> getCNameList() {
                return cNameList;
            }

            public void setCNameList(List<?> cNameList) {
                this.cNameList = cNameList;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(Id);
                dest.writeString(PId);
                dest.writeString(Name);
                dest.writeInt(Sort);
                dest.writeInt(Count);
            }
        }
    }
}

package com.example.q_kang.pmsystem.modules.bean.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by appler on 2018/7/27.
 */

public class NewsData implements Parcelable{


    /**
     * code : 0
     * msg :
     * count : 6
     * data : [{"Id":"a6c6d06a-883b-4136-b88c-d0fbee5079aa","Title":"新闻","AddTime":"2018-06-22T17:41:17.537","Content":null,"Flag":0,"UpdateTime":"2018-06-26T14:14:06.493","Brief":"标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一"},{"Id":"fb298bf3-b7b8-4b09-8837-0afd1184d3ef","Title":"通知","AddTime":"2018-06-22T17:44:22.073","Content":null,"Flag":1,"UpdateTime":"2018-06-26T14:13:53.523","Brief":"公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一公告一"},{"Id":"4b6a516e-e1c4-44b1-b91f-2415fbbae4fe","Title":"电信公告","AddTime":"2018-06-26T14:36:09.347","Content":null,"Flag":1,"UpdateTime":"2018-07-27T09:47:50.7229417+08:00","Brief":"中国电信取消国内手机流量漫游费"},{"Id":"ca8ce244-7f80-45de-bb91-1f36a3826c3c","Title":"高考理综科目高考理综科目高考理综科目","AddTime":"2018-06-26T14:39:59.923","Content":null,"Flag":1,"UpdateTime":"2018-06-26T15:44:21.773","Brief":"高考理综科目选择题第8题解决方案"},{"Id":"77744111-d8f4-48d6-a00b-23e3403ef369","Title":"新时代共青团工作怎么做？","AddTime":"2018-06-26T15:53:31.55","Content":null,"Flag":0,"UpdateTime":"2018-07-27T09:47:50.7229417+08:00","Brief":"新时代共青团工的职责"},{"Id":"3c77fcc0-f6ad-4905-be7e-2927194edde6","Title":"康芝药业","AddTime":"2018-06-26T14:41:50.397","Content":null,"Flag":1,"UpdateTime":"2018-07-27T09:47:50.7229417+08:00","Brief":"康芝药业取得美国专利证书"}]
     */

    private int code;
    private String msg;
    private int count;
    private List<DataBean> data;

    public NewsData() {
    }

    public NewsData(int code, String msg, int count, List<DataBean> data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    protected NewsData(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        count = in.readInt();
        data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Creator<NewsData> CREATOR = new Creator<NewsData>() {
        @Override
        public NewsData createFromParcel(Parcel in) {
            return new NewsData(in);
        }

        @Override
        public NewsData[] newArray(int size) {
            return new NewsData[size];
        }
    };

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
         * Id : a6c6d06a-883b-4136-b88c-d0fbee5079aa
         * Title : 新闻
         * AddTime : 2018-06-22T17:41:17.537
         * Content : null
         * Flag : 0
         * UpdateTime : 2018-06-26T14:14:06.493
         * Brief : 标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一标题一
         */

        private String Id;
        private String Title;
        private String AddTime;
        private String Content;
        private int Flag;
        private String UpdateTime;
        private String Brief;

        protected DataBean(Parcel in) {
            Id = in.readString();
            Title = in.readString();
            AddTime = in.readString();
            Content = in.readString();
            Flag = in.readInt();
            UpdateTime = in.readString();
            Brief = in.readString();
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

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getAddTime() {
            return AddTime;
        }

        public void setAddTime(String AddTime) {
            this.AddTime = AddTime;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public int getFlag() {
            return Flag;
        }

        public void setFlag(int Flag) {
            this.Flag = Flag;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public String getBrief() {
            return Brief;
        }

        public void setBrief(String Brief) {
            this.Brief = Brief;
        }

        public DataBean() {
        }

        public DataBean(String id, String title, String addTime, String content, int flag, String updateTime, String brief) {
            Id = id;
            Title = title;
            AddTime = addTime;
            Content = content;
            Flag = flag;
            UpdateTime = updateTime;
            Brief = brief;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "Id='" + Id + '\'' +
                    ", Title='" + Title + '\'' +
                    ", AddTime='" + AddTime + '\'' +
                    ", Content='" + Content + '\'' +
                    ", Flag=" + Flag +
                    ", UpdateTime='" + UpdateTime + '\'' +
                    ", Brief='" + Brief + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(Id);
            dest.writeString(Title);
            dest.writeString(AddTime);
            dest.writeString(Content);
            dest.writeInt(Flag);
            dest.writeString(UpdateTime);
            dest.writeString(Brief);
        }
    }
}

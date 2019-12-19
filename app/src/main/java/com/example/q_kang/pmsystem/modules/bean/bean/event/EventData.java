package com.example.q_kang.pmsystem.modules.bean.bean.event;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class EventData implements Parcelable {


    /**
     * code : 0
     * msg :
     * count : 63
     * data : [{"Id":"1634e93b-c9a5-4ddd-b13c-1cd266000522","pId":"30d36c7a-62bd-46d2-9981-f93e83c29a28","Content":"0716测试测试测试测试测试测试测试","StartTime":"2018-07-16T00:00:00","EndTime":"2018-07-27T00:00:00","LaunchPerson":"9cbe899d-13ad-4d76-8568-a967151bcf15","AssignPerson":"4fd69a5b-8f8c-4997-9500-66f4ee5c835c","Location":"point(113.649782,34.876364)","AddTime":"2018-07-16T06:07:04","UpdateTime":"2018-08-04T11:23:53","Flag":false,"Upload":",/Upload/2018年08月04日 11时23分52秒/1.png","Progress":"0","Lable":"事件,测试","Title":"测试测试测试测试","assignPersons":"高昌利","imagePerson":"/Upload/2018年06月25日 15时46分24秒/3.jpg","LeaderPerson":"段妞妞","WorkName":"需求分析","image":"/Upload/2018年06月25日 15时46分24秒/3.jpg","_startTime":"2018-07-16","_endTime":"2018-07-27","ProjectName":null,"wLeaderId":"9cbe899d-13ad-4d76-8568-a967151bcf15","pLeaderId":"4fd69a5b-8f8c-4997-9500-66f4ee5c835c"},{"Id":"cf995cf9-c556-4758-b5b1-3cd9d81f477d","pId":"00000000-0000-0000-0000-000000000000","Content":"撒大大","StartTime":"2018-08-01T00:00:00","EndTime":"2018-08-31T00:00:00","LaunchPerson":"9cbe899d-13ad-4d76-8568-a967151bcf15","AssignPerson":"b6d33aec-9135-4415-8314-45849bbb00d6","Location":"POINT(113.671783,34.787337)","AddTime":"2018-08-03T14:52:44","UpdateTime":"2018-08-04T10:56:27","Flag":false,"Upload":"","Progress":"0","Lable":"","Title":"地图测试哈哈哈哈","assignPersons":"工程科3","imagePerson":"/Upload/2018年07月16日 12时42分10秒/main_cxgh1.jpg","LeaderPerson":"段妞妞","WorkName":null,"image":"/Upload/2018年06月25日 15时46分24秒/3.jpg","_startTime":"2018-08-01","_endTime":"2018-08-31","ProjectName":null,"wLeaderId":null,"pLeaderId":null}]
     */

    private int code;
    private String msg;
    private int count;
    private String _lable;
    private List<DataBean> data;

    public EventData() {
    }

    public EventData(int code, String msg, int count, String _lable, List<DataBean> data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this._lable = _lable;
        this.data = data;
    }


    protected EventData(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        count = in.readInt();
        _lable = in.readString();
        data = in.createTypedArrayList(DataBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeInt(count);
        dest.writeString(_lable);
        dest.writeTypedList(data);
    }

    public static final Creator<EventData> CREATOR = new Creator<EventData>() {
        @Override
        public EventData createFromParcel(Parcel in) {
            return new EventData(in);
        }

        @Override
        public EventData[] newArray(int size) {
            return new EventData[size];
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

    public String get_lable() {
        return _lable;
    }

    public void set_lable(String _lable) {
        this._lable = _lable;
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
    public String toString() {
        return "EventData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", _lable='" + _lable + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean implements Parcelable{
        /**
         * Id : 1634e93b-c9a5-4ddd-b13c-1cd266000522
         * pId : 30d36c7a-62bd-46d2-9981-f93e83c29a28
         * Content : 0716测试测试测试测试测试测试测试
         * StartTime : 2018-07-16T00:00:00
         * EndTime : 2018-07-27T00:00:00
         * LeaderId : 9cbe899d-13ad-4d76-8568-a967151bcf15
         * AssignPerson : 4fd69a5b-8f8c-4997-9500-66f4ee5c835c
         * Location : point(113.649782,34.876364)
         * AddTime : 2018-07-16T06:07:04
         * UpdateTime : 2018-08-04T11:23:53
         * Flag : false
         * Upload : ,/Upload/2018年08月04日 11时23分52秒/1.png
         * Progress : 0
         * Lable : 事件,测试
         * Title : 测试测试测试测试
         * assignPersons : 高昌利
         * imagePerson : /Upload/2018年06月25日 15时46分24秒/3.jpg
         * LeaderPerson : 段妞妞
         * WorkName : 需求分析
         * image : /Upload/2018年06月25日 15时46分24秒/3.jpg
         * _startTime : 2018-07-16
         * _endTime : 2018-07-27
         * ProjectName : null
         * wLeaderId : 9cbe899d-13ad-4d76-8568-a967151bcf15
         * pLeaderId : 4fd69a5b-8f8c-4997-9500-66f4ee5c835c
         */

        private String Id;
        private String pId;
        private String Content;
        private String StartTime;
        private String EndTime;
        private String LeaderId;
        private String AssignPerson;
        private String Location;
        private String AddTime;
        private String UpdateTime;
        private boolean Flag;
        private String Upload;
        private String Progress;
        private String Lable;
        private String Title;
        private String assignPersons;
        private String imagePerson;
        private String LeaderPerson;
        private String WorkName;
        private String image;
        private String _startTime;
        private String _endTime;
        private String ProjectName;
        private String wLeaderId;
        private String pLeaderId;

        private int CommentCount;
        private String Address;
        private String LaunchPerson;


        protected DataBean(Parcel in) {
            Id = in.readString();
            pId = in.readString();
            Content = in.readString();
            StartTime = in.readString();
            EndTime = in.readString();
            LeaderId = in.readString();
            AssignPerson = in.readString();
            Location = in.readString();
            AddTime = in.readString();
            UpdateTime = in.readString();
            Flag = in.readByte() != 0;
            Upload = in.readString();
            Progress = in.readString();
            Lable = in.readString();
            Title = in.readString();
            assignPersons = in.readString();
            imagePerson = in.readString();
            LeaderPerson = in.readString();
            WorkName = in.readString();
            image = in.readString();
            _startTime = in.readString();
            _endTime = in.readString();
            ProjectName = in.readString();
            wLeaderId = in.readString();
            pLeaderId = in.readString();
            CommentCount = in.readInt();
            Address = in.readString();
            LaunchPerson = in.readString();
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

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getpId() {
            return pId;
        }

        public void setpId(String pId) {
            this.pId = pId;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String content) {
            Content = content;
        }

        public String getStartTime() {
            return StartTime;
        }

        public void setStartTime(String startTime) {
            StartTime = startTime;
        }

        public String getEndTime() {
            return EndTime;
        }

        public void setEndTime(String endTime) {
            EndTime = endTime;
        }

        public String getLeaderId() {
            return LeaderId;
        }

        public void setLeaderId(String leaderId) {
            LeaderId = leaderId;
        }

        public String getAssignPerson() {
            return AssignPerson;
        }

        public void setAssignPerson(String assignPerson) {
            AssignPerson = assignPerson;
        }

        public String getLocation() {
            return Location;
        }

        public void setLocation(String location) {
            Location = location;
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

        public boolean isFlag() {
            return Flag;
        }

        public void setFlag(boolean flag) {
            Flag = flag;
        }

        public String getUpload() {
            return Upload;
        }

        public void setUpload(String upload) {
            Upload = upload;
        }

        public String getProgress() {
            return Progress;
        }

        public void setProgress(String progress) {
            Progress = progress;
        }

        public String getLable() {
            return Lable;
        }

        public void setLable(String lable) {
            Lable = lable;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getAssignPersons() {
            return assignPersons;
        }

        public void setAssignPersons(String assignPersons) {
            this.assignPersons = assignPersons;
        }

        public String getImagePerson() {
            return imagePerson;
        }

        public void setImagePerson(String imagePerson) {
            this.imagePerson = imagePerson;
        }

        public String getLeaderPerson() {
            return LeaderPerson;
        }

        public void setLeaderPerson(String leaderPerson) {
            LeaderPerson = leaderPerson;
        }

        public String getWorkName() {
            return WorkName;
        }

        public void setWorkName(String workName) {
            WorkName = workName;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String get_startTime() {
            return _startTime;
        }

        public void set_startTime(String _startTime) {
            this._startTime = _startTime;
        }

        public String get_endTime() {
            return _endTime;
        }

        public void set_endTime(String _endTime) {
            this._endTime = _endTime;
        }

        public String getProjectName() {
            return ProjectName;
        }

        public void setProjectName(String projectName) {
            ProjectName = projectName;
        }

        public String getwLeaderId() {
            return wLeaderId;
        }

        public void setwLeaderId(String wLeaderId) {
            this.wLeaderId = wLeaderId;
        }

        public String getpLeaderId() {
            return pLeaderId;
        }

        public void setpLeaderId(String pLeaderId) {
            this.pLeaderId = pLeaderId;
        }

        public int getCommentCount() {
            return CommentCount;
        }

        public void setCommentCount(int commentCount) {
            CommentCount = commentCount;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getLaunchPerson() {
            return LaunchPerson;
        }

        public void setLaunchPerson(String launchPerson) {
            LaunchPerson = launchPerson;
        }

        public static Creator<DataBean> getCREATOR() {
            return CREATOR;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "Id='" + Id + '\'' +
                    ", pId='" + pId + '\'' +
                    ", Content='" + Content + '\'' +
                    ", StartTime='" + StartTime + '\'' +
                    ", EndTime='" + EndTime + '\'' +
                    ", LeaderId='" + LeaderId + '\'' +
                    ", AssignPerson='" + AssignPerson + '\'' +
                    ", Location='" + Location + '\'' +
                    ", AddTime='" + AddTime + '\'' +
                    ", UpdateTime='" + UpdateTime + '\'' +
                    ", Flag=" + Flag +
                    ", Upload='" + Upload + '\'' +
                    ", Progress='" + Progress + '\'' +
                    ", Lable='" + Lable + '\'' +
                    ", Title='" + Title + '\'' +
                    ", assignPersons='" + assignPersons + '\'' +
                    ", imagePerson='" + imagePerson + '\'' +
                    ", LeaderPerson='" + LeaderPerson + '\'' +
                    ", WorkName='" + WorkName + '\'' +
                    ", image='" + image + '\'' +
                    ", _startTime='" + _startTime + '\'' +
                    ", _endTime='" + _endTime + '\'' +
                    ", ProjectName='" + ProjectName + '\'' +
                    ", wLeaderId='" + wLeaderId + '\'' +
                    ", pLeaderId='" + pLeaderId + '\'' +
                    ", CommentCount=" + CommentCount +
                    ", Address='" + Address + '\'' +
                    ", LaunchPerson='" + LaunchPerson + '\'' +
                    '}';
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(Id);
            dest.writeString(pId);
            dest.writeString(Content);
            dest.writeString(StartTime);
            dest.writeString(EndTime);
            dest.writeString(LeaderId);
            dest.writeString(AssignPerson);
            dest.writeString(Location);
            dest.writeString(AddTime);
            dest.writeString(UpdateTime);
            dest.writeByte((byte) (Flag ? 1 : 0));
            dest.writeString(Upload);
            dest.writeString(Progress);
            dest.writeString(Lable);
            dest.writeString(Title);
            dest.writeString(assignPersons);
            dest.writeString(imagePerson);
            dest.writeString(LeaderPerson);
            dest.writeString(WorkName);
            dest.writeString(image);
            dest.writeString(_startTime);
            dest.writeString(_endTime);
            dest.writeString(ProjectName);
            dest.writeString(wLeaderId);
            dest.writeString(pLeaderId);
            dest.writeInt(CommentCount);
            dest.writeString(Address);
            dest.writeString(LaunchPerson);
        }
    }
}

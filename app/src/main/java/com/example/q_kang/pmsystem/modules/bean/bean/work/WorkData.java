package com.example.q_kang.pmsystem.modules.bean.bean.work;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by appler on 2018/7/13.
 */

public class WorkData implements Parcelable{


    /**
     * code : 0
     * msg :
     * count : 1
     * data : [{"Id":"8cb5bd74-7b65-4b06-8383-0cb75cf5cbf7","Name":"测试","pId":"ff44010b-f287-494c-b283-9e0321448b9f","StartTime":"0001-01-01T00:00:00","JoinPerson":"","EndTime":"0001-01-01T00:00:00","LeaderId":"22103b61-7303-4608-8402-d36488d60e3b","Content":"","Location":"","AddUserId":"85722c8b-7122-e811-9c45-80a589abefb8","AddTime":"2018-09-07T10:38:27.25","UpdateTime":"2018-09-11T09:46:20","Flag":false,"Progress":null,"Lable":null,"CommentCount":0,"Leader":"副局3","LeaderImage":"/Upload/2018年07月16日 12时40分03秒/main_zssdfyz1.jpg","JoinPersons":null,"JoinImage":null,"ProjectName":"发生的但是时代的","EventList":null,"LeaderRole":"副总经理","LeaderDepartment":null,"_startTime":"0001-01-01","_endTime":"0001-01-01","Address":null,"SerialNumber":3,"NextId":null,"wNameList":null,"wLeaderIdList":null}]
     */

    private int code;
    private String msg;
    private String _lable;
    private int count;
    private List<DataBean> data;

    public WorkData() {
    }

    public WorkData(int code, String msg, String _lable, int count, List<DataBean> data) {
        this.code = code;
        this.msg = msg;
        this._lable = _lable;
        this.count = count;
        this.data = data;
    }

    protected WorkData(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        _lable = in.readString();
        count = in.readInt();
        data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Creator<WorkData> CREATOR = new Creator<WorkData>() {
        @Override
        public WorkData createFromParcel(Parcel in) {
            return new WorkData(in);
        }

        @Override
        public WorkData[] newArray(int size) {
            return new WorkData[size];
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
    public String toString() {
        return "WorkData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", _lable='" + _lable + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeString(_lable);
        dest.writeInt(count);
        dest.writeTypedList(data);
    }

    public static class DataBean implements Parcelable{
        /**
         * Id : 8cb5bd74-7b65-4b06-8383-0cb75cf5cbf7
         * Name : 测试
         * pId : ff44010b-f287-494c-b283-9e0321448b9f
         * StartTime : 0001-01-01T00:00:00
         * JoinPerson :
         * EndTime : 0001-01-01T00:00:00
         * LeaderId : 22103b61-7303-4608-8402-d36488d60e3b
         * Content :
         * Location :
         * AddUserId : 85722c8b-7122-e811-9c45-80a589abefb8
         * AddTime : 2018-09-07T10:38:27.25
         * UpdateTime : 2018-09-11T09:46:20
         * Flag : false
         * Progress : null
         * Lable : null
         * CommentCount : 0
         * Leader : 副局3
         * LeaderImage : /Upload/2018年07月16日 12时40分03秒/main_zssdfyz1.jpg
         * JoinPersons : null
         * JoinImage : null
         * ProjectName : 发生的但是时代的
         * EventList : null
         * LeaderRole : 副总经理
         * LeaderDepartment : null
         * _startTime : 0001-01-01
         * _endTime : 0001-01-01
         * Address : null
         * SerialNumber : 3
         * NextId : null
         * wNameList : null
         * wLeaderIdList : null
         */

        private String Id;
        private String Name;
        private String pId;
        private String StartTime;
        private String JoinPerson;
        private String EndTime;
        private String LeaderId;
        private String Content;
        private String Location;
        private String AddUserId;
        private String AddTime;
        private String UpdateTime;
        private boolean Flag;
        private String Progress;
        private String Lable;
        private int CommentCount;
        private String Leader;
        private String LeaderImage;
        private String JoinPersons;
        private String JoinImage;
        private String ProjectName;
        private Object EventList;
        private String LeaderRole;
        private String LeaderDepartment;
        private String _startTime;
        private String _endTime;
        private String Address;
        private int SerialNumber;
        private String NextId;
        private Object wNameList;
        private Object wLeaderIdList;

        public DataBean() {
        }

        public DataBean(String id, String name, String pId, String startTime, String joinPerson, String endTime, String leaderId, String content, String location, String addUserId, String addTime, String updateTime, boolean flag, String progress, String lable, int commentCount, String leader, String leaderImage, String joinPersons, String joinImage, String projectName, Object eventList, String leaderRole, String leaderDepartment, String _startTime, String _endTime, String address, int serialNumber, String nextId, Object wNameList, Object wLeaderIdList) {
            Id = id;
            Name = name;
            this.pId = pId;
            StartTime = startTime;
            JoinPerson = joinPerson;
            EndTime = endTime;
            LeaderId = leaderId;
            Content = content;
            Location = location;
            AddUserId = addUserId;
            AddTime = addTime;
            UpdateTime = updateTime;
            Flag = flag;
            Progress = progress;
            Lable = lable;
            CommentCount = commentCount;
            Leader = leader;
            LeaderImage = leaderImage;
            JoinPersons = joinPersons;
            JoinImage = joinImage;
            ProjectName = projectName;
            EventList = eventList;
            LeaderRole = leaderRole;
            LeaderDepartment = leaderDepartment;
            this._startTime = _startTime;
            this._endTime = _endTime;
            Address = address;
            SerialNumber = serialNumber;
            NextId = nextId;
            this.wNameList = wNameList;
            this.wLeaderIdList = wLeaderIdList;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "Id='" + Id + '\'' +
                    ", Name='" + Name + '\'' +
                    ", pId='" + pId + '\'' +
                    ", StartTime='" + StartTime + '\'' +
                    ", JoinPerson='" + JoinPerson + '\'' +
                    ", EndTime='" + EndTime + '\'' +
                    ", LeaderId='" + LeaderId + '\'' +
                    ", Content='" + Content + '\'' +
                    ", Location='" + Location + '\'' +
                    ", AddUserId='" + AddUserId + '\'' +
                    ", AddTime='" + AddTime + '\'' +
                    ", UpdateTime='" + UpdateTime + '\'' +
                    ", Flag=" + Flag +
                    ", Progress='" + Progress + '\'' +
                    ", Lable='" + Lable + '\'' +
                    ", CommentCount=" + CommentCount +
                    ", Leader='" + Leader + '\'' +
                    ", LeaderImage='" + LeaderImage + '\'' +
                    ", JoinPersons='" + JoinPersons + '\'' +
                    ", JoinImage='" + JoinImage + '\'' +
                    ", ProjectName='" + ProjectName + '\'' +
                    ", EventList=" + EventList +
                    ", LeaderRole='" + LeaderRole + '\'' +
                    ", LeaderDepartment='" + LeaderDepartment + '\'' +
                    ", _startTime='" + _startTime + '\'' +
                    ", _endTime='" + _endTime + '\'' +
                    ", Address='" + Address + '\'' +
                    ", SerialNumber=" + SerialNumber +
                    ", NextId='" + NextId + '\'' +
                    ", wNameList=" + wNameList +
                    ", wLeaderIdList=" + wLeaderIdList +
                    '}';
        }

        protected DataBean(Parcel in) {
            Id = in.readString();
            Name = in.readString();
            pId = in.readString();
            StartTime = in.readString();
            JoinPerson = in.readString();
            EndTime = in.readString();
            LeaderId = in.readString();
            Content = in.readString();
            Location = in.readString();
            AddUserId = in.readString();
            AddTime = in.readString();
            UpdateTime = in.readString();
            Flag = in.readByte() != 0;
            Progress = in.readString();
            Lable = in.readString();
            CommentCount = in.readInt();
            Leader = in.readString();
            LeaderImage = in.readString();
            JoinPersons = in.readString();
            JoinImage = in.readString();
            ProjectName = in.readString();
            LeaderRole = in.readString();
            LeaderDepartment = in.readString();
            _startTime = in.readString();
            _endTime = in.readString();
            Address = in.readString();
            SerialNumber = in.readInt();
            NextId = in.readString();
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

        public void setId(String id) {
            Id = id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getpId() {
            return pId;
        }

        public void setpId(String pId) {
            this.pId = pId;
        }

        public String getStartTime() {
            return StartTime;
        }

        public void setStartTime(String startTime) {
            StartTime = startTime;
        }

        public String getJoinPerson() {
            return JoinPerson;
        }

        public void setJoinPerson(String joinPerson) {
            JoinPerson = joinPerson;
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

        public String getContent() {
            return Content;
        }

        public void setContent(String content) {
            Content = content;
        }

        public String getLocation() {
            return Location;
        }

        public void setLocation(String location) {
            Location = location;
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

        public boolean isFlag() {
            return Flag;
        }

        public void setFlag(boolean flag) {
            Flag = flag;
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

        public int getCommentCount() {
            return CommentCount;
        }

        public void setCommentCount(int commentCount) {
            CommentCount = commentCount;
        }

        public String getLeader() {
            return Leader;
        }

        public void setLeader(String leader) {
            Leader = leader;
        }

        public String getLeaderImage() {
            return LeaderImage;
        }

        public void setLeaderImage(String leaderImage) {
            LeaderImage = leaderImage;
        }

        public String getJoinPersons() {
            return JoinPersons;
        }

        public void setJoinPersons(String joinPersons) {
            JoinPersons = joinPersons;
        }

        public String getJoinImage() {
            return JoinImage;
        }

        public void setJoinImage(String joinImage) {
            JoinImage = joinImage;
        }

        public String getProjectName() {
            return ProjectName;
        }

        public void setProjectName(String projectName) {
            ProjectName = projectName;
        }

        public Object getEventList() {
            return EventList;
        }

        public void setEventList(Object eventList) {
            EventList = eventList;
        }

        public String getLeaderRole() {
            return LeaderRole;
        }

        public void setLeaderRole(String leaderRole) {
            LeaderRole = leaderRole;
        }

        public String getLeaderDepartment() {
            return LeaderDepartment;
        }

        public void setLeaderDepartment(String leaderDepartment) {
            LeaderDepartment = leaderDepartment;
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

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public int getSerialNumber() {
            return SerialNumber;
        }

        public void setSerialNumber(int serialNumber) {
            SerialNumber = serialNumber;
        }

        public String getNextId() {
            return NextId;
        }

        public void setNextId(String nextId) {
            NextId = nextId;
        }

        public Object getwNameList() {
            return wNameList;
        }

        public void setwNameList(Object wNameList) {
            this.wNameList = wNameList;
        }

        public Object getwLeaderIdList() {
            return wLeaderIdList;
        }

        public void setwLeaderIdList(Object wLeaderIdList) {
            this.wLeaderIdList = wLeaderIdList;
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
            dest.writeString(StartTime);
            dest.writeString(JoinPerson);
            dest.writeString(EndTime);
            dest.writeString(LeaderId);
            dest.writeString(Content);
            dest.writeString(Location);
            dest.writeString(AddUserId);
            dest.writeString(AddTime);
            dest.writeString(UpdateTime);
            dest.writeByte((byte) (Flag ? 1 : 0));
            dest.writeString(Progress);
            dest.writeString(Lable);
            dest.writeInt(CommentCount);
            dest.writeString(Leader);
            dest.writeString(LeaderImage);
            dest.writeString(JoinPersons);
            dest.writeString(JoinImage);
            dest.writeString(ProjectName);
            dest.writeString(LeaderRole);
            dest.writeString(LeaderDepartment);
            dest.writeString(_startTime);
            dest.writeString(_endTime);
            dest.writeString(Address);
            dest.writeInt(SerialNumber);
            dest.writeString(NextId);
        }
    }
}

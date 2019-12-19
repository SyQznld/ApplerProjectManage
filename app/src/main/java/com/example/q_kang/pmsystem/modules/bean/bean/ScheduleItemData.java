package com.example.q_kang.pmsystem.modules.bean.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by appler on 2018/10/19.
 */

public class ScheduleItemData implements Parcelable{

    /**
     * Id : e1331ac9-4f64-473f-8a4d-60bf419989a6
     * title : 非全天
     * content : 水水大大撒旦顶顶顶
     * startTime : 2018-10-18T03:00:00
     * endTime : 2018-10-19T20:00:00
     * isFinish : false
     * ItemId : 00000000-0000-0000-0000-000000000000
     * ItemName :
     * ItemType : 0
     * IsDelete : false
     * IsAllDay : false
     * AddTime : 2018-10-19T00:00:00
     */

    private String Id;
    private String title;
    private String content;
    private String startTime;
    private String endTime;
    private boolean isFinish;
    private String ItemId;
    private String ItemName;
    private int ItemType;
    private boolean IsDelete;
    private boolean IsAllDay;
    private String AddTime;

    protected ScheduleItemData(Parcel in) {
        Id = in.readString();
        title = in.readString();
        content = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        isFinish = in.readByte() != 0;
        ItemId = in.readString();
        ItemName = in.readString();
        ItemType = in.readInt();
        IsDelete = in.readByte() != 0;
        IsAllDay = in.readByte() != 0;
        AddTime = in.readString();
    }

    public static final Creator<ScheduleItemData> CREATOR = new Creator<ScheduleItemData>() {
        @Override
        public ScheduleItemData createFromParcel(Parcel in) {
            return new ScheduleItemData(in);
        }

        @Override
        public ScheduleItemData[] newArray(int size) {
            return new ScheduleItemData[size];
        }
    };

    @Override
    public String toString() {
        return "ScheduleItemData{" +
                "Id='" + Id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", isFinish=" + isFinish +
                ", ItemId='" + ItemId + '\'' +
                ", ItemName='" + ItemName + '\'' +
                ", ItemType=" + ItemType +
                ", IsDelete=" + IsDelete +
                ", IsAllDay=" + IsAllDay +
                ", AddTime='" + AddTime + '\'' +
                '}';
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isIsFinish() {
        return isFinish;
    }

    public void setIsFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String ItemId) {
        this.ItemId = ItemId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String ItemName) {
        this.ItemName = ItemName;
    }

    public int getItemType() {
        return ItemType;
    }

    public void setItemType(int ItemType) {
        this.ItemType = ItemType;
    }

    public boolean isIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(boolean IsDelete) {
        this.IsDelete = IsDelete;
    }

    public boolean isIsAllDay() {
        return IsAllDay;
    }

    public void setIsAllDay(boolean IsAllDay) {
        this.IsAllDay = IsAllDay;
    }

    public String getAddTime() {
        return AddTime;
    }

    public void setAddTime(String AddTime) {
        this.AddTime = AddTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeByte((byte) (isFinish ? 1 : 0));
        dest.writeString(ItemId);
        dest.writeString(ItemName);
        dest.writeInt(ItemType);
        dest.writeByte((byte) (IsDelete ? 1 : 0));
        dest.writeByte((byte) (IsAllDay ? 1 : 0));
        dest.writeString(AddTime);
    }
}

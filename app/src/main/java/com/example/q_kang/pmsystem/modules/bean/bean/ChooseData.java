package com.example.q_kang.pmsystem.modules.bean.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.q_kang.pmsystem.modules.bean.bean.project.ProjectData;
import com.example.q_kang.pmsystem.modules.bean.bean.work.WorkData;

public class ChooseData implements Parcelable{
    private Frame frame;

    private WorkData.DataBean belongWork;
    private ProjectData.DataBean belongProject;
    private String workCreateDate;

    private boolean isSelect;

    public ChooseData() {
    }

    public ChooseData(Frame frame, WorkData.DataBean belongWork, ProjectData.DataBean belongProject, String workCreateDate, boolean isSelect) {
        this.frame = frame;
        this.belongWork = belongWork;
        this.belongProject = belongProject;
        this.workCreateDate = workCreateDate;
        this.isSelect = isSelect;
    }

    protected ChooseData(Parcel in) {
        frame = in.readParcelable(Frame.class.getClassLoader());
        belongWork = in.readParcelable(WorkData.DataBean.class.getClassLoader());
        belongProject = in.readParcelable(ProjectData.DataBean.class.getClassLoader());
        workCreateDate = in.readString();
        isSelect = in.readByte() != 0;
    }

    public static final Creator<ChooseData> CREATOR = new Creator<ChooseData>() {
        @Override
        public ChooseData createFromParcel(Parcel in) {
            return new ChooseData(in);
        }

        @Override
        public ChooseData[] newArray(int size) {
            return new ChooseData[size];
        }
    };

    public WorkData.DataBean getBelongWork() {
        return belongWork;
    }

    public void setBelongWork(WorkData.DataBean belongWork) {
        this.belongWork = belongWork;
    }

    public ProjectData.DataBean getBelongProject() {
        return belongProject;
    }

    public void setBelongProject(ProjectData.DataBean belongProject) {
        this.belongProject = belongProject;
    }

    public String getWorkCreateDate() {
        return workCreateDate;
    }

    public void setWorkCreateDate(String workCreateDate) {
        this.workCreateDate = workCreateDate;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    @Override
    public String toString() {
        return "ChooseData{" +
                "frame=" + frame +
                ", belongWork=" + belongWork +
                ", belongProject=" + belongProject +
                ", workCreateDate='" + workCreateDate + '\'' +
                ", isSelect=" + isSelect +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(frame, flags);
        dest.writeParcelable(belongWork, flags);
        dest.writeParcelable(belongProject, flags);
        dest.writeString(workCreateDate);
        dest.writeByte((byte) (isSelect ? 1 : 0));
    }
}

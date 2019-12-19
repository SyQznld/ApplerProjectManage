package com.example.q_kang.pmsystem.modules.bean.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by appler on 2018/7/28.
 */

@Entity
public class ScheduleData {

    @Id
    private Long id;
    
    private String startTime;
    private String endTime;
    private String title;
    private String content;
    private boolean isFinish;


    public ScheduleData() {
    }

    public ScheduleData(String startTime, String endTime, String title, String content, boolean isFinish) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.content = content;
        this.isFinish = isFinish;
    }

    @Generated(hash = 755753452)
    public ScheduleData(Long id, String startTime, String endTime, String title, String content,
            boolean isFinish) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.content = content;
        this.isFinish = isFinish;
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

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }



    @Override
    public String toString() {
        return "ScheduleData{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isFinish=" + isFinish +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsFinish() {
        return this.isFinish;
    }

    public void setIsFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }
}

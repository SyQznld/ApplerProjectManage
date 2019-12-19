package com.example.q_kang.pmsystem.model;

/**
 * Created by appler on 2018/10/19.
 */

public interface ICreateScheduleModel extends IModel{

    void createSchedule(String string);

    void editSchedule(String string);

    void getScheduleDetail(String string);
}

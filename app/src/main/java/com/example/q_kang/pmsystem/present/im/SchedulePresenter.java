package com.example.q_kang.pmsystem.present.im;

import com.example.q_kang.pmsystem.model.ICreateScheduleModel;
import com.example.q_kang.pmsystem.model.im.ScheduleModel;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.CreateScheduleView;


public class SchedulePresenter extends BaseMvpPresenter {
    private String TAG = "SchedulePresenter";
    private ScheduleModel scheduleModel;
    private CreateScheduleView scheduleView;

    public SchedulePresenter(CreateScheduleView scheduleView) {
        this.scheduleView = scheduleView;

        scheduleModel = new ScheduleModel(new ICreateScheduleModel() {
            @Override
            public void createSchedule(String string) {
                scheduleView.createSchedule(string);
            }

            @Override
            public void editSchedule(String string) {
                scheduleView.editSchedule(string);
            }

            @Override
            public void getScheduleDetail(String string) {
                scheduleView.getScheduleDetail(string);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onNext(String str) {

            }
        });
    }


    public void createSchedule(String title,
                               String content,
                               String startTime,
                               String endTime,
                               boolean isFinish,
                               String ItemId,
                               String ItemName,
                               int ItemType,
                               boolean IsDelete,
                               boolean IsAllDay,
                               String AddTime) {
        scheduleModel.createSchedule(mCompositeSubscription, title, content, startTime, endTime, isFinish, ItemId, ItemName, ItemType, IsDelete, IsAllDay, AddTime);
    }

    public void editSchedule(String scheduleId,
                             String title,
                             String content,
                             String startTime,
                             String endTime,
                             boolean isFinish,
                             String ItemId,
                             String ItemName,
                             int ItemType,
                             boolean IsDelete,
                             boolean IsAllDay,
                             String AddTime) {
        scheduleModel.editSchedule(mCompositeSubscription, scheduleId, title, content, startTime, endTime, isFinish, ItemId, ItemName, ItemType, IsDelete, IsAllDay, AddTime);
    }


    public void getScheduleDetail(String scheduleId) {
        scheduleModel.getScheduleDatail(mCompositeSubscription, scheduleId);
    }


}
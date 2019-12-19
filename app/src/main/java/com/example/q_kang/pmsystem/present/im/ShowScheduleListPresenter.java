package com.example.q_kang.pmsystem.present.im;

import com.example.q_kang.pmsystem.model.IScheduleListModel;
import com.example.q_kang.pmsystem.model.im.ShowScheduleListModel;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.ScheduleListView;

/**
 * Created by appler on 2018/8/10.
 */

public class ShowScheduleListPresenter extends BaseMvpPresenter {

    private String TAG = "ShowScheduleListPresenter";

    private ShowScheduleListModel scheduleListModel;

    private ScheduleListView scheduleListView;

    public ShowScheduleListPresenter(ScheduleListView scheduleListView) {
        this.scheduleListView = scheduleListView;
        scheduleListModel = new ShowScheduleListModel(new IScheduleListModel() {
            @Override
            public void getScheduleList(String string) {
                scheduleListView.getScheduleList(string);
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


    public void getDayScheduleList(String time) {
        scheduleListModel.getScheduleList(mCompositeSubscription, time);
    }


}

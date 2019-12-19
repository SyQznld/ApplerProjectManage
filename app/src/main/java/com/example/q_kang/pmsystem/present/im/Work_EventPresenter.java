package com.example.q_kang.pmsystem.present.im;

import com.example.q_kang.pmsystem.model.IWork_EventListModel;
import com.example.q_kang.pmsystem.model.im.ShowWork_EventListModel;
import com.example.q_kang.pmsystem.modules.bean.bean.event.EventData;
import com.example.q_kang.pmsystem.modules.bean.bean.work.WorkData;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.Work_EventListView;

/**
 * Created by appler on 2018/7/17.
 */

public class Work_EventPresenter extends BaseMvpPresenter {

    private String TAG = "Work_EventPresenter";

    private ShowWork_EventListModel work_eventListModel;

    private Work_EventListView work_eventListView;

    public Work_EventPresenter(Work_EventListView work_eventListView) {
        this.work_eventListView = work_eventListView;

        work_eventListModel = new ShowWork_EventListModel(new IWork_EventListModel() {
            @Override
            public void showWork_EventList(EventData eventData) {
                work_eventListView.showWork_EvetList(eventData);
            }

            @Override
            public void getWorkDetail(WorkData.DataBean dataBean) {
                work_eventListView.getWorkDetail(dataBean);
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

    public void getWork_EventList(String workId) {
        work_eventListModel.getWork_EventList(mCompositeSubscription, workId);
    }


    public void getWorkDetail(String workId) {
        work_eventListModel.getWorkDetail(mCompositeSubscription, workId);
    }
}

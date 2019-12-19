package com.example.q_kang.pmsystem.present.im;


import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.model.IShowEventListModel;
import com.example.q_kang.pmsystem.model.im.ShowEventListModel;
import com.example.q_kang.pmsystem.modules.bean.bean.Frame;
import com.example.q_kang.pmsystem.modules.bean.bean.event.EventData;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.EventListView;

import java.util.List;

/**
 * Created by appler on 2018/7/16.
 */

public class ShowEventListPresenter extends BaseMvpPresenter {

    private String TAG = "ShowEventListPresenter";

    private EventListView eventListView;

    private ShowEventListModel showEventListModel;

    public ShowEventListPresenter(EventListView eventListView) {
        this.eventListView = eventListView;

        showEventListModel = new ShowEventListModel(new IShowEventListModel() {


            @Override
            public void showEventList(EventData eventData) {
                eventListView.showEventList(eventData);
            }

            @Override
            public void showEventSearchUser(List<Frame> frameList) {
                eventListView.showEventSearchUser(frameList);
            }

            @Override
            public void showEventDepartment(String string) {
                eventListView.showEventDepartment(string);
            }

            @Override
            public void onStart() {
                eventListView.setState(Globle.LOADING_STATE);
            }

            @Override
            public void onError() {
                eventListView.setState(Globle.LOADING_FAIL);
            }

            @Override
            public void onComplete() {
                eventListView.setState(Globle.LOADING_SUCEESS);
            }

            @Override
            public void onNext(String str) {

            }
        });

    }

    public void getEventList(int page,
                             int limit,
                             int flag,
                             String currUserId,
                             String keyword,
                             String SearchUserId,
                             String orgId,String Labels) {
        showEventListModel.getEventList(mCompositeSubscription, page,
                limit,
                flag,
                currUserId,
                keyword,
                SearchUserId,orgId,Labels);
    }


    public void getEventSearchUser(String userId, String role) {
        showEventListModel.getEventSearchUser(mCompositeSubscription, userId, role);
    }

    public void getEventDepartment(String userId) {
        showEventListModel.getEventDepartment(mCompositeSubscription, userId);
    }
}
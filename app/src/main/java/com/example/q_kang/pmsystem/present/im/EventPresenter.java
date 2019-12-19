package com.example.q_kang.pmsystem.present.im;

import android.util.Log;

import com.example.q_kang.pmsystem.model.IModel;
import com.example.q_kang.pmsystem.model.im.EventModel;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.CreateEventView;
import com.example.q_kang.pmsystem.view.EventEditView;
import com.example.q_kang.pmsystem.view.EventListView;
import com.example.q_kang.pmsystem.view.GetEventDetailView;

import java.io.File;
import java.util.List;

/**
 * Created by appler on 2018/7/6.
 */

public class EventPresenter extends BaseMvpPresenter {

    private String TAG = "EventPresenter";

    private CreateEventView createEventView;
    private EventListView eventListView;
    private EventEditView eventEditView;
    private EventModel eventModel;

    private GetEventDetailView getEventDetailView;

    public EventPresenter(CreateEventView createEventView) {
        this.createEventView = createEventView;

        eventModel = new EventModel(new IModel() {
            @Override
            public void onStart() {
//createEventView.setState(Globle.LOADING_STATE);
            }

            @Override
            public void onError() {
//                createEventView.setState(Globle.LOADING_FAIL);
            }

            @Override
            public void onComplete() {
//                createEventView.setState(Globle.LOADING_SUCEESS);
            }

            @Override
            public void onNext(String str) {
                Log.i(TAG, "onNext onClick=====: " + str);

                createEventView.createEvent(str);
            }
        });
    }


    public EventPresenter(EventEditView eventEditView) {
        this.eventEditView = eventEditView;

        eventModel = new EventModel(new IModel() {
            @Override
            public void onStart() {
//                eventEditView.setState(Globle.LOADING_STATE);
            }

            @Override
            public void onError() {
//                eventEditView.setState(Globle.LOADING_FAIL);
            }

            @Override
            public void onComplete() {
//                eventEditView.setState(Globle.LOADING_SUCEESS);
            }

            @Override
            public void onNext(String str) {
                eventEditView.editEvent(str);
            }
        });
    }


    public void getFilterEventList(int page, int limit, int flag, String userId, String keyword) {
        eventModel.getFilterEventList(mCompositeSubscription, page, limit, flag, userId, keyword);
    }


    public void eventFileUpload(List<File> filePath) {
        eventModel.eventFileUpload(mCompositeSubscription, filePath);
    }


    public void createEvent(String pId, String Title, String Content, String StartTime, String EndTime, String LaunchPerson, String leaderId, String AssignPerson,
                            String Location, String address, String Upload, String Progress, String Lable, String nextId) {
        eventModel.eventCreate(mCompositeSubscription,
                pId, Title, Content, StartTime, EndTime, LaunchPerson, leaderId, AssignPerson, Location, address, Upload, Progress, Lable, nextId);
    }


    public void editEvent(String pId, String Id, String Title, String Content, String StartTime, String EndTime, String LaunchPerson, String leaderid, String AssignPerson,
                          String Location, String address, String Upload, String Progress, String Lable, String nextId) {
        eventModel.editCreate(mCompositeSubscription,
                pId, Id, Title, Content, StartTime, EndTime, LaunchPerson, leaderid, AssignPerson, Location, address, Upload, Progress, Lable, nextId);
    }


}

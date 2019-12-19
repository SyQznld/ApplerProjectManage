package com.example.q_kang.pmsystem.present.im;

import com.example.q_kang.pmsystem.model.IEventDetailModel;
import com.example.q_kang.pmsystem.model.im.EventDetailModel;
import com.example.q_kang.pmsystem.modules.bean.bean.event.EventData;
import com.example.q_kang.pmsystem.present.BaseMvpPresenter;
import com.example.q_kang.pmsystem.view.CreateEventView;

/**
 * Created by appler on 2018/7/19.
 */

public class EventDetailPresenter extends BaseMvpPresenter {

    private String TAG = "EventDetailPresenter";

    private CreateEventView eventView;

    private EventDetailModel eventDetailModel;

    public EventDetailPresenter(CreateEventView eventView) {
        this.eventView = eventView;

        eventDetailModel = new EventDetailModel(new IEventDetailModel() {
            @Override
            public void createEvent(String string) {
                eventView.createEvent(string);
            }

            @Override
            public void getEventDetail(EventData.DataBean dataBean) {
                eventView.getEventDetail(dataBean);
            }

            @Override
            public void getEventComment(String string) {
                eventView.getEventComment(string);
            }

            @Override
            public void postComment(String string) {
                eventView.postComment(string);
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


    public void eventComplete(String guid) {
        eventDetailModel.eventComplete(mCompositeSubscription, guid);
    }


    public void getEventDetail(String eventId) {
        eventDetailModel.getEventDetail(mCompositeSubscription, eventId);
    }

    public void getEventComment(String eventId) {
        eventDetailModel.getEventComment(mCompositeSubscription, eventId);
    }


    public void createComment(String eventId, String comment, String addUserId,String commentId) {
        eventDetailModel.createComment(mCompositeSubscription, eventId, comment, addUserId,commentId);
    }
}

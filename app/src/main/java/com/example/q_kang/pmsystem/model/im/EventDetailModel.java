package com.example.q_kang.pmsystem.model.im;

import android.util.Log;

import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.IEventDetailModel;
import com.example.q_kang.pmsystem.modules.bean.bean.event.EventData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by appler on 2018/7/19.
 */

public class EventDetailModel {

    private String TAG = "EventDetailModel";

    private BaseRetrofit baseRetrofit = new BaseRetrofit();

    private IEventDetailModel eventDetailModel;

    public EventDetailModel(IEventDetailModel eventDetailModel) {
        this.eventDetailModel = eventDetailModel;
    }

    public void eventComplete(CompositeSubscription mCompositeSubscription, String guid) {

        Log.i(TAG, "eventComplete  guid: " + guid);
        mCompositeSubscription.add(baseRetrofit.getApiService().
                eventComplete(guid)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                        Log.i(TAG, "onCompleted  eventComplete: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError  eventComplete: " + e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Log.i(TAG, "onNext  eventComplete=====: " + string);

                            eventDetailModel.onNext(string);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }));
    }



    public void getEventDetail(CompositeSubscription mCompositeSubscription, String eventId) {

        Log.i(TAG, "getEventDetail  eventId: " + eventId);
        mCompositeSubscription.add(baseRetrofit.getApiService().
                getEventDetail(eventId)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                        Log.i(TAG, "onCompleted  getEventDetail: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError  getEventDetail: " + e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Log.i(TAG, "onNext  getEventDetail=====: " + string);


                            EventData.DataBean dataBean = new Gson().fromJson(string, new TypeToken<   EventData.DataBean>() {
                            }.getType());

                            eventDetailModel.getEventDetail(dataBean);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }));
    }


    public void getEventComment(CompositeSubscription mCompositeSubscription, String eventId) {

        Log.i(TAG, "getEventComment  eventId: " + eventId);
        mCompositeSubscription.add(baseRetrofit.getApiService().
                eventGetComment(eventId)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                        Log.i(TAG, "onCompleted  getEventComment: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError  getEventComment: " + e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Log.i(TAG, "onNext  getEventComment=====: " + string);

                            eventDetailModel.getEventComment(string);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }));
    }



    public void createComment(CompositeSubscription mCompositeSubscription, String eventId,String comment,String addUserId,String commentId) {

        Log.i(TAG, "createComment  eventId: " + eventId);
        Log.i(TAG, "createComment  comment: " + comment);
        Log.i(TAG, "createComment  addUserId: " + addUserId);
        Log.i(TAG, "createComment  commentId: " + commentId);
        mCompositeSubscription.add(baseRetrofit.getApiService().
                eventCreateComment(eventId,comment,addUserId,commentId)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                        Log.i(TAG, "onCompleted  createComment: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError  createComment: " + e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Log.i(TAG, "onNext  createComment=====: " + string);

                            eventDetailModel.postComment(string);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }));
    }
}

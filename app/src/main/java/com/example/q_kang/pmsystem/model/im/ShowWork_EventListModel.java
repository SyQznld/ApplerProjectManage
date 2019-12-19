package com.example.q_kang.pmsystem.model.im;

import android.util.Log;

import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.IWork_EventListModel;
import com.example.q_kang.pmsystem.modules.bean.bean.event.EventData;
import com.example.q_kang.pmsystem.modules.bean.bean.work.WorkData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by appler on 2018/7/17.
 */

public class ShowWork_EventListModel {

    private String TAG = "ShowWork_EventListModel";

    private BaseRetrofit baseRetrofit = new BaseRetrofit();

    private IWork_EventListModel work_eventListModel;

    public ShowWork_EventListModel(IWork_EventListModel work_eventListModel) {
        this.work_eventListModel = work_eventListModel;
    }

    public void getWork_EventList(CompositeSubscription compositeSubscription,String workId){
        compositeSubscription.add(
                baseRetrofit.getApiService().getWork_EventList(workId)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError  getWork_EventList: " + e.getMessage());
                        work_eventListModel.onError();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Log.i(TAG, "onNext  getWork_EventList: " + string);
                            EventData eventData = new Gson().fromJson(string, new TypeToken<EventData>() {
                            }.getType());
                            work_eventListModel.showWork_EventList(eventData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
        );
    }




    public void getWorkDetail(CompositeSubscription compositeSubscription,String workId){
        compositeSubscription.add(
                baseRetrofit.getApiService().getWorkDetail(workId)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError  getWorkDetail: " + e.getMessage());
                                work_eventListModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext  getWorkDetail: " + string);

                                    WorkData.DataBean workData = new Gson().fromJson(string,new TypeToken<WorkData.DataBean>() {
                                    }.getType());
                                    work_eventListModel.getWorkDetail(workData);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
        );
    }
}

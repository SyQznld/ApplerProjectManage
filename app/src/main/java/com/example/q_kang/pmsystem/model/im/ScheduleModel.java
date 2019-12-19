package com.example.q_kang.pmsystem.model.im;

import android.util.Log;

import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.ICreateScheduleModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class ScheduleModel {
    private String TAG = "ScheduleModel";

    private BaseRetrofit baseRetrofit = new BaseRetrofit();

    private ICreateScheduleModel iCreateScheduleModel;

    public ScheduleModel(ICreateScheduleModel iCreateScheduleModel) {
        this.iCreateScheduleModel = iCreateScheduleModel;
    }

    public void createSchedule(CompositeSubscription compositeSubscription,
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

        compositeSubscription.add(
                baseRetrofit.getApiService().createSchedule(

                        title, content, startTime, endTime, isFinish, ItemId, ItemName, ItemType, IsDelete, IsAllDay, AddTime)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                iCreateScheduleModel.onError();
                                Log.i(TAG, "createSchedule onError: " + e.getMessage());

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {

                                    String string = responseBody.string();
                                    Log.i(TAG, " createSchedule  onNext: " + string);
                                    iCreateScheduleModel.createSchedule(string);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iCreateScheduleModel.onError();
                                }
                            }
                        })
        );
    }


    public void editSchedule(CompositeSubscription compositeSubscription,
                             String scheduleId,
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

        Log.i(TAG, "editSchedule scheduleId: " + scheduleId);
        Log.i(TAG, "editSchedule title: " + title);
        Log.i(TAG, "editSchedule startTime: " + startTime);

        compositeSubscription.add(baseRetrofit.getApiService().
                editSchedule(scheduleId, title, content, startTime, endTime, isFinish, ItemId, ItemName, ItemType, IsDelete, IsAllDay, AddTime)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        iCreateScheduleModel.onError();
                        Log.i(TAG, "editSchedule onError: " + e.getMessage());

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {

                            String string = responseBody.string();
                            iCreateScheduleModel.editSchedule(string);

                        } catch (IOException e) {
                            e.printStackTrace();
                            iCreateScheduleModel.onError();
                        }
                    }
                })
        );
    }


    public void getScheduleDatail(CompositeSubscription compositeSubscription, String scheduleId) {
        Log.i(TAG, "getScheduleDatail id: " + scheduleId);


        compositeSubscription.add(
                baseRetrofit.getApiService().getScheduleDetail(scheduleId)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                iCreateScheduleModel.onError();
                                Log.i(TAG, "getScheduleDatail onError: " + e.getMessage());

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {

                                    String string = responseBody.string();
                                    Log.i(TAG, " getScheduleDatail  onNext: " + string);
                                    iCreateScheduleModel.getScheduleDetail(string);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iCreateScheduleModel.onError();
                                }
                            }
                        })
        );
    }


}
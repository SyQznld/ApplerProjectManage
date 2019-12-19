package com.example.q_kang.pmsystem.model.im;


import android.util.Log;

import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.IWorkModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class WorkModel {

    private String TAG = "WorkModel";

    private BaseRetrofit baseRetrofit = new BaseRetrofit();

    private IWorkModel iWorkModel;

    public WorkModel(IWorkModel iWorkModel) {
        this.iWorkModel = iWorkModel;
    }

    //Id Name pId StartTime JoinPersons EndTime LeaderId Content Location AddUserId Progress Lable
    public void createWork(CompositeSubscription compositeSubscription,
                           String Name, String pId, String StartTime,
                           String JoinPersons, String EndTime, String LeaderId,
                           String Content, String Location, String address, String AddUserId,
                           String Progress, String Lable, String nameList, String leaderIdList,String nextid) {

        compositeSubscription.add(baseRetrofit.getApiService().createWork(
                Name, pId, StartTime, JoinPersons, EndTime, LeaderId,
                Content, Location, address, AddUserId, Progress, Lable,
                nameList, leaderIdList,nextid
                )
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "createWork onError: " + e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {

                                    String string = responseBody.string();
                                    Log.i(TAG, " createWork  onNext: " + string);
                                    iWorkModel.onCreateNext(string);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
        );
    }


    public void editWork(CompositeSubscription compositeSubscription,
                         String Id, String Name, String pId, String StartTime,
                         String JoinPersons, String EndTime, String LeaderId,
                         String Content, String Location, String address, String AddUserId,
                         String Progress, String Lable, String nameList, String leaderIdList,String nextId) {

        compositeSubscription.add(baseRetrofit.getApiService().editWork(
                Id, Name, pId, StartTime, JoinPersons, EndTime, LeaderId,
                Content, Location, address, AddUserId, Progress, Lable,
                nameList, leaderIdList,nextId
                )
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {

                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext  editWork: " + string);
                                    iWorkModel.onEditNext(string);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
        );
    }


    public void getEventModels(CompositeSubscription mCompositeSubscription, int page,int limit,String Templet) {
        mCompositeSubscription.add(baseRetrofit.getApiService().getEventModels(page,limit,Templet)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "getEventModels onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String s = responseBody.string();
                            Log.i(TAG, "getEventModels  onNext: " + s);

                            iWorkModel.getEventModels(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }));
    }






    public void getEventListOrNo(CompositeSubscription mCompositeSubscription, String Templet) {
        mCompositeSubscription.add(baseRetrofit.getApiService().getWork_EventList(Templet)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String s = responseBody.string();
                            Log.i(TAG, "getEventListOrNo  onNext: " + s);

                            iWorkModel.getEventList(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }));
    }
}

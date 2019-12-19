package com.example.q_kang.pmsystem.model.im;

import android.util.Log;

import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.IScheduleListModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class ShowScheduleListModel {


    private String TAG = "ShowScheduleListModel";

    private BaseRetrofit baseRetrofit = new BaseRetrofit();


    private IScheduleListModel iModel;

    public ShowScheduleListModel(IScheduleListModel iModel) {
        this.iModel = iModel;
    }

    public void getScheduleList(CompositeSubscription compositeSubscription,String time) {

        Log.i(TAG, "time  getScheduleList: " + time);


        compositeSubscription.add(
                baseRetrofit.getApiService()
                        .getDayScheduleList(time)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                //           iModel.onStart();
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                //         iModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getScheduleList: " + e.getMessage());
                                iModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getScheduleList: " + string);
                                    iModel.getScheduleList(string);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iModel.onError();
                                }


                            }
                        }));
    }




}

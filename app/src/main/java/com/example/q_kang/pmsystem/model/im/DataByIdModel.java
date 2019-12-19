package com.example.q_kang.pmsystem.model.im;

import android.util.Log;

import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.IDataByIdModel;
import com.example.q_kang.pmsystem.modules.bean.bean.AllData;
import com.example.q_kang.pmsystem.modules.bean.bean.Frame;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by appler on 2018/7/23.
 */

public class DataByIdModel {

    private String TAG = "DataByIdModel";

    private IDataByIdModel iModel;
    private BaseRetrofit baseRetrofit = new BaseRetrofit();

    public DataByIdModel(IDataByIdModel iModel) {
        this.iModel = iModel;
    }

    public void getDataById(CompositeSubscription compositeSubscription, String userId) {

        compositeSubscription.add(
                baseRetrofit.getApiService().getDataById(userId)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                iModel.onStart();
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                iModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "getDataById  onError: " + e.getMessage());
                                iModel.onError();

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {

                                    String string = responseBody.string();
                                    Log.i(TAG, " getDataById  onNext: " + string);

                                    AllData allData = new Gson().fromJson(string, new TypeToken<AllData>() {
                                    }.getType());

                                    iModel.getDataById(allData);


                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iModel.onError();
                                }
                            }
                        })
        );
    }

    public void getSearchUserById(CompositeSubscription compositeSubscription, String userId, String role) {

        Log.i(TAG, "getSearchUserById userid: " + userId);
        Log.i(TAG, "getSearchUserById role: " + role);

        compositeSubscription.add(
                baseRetrofit.getApiService().getSearchUserById(userId, role)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "getSearchUserById  onError: " + e.getMessage());

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {

                                    String string = responseBody.string();
                                    Log.i(TAG, " getSearchUserById  onNext: " + string);
                                    List<Frame> frames = new Gson().fromJson(string, new TypeToken<List<Frame>>() {
                                    }.getType());
                                    iModel.showSearchUser(frames);


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
        );
    }
}

package com.example.q_kang.pmsystem.model.im;

import android.util.Log;

import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.IPro_WorkListModel;
import com.example.q_kang.pmsystem.modules.bean.bean.project.ProjectData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by appler on 2018/7/16.
 */

public class ShowPro_WorkListModel {

    private String TAG = "ShowPro_WorkListModel";

    private BaseRetrofit baseRetrofit = new BaseRetrofit();

    private IPro_WorkListModel pro_workListModel;

    public ShowPro_WorkListModel(IPro_WorkListModel pro_workListModel) {
        this.pro_workListModel = pro_workListModel;
    }

    public void getPro_WorkList(CompositeSubscription compositeSubscription, String projectId) {
        Log.i(TAG, "getPro_WorkList: " + projectId);
        compositeSubscription.add(
                baseRetrofit.getApiService().getPro_WorkList(projectId)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                pro_workListModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getPro_WorkList: " + e.getMessage());
                                pro_workListModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext  getPro_WorkList: " + string);

                                    pro_workListModel.showPro_WorkList(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })
        );


    }



    public void getProDetail(CompositeSubscription compositeSubscription, String projectId) {
        Log.i(TAG, "getProDetail: " + projectId);
        compositeSubscription.add(
                baseRetrofit.getApiService().getProDetail(projectId)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                pro_workListModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getProDetail: " + e.getMessage());
                                pro_workListModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext  getProDetail: " + string);
                                    ProjectData.DataBean projectData = new Gson().fromJson(string,new TypeToken<ProjectData.DataBean>() {
                                    }.getType());
                                    pro_workListModel.getProDetail(projectData);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })
        );


    }



}

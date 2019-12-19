package com.example.q_kang.pmsystem.model.im;

import android.util.Log;

import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.IShowWorkModel;
import com.example.q_kang.pmsystem.modules.bean.bean.Frame;
import com.example.q_kang.pmsystem.modules.bean.bean.work.WorkData;
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
 * Created by appler on 2018/7/14.
 */

public class ShowWorkListModel {


    private String TAG = "WorkModel";

    private BaseRetrofit baseRetrofit = new BaseRetrofit();


    private IShowWorkModel iModel;

    public ShowWorkListModel(IShowWorkModel iModel) {
        this.iModel = iModel;
    }

    public void getWorkList(CompositeSubscription compositeSubscription, int page, int limit, int flag,
                            String keyword, String userId, String SearchUserId, String orgId,String Lables) {
        Log.i(TAG, "page  getWorkList: " + page);
        Log.i(TAG, "limit  getWorkList: " + limit);
        Log.i(TAG, "flag  getWorkList: " + flag);
        Log.i(TAG, "keyword  getWorkList: " + keyword);
        Log.i(TAG, "userId  getWorkList: " + userId);
        Log.i(TAG, "SearchUserId  getWorkList: " + SearchUserId);
        Log.i(TAG, "orgId  getWorkList: " + orgId);
        Log.i(TAG, "Lables  getWorkList: " + Lables);

        compositeSubscription.add(
                baseRetrofit.getApiService()
                        .getWorkList(page, limit, flag, keyword, userId, SearchUserId, orgId,Lables)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                //       iModel.onStart();
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                //      iModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                iModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getWorkList: " + string);

                                    WorkData workData = new Gson().fromJson(string, new TypeToken<WorkData>() {
                                    }.getType());
                                    iModel.showWorkList(workData);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iModel.onError();
                                }
                            }
                        }));
    }


    public void getWorkSearchUser(CompositeSubscription compositeSubscription, String userId, String role) {

        compositeSubscription.add(
                baseRetrofit.getApiService()
                        .getSearchUserById(userId, role)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getWorkSearchUser: " + e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getWorkSearchUser: " + string);
                                    if ("".equals(string)) {
                                        string = "[]";
                                    }

                                    List<Frame> frames = new Gson().fromJson(string, new TypeToken<List<Frame>>() {
                                    }.getType());
                                    iModel.showWorkSearchUser(frames);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            }
                        }));
    }


    public void getWorkDepartment(CompositeSubscription compositeSubscription, String userId) {

        compositeSubscription.add(
                baseRetrofit.getApiService()
                        .getDepartment(userId)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getWorkDepartment: " + e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getWorkDepartment: " + string);
                                    iModel.showWorkDepartment(string);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            }
                        }));
    }

}

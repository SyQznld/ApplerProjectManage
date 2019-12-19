package com.example.q_kang.pmsystem.model.im;

import android.util.Log;

import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.IShowProjectListModel;
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


public class ShowProjectListModel {

    private static final String TAG = "ShowProjectListModel";

    private IShowProjectListModel iModel;

    private BaseRetrofit baseRetrofit = new BaseRetrofit();

    public ShowProjectListModel(IShowProjectListModel iModel) {
        this.iModel = iModel;
    }

    public void getProjectList(CompositeSubscription mCompositeSubscription,
                               int page,
                               int limit,
                               int flag,
                               String currUserId,
                               String keyword,
                               String currRole,
                               String SearchUserId,
                               String orgId,
                               String Lables,String userName) {


        Log.i(TAG, "getProjectList  flag: " + flag);
        Log.i(TAG, "getProjectList  currUserId: " + currUserId);
        Log.i(TAG, "getProjectList  keyword: " + keyword);
        Log.i(TAG, "getProjectList  currRole: " + currRole);
        Log.i(TAG, "getProjectList  SearchUserId: " + SearchUserId);
        Log.i(TAG, "getProjectList  org: " + orgId);
        Log.i(TAG, "getProjectList  Lables: " + Lables);

        mCompositeSubscription.add(
                baseRetrofit.getApiService()
                        .getProjectList(
                                page,
                                limit,
                                flag,
                                currUserId,
                                keyword,
                                currRole,
                                SearchUserId,orgId,Lables,userName)
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
                                Log.i(TAG, "onCompleted getProjectList");
                                iModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError  getProjectList: " + e.getMessage());
                                iModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String s = responseBody.string();
                                    Log.i(TAG, "onNext  getProjectList: " + s);
//                                    if ("".equals(s)) {
//                                        s = "{\"count\":0,\"data\":[]}";
//                                    }
//                                    ProjectData projectData = new Gson().fromJson(s, new TypeToken<ProjectData>() {
//                                    }.getType());
                                    iModel.onProjectList(s);


                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iModel.onError();
                                }
                            }
                        }));
    }


    public void getProSearchUser(CompositeSubscription mCompositeSubscription, String userId, String role) {

//        Log.i(TAG, "getProSearchUser  currUserId: " + currUserId);

        mCompositeSubscription.add(
                baseRetrofit.getApiService()
                        .getSearchUserById(userId, role)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                Log.i(TAG, "onCompleted getProSearchUser");
                                iModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError  getProSearchUser: " + e.getMessage());
                                iModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String s = responseBody.string();
                                    Log.i(TAG, "onNext  getProSearchUser: " + s);
//                                    if (!"".equals(s)){
//
//                                    }
                                    List<Frame> frames = new Gson().fromJson(s, new TypeToken<List<Frame>>() {
                                    }.getType());

                                    iModel.showSearchUser(frames);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }));
    }




    public void getProDepartment(CompositeSubscription mCompositeSubscription, String userId) {

//        Log.i(TAG, "getProSearchUser  currUserId: " + currUserId);

        mCompositeSubscription.add(
                baseRetrofit.getApiService()
                        .getDepartment(userId)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                Log.i(TAG, "onCompleted getProDepartment");
                                iModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError  getProDepartment: " + e.getMessage());
                                iModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String s = responseBody.string();
                                    Log.i(TAG, "onNext  getProDepartment: " + s);

                                    iModel.showProDepartment(s);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }));
    }

}

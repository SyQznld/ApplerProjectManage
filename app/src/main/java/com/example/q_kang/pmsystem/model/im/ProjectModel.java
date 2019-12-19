package com.example.q_kang.pmsystem.model.im;

import android.util.Log;

import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.IProjectModel;
import com.example.q_kang.pmsystem.modules.bean.bean.ModelData;
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

public class ProjectModel {

    private String TAG = "ProjectModel";
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private IProjectModel iProjectModel;

    public ProjectModel(IProjectModel iProjectModel) {
        this.iProjectModel = iProjectModel;
    }

    public void createProject(CompositeSubscription compositeSubscription, String json) {

        compositeSubscription.add(
                baseRetrofit.getApiService().createProject(
                        json
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
                                Log.i(TAG, "createProject  onError: " + e.getMessage());

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {

                                    String string = responseBody.string();
                                    Log.i(TAG, " createProject  onNext: " + string);
                                    iProjectModel.createProject(string);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
        );
    }


    public void editProject(CompositeSubscription compositeSubscription,
                            String json) {

        compositeSubscription.add(
                baseRetrofit.getApiService().editProject(json)
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
                                    Log.i(TAG, "onNext  editProject: " + string);
                                    iProjectModel.editProject(string);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
        );
    }



    public void getMobans(CompositeSubscription mCompositeSubscription) {
        mCompositeSubscription.add(baseRetrofit.getApiService().getAllModels()
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
                            Log.i(TAG, "getMobans  onNext: " + s);
//                            iProjectModel.onNext(s);

                            Gson gson = new Gson();
                            List<ModelData> models = gson.fromJson(s, new TypeToken<List<ModelData>>() {
                            }.getType());
                            for (int i = 0; i < models.size(); i++) {

                            }
                            iProjectModel.initMobanData(models);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }));
    }


}

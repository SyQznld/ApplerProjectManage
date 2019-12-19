package com.example.q_kang.pmsystem.model.im;

import android.util.Log;

import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.IMobanModel;
import com.example.q_kang.pmsystem.modules.bean.bean.ModelData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MobanModel {
    private static final String TAG = "MobanModel";

    private IMobanModel iModel;

    private BaseRetrofit retrofit = new BaseRetrofit();

    public MobanModel(IMobanModel iModel) {
        this.iModel = iModel;
    }


    public void getMobans(CompositeSubscription mCompositeSubscription) {
        mCompositeSubscription.add(retrofit.getApiService().getAllModels()
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
                        iModel.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        iModel.onError();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String s = responseBody.string();
                            Log.i(TAG, "getMobans  onNext: " + s);
//                            iModel.onNext(s);

                            Gson gson = new Gson();
                            List<ModelData> models = gson.fromJson(s, new TypeToken<List<ModelData>>() {
                            }.getType());
                            for (int i = 0; i < models.size(); i++) {

                            }
                            iModel.setModelAdapter(models);

                        } catch (IOException e) {
                            e.printStackTrace();
                            iModel.onError();
                        }
                    }
                }));
    }


    public void getEventMobans(CompositeSubscription mCompositeSubscription, int page, int limit, String Templet) {

        mCompositeSubscription.add(
                retrofit.getApiService().getEventModels(page, limit, Templet)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {

//                                iModel.onStart();
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                iModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, " getEventMobans  onNext: " + e.getMessage());
                                iModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String s = responseBody.string();
                                    Log.i(TAG, " getEventMobans  onNext: " + s);
                                    iModel.showEventModel(s);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iModel.onError();
                                }
                            }
                        }));
    }

    public void loadModel(CompositeSubscription mCompositeSubscription
            , String id
            , String name
            , String oldname
            , Integer[] sortList
            , String[] cNameList) {
        mCompositeSubscription.add(
                (Subscription) retrofit.getApiService().loadModel(
                        id,
                        name,
                        oldname,
                        sortList,
                        cNameList)
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
                                    String s = responseBody.string();
                                    Log.i(TAG, "onNext: " + id);
                                    Log.i(TAG, "onNext: " + name);
                                    Log.i(TAG, "onNext: " + oldname);
                                    Log.i(TAG, "onNext: " + Arrays.toString(sortList));
                                    Log.i(TAG, "onNext: " + Arrays.toString(cNameList));
                                    Log.i(TAG, "onNext: " + s);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }));
    }


    public void loadEventModel(CompositeSubscription mCompositeSubscription
            , String id
            , String name
            , String[] cNameList) {
        mCompositeSubscription.add(
                (Subscription) retrofit.getApiService().createEventModel(
                        id,
                        name,
                        cNameList)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                iModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "loadEventModel onError: " + e.getMessage());
                                iModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String s = responseBody.string();
                                    Log.i(TAG, " loadEventModel onNext: " + id);
                                    Log.i(TAG, " loadEventModel onNext: " + name);
                                    Log.i(TAG, "loadEventModel onNext: " + Arrays.toString(cNameList));
                                    Log.i(TAG, "loadEventModel onNext: " + s);

                                    iModel.loadEventModel(s);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }));
    }

}

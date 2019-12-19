package com.example.q_kang.pmsystem.model.im;

import android.util.Log;

import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.IDocumentListModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by appler on 2018/8/10.
 */

public class ShowDocuListModel {


    private String TAG = "ShowDocuListModel";

    private BaseRetrofit baseRetrofit = new BaseRetrofit();


    private IDocumentListModel iModel;

    public ShowDocuListModel(IDocumentListModel iModel) {
        this.iModel = iModel;
    }

    public void getSendDocumentsList(CompositeSubscription compositeSubscription,String userId,boolean IsSend) {

        Log.i(TAG, "keyword  getDocumentsList: " + userId);


        compositeSubscription.add(
                baseRetrofit.getApiService()
                        .getSendDocuList(userId,IsSend)
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
                                Log.i(TAG, "onError getDocumentsList: " + e.getMessage());
                                iModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getDocumentsList: " + string);
                                    iModel.showSendDocList(string);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iModel.onError();
                                }


                            }
                        }));
    }


    public void getReceiveDocumentsList(CompositeSubscription compositeSubscription,String userId) {

        Log.i(TAG, "keyword  getReceiveDocumentsList: " + userId);


        compositeSubscription.add(
                baseRetrofit.getApiService()
                        .getReceiveDocList(userId)
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
                                Log.i(TAG, "onError getReceiveDocumentsList: " + e.getMessage());
                                iModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getReceiveDocumentsList: " + string);
                                    iModel.showReceiveDocList(string);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iModel.onError();
                                }


                            }
                        }));
    }

}

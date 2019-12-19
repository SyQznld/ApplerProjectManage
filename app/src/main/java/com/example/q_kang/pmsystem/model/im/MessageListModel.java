package com.example.q_kang.pmsystem.model.im;

import android.util.Log;

import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.IMessageListModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by appler on 2018/9/3.
 */

public class MessageListModel {

    private String TAG = "MessageListModel";
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private IMessageListModel iMessageListModel;


    public MessageListModel(IMessageListModel iMessageListModel) {
        this.iMessageListModel = iMessageListModel;
    }

    public void getMessagesList(CompositeSubscription compositeSubscription, String userId) {
        Log.i(TAG, "userId  getMessagesList: " + userId);


        compositeSubscription.add(
                baseRetrofit.getApiService()
                        .getMessageList(userId)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
//                                iMessageListModel.onStart();
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
//                                iMessageListModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getMessagesList: " + e.getMessage());
                                iMessageListModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getMessagesList: " + string);

                                    iMessageListModel.getMessageList(string);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iMessageListModel.onError();
                                }


                            }
                        }));
    }


    public void deleteAllMessage(CompositeSubscription compositeSubscription, String userId,String type) {
        Log.i(TAG, "userId  deleteAllMessage: " + userId);
        Log.i(TAG, "type  deleteAllMessage: " + type);


        compositeSubscription.add(
                baseRetrofit.getApiService()
                        .deleteMessage(userId,type)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
//                                iMessageListModel.onStart();
                            }
                        })
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
//                                iMessageListModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError deleteAllMessage: " + e.getMessage());
                                iMessageListModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext deleteAllMessage: " + string);

                                    iMessageListModel.deleteAllMessage(string);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iMessageListModel.onError();
                                }


                            }
                        }));
    }
}

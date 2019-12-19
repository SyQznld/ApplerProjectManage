package com.example.q_kang.pmsystem.model.im;

import android.util.Log;

import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.IUpdateMessageModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by appler on 2018/9/10.
 */

public class MessageUpdateModel {


    private String TAG = "MessageUpdateModel";

    private BaseRetrofit baseRetrofit = new BaseRetrofit();

    private IUpdateMessageModel iUpdateMessageModel;

    public MessageUpdateModel(IUpdateMessageModel iUpdateMessageModel) {
        this.iUpdateMessageModel = iUpdateMessageModel;
    }


    public void updateMessageState(CompositeSubscription compositeSubscription, String id) {

        compositeSubscription.add(
                baseRetrofit.getApiService().
                        updateMessage(id)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                iUpdateMessageModel.onError();
                                Log.i(TAG, "updateMessageState onError: " + e.getMessage());

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {

                                    String string = responseBody.string();
                                    Log.i(TAG, " updateMessageState  onNext: " + string);
                                    iUpdateMessageModel.updateMessage(string);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iUpdateMessageModel.onError();
                                }
                            }
                        })
        );
    }



    public void deleteMessage(CompositeSubscription compositeSubscription, String userId,String type) {

        compositeSubscription.add(
                baseRetrofit.getApiService().
                        deleteMessage(userId,type)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                iUpdateMessageModel.onError();
                                Log.i(TAG, "deleteMessage onError: " + e.getMessage());

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {

                                    String string = responseBody.string();
                                    Log.i(TAG, " deleteMessage  onNext: " + string);
                                    iUpdateMessageModel.deleteMessage(string);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iUpdateMessageModel.onError();
                                }
                            }
                        })
        );
    }
}

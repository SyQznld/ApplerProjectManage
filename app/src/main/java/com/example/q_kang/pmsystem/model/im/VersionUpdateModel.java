package com.example.q_kang.pmsystem.model.im;

import android.util.Log;

import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.IVersionUpdateModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by appler on 2018/9/14.
 */

public class VersionUpdateModel {
    private String TAG = "VersionUpdateModel";
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private IVersionUpdateModel iVersionUpdateModel;

    public VersionUpdateModel(IVersionUpdateModel iVersionUpdateModel) {
        this.iVersionUpdateModel = iVersionUpdateModel;
    }


    public void versionUpdate(CompositeSubscription mCompositeSubscription,String Version) {
        mCompositeSubscription.add(baseRetrofit.getApiService().versionUpdate(Version)
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
                        Log.i(TAG, "versionUpdate onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String s = responseBody.string();
                            Log.i(TAG, "versionUpdate  onNext: " + s);

                            iVersionUpdateModel.versionUpdate(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }));
    }




    public void resetPassword(CompositeSubscription mCompositeSubscription,String oldpwd, String newpwd, String tnewpwd, String userId) {
        mCompositeSubscription.add(baseRetrofit.getApiService().resetPassword(oldpwd,newpwd,tnewpwd,userId)
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
                        Log.i(TAG, "resetPassword onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String s = responseBody.string();
                            Log.i(TAG, "resetPassword  onNext: " + s);

                            iVersionUpdateModel.resetPassword(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }));
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
                                iVersionUpdateModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getMessagesList: " + string);

                                    iVersionUpdateModel.getMessage(string);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iVersionUpdateModel.onError();
                                }


                            }
                        }));
    }
}

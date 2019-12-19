package com.example.q_kang.pmsystem.model.im;

import android.util.Log;

import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.INewsDetailModel;

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

public class NewsDetailModel {

    private String TAG = "NewsDetailModel";

    private BaseRetrofit baseRetrofit = new BaseRetrofit();


    private INewsDetailModel iModel;

    public NewsDetailModel(INewsDetailModel iModel) {
        this.iModel = iModel;
    }

    public void getNewsDetail(CompositeSubscription compositeSubscription, String newsId) {
        Log.i(TAG, "keyword  getNewsDetail: " + newsId);


        compositeSubscription.add(
                baseRetrofit.getApiService()
                        .getNewsDetail(newsId)
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
                                Log.i(TAG, "onError getNewsDetail: " + e.getMessage());
                                iModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getNewsDetail: " + string);

                                   iModel.getNewsDetail(string);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iModel.onError();
                                }


                            }
                        }));
    }

}

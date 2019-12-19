package com.example.q_kang.pmsystem.model.im;

import android.util.Log;

import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.INewsListModel;
import com.example.q_kang.pmsystem.modules.bean.bean.NewsData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by appler on 2018/7/27.
 */

public class NewsListModel {

    private String TAG = "NewsListModel";

    private BaseRetrofit baseRetrofit = new BaseRetrofit();


    private INewsListModel iModel;

    public NewsListModel(INewsListModel iModel) {
        this.iModel = iModel;
    }

    public void getNewsList(CompositeSubscription compositeSubscription, int page, int limit, String keyword) {
        Log.i(TAG, "page  getNewsList: " + page);
        Log.i(TAG, "limit  getNewsList: " + limit);
        Log.i(TAG, "keyword  getNewsList: " + keyword);


        compositeSubscription.add(
                baseRetrofit.getApiService()
                        .getNewsList(page, limit, keyword)
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
                                Log.i(TAG, "onError getNewsList: " + e.getMessage());
                                iModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getNewsList: " + string);

                                    NewsData newsData = new Gson().fromJson(string, new TypeToken<NewsData>() {
                                    }.getType());
                                    iModel.showNewsList(newsData);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iModel.onError();
                                }


                            }
                        }));
    }


}

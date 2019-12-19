package com.example.q_kang.pmsystem.model.im;

import android.util.Log;

import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.IGlobalSearchModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;



public class GlobalSearchModel {

    private String TAG = "GlobalSearchModel";
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private IGlobalSearchModel iGlobalSearchModel;

    public GlobalSearchModel(IGlobalSearchModel iGlobalSearchModel) {
        this.iGlobalSearchModel = iGlobalSearchModel;
    }

    public void showGlobalSearch(CompositeSubscription compositeSubscription, int flag,String keyword,String state,String userId) {
        Log.i(TAG, "showGlobalSearch  flag: " + flag);
        Log.i(TAG, "showGlobalSearch  keyword: " + keyword);
        Log.i(TAG, "showGlobalSearch  state: " + state);
        Log.i(TAG, "showGlobalSearch  userId: " + userId);
        compositeSubscription.add(
                baseRetrofit.getApiService()
                        .getGlobalSearch(flag,keyword,state,userId)

                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError  showGlobalSearch: " + e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext showGlobalSearch: " + string);

                                    iGlobalSearchModel.showGlobalSearch(string);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            }
                        }));
    }
}

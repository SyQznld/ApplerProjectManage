package com.example.q_kang.pmsystem.model.im;

import android.util.Log;

import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.IDeleteModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by appler on 2018/8/28.
 */

public class DeleteModel {
    private String TAG = "DeleteModel";

    private IDeleteModel iModel;
    private BaseRetrofit baseRetrofit = new BaseRetrofit();

    public DeleteModel(IDeleteModel iModel) {
        this.iModel = iModel;
    }

    public void deleteListItem(CompositeSubscription compositeSubscription, String id,String type) {

        compositeSubscription.add(
                baseRetrofit.getApiService().deleteListItem(id,type)
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
                                iModel.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "deleteListItem  onError: " + e.getMessage());
                                iModel.onError();

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {

                                    String string = responseBody.string();
                                    Log.i(TAG, " deleteListItem  onNext: " + string);

                                 iModel.deleteItem(string);


                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iModel.onError();
                                }
                            }
                        })
        );
    }

}

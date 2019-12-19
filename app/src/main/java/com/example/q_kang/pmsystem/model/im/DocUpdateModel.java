package com.example.q_kang.pmsystem.model.im;

import android.util.Log;

import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.IDocUpdateModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by appler on 2018/8/17.
 */

public class DocUpdateModel {

    private String TAG = "DocUpdateModel";

    private BaseRetrofit baseRetrofit = new BaseRetrofit();

    private IDocUpdateModel iDocumentModel;

    public DocUpdateModel(IDocUpdateModel iDocumentModel) {
        this.iDocumentModel = iDocumentModel;
    }


    public void updateDocState(CompositeSubscription compositeSubscription, String id, boolean isRead) {

        compositeSubscription.add(
                baseRetrofit.getApiService().
                        updateReceiveDoc(id, isRead)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                iDocumentModel.onError();
                                Log.i(TAG, "updateDocState onError: " + e.getMessage());

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {

                                    String string = responseBody.string();
                                    Log.i(TAG, " updateDocState  onNext: " + string);
                                    iDocumentModel.updateDocState(string);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    iDocumentModel.onError();
                                }
                            }
                        })
        );
    }
}

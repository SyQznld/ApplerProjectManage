package com.example.q_kang.pmsystem.model.im;

import android.util.Log;

import com.example.q_kang.pmsystem.base.BaseRetrofit;
import com.example.q_kang.pmsystem.model.IModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class FrameModel {
    private static final String TAG = "BaseModel";
    private BaseRetrofit retrofit = new BaseRetrofit();
    private IModel iModel;

    public FrameModel(IModel iModel) {
        this.iModel = iModel;
    }

    public void getFrames(CompositeSubscription mCompositeSubscription) {
        mCompositeSubscription.add(retrofit.getApiService().getAllUsers()
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
                        Log.i(TAG, "getFrames  onError: " + e.getMessage());
                        iModel.onError();
                    }

                    @Override
                    public void onNext(ResponseBody frame) {
                        try {
                            String string = frame.string();
                            Log.i(TAG, "getFrames   onNext: " + string);
                            iModel.onNext(string);
                        } catch (IOException e) {
                            e.printStackTrace();
                            iModel.onError();
                        }
                    }
                })
        );
    }
}

package com.example.q_kang.pmsystem.model;

/**
 * Created by Q-Kang on 2018/6/28.
 */

public interface IModel {

    void onStart();

    void onError();

    void onComplete();

    void onNext(String str);



}

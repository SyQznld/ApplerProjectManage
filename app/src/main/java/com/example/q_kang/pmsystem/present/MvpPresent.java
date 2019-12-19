package com.example.q_kang.pmsystem.present;

import android.support.annotation.UiThread;

public interface MvpPresent<V> {

    /**
     * 绑定视图
     * */
    @UiThread
    void attachView(V view);


    /**
     * 解除绑定（每个v记得使用完之后解绑，用户防止内存泄漏问题）
     * */
    @UiThread
    void detachView();

}

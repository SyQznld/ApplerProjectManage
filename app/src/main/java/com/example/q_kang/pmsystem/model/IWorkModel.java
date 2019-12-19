package com.example.q_kang.pmsystem.model;

/**
 * Created by appler on 2018/7/13.
 */

public interface IWorkModel extends IModel{

    void onCreateNext(String str);

    void onEditNext(String str);

    void getEventModels(String string);

    void getEventList(String string);
}

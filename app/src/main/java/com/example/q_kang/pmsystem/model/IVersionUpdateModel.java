package com.example.q_kang.pmsystem.model;

/**
 * Created by appler on 2018/9/14.
 */

public interface IVersionUpdateModel  extends IModel{
    void versionUpdate(String string);

    void resetPassword(String string);


    void getMessage(String string);
}

package com.example.q_kang.pmsystem.model;

/**
 * Created by appler on 2018/9/10.
 */

public interface IUpdateMessageModel extends IModel{

    void updateMessage(String string);

    void deleteMessage(String string);
}

package com.example.q_kang.pmsystem.model;

/**
 * Created by appler on 2018/9/3.
 */

public interface IMessageListModel extends IModel{

    void getMessageList(String string);

    void deleteAllMessage(String string);
}

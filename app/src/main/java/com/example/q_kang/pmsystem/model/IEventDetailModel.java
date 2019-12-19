package com.example.q_kang.pmsystem.model;

import com.example.q_kang.pmsystem.modules.bean.bean.event.EventData;

/**
 * Created by appler on 2018/7/19.
 */

public interface IEventDetailModel extends IModel{

    void createEvent(String string);


    void getEventDetail(EventData.DataBean dataBean);


    void getEventComment(String string);

    void postComment(String string);
}

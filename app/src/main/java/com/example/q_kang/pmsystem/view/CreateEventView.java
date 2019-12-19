package com.example.q_kang.pmsystem.view;

import com.example.q_kang.pmsystem.modules.bean.bean.event.EventData;

/**
 * Created by appler on 2018/7/6.
 */

public interface CreateEventView extends BaseView{

    //void returnFilePath(List<ReturnFilePath> returnFilePathList);


    void createEvent(String string);


    void getEventDetail(EventData.DataBean dataBean);


    void getEventComment(String string);

    void postComment(String string);

}

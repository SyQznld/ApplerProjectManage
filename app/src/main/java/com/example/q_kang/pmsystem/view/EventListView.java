package com.example.q_kang.pmsystem.view;

import com.example.q_kang.pmsystem.modules.bean.bean.Frame;
import com.example.q_kang.pmsystem.modules.bean.bean.event.EventData;

import java.util.List;


public interface EventListView extends BaseView{
    void showEventList(EventData eventData);

    void showEventSearchUser(List<Frame> frameList);


    void showEventDepartment(String string);
}
